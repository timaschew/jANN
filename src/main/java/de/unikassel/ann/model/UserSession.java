/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.model;

import java.util.Set;
import java.util.TreeSet;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.model.func.ActivationFunction;
import de.unikassel.ann.model.func.SigmoidFunction;

/**
 * @author anton
 * 
 */
public class UserSession {

	private ActivationFunction defaultFunction = new SigmoidFunction();

	private Network network;

	public SidebarModel sidebarModel;

	private String name;

	private static Set<String> names;

	public UserSession() {
		this("Neu");
	}

	public UserSession(final String name) {
		initName(name);
		initDefaultNetwork();
		sidebarModel = new SidebarModel();
	}

	/**
	 * Creates user session when name is null <br>
	 * unnamed, unnamed(1), unnamed(2) ...
	 * 
	 * @param name
	 */
	private void initName(final String name) {
		if (names == null) {
			names = new TreeSet<String>();
		}
		int count = 1;
		String realName = name;
		while (names.contains(realName)) {
			realName = name + "(" + count + ")";
			count++;
		}
		names.add(realName);
		this.name = realName;
	}

	/**
	 * 
	 */
	private void initDefaultNetwork() {
		NetConfig config = new NetConfig();
		config.addLayer(1, false, defaultFunction); // input
		config.addLayer(1, false, defaultFunction); // output

	}

	/**
	 * @return the defaultFunction
	 */
	public ActivationFunction getDefaultFunction() {
		return defaultFunction;
	}

	/**
	 * @param defaultFunction
	 *          the defaultFunction to set
	 */
	public void setDefaultFunction(final ActivationFunction defaultFunction) {
		this.defaultFunction = defaultFunction;
	}

	/**
	 * @return the network
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * @param network
	 *          the network to set
	 */
	public void setNetwork(final Network network) {
		this.network = network;
	}

	@Override
	public String toString() {
		return name;
	}

}
