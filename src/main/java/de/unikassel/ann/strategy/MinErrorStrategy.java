package de.unikassel.ann.strategy;

public class MinErrorStrategy extends Strategy {

	/**
	 * Only used for reflection, DONT CALL THIS constructor!
	 */
	public MinErrorStrategy() {
	}

	public Double _minerror;

	public MinErrorStrategy(final Double minimalError) {
		_minerror = minimalError;
	}

	@Override
	public void preIteration() {
		// nothing
	}

	@Override
	public void postIteration() {
		if (config.getTrainingModule().getCurrentError() <= _minerror) {
			stopTraining = true;
		}
	}

}
