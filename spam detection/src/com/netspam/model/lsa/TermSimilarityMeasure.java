package com.netspam.model.lsa;

public interface TermSimilarityMeasure
extends SimilarityMeasure
{
/**
 * Returned as similarity if either of two compared terms could not be found.
 *
 * @see #getSimilarity(String, String)
 */
static final double NOT_FOUND = -1.0;
static double EQUALITY_SCORE = 1.0;

// TODO what is this actually used for?
void beginMassOperation();
void endMassOperation();

/**
 * Computes the similarity between two terms.
 */
double getSimilarity(String string1, String string2)
	throws SimilarityException;
}