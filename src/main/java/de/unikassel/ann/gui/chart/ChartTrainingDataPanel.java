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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.model.DataPairSet;

/**
 * @author anton
 * 
 */
public class ChartTrainingDataPanel extends JPanel {

	private static final long serialVersionUID = -8795234383972917391L;
	private XYSeriesCollection trainingsDataSet;

	// private XYSeriesCollection normalizedDataDataSet;

	public ChartTrainingDataPanel() {
		setLayout(new BorderLayout());

		trainingsDataSet = new XYSeriesCollection();
		// normalizedDataDataSet = new XYSeriesCollection();

		JFreeChart chart = ChartFactory.createXYLineChart("Trainingsdaten", // chart title
				"Wertebereich", // x axis label
				"Feld", // y axis label
				trainingsDataSet, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		// plot.getDomainAxis().setLowerMargin(0.0);

		plot.setBackgroundPaint(Color.white);
		// plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);

		// change the auto tick unit selection to integer units only...
		// NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		// xAxis.setTickUnit(new NumberTickUnit(1));

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		ChartPanel panel = new ChartPanel(chart);
		add(panel);
	}

	public void updateTrainingData() {
		trainingsDataSet.removeAllSeries();
		NetConfig netConfig = Settings.getInstance().getCurrentSession().getNetworkConfig();
		DataPairSet trainData = netConfig.getTrainingData();
		if (trainData != null) {
			int inputSize = trainData.getPairs().get(0).getInput().length;
			Double[][] inputAndIdeal = trainData.getInputAndIdeal();
			int field = 0;
			for (int i = 0; i < inputAndIdeal[field].length; i++) {
				String postfix = field < inputSize ? "input" : "ideal";
				XYSeries seriesFor = new XYSeries(field + 1 + "(" + postfix + ")");
				trainingsDataSet.addSeries(seriesFor);
				for (int j = 0; j < inputAndIdeal.length; j++) {
					seriesFor.add(new Double(inputAndIdeal[j][field]), new Integer(field + 1));
				}
				field++;
			}
		}

	}

}
