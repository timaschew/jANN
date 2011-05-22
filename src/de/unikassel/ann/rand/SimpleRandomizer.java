package de.unikassel.ann.rand;

import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Synapse;

public class SimpleRandomizer implements Randomizer {

	private Double maximum;
	private Double minimum;

	public SimpleRandomizer(Double min, Double max) {
		this.minimum = min;
		this.maximum = max;
	}
	
	@Override
	public void randomize(Network net) {

		
	}

	@Override
	public void randomize(Double beta, Synapse s) {
		// TODO Auto-generated method stub
		
	}

}
