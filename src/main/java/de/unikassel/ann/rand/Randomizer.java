package de.unikassel.ann.rand;

import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Synapse;

public interface Randomizer {

	public void randomize(Network net);
	
	public void randomize(Double beta, Synapse s);
	
}
