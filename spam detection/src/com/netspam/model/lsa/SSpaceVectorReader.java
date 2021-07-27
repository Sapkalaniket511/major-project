package com.netspam.model.lsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import edu.ucla.sspace.common.SemanticSpace;
import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.sparse.SparseVector;

public class SSpaceVectorReader
extends VectorReader
{
private final SemanticSpace sspace;
private final DocumentVectorBuilder builder;

/**
 * Create a vector source for an existing semantic space. To build such a space, you best
 * use the LsiIndexWriter from DKPro IR.
 *
 * @param aSSpace An existing semantic space (usually an *.sspace file)
 */
public SSpaceVectorReader(SemanticSpace aSSpace)
{
	sspace = aSSpace;
	builder = new DocumentVectorBuilder(aSSpace);
}

/**
 * Creates a new semantic space from scratch based on all "txt" documents in the specified path.
 * If you want to have full control over the tokenization, normalization, stop word removal
 * and such, you should build the semantic space yourself and then use the other constructor.
 *
 * @param aDirectory Path to the document collection
 * @see #createSemanticSpace
 */
public SSpaceVectorReader(File aDirectory)
	throws IOException
{
	this(createSemanticSpace(aDirectory, -1));
}

@Override
public Vector getVector(String aTerm)
	throws SimilarityException
{
	Vector vec1 = new SparseVector(getConceptCount());
	builder.buildVector(asList(aTerm), VectorAdapter.create(vec1));
	return vec1;
}

@Override
public int getConceptCount()
	throws SimilarityException
{
	// Nasty stack-overflow bug in getVectorLength()!
	return sspace.getVector(sspace.getWords().iterator().next()).length();
}

@Override
public String getId()
{
	return sspace.getSpaceName();
}

@Override
public void close()
{
	// Nothing to do
}

/**
 * Create a LSA space from all "txt" files in the specified directory.
 *
 * @param aInputDir directory containing the input files.
 * @param aMaxDimensions maximum number of dimensions. If 0 or negative, 300 is used, which has
 * been determined as a good default for LSA models. If the directory contains less than 300
 * documents, the number of documents is used as the number of dimensions.
 * @return a semantic space.
 * @throws IOException
 */
public static SemanticSpace createSemanticSpace(File aInputDir, int aMaxDimensions)
	throws IOException
{
	LatentSemanticAnalysis sspace = new LatentSemanticAnalysis();

	Collection<File> documents = FileUtils.listFiles(aInputDir, new String[] { "txt" }, true);

	for (File document : documents) {
		BufferedReader reader = new BufferedReader(new FileReader(document));
		sspace.processDocument(reader);
	}

	int dimensions = Math.min(documents.size(), aMaxDimensions <= 0 ? 300 : aMaxDimensions);

	Properties props = new Properties();
	props.setProperty(LatentSemanticAnalysis.LSA_DIMENSIONS_PROPERTY, Integer.toString(dimensions));
	sspace.processSpace(props);

	return sspace;
}
}