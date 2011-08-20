package de.unikassel.ann.strategy;

public class MinErrorStrategy extends Strategy {

	private Double minerror;

	public MinErrorStrategy(Double minimalError) {
		this.minerror = minimalError;
	}

	@Override
	public void preIteration() {
		// nothing
	}

	@Override
	public void postIteration() {
		if (config.getTrainingModule().getCurrentError() <= minerror) {
			stopTraining = true;
		}
	}

	
	

}
