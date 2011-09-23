package de.unikassel.ann.strategy;

public class RestartErrorStrategy extends Strategy {

	public Double _error;
	public Integer _iterationForRestart;
	private boolean badError;

	/**
	 * Only used for reflection, DONT CALL THIS constructor!
	 */
	public RestartErrorStrategy() {

	}

	public RestartErrorStrategy(final Double maxErrorForRestart, final Integer iterationForRestart) {
		_error = maxErrorForRestart;
		_iterationForRestart = iterationForRestart;
	}

	@Override
	public void reset() {
		super.reset();
		badError = false;
	}

	@Override
	public void preIteration() {
		// TODO Auto-generated method stub

	}

	@Override
	public void postIteration() {
		if (config.getTrainingModule().getCurrentError() > _error) {
			badError = true;
		}
		if (config.getTrainingModule().getCurrentIteration() >= _iterationForRestart && badError) {
			restartTraining = true;
		}
	}

}
