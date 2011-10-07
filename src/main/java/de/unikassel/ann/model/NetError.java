package de.unikassel.ann.model;

import de.unikassel.ann.algo.TrainingModule;

public class NetError {

	private double errorSum;

	private int trainingSize;

	private int size;

	private TrainingModule trainingModule;

	public NetError(final TrainingModule trainigModule, final DataPairSet trainingData) {
		trainingModule = trainigModule;
		trainingSize = trainingData.getRows();
	}

	/**
	 * Resets errorSum and size
	 */
	public final void reset() {
		errorSum = 0;
		size = 0;
	}

	/**
	 * Update the error with single values with (ideal-output)^2
	 * 
	 * @param output
	 *            The input value.
	 * @param ideal
	 *            The ideal value.
	 */
	public final void updateError(final double output, final double ideal) {
		double delta = ideal - output;
		errorSum += delta * delta;
		size++;
	}

	/**
	 * Called to update for each element
	 * 
	 * @param output
	 *            The output number.
	 * @param ideal
	 *            The ideal number.
	 * @see #updateError(double, double)
	 */
	public final void updateError(final double[] output, final double[] ideal) {
		for (int i = 0; i < output.length; i++) {
			updateError(output[i], ideal[i]);
		}
	}

	/**
	 * Calculate the error with RMSE.
	 * 
	 * @return The current root mean square error
	 */
	public final double calculateRMS() {
		if (size == 0) {
			return 0;
		}
		final double err = Math.sqrt(errorSum / size);
		return err;
	}
}
