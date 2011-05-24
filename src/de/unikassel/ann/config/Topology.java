package de.unikassel.ann.config;

import de.unikassel.ann.model.SynapseMatrix;

public class Topology {
	
	private Boolean strictForwardFeedback;
	
	private SynapseMatrix synapses;
	
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
	public void setSynapses(SynapseMatrix s) {
		strictForwardFeedback = false;
		synapses = s;
	}

	public boolean isStrictFF() {
		return strictForwardFeedback;
	}

	public SynapseMatrix getSynapses() {
		return synapses;
	}
	

}
