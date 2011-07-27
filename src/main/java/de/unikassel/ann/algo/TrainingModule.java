package de.unikassel.ann.algo;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.NetError;


public abstract class TrainingModule {
	
	protected Double currentError = Double.NaN;
	
//	protected Double currentSingleError = Double.NaN;
	
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

	public void setConfig(NetConfig config) {
		this.config = config;
	}

	public Double getCurrentError() {
		return currentError;
	}

//	public Double getCurrentSingleError() {
//		return currentSingleError;
//	}

}
