package com.netspam.model.lsa;
import java.util.Collection;
import no.uib.cipr.matrix.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class VectorComparator
extends TextSimilarityMeasureBase
{
private final Log log = LogFactory.getLog(getClass());

private final VectorReader readerA;
private final VectorReader readerB;

private InnerVectorProduct innerProduct = InnerVectorProduct.COSINE;
private VectorNorm normalization = VectorNorm.L2;

private boolean reportNotFoundAsZero = false;

public VectorComparator(VectorReader aIndex)
{
	this(aIndex, aIndex);
}

public VectorComparator(VectorReader aIndexA, VectorReader aIndexB)
{
	readerA = aIndexA;
	readerB = aIndexB;
}

/**
 * Normally {@link #getSimilarity(String, String)} returns {@code Comparator#NOT_FOUND} when
 * one of the terms was not found in an index. If you do not care about that and simply want to
 * treat this case as a relatedness of zero, set this flag.
 *
 * @param aReportNotFoundAsZero
 */
public void setReportNotFoundAsZero(boolean aReportNotFoundAsZero)
{
	reportNotFoundAsZero = aReportNotFoundAsZero;
}

public boolean isReportNotFoundAsZero()
{
	return reportNotFoundAsZero;
}

private double getNotFound()
{
	return reportNotFoundAsZero ? 0.0 : NOT_FOUND;
}

@Override
public double getSimilarity(String term1, String term2)
	throws SimilarityException
{
	Double preScore = preScore(term1, term2);
	if (preScore != null) {
		return preScore;
	}

	Vector v1 = readerA.getVector(term1);

	if (v1 == null) {
		// The first index usually contains the terms which have quite short concept
		// vectors. If a term concept vector is not found, there is no need to load
		// the heavy-weight document concept vector.
		if (log.isDebugEnabled()) {
			log.debug("Cutting short on term ["+term1+"] without concept vector");
		}
		return getNotFound();
	}

	Vector v2 = readerB.getVector(term2);
	if (v2 == null) {
		return getNotFound();
	}

	return innerProduct.apply(v1, v2) / normalization.apply(v1, v2);
}

@Override
public double getSimilarity(Collection<String> tokenList1, Collection<String> tokenList2)
	throws SimilarityException
{
	Vector v1 = readerA.getVector(tokenList1);

	if (v1 == null) {
		// The first index usually contains the terms which have quite short concept
		// vectors. If a term concept vector is not found, there is no need to load
		// the heavy-weight document concept vector.
		return getNotFound();
	}

	Vector v2 = readerB.getVector(tokenList2);
	if (v2 == null) {
		return getNotFound();
	}

	return innerProduct.apply(v1, v2) / normalization.apply(v1, v2);
}

@Override
public String getName()
{
	return getClass().getSimpleName() + " (" + readerA.getId() + ", " + readerB.getId() + ")";
}

public void setInnerProduct(InnerVectorProduct aInnerProduct)
{
	innerProduct = aInnerProduct;
}

public InnerVectorProduct getInnerProduct()
{
	return innerProduct;
}

public void setNormalization(VectorNorm aNormalization)
{
	normalization = aNormalization;
}

public VectorNorm getNormalization()
{
	return normalization;
}
}