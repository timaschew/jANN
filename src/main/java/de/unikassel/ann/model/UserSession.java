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
import de.unikassel.ann.controller.GraphController;

/**
 * @author anton
 * 
 */
public class UserSession {

	public SidebarModel sidebarModel;

	private String name;

	private NetConfig config;

	private static Set<String> names;

	public UserSession() {
		this("Neu");
	}

	public UserSession(final String name) {
		initName(name);
		sidebarModel = new SidebarModel();
		config = new NetConfig();

		// Add GraphController as listener
		config.getNetwork().addPropertyChangeListener(GraphController.getInstance());

		// TODO add sidebar as listener

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

	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return the config
	 */
	public NetConfig getNetworkConfig() {
		return config;
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setNetworkConfig(final NetConfig config) {
		this.config = config;
	}

}
