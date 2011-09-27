/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.io.NetIO;
import de.unikassel.ann.model.UserSession;
import de.unikassel.ann.util.XMLResourceBundleControl;

/**
 * @author anton
 * 
 */
public class Settings {

	/*
	 * public fields
	 */
	public static ResourceBundle i18n;
	public static Properties properties;

	/**
	 * Returns the locale specific decimal sperator, grouping seperator etc.
	 */
	public static DecimalFormatSymbols decimalSymbols;
	public static Locale locale;

	private List<UserSession> sessionList;
	private UserSession currentSession;
	/**
	 * Hack to call Setings.xyz instead of Settings.getInstance().xyz
	 */
	private static Settings instance = getInstance();

	/**
	 * Catch the missing resource exception if the key does not exist
	 */
	public static String getI18n(final String key) {
		return getI18n(key, null);
	}

	/**
	 * Catch the missing resource exception if the key does not exist and return the default string
	 */
	public static String getI18n(final String key, final String defaulString) {
		try {
			return i18n.getString(key);
		} catch (MissingResourceException e) {
			return defaulString;
		}
	}

	private Settings() {
		i18n = ResourceBundle.getBundle("langpack", new XMLResourceBundleControl());

		properties = new Properties();

		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			properties.load(inputStream);
		} catch (IOException e) {
			System.err.println("could not load property file");
			e.printStackTrace();
		}

		locale = new Locale(properties.getProperty("gui.locale"));
		decimalSymbols = DecimalFormatSymbols.getInstance(locale);

		sessionList = new ArrayList<UserSession>();

		createNewSession(getI18n("session.initial.name"));
	}

	public UserSession createNewSession(final String name) {
		return createNewSession(name, null);
	}

	public UserSession createNewSession(final String name, final NetConfig cfg) {
		UserSession session = null;
		if (name != null) {
			session = new UserSession(name, cfg);
		} else {
			session = new UserSession(getI18n("session.initial.name"), cfg);
		}
		sessionList.add(session);
		currentSession = session;

		// create new instances for jung and sidebar
		// and update

		currentSession.getNetworkConfig().getNetwork().removeAllPropertyChangeListeners();
		if (Main.instance != null) {
			Main.instance.initSidebarPanel();
		}

		// Add GraphController as listener
		if (GraphController.getInstance().isInitialized()) {
			GraphController.getInstance().reset();
			GraphController.getInstance().renderNetwork(currentSession.getNetworkConfig().getNetwork());
		}
		currentSession.getNetworkConfig().getNetwork().addPropertyChangeListener(GraphController.getInstance());
		return session;
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
			Settings.getInstance().createNewSession(file.getName(), netConfig);
		}
	}

	public List<UserSession> getUserSessions() {
		return sessionList;
	}

	public UserSession getCurrentSession() {
		return currentSession;
	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

}
