package com.netspam.model.lsa;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public interface JCasTextSimilarityMeasure
extends TextSimilarityMeasure
{
/**
 * Computes the similarity between two JCas text representations.
 */
double getSimilarity(JCas jcas1, JCas jcas2)
	throws SimilarityException;

/**
 * Computes the similarity between two JCas text representations.
 * Processing is limited to the two covering {@link Annotation}s within
 * each JCas. 
 */
double getSimilarity(JCas jcas1, JCas jcas2, Annotation coveringAnnotation1, Annotation coveringAnnotation2)
	throws SimilarityException;

}