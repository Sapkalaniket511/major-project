package com.netspam.model.lsa;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Vector;

public abstract class VectorReader
{
	private VectorAggregationStrategy vectorAggregationTemplate;

	{
		setVectorAggregation(VectorAggregation.CENTROID);
	}

	/**
	 * Get the concept vector for the given term.
	 */
	abstract public Vector getVector(String term)
		throws SimilarityException;

	/**
	 * Calculate the average ESA vector for the given terms.
	 *
	 * @param terms a list of terms, may contain duplicates.
	 * @return the average concept vector.
	 */
	public final Vector getVector(Collection<String> terms)
		throws SimilarityException
	{
		if (terms == null || terms.size() == 0) {
			return null; // Not found
		}

		// Calculate the term frequencies. This allows us to hit the DB only once for each term
		Map<String, AtomicInteger> counts = new HashMap<String, AtomicInteger>();
		for (String term : terms) {
			AtomicInteger count = counts.get(term);
			if (count == null) {
				count = new AtomicInteger(1);
				counts.put(term, count);
			}
			else {
				count.incrementAndGet();
			}
		}

		// Accumulate the vector
		VectorAggregationStrategy agg = vectorAggregationTemplate.newInstance();
		for (Entry<String, AtomicInteger> e : counts.entrySet()) {
			Vector termVec = getVector(e.getKey());
			if (termVec == null) {
				continue;
			}

			if (!agg.isInitialized()) {
				// Lazily initialize accumulation vector when we know the size from a term vector
				agg.init(new DenseVector(termVec.size()));
			}

			agg.add(e.getValue().get(), termVec);
		}

		if (!agg.isInitialized()) {
			return null; // Not found
		}

		// Return the aggregated vector
		return agg.get();
	}

	/**
	 * Get the number of concepts known to this source. This is equivalent to the size of the
	 * concept vectors.
	 */
	abstract public int getConceptCount()
		throws SimilarityException;

	abstract public String getId();

	public abstract void close();

	public void setVectorAggregation(VectorAggregation aVectorAggregation)
	{
		setVectorAggregation(aVectorAggregation.newInstance());
	}

	public void setVectorAggregation(VectorAggregationStrategy aVectorAggregationTemplate)
	{
		vectorAggregationTemplate = aVectorAggregationTemplate;
	}

	public VectorAggregationStrategy getVectorAggregationTemplate()
	{
		return vectorAggregationTemplate;
	}
}