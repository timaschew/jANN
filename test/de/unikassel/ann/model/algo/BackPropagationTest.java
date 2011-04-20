package de.unikassel.ann.model.algo;

import org.junit.Test;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.SigmoidFunction;

public class BackPropagationTest {
	
	@Test
	public void testForwardPass() {
		
		Network net = NetworkFactory.createNetwork(2, new int[]{2}, 1, new SigmoidFunction(), true);
		
		DataPair pair1 = new DataPair(new Double[] {0d,0d}, new Double[]{0d});
		DataPair pair2 = new DataPair(new Double[] {0d,1d}, new Double[]{1d});
		DataPair pair3 = new DataPair(new Double[] {1d,0d}, new Double[]{1d});
		DataPair pair4 = new DataPair(new Double[] {1d,1d}, new Double[]{0d});
		
		DataPairSet set = new DataPairSet();
		set.addPair(pair1);
		set.addPair(pair2);
		set.addPair(pair3);
		set.addPair(pair4);
		
		BackPropagation.forwardStep(net, pair1);
		net.printSynapses();
		
		BackPropagation.backwardStep(net, pair1);
		net.printSynapses();
		
		for (int i=0; i<50000; i++) {
			BackPropagation.train(net, set);
		}
		
		System.out.println();
		BackPropagation.printStep(net, pair1);
		BackPropagation.printStep(net, pair2);
		BackPropagation.printStep(net, pair3);
		BackPropagation.printStep(net, pair4);
		
	}

}
