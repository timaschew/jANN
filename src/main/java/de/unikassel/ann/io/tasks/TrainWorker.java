/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 */
package de.unikassel.ann.io.tasks;

import java.util.List;

import javax.swing.SwingWorker;

import de.unikassel.ann.algo.TrainingModule;
import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.chart.ChartTrainingErrorPanel;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.util.Logger;

public class TrainWorker extends SwingWorker<Void, Double> {

	private NetConfig net;
	private TrainingModule train;
	private DataPairSet testData;

	public TrainWorker(final NetConfig net, final TrainingModule train, final DataPairSet testData) {
		this.net = net;
		this.train = train;
		this.testData = testData;
	}

	@Override
	protected Void doInBackground() throws Exception {
		Logger.info(this.getClass(), "training started");
		for (DataPair pair : net.getTrainingData().getPairs()) {
			DataPairSet set = new DataPairSet();
			set.addPair(pair);
			train.train(set);
			Double currentError = train.getCurrentError();
			System.out.println(currentError);
			publish(currentError);
		}
		Logger.info(this.getClass(), testData.toString());
		Logger.info(this.getClass(), "training finished");
		net.getWorkingModule().work(net.getNetwork(), testData);
		return null;
	}

	@Override
	protected void process(final List<Double> errorList) {
		// process is only interested in the last value reported each time, using it to update the GUI
		System.out.println(errorList);
		ChartTrainingErrorPanel errorChartPanel = Main.instance.trainingErrorChartPanel;
		errorChartPanel.createNewSeries();

		for (Double error : errorList) {
			errorChartPanel.addToCurrentSeries(error);
		}
		GraphController.getInstance().repaint();
	}

	@Override
	protected void done() {
		super.done();
	}

}
