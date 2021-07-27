package com.netspam.model.lsa;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.vector.DoubleVector;
import edu.ucla.sspace.vector.SparseVector;
import edu.ucla.sspace.vector.Vector;

public class DocumentVectorBuilder {

    /**
     * The base prefix for all properties.
     */
    private static final String PROPERTY_PREFIX =
        "edu.ucla.sspace.common.DocumentVectorBuilder";

    /**
     * The property to specify if term frequencies should be used when combining
     * term vectors.
     */
    public static final String USE_TERM_FREQUENCIES_PROPERTY =
        PROPERTY_PREFIX + ".usetf";

    /**
     * The {@code SemanticSpace} which will provide a {@code Vector} for each
     * word found in a document.
     */
    private final SemanticSpace sspace;

    private final boolean useTermFreq;

    /**
     * Creates a {@code DocumentVectorBuilder} from a {@code SemanticSpace} and
     * extracts options from the system wide {@code Properties}.
     */
    public DocumentVectorBuilder(SemanticSpace baseSpace) {
        this(baseSpace, System.getProperties());
    }

    /**
     * Creates a {@code DocumentVectorBuilder} from a {@code SemanticSpace} and
     * extracts options from the given {@code Properties}.
     */
    public DocumentVectorBuilder(SemanticSpace baseSpace, Properties props) {
        sspace = baseSpace;
        useTermFreq = props.getProperty(USE_TERM_FREQUENCIES_PROPERTY) != null;
    }

    /**
     * Represent a document as the summation of term Vectors.
     *
     * @param documentVector A {@code Vector} which has been pre-allocated to
     *                       store the document's representation.  This is
     *                       pre-allocated so that users of {@code
     *                       DocumentVectorBuilder} can decide what type of
     *                       {@code Vector} should be used to represent a
     *                       document.
     *
     * @return {@code documentVector} after it has been modified to represent
     *         the terms in {@code document}.
     */
    public DoubleVector buildVector(Collection<String> articleTokens,
                              DoubleVector documentVector) {
        // Tokenize and determine what words exist in the document, along with
        // the requested meta information, such as a term frequency.
        Map<String, Integer> termCounts = new HashMap<String, Integer>();
        for (String term : articleTokens) {
            Integer count = termCounts.get(term);
            termCounts.put(term, (count == null || !useTermFreq)
                                 ? 1 : count.intValue() + 1);
        }

        // Iterate through each term in the document and sum the term Vectors
        // found in the provided SemanticSpace.
        for (Map.Entry<String, Integer> entry : termCounts.entrySet()) {
            Vector termVector = sspace.getVector(entry.getKey());
            if (termVector == null) {
				continue;
			}
            add(documentVector, termVector, entry.getValue());
        }

        return documentVector;
    }

    public void add(DoubleVector dest, Vector src, int factor) {
        if (src instanceof SparseVector) {
            int[] nonZeros = ((SparseVector) src). getNonZeroIndices();
            for (int i : nonZeros) {
				dest.add(i, src.getValue(i).doubleValue());
			}
        } else {
            for (int i = 0; i < src.length(); ++i) {
				dest.add(i, src.getValue(i).doubleValue());
			}
        }
    }
}