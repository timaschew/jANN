package de.unikassel.ann.algo;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Network;

public abstract class TrainingModule {
	
	protected Double currentError = Double.NaN;
	
	protected Integer currentIteration = 0;
	
	protected Long currentStep = 0l;

	protected NetConfig config;
	
	abstract public void train(DataPairSet trainingData);
	
	public Double getCurrentError() {
		return currentError;
	}
	
	public Integer getCurrentIteration() {
		return currentIteration;
	}
	public Long getCurrentStep() {
		return currentStep;
	}

	public void setConfig(NetConfig config) {
		this.config = config;
		
	}


}
