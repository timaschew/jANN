/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 */
package de.unikassel.ann.io.tasks;

import java.util.Collections;

import javax.swing.SwingWorker;

import de.unikassel.ann.algo.TrainingModule;
import de.unikassel.ann.config.NetConfig;
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
		Main.instance.switchBottomPanel(Panel.TRAINERROR_CHART);
		Thread.sleep(100);
		Logger.info(this.getClass(), "training started");
		Collections.shuffle(trainingData.getPairs());
		train.train(new DataPairSet(trainingData));
		Logger.debug(this.getClass(), trainingData.toString());
		net.getWorkingModule().work(net.getNetwork(), trainingData);

		return null;
	}

	@Override
	public void done() {
		// Main.instance.sidebar.trainControlPanel.btnPlay.setEnabled(true);
		Main.instance.sidebar.trainStrategyPanel.btnStartTraining.setEnabled(true);
	}
}
