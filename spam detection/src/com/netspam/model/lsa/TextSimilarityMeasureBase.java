package com.netspam.model.lsa;

import static java.util.Arrays.asList;

public abstract class TextSimilarityMeasureBase
	extends TermSimilarityMeasureBase
	implements TextSimilarityMeasure
{
	@Override
	public double getSimilarity(final String aTerm1, final String aTerm2)
		throws SimilarityException
	{
		return getSimilarity(asList(aTerm1), asList(aTerm2));
	}
	
    @Override
    public double getSimilarity(final String[] strings1, final String[] strings2)
        throws SimilarityException
    {
        return getSimilarity(asList(strings1), asList(strings2));
    }
}