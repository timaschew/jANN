package de.unikassel.ann.model;

public class DataPair {
	
	private Double[] input;
	private Double[] ideal;
	
	public DataPair(Double[] input, Double[] ideal) {
		this.input = input;
		this.ideal = ideal;
	}
	
	public Double[] getInput() {
		return input;
	}
	
	public Double[] getIdeal() {
		return ideal;
	}
	
	

}
