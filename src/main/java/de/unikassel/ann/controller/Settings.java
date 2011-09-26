/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

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

		createNewSession("Session");
	}

	public UserSession createNewSession(final String name) {
		UserSession session = null;
		if (name != null) {
			session = new UserSession(name);
		} else {
			session = new UserSession();
		}
		sessionList.add(session);
		currentSession = session;
		return session;
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
