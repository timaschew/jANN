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

/**
 * @author anton
 * 
 */
public class SidebarModel {

	private PropertyChangeSupport pcs;

	public enum P {
		inputNeurons, outputNeurons, hiddenLayers, hiddenNeurons
	};

	private Integer inputNeurons = 1;
	private Integer outputNeurons = 1;
	private Integer hiddenLayers = 0;
	private Map<Integer, Integer> hiddenNeurons;
	private Map<Integer, Boolean> bias;
	private ActionController ac;

	public SidebarModel() {
		pcs = new PropertyChangeSupport(this);
		hiddenNeurons = new HashMap<Integer, Integer>();
		bias = new HashMap<Integer, Boolean>();
		initChangeListener();
		ac = ActionController.get();
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
		if (inputNeurons < 1) {
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
		if (outputNeurons < 1) {
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
	
	/**
	 * @return the bias
	 */
	public Map<Integer, Boolean> getBias() {
		return bias;
	}

	/**
	 * @param bias
	 * 			the bias to set
	 */
	public void setBias(Map<Integer, Boolean> bias) {
		this.bias = bias;
	}

}
