package de.unikassel.ann.strategy;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class RestartImprovementStrategy extends Strategy {

	private static NumberFormat fmt = new DecimalFormat("0.00000",  DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	private Integer iterationForRestart;
	private Double minimalImprovement;
	private Integer iterationWithBadImprovement = 0;

	public RestartImprovementStrategy(Double minimalImprovementForRestart, Integer iterationForRestart) {
		this.minimalImprovement = minimalImprovementForRestart;
		this.iterationForRestart = iterationForRestart;
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
		if (improvement < minimalImprovement) {
			iterationWithBadImprovement++;
//			System.err.println("improvement was: "+fmt.format(improvement));
		} else {
			iterationWithBadImprovement = 0;
		}
		if (iterationWithBadImprovement >= iterationForRestart) {
			restartTraining = true;
			System.err.println(">>> restart in iteration "+config.getTrainingModule().getCurrentIteration()+ " with error "+ fmt.format(config.getTrainingModule().getCurrentError()));
		}
	}

}
