package com.netspam.model.lsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.matrix.LogEntropyTransform;
import edu.ucla.sspace.matrix.Matrices;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixBuilder;
import edu.ucla.sspace.matrix.SVD;
import edu.ucla.sspace.matrix.Transform;
import edu.ucla.sspace.text.IteratorFactory;
import edu.ucla.sspace.util.SparseArray;
import edu.ucla.sspace.util.SparseIntHashArray;
import edu.ucla.sspace.vector.DoubleVector;
import edu.ucla.sspace.vector.Vector;

public class LatentSemanticAnalysis implements SemanticSpace, Serializable {

    private static final long serialVersionUID = 220l;

    /**
     * The prefix for naming publically accessible properties
     */
    private static final String PROPERTY_PREFIX =
        "edu.ucla.sspace.lsa.LatentSemanticAnalysis";

    /**
     * The property to define the {@link Transform} class to be used
     * when processing the space after all the documents have been seen.
     */
    public static final String MATRIX_TRANSFORM_PROPERTY =
        PROPERTY_PREFIX + ".transform";

    /**
     * The property to set the number of dimension to which the space should be
     * reduced using the SVD
     */
    public static final String LSA_DIMENSIONS_PROPERTY =
        PROPERTY_PREFIX + ".dimensions";

    /**
     * The property to set the specific SVD algorithm used by an instance during
     * {@code processSpace}.  The value should be the name of a {@link
     * edu.ucla.sspace.matrix.SVD.Algorithm}.  If this property is unset, any
     * available algorithm will be used according to the ordering defined in
     * {@link SVD}.
     */
    public static final String LSA_SVD_ALGORITHM_PROPERTY =
        PROPERTY_PREFIX + ".svd.algorithm";

    /**
     * The name prefix used with {@link #getName()}
     */
    private static final String LSA_SSPACE_NAME =
        "lsa-semantic-space";

    /**
     * The logger used to record all output
     */
    private static final Logger LSA_LOGGER =
        Logger.getLogger(LatentSemanticAnalysis.class.getName());

    /**
     * A mapping from a word to the row index in the that word-document matrix
     * that contains occurrence counts for that word.
     */
    private final ConcurrentMap<String,Integer> termToIndex;

    /**
     * The counter for recording the current, largest word index in the
     * word-document matrix.
     */
    private final AtomicInteger termIndexCounter;

    /**
     * The builder used to construct the term-document matrix as new documents
     * are processed.
     */
    private final MatrixBuilder termDocumentMatrixBuilder;

    /**
     * The word space of the LSA model, which is the left factor matrix of the
     * SVD of the word-document matrix.  This matrix is only available after the
     * {@link #processSpace(Properties) processSpace} method has been called.
     */
    private Matrix wordSpace;

    /**
     * The document space of the LSA model, which is the right factor matrix of
     * the SVD of the word-document matrix.  This matrix is only available after
     * the {@link #processSpace(Properties) processSpace} method has been
     * called.
     */
    private Matrix documentSpace;

    /**
     * Constructs the {@code LatentSemanticAnalysis} using the system properties
     * for configuration.
     *
     * @throws IOException if this instance encounters any errors when creatng
     *         the backing array files required for processing
     */
    public LatentSemanticAnalysis() throws IOException {
        this(System.getProperties());
    }

    /**
     * Constructs the {@code LatentSemanticAnalysis} using the specified
     * properties for configuration.
     *
     * @throws IOException if this instance encounters any errors when creatng
     *         the backing array files required for processing
     */
    public LatentSemanticAnalysis(Properties properties) throws IOException {
        termToIndex = new ConcurrentHashMap<String,Integer>();
        termIndexCounter = new AtomicInteger(0);

        termDocumentMatrixBuilder = Matrices.getMatrixBuilderForSVD();

        wordSpace = null;
        documentSpace = null;
    }

