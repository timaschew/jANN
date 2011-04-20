package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

	private Layer layer;
	
	private Integer index = -1;
	
	private ActivationFunction function;

	private Double activationValue;
	
	private Double inputValue;
	
	private Double error;
	
	private List<Synapse> inputSynapses;
	
	private List<Synapse> outputSynapses;
	
	public Neuron(ActivationFunction function, Double inputValue) {
		this.function = function;
		this.inputValue = inputValue;
		inputSynapses = new ArrayList<Synapse>();
		outputSynapses = new ArrayList<Synapse>();

	}
	
	public Neuron(ActivationFunction function) {
		this(function, -1d);
	}

	public List<Synapse> getInputSynapses() {
		return inputSynapses;
	}
	
	public List<Synapse> getOutputSynapses() {
		return outputSynapses;
	}

	public Double getActivationValue() {
		return activationValue;
	}

	public void setInputValue(Double value) {
		inputValue = value;
	}
	
	public void addOutputSynapse(Synapse synapse) {
		outputSynapses.add(synapse);
		if ((synapse.getFromNeuron() != null && synapse.getFromNeuron().equals(this)) == false) {
			synapse.setFromNeuron(this);
		}
	}
	
	public void addInputSynapse(Synapse synapse) {
		inputSynapses.add(synapse);
		if ((synapse.getToNeuron() != null && synapse.getToNeuron().equals(this)) == false) {
			synapse.setToNeuron(this);
		}
	}
	
	public void setError(double error) {
		this.error = error;
	}

	/**
	 * Calculates the activation function with the value and set activatinoValue<br>
	 * activatinoValue = activationFunction(value)
	 */
	public void activate() {
		activationValue = function.calc(inputValue);
	}
	

	public void setLayer(Layer layer) {
		this.layer = layer;
		if (layer.getNeurons() != null && layer.getNeurons().contains(this) == false) {
			layer.addNeuron(this);
		}
		
	}

	public Layer getLayer() {
		return layer;
	}

	public void setIndex(int size) {
		index = size;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getPosition());
		sb.append(" inputValue=  ");
		
		sb.append(inputValue == null ? "N/A" : inputValue);
		sb.append(" / f(x) = ");
		sb.append(activationValue == null ? "N/A" : activationValue);
		
		return sb.toString();
	}
	
	public String getPosition() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(layer.getIndex());
		sb.append("]");
		
		sb.append("[");
		sb.append(index);
		sb.append("]");
		return sb.toString();
	}

	public double getError() {
		return error;
	}


}
