package de.unikassel.ann.gui;

import java.awt.Component;
import java.util.concurrent.locks.ReentrantLock;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.dataset.PointDataSet;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.swing.JPlot;

import de.unikassel.ann.controller.Settings;

public class TrainErrorPlot implements TrainErrorListener {

	private PointDataSet<Double> numberSet;
	private JPlot plot;
	private JavaPlot p;
	private int run = 0;
	private int iteration = 0;
	private ReentrantLock rl;

	@Override
	public void updateUI() {
		if (rl.tryLock()) {
			plot.plot();
			rl.unlock();
			plot.repaint();
		}
	}

	public void init() {
		rl = new ReentrantLock();
		numberSet = new PointDataSet<Double>();
		DataSetPlot dataSetPlot = new DataSetPlot(numberSet);
		dataSetPlot.set("with", "lines");
		dataSetPlot.setTitle("run no #" + run);
		String path = Settings.properties.getProperty("gnuplot.path");
		JavaPlot javaPlot = new JavaPlot(path, false);
		javaPlot.setTitle("Trainingsfehler");
		plot = new JPlot(javaPlot);
		p = plot.getJavaPlot();
		p.addPlot(new int[][] { { 0, 0 } });
		plot.plot();
		p.addPlot(dataSetPlot);
	}

	public void nextRun() {
		iteration = 0;
		run++;
		numberSet = new PointDataSet<Double>();
		DataSetPlot dataSetPlot = new DataSetPlot(numberSet);
		dataSetPlot.set("with", "lines");
		dataSetPlot.setTitle("run no #" + run);
		rl.lock();
		p.addPlot(dataSetPlot);
		rl.unlock();
	}

	@Override
	public void addError(final Integer iter, final Double error) {
		numberSet.addPoint(Integer.valueOf(iter).doubleValue(), error);
	}

	public Component getPlotPanel() {
		return plot;
	}

}
