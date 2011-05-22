package de.unikassel.ann.factory;

import de.unikassel.ann.algo.WorkModule;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Network;

public class SomAlgorithm implements WorkModule {

	@Override
	public void work(Network net, DataPairSet testData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validateDataSet(Network net, DataPairSet testData) {
		// TODO Auto-generated method stub
		return false;
	}

}
