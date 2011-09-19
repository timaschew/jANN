package de.unikassel.ann.gui.graph;

import java.beans.PropertyChangeEvent;
import java.text.DecimalFormat;

import de.unikassel.ann.controller.ActionController;
import de.unikassel.ann.controller.Actions;
import de.unikassel.ann.controller.LayerController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.SidebarModel;
import de.unikassel.ann.model.UserSession;

public class Vertex implements Comparable<Vertex> {

	// Neuron data model
	private Neuron model;

	// Vertex index (raised by the VertexFactory)
	private int index;

	// Vertex value format
	private static DecimalFormat df;

	/**
	 * Constructor
	 */
	public Vertex() {
		// TODO get activateFunction
		String activateFunc = "SigmoidFunction";

		// TODO bias enabled?
		boolean bias = false;

		// Create model
		model = new Neuron(activateFunc, bias);
	}

	/**
	 * Vertex setup. Sets the current sidebar config values to the layer and add
	 * it to the layercontroller.
	 */
	public void setup() {
		UserSession currentSession = Settings.getInstance().getCurrentSession();
		LayerController<Layer> layerController = LayerController.getInstance();

		// Set current layer index for this vertex (for its neuron model)
		int layerIndex = currentSession.sidebarModel.getMouseInsertLayer();
		setLayer(layerIndex);

		// TODO Set its startvalue to 0
		// TODO remove later, just for testing purpose
		setValue(new Double(Math.random()));

		// Get the number of vertices in the layer BEFORE adding the new vertex
		int layerSize = layerController.getVerticesInLayer(layerIndex).size();

		// Add the new vertex to the current jung layer but DO NOT add the
		// vertex to the graph at this position!
		layerController.addVertex(layerIndex, this, false);

		// Input layer (index = 0)
		Actions action = Actions.UPDATE_SIDEBAR_CONFIG_INPUT_NEURON_MODEL;
		if (layerIndex > 0) {
			if (layerIndex == layerController.getLayers().size() - 1) {
				// Output Layer (index = # layers - 1)
				action = Actions.UPDATE_SIDEBAR_CONFIG_OUTPUT_NEURON_MODEL;
			} else {
				// Hidden Layer (0 < index < # layers - 1)
				action = Actions.UPDATE_SIDEBAR_CONFIG_HIDDEN_LAYER_MODEL;
			}
		}
		ActionController.get().doAction(
				action,
				new PropertyChangeEvent(this, SidebarModel.P.inputNeurons
						.name(), layerSize, layerSize + 1));
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	/*
	 * Vertex model
	 */

	public void setModel(Neuron model) {
		this.model = model;
	}

	public Neuron getModel() {
		return model;
	}

	public void setLayer(int index) {
		// TODO Get layer by its index
		Layer layer = new Layer();
		layer.setIndex(index);
		this.model.setLayer(layer);
	}

	public int getLayer() {
		if (this.model == null) {
			return -1;
		}
		return this.model.getLayer().getIndex();
	}

	public void setValue(Double value) {
		this.model.setOutputValue(value);
	}

	public Double getValue() {
		if (this.model == null) {
			return null;
		}
		return this.model.getValue();
	}

	public void remove() {
		LayerController<Layer> layerController = LayerController.getInstance();
		int layerIndex = getLayer();
		int layerSize = layerController.getVerticesInLayer(layerIndex).size();

		// Input layer (index = 0)
		Actions action = Actions.UPDATE_SIDEBAR_CONFIG_INPUT_NEURON_MODEL;
		if (layerIndex > 0) {
			if (layerIndex == layerController.getLayers().size() - 1) {
				// Output Layer (index = # layers - 1)
				action = Actions.UPDATE_SIDEBAR_CONFIG_OUTPUT_NEURON_MODEL;
			} else {
				// Hidden Layer (0 < index < # layers - 1)
				action = Actions.UPDATE_SIDEBAR_CONFIG_HIDDEN_LAYER_MODEL;
			}
		}
		ActionController.get().doAction(
				action,
				new PropertyChangeEvent(this, SidebarModel.P.inputNeurons
						.name(), layerSize, layerSize - 1));
	}

	@Override
	public String toString() {
		if (df == null) {
			df = new DecimalFormat(
					Settings.properties.getProperty("gui.decimalFormat"),
					Settings.decimalSymbols);
		}
		String value = df.format(getValue());
		return "#" + index + " (" + value + ")";
	}

	@Override
	public int compareTo(Vertex v) {
		return getIndex() - v.getIndex();
	}

}
