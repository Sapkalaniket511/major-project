package com.netspam.model.lsa;

import java.util.Collection;

/**
 * Similarity measure on two bag of words text representations, e.g. already
 * lemmatized texts.
 */
public interface TextSimilarityMeasure
	extends TermSimilarityMeasure
{
	/**
	 * Computes the similarity between two bags of words text representations.
	 */
	double getSimilarity(Collection<String> strings1, Collection<String> strings2)
		throws SimilarityException;
	
	   /**
     * Computes the similarity between two bags of words text representations.
     */
    double getSimilarity(String[] strings1, String[] strings2)
        throws SimilarityException;
}