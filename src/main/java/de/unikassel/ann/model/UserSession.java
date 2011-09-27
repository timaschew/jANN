/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.model;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.io.NetIO;

/**
 * @author anton
 * 
 */
public class UserSession {

	public SidebarModel sidebarModel;

	private String name;

	private NetConfig config;

	private static Set<String> names;

	/**
	 * Should only called by Settings class
	 * 
	 * @param name
	 */
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

	public void loadNetworkFromFile(final File file) {
		loadNetworkFromFile(file, true, true, true);
	}

	/**
	 * @param file
	 * @param training
	 * @param synapse
	 * @param topo
	 */
	public void loadNetworkFromFile(final File file, final boolean topo, final boolean synapse, final boolean training) {
		NetIO reader = new NetIO();
		try {
			reader.readConfigFile(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		NetConfig netConfig = reader.generateNetwork(topo, synapse, training);
		if (topo) {
			Settings.getInstance().createNewSession(file.getName());
		}

		Settings.getInstance().getCurrentSession().setNetworkConfig(netConfig);
		Main.instance.getGraphLayoutViewer().renderNetwork(netConfig.getNetwork());
		Main.instance.initSidebarPanel();

	}

}
