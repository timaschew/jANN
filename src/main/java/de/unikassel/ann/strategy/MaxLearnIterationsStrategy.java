package de.unikassel.ann.strategy;

public class MaxLearnIterationsStrategy extends Strategy {

	public int _maxIteration;

	/**
	 * Only used for reflection, DONT CALL THIS constructor!
	 */
	public MaxLearnIterationsStrategy() {
	}

	public MaxLearnIterationsStrategy(final int iterations) {
		_maxIteration = iterations;
	}

	@Override
	public void preIteration() {
		// nothing
	}

	@Override
	public void postIteration() {
		if (config.getTrainingModule().getCurrentIteration() >= _maxIteration) {
			stopTraining = true;
		}
	}

}
