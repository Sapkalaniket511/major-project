package com.netspam.model.lsa;

public enum VectorAggregation
{
	/**
	 * Aggregates multiple vectors into one by simple addition.
	 *
	 * @see SumVectorAggregation
	 */
	SUM,

	/**
	 * Aggregates multiple vectors into one by addition and then dividing each element by the number
	 * of aggregated vectors.
	 *
	 * @see CentroidVectorAggregation
	 */
	CENTROID;

	public VectorAggregationStrategy newInstance()
	{
		switch (this) {
		case SUM:
			return VectorAggregationStrategy.getSum();
		case CENTROID:
			return VectorAggregationStrategy.getCentroid();
		default:
			throw new IllegalStateException("Vector aggregation ["+this+"] not supported");
		}
	}
}