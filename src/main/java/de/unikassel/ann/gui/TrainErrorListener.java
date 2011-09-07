package de.unikassel.ann.gui;

public interface TrainErrorListener {
	
	public void addError(Integer iteration, Double error);

	public void updateUI();
	
}
