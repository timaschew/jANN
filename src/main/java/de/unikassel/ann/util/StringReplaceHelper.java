/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.util;

import java.util.regex.Matcher;

/**
 * Replace multiple {} in a string wit the parameters
 * 
 * @author anton
 * 
 */
public class StringReplaceHelper {

	private final static String REGEX = "([{][}])";

	public static String replace(final String message, final Object... params) {
		String finalMsg = message;
		for (Object o : params) {
			try {
				// quoteReplacement
				String escaped = Matcher.quoteReplacement(o.toString());
				finalMsg = finalMsg.replaceFirst(REGEX, escaped);
			} catch (Exception e) {
				System.err.println("could not parse parameter message '" + message + "' with param: " + o);
				e.printStackTrace();
			}
		}
		return finalMsg;
	}

}
