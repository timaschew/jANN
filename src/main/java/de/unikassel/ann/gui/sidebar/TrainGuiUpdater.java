/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.gui.sidebar;

import javax.swing.SwingWorker;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.chart.ChartTrainingErrorPanel;
import de.unikassel.ann.io.tasks.TrainWorker;

/**
 * @author anton
 * 
 */
public class TrainGuiUpdater extends SwingWorker<Void, Void> {
	private TrainWorker trainWorker;

	/**
	 * @param worker
	 */
	public TrainGuiUpdater(final TrainWorker worker) {
		trainWorker = worker;
	}

	@Override
	protected Void doInBackground() throws Exception {
		NetConfig netCfg = Settings.getInstance().getCurrentSession().getNetworkConfig();
		BackPropagation bp = (BackPropagation) netCfg.getTrainingModule();
		ChartTrainingErrorPanel chart = Main.instance.trainingErrorChartPanel;
		while (trainWorker.isDone() == false) {
			GraphController.getInstance().repaint();
			Integer iteration = bp.getCurrentIteration();
			Double error = bp.getCurrentError();
			if (iteration != Double.NaN || error != Double.NaN) {
				chart.addToCurrentSeries(iteration, error);
			}
		}
		GraphController.getInstance().repaint();
		Integer iteration = bp.getCurrentIteration();
		Double error = bp.getCurrentError();
		if (iteration != Double.NaN || error != Double.NaN) {
			chart.addToCurrentSeries(iteration, error);
		}
		return null;
	}

}