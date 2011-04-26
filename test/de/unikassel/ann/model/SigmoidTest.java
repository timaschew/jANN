package de.unikassel.ann.model;

import org.junit.Test;

import de.unikassel.ann.model.func.SigmoidFunction;

public class SigmoidTest {

	@Test
	public void testEfunc() {
		SigmoidFunction s = new SigmoidFunction();
		System.out.println(s.calc(0.0));
		System.out.println(s.calc(1.0));
		System.out.println(s.calc(2.0));
	}
}
