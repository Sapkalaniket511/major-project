package com.netspam.model.lsa;

import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.Vector.Norm;

public enum VectorNorm
{
	/**
	 * Do not perform any normalization.
	 */
	NONE,

	/**
	 * Calculate the L1 norm (Manhattan norm).
	 *
	 * @see <a href="http://en.wikipedia.org/wiki/Norm_(mathematics)#Taxicab_norm_or_Manhattan_norm">Manhattan norm (Wikipedia)</a>
	 */
	L1,

	/**
	 * Calculate the L2 norm (euclidean norm).
	 *
	 * @see <a href="http://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm">Euclidean Norm (Wikipedia)</a>
	 */
	L2;

	/**
	 * Calculates the norm of the given vector. If multiple vectors are given, calculates the
	 * product of the vector norms. If no vector is given, 1.0 is returned.
	 *
	 * @param aVectors a list of vectors.
	 * @return the vector norm or product of vector norms.
	 */
	public double apply(Vector... aVectors)
	{
		double result = 1.0;
		if (!this.equals(NONE)) {
			for (Vector v : aVectors) {
				switch (this) {
				case L1:
					result = result * v.norm(Norm.One);
					break;
				case L2:
					result = result * v.norm(Norm.TwoRobust);
					break;
				default:
					throw new IllegalStateException("Norm ["+this+"] not supported");
				}
			}
		}
		return result;
	}
}