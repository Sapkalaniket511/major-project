package com.netspam.model.lsa;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;

public class LsaSimilarityMeasure 
extends TextSimilarityMeasureBase
{

private TextSimilarityMeasure measure;

private int cacheSize;

public LsaSimilarityMeasure(File modelFile) throws IOException {
	cacheSize = 100;
	
	SemanticSpace sspace = SemanticSpaceIO.load(modelFile); 

	measure = new VectorComparator(new CachingVectorReader(
            new SSpaceVectorReader(sspace), cacheSize));
}

@Override
public double getSimilarity(Collection<String> strings1,
		Collection<String> strings2) throws SimilarityException {
	return measure.getSimilarity(strings1, strings2);
}

public void setCacheSize(int cacheSize) {
	this.cacheSize = cacheSize;
}
}