package de.unikassel.ann.strategy;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class RestartImprovementStrategy extends Strategy {

	private static NumberFormat fmt = new DecimalFormat("0.00000", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	private Integer _iterationForRestart;
	private Double _minimalImprovement;
	private Integer iterationWithBadImprovement = 0;

	/**
	 * Only used for reflection, DONT CALL THIS constructor!
	 */
	public RestartImprovementStrategy() {

	}

	public RestartImprovementStrategy(final Double minimalImprovementForRestart, final Integer iterationForRestart) {
		_minimalImprovement = minimalImprovementForRestart;
		_iterationForRestart = iterationForRestart;
	}

	@Override
	public void reset() {
		super.reset();
		iterationWithBadImprovement = 0;
	}

	@Override
	public void preIteration() {
		// TODO Auto-generated method stub

	}

	@Override
	public void postIteration() {
		Double improvement = config.getTrainingModule().getCurrentImprovement();
		if (improvement < _minimalImprovement) {
			iterationWithBadImprovement++;
			// System.err.println("improvement was: "+fmt.format(improvement));
		} else {
			iterationWithBadImprovement = 0;
		}
		if (iterationWithBadImprovement >= _iterationForRestart) {
			restartTraining = true;
			System.err.println(">>> restart in iteration " + config.getTrainingModule().getCurrentIteration() + " with error "
					+ fmt.format(config.getTrainingModule().getCurrentError()));
		}
	}

}
