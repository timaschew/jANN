package de.unikassel.ann.strategy;


public class MaxLearnIterationsStrategy extends Strategy {

	private int maxIteration;

	public MaxLearnIterationsStrategy(int iterations) {
		this.maxIteration = iterations;
	}
	
	@Override
	public void preIteration() {
		// nothing
	}

	@Override
	public void postIteration() {
		if (config.getTrainingModule().getCurrentIteration() >= maxIteration) {
			stopTraining = true;
		}
	}

}
