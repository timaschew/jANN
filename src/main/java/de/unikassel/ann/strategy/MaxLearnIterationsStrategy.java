package de.unikassel.ann.strategy;

public class MaxLearnIterationsStrategy extends Strategy {

	private int maxIteration;

	public MaxLearnIterationsStrategy() {
	}

	public MaxLearnIterationsStrategy(final int iterations) {
		maxIteration = iterations;
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
