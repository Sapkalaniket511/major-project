package com.netspam.model.lsa;

import no.uib.cipr.matrix.Vector;

public class CachingVectorReader
extends VectorReader
{
private final VectorReader source;
private final StringKeyCache<Vector> vectorCache;

public CachingVectorReader(VectorReader aSource, int aCapacity)
{
	source = aSource;
	vectorCache = new StringKeyCache<Vector>(aCapacity);
}

public StringKeyCache<Vector> getVectorCache()
{
	return vectorCache;
}

public VectorReader getSource()
{
	return source;
}

@Override
public String getId()
{
	return source.getId();
}

@Override
public void close()
{
	source.close();
}

@Override
public int getConceptCount()
	throws SimilarityException
{
	return source.getConceptCount();
}

@Override
public Vector getVector(String aTerm)
	throws SimilarityException
{
	Vector v = getVectorCache().get(aTerm);
	if (v != null) {
		return v;
	}

	v = getSource().getVector(aTerm);

	if (v != null) {
		getVectorCache().put(aTerm, v);
	}

	return v;
}
}