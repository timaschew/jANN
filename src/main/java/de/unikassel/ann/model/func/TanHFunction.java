package de.unikassel.ann.model.func;

public class TanHFunction implements ActivationFunction {

	@Override
	public Double calc(Double x) {
		// 1 / 1 + e^-x
		return 1 / (1 + Math.pow(Math.E, -x));
//		return Math.tanh(x);
	}
	
}
