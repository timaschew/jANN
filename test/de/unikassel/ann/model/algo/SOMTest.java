package de.unikassel.ann.model.algo;

import org.junit.Test;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.SomNetwork;
import de.unikassel.mdda.MDDAPseudo;

public class SOMTest {
	
	@Test
	public void testSOM() {
		
		SomNetwork som = new SomNetwork(3, 8,8);
		
		som.train();
		
	}

}
