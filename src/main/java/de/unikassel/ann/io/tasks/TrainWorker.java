/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 */
package de.unikassel.ann.io.tasks;

import java.util.Collections;
import java.util.List;

import javax.swing.SwingWorker;

import de.unikassel.ann.algo.TrainingModule;
import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.Main.Panel;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.util.Logger;

public class TrainWorker extends SwingWorker<Void, Double> {

	private NetConfig net;
	private TrainingModule train;
	private DataPairSet trainingData;

	public TrainWorker(final NetConfig net, final TrainingModule train, final DataPairSet trainingData) {
		this.net = net;
		this.train = train;
		this.trainingData = trainingData;
		Main.instance.trainingErrorChartPanel.createNewSeries();
	}

	@Override
	protected Void doInBackground() throws Exception {
		Logger.info(this.getClass(), "training started");
		Main.instance.switchBottomPanel(Panel.TRAINERROR_CHART);
		Collections.shuffle(trainingData.getPairs());
		train.train(new DataPairSet(trainingData));
		Logger.info(this.getClass(), trainingData.toString());
		Logger.info(this.getClass(), "training finished");
		net.getWorkingModule().work(net.getNetwork(), trainingData);
		return null;
	}

	@Override
	protected void process(final List<Double> errorList) {
		// process is only interested in the last value reported each time, using it to update the GUI
		GraphController.getInstance().repaint();
	}

	@Override
	public void done() {
		GraphController.getInstance().repaint();
	}

}
