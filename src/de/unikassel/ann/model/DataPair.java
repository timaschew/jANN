package de.unikassel.ann.model;

public class DataPair {
	
	private Double[] input;
	private Double[] output;
	
	public DataPair(Double[] input, Double[] output) {
		this.input = input;
		this.output = output;
	}
	
	public Double[] getInput() {
		return input;
	}
	
	public Double[] getOutput() {
		return output;
	}

}
