/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.normalize;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author anton
 * 
 */
public class NormUtil {

	private double dataLow;
	private double dataHigh;
	private double normalizedLow;
	private double normalizedHigh;

	/**
	 * Construct the normalization utility. Default to normalization range of -1 to +1.
	 * 
	 * @param dataLow
	 *            The low value for the input data.
	 * @param dataHigh
	 *            The high value for the input data.
	 */
	public NormUtil(final double dataLow, final double dataHigh) {
		this(dataLow, dataHigh, -1, +1);
	}

	/**
	 * Construct the normalization utility, allow the normalization range to be specified.
	 * 
	 * @param dataHigh
	 *            The high value for the input data.
	 * @param dataLow
	 *            The low value for the input data.
	 * @param dataHigh
	 *            The high value for the normalized data.
	 * @param dataLow
	 *            The low value for the normalized data.
	 */
	public NormUtil(final double dataLow, final double dataHigh, final double normalizedLow, final double normalizedHigh) {
		this.dataLow = dataLow;
		this.dataHigh = dataHigh;
		this.normalizedLow = normalizedLow;
		this.normalizedHigh = normalizedHigh;
	}

	/**
	 * Normalize x.
	 * 
	 * @param x
	 *            The value to be normalized.
	 * @return The result of the normalization.
	 */
	public double normalize(final double x) {
		return (x - dataHigh) / (dataLow - dataHigh) * (normalizedLow - normalizedHigh) + normalizedHigh;
	}

	/**
	 * @see #normalize(double)
	 */
	public double[] normalize(final double[] x) {
		double[] ar = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			ar[i] = (x[i] - dataHigh) / (dataLow - dataHigh) * (normalizedLow - normalizedHigh) + normalizedHigh;
		}
		return ar;
	}

	/**
	 * @see #normalize(double)
	 */
	public Double[] normalize(final Double[] x) {
		return ArrayUtils.toObject(normalize(ArrayUtils.toPrimitive(x)));
	}

	/**
	 * Denormalize x.
	 * 
	 * @param x
	 *            The value to denormalize.
	 * @return The denormalized value.
	 */
	public double denormalize(final double x) {
		return ((dataHigh - dataLow) * x - normalizedLow * dataHigh + dataLow * normalizedHigh) / (normalizedHigh - normalizedLow);
	}

	/**
	 * @see #denormalize(double)
	 */
	public double[] denormalize(final double[] x) {
		double[] ar = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			ar[i] = ((dataHigh - dataLow) * x[i] - normalizedLow * dataHigh + dataLow * normalizedHigh) / (normalizedHigh - normalizedLow);
		}
		return ar;
	}

	/**
	 * @see #denormalize(double)
	 */
	public Double[] denormalize(final Double[] x) {
		return ArrayUtils.toObject(denormalize(ArrayUtils.toPrimitive(x)));
	}
}