package de.unikassel.ann.algo;

import org.junit.Test;

import de.unikassel.ann.model.SomNetwork;

public class SOMTest {

	@Test
	public void testSOM() {

		SomNetwork som = new SomNetwork(2.0, 5, 5);

		som.printSynapses();

		som.train(-1, 1);

		som.printSynapses();

	}

}
