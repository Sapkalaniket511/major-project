package com.netspam.model.lsa;

import no.uib.cipr.matrix.Vector;
import edu.ucla.sspace.vector.DoubleVector;


public class VectorAdapter<V extends Vector> implements DoubleVector
{
	private V wrapped;

	public VectorAdapter(V aVector)
	{
		wrapped = aVector;
	}

	@Override
	public int length()
	{
		return wrapped.size();
	}

	@Override
	public void set(int aParamInt, Number aParamNumber)
	{
		wrapped.set(aParamInt, aParamNumber.doubleValue());
	}

	@Override
	public double add(int aParamInt, double aParamDouble)
	{
		wrapped.add(aParamInt, aParamDouble);
		return wrapped.get(aParamInt);
	}

	@Override
	public double get(int aParamInt)
	{
		return wrapped.get(aParamInt);
	}

	@Override
	public Double getValue(int aParamInt)
	{
		return wrapped.get(aParamInt);
	}

	@Override
	public void set(int aParamInt, double aParamDouble)
	{
		wrapped.set(aParamInt, aParamDouble);
	}

	@Override
	public double[] toArray()
	{
		throw new UnsupportedOperationException();
	}

	public static <V extends Vector > DoubleVector create(V aVector)
	{
		return new VectorAdapter<V>(aVector);
	}

    @Override
    public double magnitude()
    {
        throw new UnsupportedOperationException();
    }
}