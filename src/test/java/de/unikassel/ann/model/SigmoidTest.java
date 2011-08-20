package de.unikassel.ann.model;

import org.junit.Test;

import de.unikassel.ann.model.func.ActivationFunction;
import de.unikassel.ann.model.func.SigmoidFunction;
import de.unikassel.ann.model.func.TanHFunction;

public class SigmoidTest {

	@Test
	public void testEfunc() {
		
		ActivationFunction[] functions = 
			new ActivationFunction[] {new SigmoidFunction(), new TanHFunction()};
		
		for (ActivationFunction s : functions) {
			for (double d = -1; d<= 1.0; d+= 0.2) {
				System.out.println(s.activate(d));
			}
			System.out.println("--");

			for (double d = -1; d<= 1.0; d+= 0.2) {
				System.out.println(s.derivate(d));
			}
			System.out.println("######");
		}
	}
}
