package de.unikassel.ann.io.beans;

public interface TrainingBean {
	
	public Double getInput(int index);
	public Double getOutput(int index);
	public int getInputSize();
	public int getOutputSize();

}