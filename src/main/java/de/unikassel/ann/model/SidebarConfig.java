/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import de.unikassel.ann.controller.ActionController;
import de.unikassel.ann.controller.Actions;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.sidebar.TopologyPanel;

/**
 * @author anton
 * 
 */
public class SidebarConfig {

	private PropertyChangeSupport pcs;

	public enum P {
		inputNeurons, outputNeurons, hiddenLayers, hiddenNeurons, topology
	};

	private Integer inputNeurons = 0;
	private Integer outputNeurons = 0;
	private Integer hiddenLayers = 0;
	private Map<Integer, Integer> hiddenNeurons;
	private ActionController ac;

	public SidebarConfig() {
		pcs = new PropertyChangeSupport(this);
		hiddenNeurons = new HashMap<Integer, Integer>();
		initChangeListener();
		ac = ActionController.get();
	}

	public Integer getMouseInsertLayer() {
		TopologyPanel topoPanel = Main.instance.sideBar.topolgyPanel;
		if (topoPanel.mouseInputRB.isSelected()) {
			return 0;
		} else if (topoPanel.mouseOutputRB.isSelected()) {
			// input + hidden + 1 = output
			return hiddenLayers + 1;
		} else if (topoPanel.mouseHiddenRB.isSelected()) {
			Integer selectedHiddenLayer = (Integer) topoPanel.comboBoxHiddenMausModus.getSelectedItem();
			return selectedHiddenLayer;
		}
		throw new IllegalAccessError("radio buttons for insert mode are all deselected");
	}

	/**
	 * 
	 */
	private void initChangeListener() {
		// fire for all property changes
		pcs.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_SIDEBAR_TOPOLOGY_VIEW, evt);
			}
		});

		pcs.addPropertyChangeListener(P.inputNeurons.name(), new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_JUNG_GRAPH, evt);
			}
		});
		pcs.addPropertyChangeListener(P.hiddenNeurons.name(), new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_JUNG_GRAPH, evt);
			}
		});
		pcs.addPropertyChangeListener(P.outputNeurons.name(), new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_JUNG_GRAPH, evt);
			}
		});
		pcs.addPropertyChangeListener(P.hiddenLayers.name(), new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_JUNG_GRAPH, evt);
			}
		});

	}

	// public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
	// pcs.addPropertyChangeListener(propertyName, listener);
	// }

	/**
	 * @return the inputNeurons
	 */
	public Integer getInputNeurons() {
		return inputNeurons;
	}

	/**
	 * @param inputNeurons
	 *          the inputNeurons to set
	 */
	public void setInputNeurons(final Integer inputNeurons) {
		if (inputNeurons < 0) {
			return;
		}
		Integer oldValue = this.inputNeurons;
		this.inputNeurons = inputNeurons;
		pcs.firePropertyChange(P.inputNeurons.name(), oldValue, inputNeurons);
	}

	/**
	 * @return the outputNeurons
	 */
	public Integer getOutputNeurons() {
		return outputNeurons;
	}

	/**
	 * @param outputNeurons
	 *          the outputNeurons to set
	 */
	public void setOutputNeurons(final Integer outputNeurons) {
		if (outputNeurons < 0) {
			return;
		}
		Integer oldValue = this.outputNeurons;
		this.outputNeurons = outputNeurons;
		pcs.firePropertyChange(P.outputNeurons.name(), oldValue, outputNeurons);
	}

	/**
	 * @return the hiddenLayers
	 */
	public Integer getHiddenLayers() {
		return hiddenLayers;
	}

	/**
	 * @param hiddenLayers
	 *          the hiddenLayers to set
	 */
	public void setHiddenLayers(final Integer hiddenLayers) {
		if (hiddenLayers < 0) {
			return;
		}
		Integer oldValue = this.hiddenLayers;
		this.hiddenLayers = hiddenLayers;
		pcs.firePropertyChange(P.hiddenLayers.name(), oldValue, hiddenLayers);
	}

	/**
	 * @return the hiddenNeurons
	 */
	public Map<Integer, Integer> getHiddenNeurons() {
		return hiddenNeurons;
	}

	/**
	 * @param hiddenNeurons
	 *          the hiddenNeurons to set
	 */
	public void addHiddenNeurons(final Integer layer, final Integer neuronCount) {
		if (layer < 0 || neuronCount < 0) {
			return;
		}
		Integer oldValue = hiddenNeurons.get(layer);
		hiddenNeurons.put(layer, neuronCount);
		pcs.firePropertyChange(P.hiddenNeurons.name(), oldValue, neuronCount);
	}

}
