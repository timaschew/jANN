package de.unikassel.ann.config;

import de.unikassel.ann.model.FlatSynapses;

public class Topology {
	
	private Boolean strictForwardFeedback;
	
	private FlatSynapses synapses;
	
	public Topology() {
		this(true);
	}
	
	public Topology(boolean strictFF) {
		strictForwardFeedback = strictFF;
	}
	
	/**
	 * Disbales strict forward feebback topology!
	 * @param s
	 */
	public void setSynapses(FlatSynapses s) {
		strictForwardFeedback = false;
		synapses = s;
	}

	public boolean isStrictFF() {
		return strictForwardFeedback;
	}

	public FlatSynapses getSynapses() {
		return synapses;
	}
	

}
