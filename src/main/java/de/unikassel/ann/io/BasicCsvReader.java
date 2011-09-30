package de.unikassel.ann.io;

import java.io.BufferedReader;
import java.util.logging.Logger;

import org.supercsv.exception.SuperCSVException;
import org.supercsv.prefs.CsvPreference;

public class BasicCsvReader {

	static protected Logger log = Logger.getAnonymousLogger();

	static protected CsvPreference pref = new CsvPreference('\"', ';', "\r\n");

	static public boolean checkEndOfFile(final Exception e, final BufferedReader bufferedReader) {
		if (e instanceof SuperCSVException) {
			if (((SuperCSVException) e).getCsvContext().lineSource.get(0).toString().equals("#>")) {
				return true;
			}
		}
		e.printStackTrace();
		return false;
	}
}
