package de.unikassel.ann.model.func;

public class TanHFunction implements ActivationFunction {

	@Override
	public Double activate(Double x) {
		return Math.tanh(x);
	}

	@Override
	public Double derivate(Double x) {
		return (1.0 - x * x);
	}
	
}
