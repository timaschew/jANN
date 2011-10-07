package de.unikassel.ann.config;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.algo.TrainingModule;
import de.unikassel.ann.algo.WorkModule;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Synapse;
import de.unikassel.ann.strategy.MaxLearnIterationsStrategy;
import de.unikassel.ann.strategy.Strategy;

public class NetConfig {

	private Network network;
	private List<Strategy> strategies;
	private WorkModule workModule;
	private TrainingModule trainingModule;
	private int restartAmount = 0;

	private DataPairSet trainingData;
	private DataPairSet testData;
	private DataPairSet originalData;

	private double initMinWeight = -2;
	private double initMaxWeight = 2;
	private List<IterationError> iterationErrorList;

	public NetConfig() {
		network = new Network();
		network.setConfig(this);
		strategies = new ArrayList<Strategy>();
		iterationErrorList = new ArrayList<IterationError>();

		/*
		 * default algorithm and strategy
		 */
		BackPropagation backProp = new BackPropagation();
		addTrainingModule(backProp);
		addWorkModule(backProp);
		addOrUpdateExisting(new MaxLearnIterationsStrategy(100000));
	}

	public boolean shouldStopTraining() {
		for (Strategy s : strategies) {
			if (s.shouldStop()) {
				return true;
			}
		}
		return false;
	}

	public boolean shouldRestartTraining() {
		for (Strategy s : strategies) {
			if (s.shouldRestart()) {
				if (restartAmount > 3) {
					return false;
				}
				initWeights();
				restartAmount++;
				reset();
				return true;
			}
		}
		return false;
	}

	/**
	 * Resets the value of the strategies, iteration, error, etc.<br>
	 * Needed for calling train() a 2nd time.
	 */
	public void reset() {
		restartAmount = 0;
		for (Strategy s : strategies) {
			s.reset();
		}
		trainingModule.reset();
	}

	/**
	 * Randomize the synapse weights with the default values.
	 */
	public void initWeights() {
		randomize(initMinWeight, initMaxWeight);
	}

	/**
	 * @param min
	 * @param max
	 */
	private void randomize(final double min, final double max) {
		Random r = new Random();
		for (Synapse s : network.getSynapseSet()) {
			s.setWeight(r.nextDouble() * Math.random() * (Math.abs(min) + Math.abs(max)) - Math.abs(min));
		}
	}

	public Network getNetwork() {
		return network;
	}

	public void addOrUpdateExisting(final Strategy strat) {
		Strategy toRemove = null;
		for (Strategy s : strategies) {
			if (s.getClass().getSimpleName().equals(strat.getClass().getSimpleName())) {
				toRemove = s;
			}
		}
		if (toRemove != null) {
			strategies.remove(toRemove);
		}
		strat.setConfig(this);
		strategies.add(strat);
	}

	public void addTrainingModule(final TrainingModule train) {
		trainingModule = train;
		train.setConfig(this);
	}

	public void addWorkModule(final WorkModule work) {
		workModule = work;
	}

	public TrainingModule getTrainingModule() {
		return trainingModule;
	}

	public WorkModule getWorkingModule() {
		return workModule;
	}

	public List<Strategy> getStrategies() {
		return strategies;
	}

	/**
	 * @return the initMinWeight
	 */
	public double getInitMinWeight() {
		return initMinWeight;
	}

	/**
	 * @param initMinWeight
	 *            the initMinWeight to set
	 */
	public void setInitMinWeight(final double initMinWeight) {
		this.initMinWeight = initMinWeight;
	}

	/**
	 * @return the initMaxWeight
	 */
	public double getInitMaxWeight() {
		return initMaxWeight;
	}

	/**
	 * @param initMaxWeight
	 *            the initMaxWeight to set
	 */
	public void setInitMaxWeight(final double initMaxWeight) {
		this.initMaxWeight = initMaxWeight;
	}

	public void printStats() {
		StringBuilder sb = new StringBuilder();
		sb.append("error = ");
		NumberFormat fmt = new DecimalFormat("0.00000", Settings.decimalSymbols);
		sb.append(fmt.format(trainingModule.getCurrentError()));
		// sb.append(" / ");
		// sb.append(fmt.format(trainingModule.getCurrentSingleError()));
		sb.append("\n");
		sb.append("learnSteps = ");
		sb.append(trainingModule.getCurrentStep());
		sb.append("\n");
		sb.append("iterations = ");
		sb.append(trainingModule.getCurrentIteration());
		sb.append("\n");
		if (restartAmount > 0) {
			sb.append("restarted " + restartAmount + " times\n");
		}
		System.out.println(sb.toString());

	}

	/**
	 * @return the trainingData
	 */
	public DataPairSet getTrainingData() {
		return trainingData;
	}

	/**
	 * @param trainingData
	 *            the trainingData to set
	 */
	public void setTrainingData(final DataPairSet trainingData) {
		this.trainingData = trainingData;
	}

	/**
	 * @return the testData
	 */
	public DataPairSet getTestData() {
		return testData;
	}

	/**
	 * @param testData
	 *            the testData to set
	 */
	public void setTestData(final DataPairSet testData) {
		this.testData = testData;
	}

	/**
	 * @return the originalData
	 */
	public DataPairSet getOriginalData() {
		return originalData;
	}

	/**
	 * @param originalData
	 *            the originalData to set
	 */
	public void setOriginalData(final DataPairSet originalData) {
		this.originalData = originalData;
	}

	/**
	 * @param currentIteration
	 * @param currentError
	 */
	public void addErrorList(final Integer iter, final Double error) {
		iterationErrorList.add(new IterationError(iter, error));
	}

	public void resetErrorList() {
		iterationErrorList = new ArrayList<IterationError>();
	}

	public List<IterationError> getErrorList() {
		return iterationErrorList;
	}

	public class IterationError {
		public IterationError(final int iteration, final double error) {
			this.iteration = iteration;
			this.error = error;
		}

		public int iteration;
		public double error;
	}
}
