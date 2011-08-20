package de.unikassel.ann.strategy;

public class RestartErrorStrategy extends Strategy {

	private Double error;
	private Integer iterationForRestart;
	private boolean badError;
	
	public RestartErrorStrategy(Double maxErrorForRestart, Integer iterationForRestart) {
		this.error = maxErrorForRestart;
		this.iterationForRestart = iterationForRestart;
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
		if (config.getTrainingModule().getCurrentError() > error) {
			badError = true;
		}
		if (config.getTrainingModule().getCurrentIteration() >= iterationForRestart && badError) {
			restartTraining = true;
		}
	}

}
