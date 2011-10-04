/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.gui.chart;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author anton
 * 
 */
public class ChartTrainingErrorPanel extends JPanel {

	private static final long serialVersionUID = -8795234383972917391L;
	private int count = 1;
	private XYSeries currentSeries;
	private XYSeriesCollection dataset;

	public ChartTrainingErrorPanel() {
		setLayout(new BorderLayout());

		currentSeries = new XYSeries("#" + count++);
		dataset = new XYSeriesCollection();
		dataset.addSeries(currentSeries);

		JFreeChart chart = ChartFactory.createXYLineChart("Trainingsfehler", // chart title
				"Iteration", // x axis label
				"RMSE in %", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);

		XYPlot plot = (XYPlot) chart.getPlot();

		plot.setBackgroundPaint(Color.white);
		// plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);

		// change the auto tick unit selection to integer units only...
		// NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		// xAxis.setTickUnit(new NumberTickUnit(1));

		ChartPanel panel = new ChartPanel(chart);
		add(panel);
	}

	public void createNewSeries() {
		if (currentSeries.isEmpty()) {
			return;
		}
		currentSeries = new XYSeries("#" + count++);
		dataset.addSeries(currentSeries);
	}

	public void addToCurrentSeries(final Integer iteration, final Double error) {
		currentSeries.add(iteration, new Double(100 * error));
	}

	// @Override
	// public void paint(final Graphics g) {
	// super.paint(g);
	// NetConfig netCfg = Settings.getInstance().getCurrentSession().getNetworkConfig();
	// BackPropagation bp = (BackPropagation) netCfg.getTrainingModule();
	// Integer iteration = bp.getCurrentIteration();
	// Double error = bp.getCurrentError();
	// if (bp.isTrainingNow()) {
	// addToCurrentSeries(iteration, error);
	// }
	// repaint();
	// }

	/**
	 * 
	 */
	public void reset() {
		count = 1;
		currentSeries = new XYSeries("#" + count++);
		dataset.removeAllSeries();
		dataset.addSeries(currentSeries);
	}

}
