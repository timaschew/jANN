package de.unikassel.ann.vis;

public interface TrainErrorListener {
	
	public void addError(Integer iteration, Double error);

	public void updateUI();
	
}
