package de.unikassel.ann.algo;

import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Network;

public interface WorkModule {
	
	abstract public void work(Network net, DataPairSet testData);
	
	public boolean validateDataSet(Network net, DataPairSet testData);

}