    @Override
    public void processDocument(BufferedReader document)
    	throws IOException
    {
        Iterator<String> documentTokens = IteratorFactory.tokenize(document);
        Collection<String> tokens = new ArrayList<String>();
        while (documentTokens.hasNext()) {
        	tokens.add(documentTokens.next());
        }
        document.close();

    	processDocument(tokens);
    }

    /**
     * <p>Parses the document.</p>
     * <p>This method is thread-safe and may be called in parallel with separate
     * documents to speed up overall processing time.</p>
     */
    public void processDocument(Collection<String> documentTokens) {
        // Create a mapping for each term that is seen in the document to the
        // number of times it has been seen.  This mapping would more elegantly
        // be a SparseArray<Integer> however, the length of the sparse array
        // isn't known ahead of time, which prevents it being used by the
        // MatrixBuilder.  Note that the SparseArray implementation would also
        // incur an additional performance hit since each word would have to be
        // converted to its index form for each occurrence, which results in a
        // double Map look-up.
        Map<String,Integer> termCounts = new HashMap<String,Integer>(1000);

        // for each word in the text document, keep a count of how many times it
        // has occurred
        for (String word : documentTokens) {
            // Skip added empty tokens for words that have been filtered out
            if (word.equals(IteratorFactory.EMPTY_TOKEN)) {
				continue;
			}

            // Add the term to the total list of terms to ensure it has a proper
            // index.  If the term was already added, this method is a no-op
            addTerm(word);
            Integer termCount = termCounts.get(word);

            // update the term count
            termCounts.put(word, (termCount == null)
                           ? 1
                           : 1 + termCount.intValue());
        }

        // Check that we actually loaded in some terms before we increase the
        // documentIndex.  This could possibly save some dimensions in the final
        // array for documents that were essentially blank.  If we didn't see
        // any terms, just perform no updates.
        if (termCounts.isEmpty()) {
			return;
		}

        // Get the total number of terms encountered so far, including any new
        // unique terms found in the most recent document
        int totalNumberOfUniqueWords = termIndexCounter.get();

        // Convert the Map count to a SparseArray
        SparseArray<Integer> documentColumn =
            new SparseIntHashArray(totalNumberOfUniqueWords);
        for (Map.Entry<String,Integer> e : termCounts.entrySet()) {
			documentColumn.set(termToIndex.get(e.getKey()), e.getValue());
		}

        // Update the term-document matrix with the results of processing the
        // document.
        termDocumentMatrixBuilder.addColumn(documentColumn);
    }

