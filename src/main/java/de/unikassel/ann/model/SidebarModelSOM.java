/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import de.unikassel.ann.controller.ActionControllerSOM;
import de.unikassel.ann.controller.Actions;

/**
 * @author Sofia
 * 
 */
public class SidebarModelSOM {

	private PropertyChangeSupport pcs;
	public ActionControllerSOM ac;

	public Integer inputNeurons = 0;
	public Integer outputDimensions = 0;
	public List<Integer> outputNeuronsInDimension;
	public String pattern;

	public enum PSom {
		inputNeurons, outputNeurons, outputDimensions, outputNeuronsInDimension, pattern
	};

	public SidebarModelSOM() {
		pcs = new PropertyChangeSupport(this);
		outputNeuronsInDimension = new ArrayList<Integer>();
		initChangeListener();
		ac = ActionControllerSOM.get();
	}

	/**
	 * @return the inputNeurons
	 */
	public Integer getInputNeurons() {
		return inputNeurons;
	}

	/**
	 * @param inputNeurons
	 *            the inputNeurons to set
	 */
	public void setInputNeurons(final Integer inputNeurons) {
		Integer oldValue = this.inputNeurons;
		this.inputNeurons = inputNeurons;
		pcs.firePropertyChange(PSom.inputNeurons.name(), oldValue, inputNeurons);
	}

	/**
	 * @return the outputDimensions
	 */
	public Integer getOutputDimensions() {
		return outputDimensions;
	}

	/**
	 * @param outputDimensions
	 *            the outputDimensions to set
	 */
	public void setOutputDimensions(final Integer outputDimensions) {
		Integer oldValue = this.outputDimensions;
		this.outputDimensions = outputDimensions;
		pcs.firePropertyChange(PSom.outputDimensions.name(), oldValue, outputDimensions);
	}

	/**
	 * @return the outputNeuronsInDimension
	 */
	public List<Integer> getOutputNeuronsInDimension() {
		return outputNeuronsInDimension;
	}

	/**
	 * @param outputNeuronsInDimension
	 *            the outputNeuronsInDimension to set
	 */
	public void setOutputNeuronsInDimension(final Integer outputNeuroDim, final Integer dimension) {
		if (outputNeuroDim < 0 || dimension < 0) {
			return;
		}
		Integer oldValue = 0;
		if (outputNeuronsInDimension.size() <= outputNeuroDim) {
			outputNeuronsInDimension.add(dimension);
		} else {
			oldValue = outputNeuronsInDimension.get(outputNeuroDim);
			outputNeuronsInDimension.set(outputNeuroDim, dimension);
		}
		pcs.firePropertyChange(PSom.outputNeuronsInDimension.name(), oldValue, dimension);
	}

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern
	 *            the pattern to set
	 */
	public void setPattern(final String pattern) {
		String oldValue = this.pattern;
		this.pattern = pattern;
		pcs.firePropertyChange(PSom.pattern.name(), oldValue, pattern);
	}

	/**
	 * 
	 */
	private void initChangeListener() {
		pcs.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doActionSOM(Actions.UPDATE_SIDEBAR_TOPOLOGYSOM_VIEW, evt);
			}
		});

	}

}
