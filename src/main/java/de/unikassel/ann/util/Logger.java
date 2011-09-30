/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import de.unikassel.ann.gui.Main;

/**
 * @author anton
 * 
 */
public class Logger {

	private final static int DEBUG = 0;
	private final static int INFO = 2;
	private final static int WARN = 3;
	private final static int ERROR = 4;

	private static Map<String, Integer> map = new HashMap<String, Integer>();

	private static int DEFAULT_LOG_LEVEL;
	private static boolean init = false;

	public static void init() {
		if (init) {
			return;
		}
		Properties properties = new Properties();
		try {
			InputStream inputStream = Logger.class.getClassLoader().getResourceAsStream("log.properties");
			properties.load(inputStream);
		} catch (IOException e) {
			System.err.println("could not load log.properties file");
			e.printStackTrace();
		}
		for (Entry<Object, Object> e : properties.entrySet()) {
			Integer value = null;
			if (e.getValue().equals("DEBUG")) {
				value = DEBUG;
			} else if (e.getValue().equals("INFO")) {
				value = INFO;
			} else if (e.getValue().equals("WARN")) {
				value = WARN;
			} else if (e.getValue().equals("ERROR")) {
				value = ERROR;
			} else {
				System.err.println("level " + e.getValue() + " is not defined in log.properties");
			}
			map.put((String) e.getKey(), value);
		}
		DEFAULT_LOG_LEVEL = map.get("default");
		init = true;
	}

	private static void log(final Class<?> clazz, final Integer level, final String message, final Object... params) {
		String finalMsg = StringReplaceHelper.replace(message, params);
		log(clazz, level, finalMsg);
	}

	private static void log(final Class<?> clazz, final Integer level, final String msg) {
		// ignore inner classes
		String className = clazz.getName().replaceFirst("\\$.*", "");
		Integer configLevel = map.get(className);
		if (configLevel == null) {
			configLevel = DEFAULT_LOG_LEVEL;
		}
		// log only if level match from config file
		if (level >= configLevel) {
			StringBuilder sb = new StringBuilder();
			if (level == WARN) {
				sb.append("Warn: ").append(msg).append("\n");
				Main.instance.updateTextArea(sb.toString(), "warn");
			} else if (level == ERROR) {
				sb.append("Error: ").append(msg).append("\n");
				Main.instance.updateTextArea(sb.toString(), "error");
			} else if (level == DEBUG) {
				sb.append("Debug: ").append(msg).append("\n");
				Main.instance.updateTextArea(sb.toString(), "regular");
			} else if (level == INFO) {
				sb.append("Info: ").append(msg).append("\n");
				Main.instance.updateTextArea(sb.toString(), "regular");
			}
		}
	}

	public static void debug(final Class<?> clazz, final String message, final Object... params) {
		log(clazz, DEBUG, message, params);
	}

	public static void debug(final Class<?> clazz, final String message) {
		log(clazz, DEBUG, message);
	}

	public static void info(final Class<?> clazz, final String message, final Object... params) {
		log(clazz, INFO, message, params);
	}

	public static void info(final Class<?> clazz, final String message) {
		log(clazz, INFO, message);
	}

	public static void warn(final Class<?> clazz, final String message, final Object... params) {
		log(clazz, WARN, message, params);
	}

	public static void warn(final Class<?> clazz, final String message) {
		log(clazz, WARN, message);
	}

	public static void error(final Class<?> clazz, final String message, final Object... params) {
		log(clazz, ERROR, message, params);
	}

	public static void error(final Class<?> clazz, final String message) {
		log(clazz, ERROR, message);
	}

}
