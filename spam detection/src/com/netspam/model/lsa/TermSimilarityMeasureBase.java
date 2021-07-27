package com.netspam.model.lsa;

public abstract class TermSimilarityMeasureBase
implements TermSimilarityMeasure
{
@Override
public void beginMassOperation()
{
	// Per default do nothing
}

@Override
public void endMassOperation()
{
	// Per default do nothing
}

protected static Double preScore(String term1, String term2)
{
	if (term1.length() == 0 || term2 == null || term2.length() == 0) {
		return NOT_FOUND;
	}

	if (term1 == term2 || term1.equals(term2)) {
		return EQUALITY_SCORE;
	}

	return null;
}

@Override
public String getName()
{
	return this.getClass().getSimpleName();
}

@Override
public boolean isDistanceMeasure()
{
	return false;
}
}