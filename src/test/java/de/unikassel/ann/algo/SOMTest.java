package de.unikassel.ann.algo;

import org.junit.Test;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.SomNetwork;
import de.unikassel.mdda.MDDA;

public class SOMTest {
	
	@Test
	public void testSOM() {
		
		SomNetwork som = new SomNetwork(2, 5,5);
		
		som.printSynapses();
		
		som.train();
		
		som.printSynapses();
		
	}

}
