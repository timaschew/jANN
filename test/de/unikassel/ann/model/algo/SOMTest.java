package de.unikassel.ann.model.algo;

import org.junit.Test;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.model.Network;

public class SOMTest {
	
	@Test
	public void testSOM() {
		
		NetConfig netConfig = NetworkFactory.createSOM(2, 5, 5);
		Network net = netConfig.getNetwork();
		
	}

}