    /**
     * Adds the term to the list of terms and gives it an index, or if the term
     * has already been added, does nothing.
     */
    private void addTerm(String term) {
        Integer index = termToIndex.get(term);

        if (index == null) {

            synchronized(this) {
                // recheck to see if the term was added while blocking
                index = termToIndex.get(term);
                // if some other thread has not already added this term while
                // the current thread was blocking waiting on the lock, then add
                // it.
                if (index == null) {
                    index = Integer.valueOf(termIndexCounter.getAndIncrement());
                    termToIndex.put(term, index);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getWords() {
        return Collections.unmodifiableSet(termToIndex.keySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getVector(String word) {
        // determine the index for the word
        Integer index = termToIndex.get(word);

        return (index == null)
            ? null
            : wordSpace.getRowVector(index.intValue());
    }

    /**
     * Returns the semantics of the document as represented by a numeric vector.
     * Note that document semantics are represented in an entirely different
     * space, so the corresponding semantic dimensions in the word space will be
     * completely unrelated.  However, document vectors may be compared to find
     * those document with similar content.<p>
     *
     * Similar to {@code getVector}, this method is only to be used after
     * {@code processSpace} has been called.<p>
     *
     * Implementation note: If a specific document ordering is needed, caution
     * should be used when using this class in a multi-threaded environment.
     * Beacuse the document number is based on what order it was
     * <i>processed</i>, no guarantee is made that this will correspond with the
     * original document ordering as it exists in the corpus files.  However, in
     * a single-threaded environment, the ordering will be preserved.
     *
     * @param documentNumber the number of the document according to when it was
     *        processed
     *
     * @return the semantics of the document in the document space
     */
    public DoubleVector getDocumentVector(int documentNumber) {
        if (documentNumber < 0 || documentNumber >= documentSpace.rows()) {
            throw new IllegalArgumentException(
                    "Document number is not within the bounds of the number of "
                    + "documents: " + documentNumber);
        }
        return documentSpace.getRowVector(documentNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpaceName() {
        return LSA_SSPACE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVectorLength() {
    	return wordSpace.getRowVector(0).length();
//        return wordSpace.columns();
    }

    /**
     * {@inheritDoc}
     *
     * @param properties {@inheritDoc} See this class's {@link
     *        LatentSemanticAnalysis javadoc} for the full list of supported
     *        properties.
     */
    @Override
    public void processSpace(Properties properties) {
        try {
            // first ensure that we are no longer writing to the matrix
            termDocumentMatrixBuilder.finish();

            Transform transform = new LogEntropyTransform();

            String transformClass =
            properties.getProperty(MATRIX_TRANSFORM_PROPERTY);
            if (transformClass != null) {
                try {
                    Class clazz = Class.forName(transformClass);
                    transform = (Transform)(clazz.newInstance());
                }
                // perform a general catch here due to the number of possible
                // things that could go wrong.  Rethrow all exceptions as an
                // error.
                catch (Exception e) {
                    throw new Error(e);
                }
            }

            LSA_LOGGER.info("performing " + transform + " transform");

                // Get the finished matrix file from the builder
            File termDocumentMatrix = termDocumentMatrixBuilder.getFile();
            if (LSA_LOGGER.isLoggable(Level.FINE)) {
                LSA_LOGGER.fine("stored term-document matrix in format " +
                        termDocumentMatrixBuilder.getMatrixFormat()
                        + " at " + termDocumentMatrix.getAbsolutePath());
            }

            // Convert the raw term counts using the specified transform
            File transformedMatrix = transform.transform(termDocumentMatrix,
                    termDocumentMatrixBuilder.getMatrixFormat());

            if (LSA_LOGGER.isLoggable(Level.FINE)) {
                LSA_LOGGER.fine("transformed matrix to " +
                        transformedMatrix.getAbsolutePath());
            }

            int dimensions = 300; // default
            String userSpecfiedDims =
            properties.getProperty(LSA_DIMENSIONS_PROPERTY);
            if (userSpecfiedDims != null) {
                try {
                    dimensions = Integer.parseInt(userSpecfiedDims);
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException(
                            LSA_DIMENSIONS_PROPERTY + " is not an integer: " +
                            userSpecfiedDims);
                }
            }

            LSA_LOGGER.info("reducing to " + dimensions + " dimensions");

            // Determine whether the user specified any specific SVD algorithm
            // or whether the fastest available should be used.
            String svdProp = properties.getProperty(LSA_SVD_ALGORITHM_PROPERTY);
            SVD.Algorithm alg = (svdProp == null)
                ? SVD.Algorithm.ANY
                : SVD.Algorithm.valueOf(svdProp);

            // Compute SVD on the pre-processed matrix.
            Matrix[] usv = SVD.svd(transformedMatrix, alg,
                    termDocumentMatrixBuilder.getMatrixFormat(),
                    dimensions);

            // Load the left factor matrix, which is the word semantic space
            wordSpace = usv[0];
            // We transpose the document space to provide easier access to the
            // document vectors, which in the un-transposed version are the
            // columns.  NOTE: if the Matrix interface ever adds a getColumn()
            // method, it might be better to use that instead.
            documentSpace = Matrices.transpose(usv[2]);
        } catch (IOException ioe) {
            //rethrow as Error
            throw new IOError(ioe);
        }
    }
}