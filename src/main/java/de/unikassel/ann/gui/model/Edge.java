package de.unikassel.ann.gui.model;

import java.text.DecimalFormat;

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.sidebar.StandardOptionsPanel;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;
import de.unikassel.ann.util.FormatHelper;

public class Edge {

	// Synapse data model
	private Synapse model;

	// Edge index (raised by the EdgeFactory)
	private int index;

	// Edge weight format
	private static DecimalFormat df;

	/**
	 * Constructor
	 */
	public Edge() {
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	/*
	 * Edge model
	 */

	public void setModel(final Synapse model) {
		this.model = model;
	}

	public Synapse getModel() {
		return model;
	}

	/**
	 * Create edge (synapse) between two vertexes (neurons)
	 * 
	 * @param Neuron
	 *            from
	 * @param Neuron
	 *            to
	 */
	public void createModel(final Neuron from, final Neuron to) {
		model = new Synapse(from, to);

		// Get random default weight between min and max defined in the standard options panel in the sidebar.
		StandardOptionsPanel panel = Main.instance.sidebar.standardOptionsPanel;
		Object minValue = panel.randomInitialWeightSpinnerMin.getValue();
		Object maxValue = panel.randomInitialWeightSpinnerMax.getValue();
		double min = FormatHelper.parse2Double(minValue);
		double max = FormatHelper.parse2Double(maxValue);
		if (min > max) {
			double temp = min;
			min = max;
			max = temp;
		}
		double weight = new Double(Math.random()) * Math.abs(min) + Math.abs(max) - Math.abs(min);
		updateWeight(weight);
	}

	public Double getWeight() {
		if (model == null) {
			return null;
		}
		return model.getWeight();
	}

	public void updateWeight(final Double weight) {
		if (model == null) {
			return;
		}
		model.setWeight(weight);
	}

	@Override
	public String toString() {
		if (df == null) {
			df = new DecimalFormat(Settings.properties.getProperty("gui.decimalFormat"), Settings.decimalSymbols);
		}
		return df.format(getWeight());
	}

}
