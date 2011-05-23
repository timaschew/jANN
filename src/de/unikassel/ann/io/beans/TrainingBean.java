package de.unikassel.ann.io.beans;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class TrainingBean {
	
	protected static int outputSize = -1;
	protected static int inputSize = -1;
	protected Double[] input;
	protected Double[] output;
	
	public TrainingBean () {
		if (inputSize > 0 && outputSize > 0) {
			input = new Double[inputSize];
			output = new Double[outputSize];
		}
	}
	
	public void setStaticSize(int i, int o) {
		inputSize = i;
		outputSize = o;
	}
	 
	public int getInputSize() {
		return inputSize;
	}

	public int getOutputSize() {
		return outputSize;
	}
	
	public Double getInput(int index) {
		return input[index];
	}

	public Double getOutput(int index) {
		return output[index];
	}
	
	public Double[] getInput() {
		return input;
	}

	public Double[] getOutput() {
		return output;
	}
	
	public void setInput(int index, Double val) {
		input[index] = val;
	}

	public void setOutput(int index, Double val) {
		output[index] = val;
	}
	
	public void setInput(Double[] input) {
		this.input = input;
	}
	
	public void setOutput(Double[] output) {
		this.output = output;
	}
	
	@Override
	public String toString() {
		NumberFormat fmt = new DecimalFormat("#.######");
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<input.length; i++) {
			sb.append(fmt.format(input[i]));
			sb.append(" ");
		}
		sb.append(" --> ");
		for (int i=0; i<output.length; i++) {
			sb.append(fmt.format(output[i]));
			sb.append(" ");
		}
		sb.append("\n");
		return sb.toString();
	}
}