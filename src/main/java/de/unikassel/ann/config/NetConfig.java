package de.unikassel.ann.config;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.unikassel.ann.algo.TrainingModule;
import de.unikassel.ann.algo.WorkModule;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.TrainErrorListener;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.NetError;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;
import de.unikassel.ann.model.func.ActivationFunction;
import de.unikassel.ann.rand.Randomizer;
import de.unikassel.ann.strategy.Strategy;

public class NetConfig {

	private Network network;
	private List<Strategy> strategies;
	private WorkModule workModule;
	private TrainingModule trainingModule;
	private Randomizer randomizer;
	private List<NetError> errorLogs;
	private int restartAmount = 0;
	private List<TrainErrorListener> trainErrorListener;
	private DataPairSet dataPairTest;
	private DataPairSet dataPairTrain;

	public NetConfig() {
		network = new Network();
		network.setConfig(this);
		strategies = new ArrayList<Strategy>();
		errorLogs = new ArrayList<NetError>();
		trainErrorListener = new ArrayList<TrainErrorListener>();
		dataPairTest = new DataPairSet();
		dataPairTrain = new DataPairSet();
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
				initWeights();
				restartAmount++;
				reset();
				return true;
			}
		}
		return false;
	}

	private void reset() {
		for (Strategy s : strategies) {
			s.reset();
		}
		trainingModule.reset();
	}

	public void initWeights() {
		Random r = new Random();
		for (Synapse s : network.getSynapseSet()) {
			s.setWeight(r.nextDouble() * 2 - 1);
		}
	}

	/**
	 * Generates the network with this configuration
	 */
	public void buildNetwork() {
		network.finalizeStructure();
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

	public void addLayer(final int neuronCount, final boolean bias, final ActivationFunction function) {
		Layer l = new Layer();
		if (bias) {
			l.addNeuron(new Neuron(function, true));
		}
		for (int i = 0; i < neuronCount; i++) {
			Neuron n = new Neuron(function, false);
			l.addNeuron(n);
		}
		network.addLayer(l);

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

	// private class TrainErrorThread extends Thread {
	// TrainErrorThread() {
	// start();
	// }
	// public void run() {
	// while (getTrainingModule().isTrainingNow()) {
	// // trainErrorListener.get(0).addError(getTrainingModule().getCurrentIteration(), getTrainingModule().getCurrentError());
	// trainErrorListener.get(0).updateUI();
	// }
	// }
	// }
	//
	// private class MyWorker extends SwingWorker<Void, Void> {
	//
	// public MyWorker() {
	// execute();
	// }
	// @Override
	// public Void doInBackground() {
	// while (getTrainingModule().isTrainingNow()) {
	// // trainErrorListener.get(0).addError(getTrainingModule().getCurrentIteration(), getTrainingModule().getCurrentError());
	// trainErrorListener.get(0).updateUI();
	// }
	// return null;
	// }
	// }

	public void addTrainErrorListener(final TrainErrorListener tel) {
		trainErrorListener.add(tel);
	}

	public void notifyError(final Double currentError) {
		for (TrainErrorListener tel : trainErrorListener) {
			tel.addError(getTrainingModule().getCurrentIteration(), currentError);

		}
	}

	/**
	 * @return the dataPairTest
	 */
	public DataPairSet getDataPairTest() {
		return dataPairTest;
	}

	/**
	 * @param dataPairTest
	 *            the dataPairTest to set
	 */
	public void setDataPairTest(final DataPairSet dataPairTest) {
		this.dataPairTest = dataPairTest;
	}

	/**
	 * @return the dataPairTrain
	 */
	public DataPairSet getDataPairTrain() {
		return dataPairTrain;
	}

	/**
	 * @param dataPairTrain
	 *            the dataPairTrain to set
	 */
	public void setDataPairTrain(final DataPairSet dataPairTrain) {
		this.dataPairTrain = dataPairTrain;
	}
}
