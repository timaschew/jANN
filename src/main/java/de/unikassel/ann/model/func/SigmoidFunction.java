package de.unikassel.ann.model.func;

public class SigmoidFunction implements ActivationFunction {

	@Override
	public Double activate(Double x) {
		// 1 / 1 + e^-x
		return 1 / (1 + Math.pow(Math.E, -x));
	}
	

//	public void activate(Double[] array) {
//		// 1 / 1 + e^-x
//		for (Double x : array) {
//			x = 1 / (1 + Math.pow(Math.E, -x));
//		}
//	}

	@Override
	public Double derivate(Double x) {
		return x * (1.0 - x);
	}
	
}
