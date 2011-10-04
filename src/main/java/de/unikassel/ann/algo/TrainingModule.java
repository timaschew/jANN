package de.unikassel.ann.algo;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.NetError;

public abstract class TrainingModule {

	protected boolean trainNow = false;

	protected Double currentError = Double.NaN;

	protected Double currentImprovement = Double.NaN;

	// protected Double currentSingleError = Double.NaN;

	protected NetError netError;

	protected Integer currentIteration = 0;

	protected Integer currentStep = 0;

	protected NetConfig config;

	abstract public void train(DataPairSet trainingData);

	public Integer getCurrentIteration() {
		return currentIteration;
	}

	public Integer getCurrentStep() {
		return currentStep;
	}

	public void setConfig(final NetConfig config) {
		this.config = config;
	}

	public Double getCurrentError() {
		return currentError;
	}

	public Double getCurrentImprovement() {
		return currentImprovement;
	}

	public void reset() {
		currentError = Double.NaN;
		currentImprovement = Double.NaN;
		currentIteration = 0;
		currentStep = 0;
		trainNow = false;

	}

	public boolean isTrainingNow() {
		return trainNow;
	}

	// public Double getCurrentSingleError() {
	// return currentSingleError;
	// }

}
