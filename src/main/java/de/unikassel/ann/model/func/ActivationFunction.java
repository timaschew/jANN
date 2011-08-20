package de.unikassel.ann.model.func;

public interface ActivationFunction {
	
	public Double activate (Double x);
	
	public Double derivate(Double x);
	
}
