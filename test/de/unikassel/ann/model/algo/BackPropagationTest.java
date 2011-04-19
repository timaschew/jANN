package de.unikassel.ann.model.algo;

import org.junit.Test;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.SigmoidFunction;

public class BackPropagationTest {
	
	@Test
	public void testForwardPass() {
		
		Network net = NetworkFactory.createNetwork(2, new int[]{2}, 1, new SigmoidFunction(), false);
		
		DataPair pair = new DataPair(new Double[] {1d,1d}, new Double[]{1d});
		
		BackPropagation.forwardPass(net, pair);
		
		
	}

}
