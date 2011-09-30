/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.util;

import java.text.DecimalFormat;

import de.unikassel.ann.controller.Settings;

/**
 * Format Helper to parse object to double value gracefully.
 * 
 * @author Way
 * 
 */
public class FormatHelper {

	/**
	 * Parse object to double.
	 * 
	 * @param obj
	 * @return Double
	 */
	public static Double parse2Double(final Object obj) {
		DecimalFormat df = new DecimalFormat(Settings.properties.getProperty("gui.decimalFormat"), Settings.decimalSymbols);
		String stringValue = df.format(obj);
		Double value = Double.parseDouble(stringValue.replace(',', '.'));
		return value;
	}

}
