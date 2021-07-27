package com.netspam.model.lsa;

import no.uib.cipr.matrix.Vector;

public abstract class VectorAggregationStrategy
{
	/**
	 * Initialize with the given vector.
	 */
	public abstract void init(Vector aVector);

	public abstract boolean isInitialized();

	/**
	 * Get the aggregated vector.
	 */
	public abstract Vector get();

	/**
	 * Add the specified vector.
	 */
	public abstract void add(Vector aVector);

	/**
	 * Add the specified vector x times.
	 */
	public abstract void add(int aTimes, Vector aVector);

	/**
	 * Create a fresh instance of the aggregator. This is useful if you want to create thread
	 * safe code and need to create throw-away aggregator instances from a template.
	 */
	public abstract VectorAggregationStrategy newInstance();

	/**
	 * Sums up vectors.
	 */
	public static VectorAggregationStrategy getSum()
	{
		return new SumVectorAggregation();
	}

	/**
	 * Sums up vectors and divides result by number of vectors.
	 */
	public static VectorAggregationStrategy getCentroid()
	{
		return new CentroidVectorAggregation();
	}

	public static class SumVectorAggregation extends VectorAggregationStrategy
	{
		private Vector vector;

		@Override
		public void init(Vector aVector)
		{
			vector = aVector;
		}

		@Override
		public boolean isInitialized()
		{
			return vector != null;
		}

		@Override
		public Vector get()
		{
			return vector.copy();
		}

		@Override
		public void add(Vector aVector)
		{
			add(1, aVector);
		}

		@Override
		public void add(int aTimes, Vector aVector)
		{
			vector.add(aTimes, aVector);
		}

		@Override
		public VectorAggregationStrategy newInstance()
		{
			return new SumVectorAggregation();
		}
	}

	public static class CentroidVectorAggregation extends SumVectorAggregation
	{
		private int count = 0;

		@Override
		public void add(int aTimes, Vector aVector)
		{
			count += aTimes;
			super.add(aTimes, aVector);
		}

		@Override
		public Vector get()
		{
			return super.get().scale(1./count);
		}


		@Override
		public VectorAggregationStrategy newInstance()
		{
			return new CentroidVectorAggregation();
		}
}}