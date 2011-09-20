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
import java.util.ArrayList;
import java.util.List;

import de.unikassel.ann.controller.ActionController;
import de.unikassel.ann.controller.Actions;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.sidebar.TopologyPanel;
import de.unikassel.ann.model.func.ActivationFunction;

/**
 * @author anton
 * 
 */
public class SidebarModel {

	private PropertyChangeSupport pcs;

	public enum P {
		inputNeurons, outputNeurons, hiddenLayers, hiddenNeurons, topology, inputBias, hiddenBias, mouseModi
	};

	private Integer inputNeurons = 0;
	private Integer outputNeurons = 0;
	private Integer hiddenLayers = 0;
	private List<Integer> hiddenNeurons;
	private List<Boolean> hiddenBias;
	private boolean inputBias;
	private ActionController ac;
	public Neuron selectedNeuron;
	public Synapse selectedSynapse;
	public ActivationFunction selectedActivation;

	public SidebarModel() {
		pcs = new PropertyChangeSupport(this);
		hiddenNeurons = new ArrayList<Integer>();
		hiddenBias = new ArrayList<Boolean>();
		initChangeListener();
		ac = ActionController.get();
	}

	public Neuron getSelectedNeuron() {
		return selectedNeuron;
	}

	public void setSelectedNeuron(final Neuron selectedNeuron) {
		this.selectedNeuron = selectedNeuron;
	}

	public Synapse getSelectedSynapse() {
		return selectedSynapse;
	}

	public void reset() {
		// TODO set sidebar panels to their default values
	}

	public void setSelectedSynapse(final Synapse selectedSynapse) {
		this.selectedSynapse = selectedSynapse;
	}

	public ActivationFunction getSelectedActivation() {
		return selectedActivation;
	}

	public void setSelectedActivation(final ActivationFunction selectedActivation) {
		this.selectedActivation = selectedActivation;
	}

	public Boolean getInputBias() {
		return inputBias;
	}

	public void setInputBias(final Boolean inputBia) {
		// wenn der neue Bias true ist, dann heißt es, dass der alte
		// 0 war, somit wird der oldvalue bestimmt
		Integer oldValue;
		if (inputBia) {
			oldValue = 0;
			pcs.firePropertyChange(P.inputBias.name(), oldValue, inputBia);
		} else {
			oldValue = 1;
			pcs.firePropertyChange(P.inputBias.name(), oldValue, inputBia);
		}

	}

	public List<Boolean> getHiddenBias() {
		return hiddenBias;
	}

	public void setHiddenBias(final Integer layer, final Boolean hiddenBia) {
		Boolean oldValue = hiddenBias.get(layer);
		hiddenBias.add(layer, hiddenBia);
		pcs.firePropertyChange(P.hiddenBias.name(), oldValue, hiddenBia);

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
		pcs.addPropertyChangeListener(P.hiddenBias.name(), new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_JUNG_GRAPH, evt);
			}
		});
		pcs.addPropertyChangeListener(P.inputBias.name(), new PropertyChangeListener() {
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
		for (int i = oldValue; i < hiddenLayers; i++) {
			// i is relaltive hidden layer index !!!!!
			addHiddenNeurons(i, 1);
		}
	}

	/**
	 * RELATIVE INDEX FOR HIDDEN LAYER !
	 * 
	 * @return the hiddenNeurons
	 */
	public List<Integer> getHiddenNeurons() {
		return hiddenNeurons;
	}

	/**
	 * RELATIVE INDEX FOR HIDDEN LAYER !
	 * 
	 * @param hiddenNeurons
	 *          the hiddenNeurons to set
	 */
	public void addHiddenNeurons(final Integer layer, final Integer neuronCount) {
		if (layer < 0 || neuronCount < 0) {
			return;
		}
		Integer oldValue = hiddenNeurons.get(layer);
		hiddenNeurons.add(layer, neuronCount);
		pcs.firePropertyChange(P.hiddenNeurons.name(), oldValue, neuronCount);
	}

	public void createNetwork() {
		// TODO create Network
	}

}
