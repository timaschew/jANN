/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.normalize;

import org.apache.commons.lang.ArrayUtils;

import de.unikassel.ann.model.DataPairSet;

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
	 * @param trainSet
	 */
	public static DataPairSet normalize(final DataPairSet trainSet, final double minNormValue, final double maxNormValue) {

		if (trainSet.getInput().length <= 0 || trainSet.getIdeal().length <= 0) {
			return null;
		}

		Double[][] inAndOut = trainSet.getInputAndIdeal();

		int fieldLenght = inAndOut[0].length;

		Double[] min = new Double[fieldLenght];
		Double[] max = new Double[fieldLenght];
		for (int j = 0; j < fieldLenght; j++) {
			min[j] = Double.MAX_VALUE;
			max[j] = Double.MIN_VALUE;
		}

		// save min, max
		for (int i = 0; i < inAndOut.length; i++) {
			for (int j = 0; j < inAndOut[i].length; j++) {
				min[j] = Math.min(inAndOut[i][j], min[j]);
				max[j] = Math.max(inAndOut[i][j], max[j]);
			}
		}

		NormUtil[] normUtil = new NormUtil[fieldLenght];
		for (int j = 0; j < fieldLenght; j++) {
			normUtil[j] = new NormUtil(min[j], max[j], minNormValue, maxNormValue);
		}

		DataPairSet resultPairSet = new DataPairSet(trainSet);
		Double[][] resultArray = resultPairSet.getInputAndIdeal();

		for (int i = 0; i < inAndOut.length; i++) {
			for (int j = 0; j < inAndOut[i].length; j++) {
				resultArray[i][j] = normUtil[j].normalize(inAndOut[i][j]);
			}
		}
		resultPairSet.setInputAndIdeal(resultArray, trainSet.getInput()[0].length, trainSet.getIdeal()[0].length);
		return resultPairSet;
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