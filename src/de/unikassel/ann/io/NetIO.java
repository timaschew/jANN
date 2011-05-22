package de.unikassel.ann.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.ArrayUtils;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCSVException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import de.unikassel.ann.gen.DynamicBeanCreator;
import de.unikassel.ann.io.beans.SynapseBean;
import de.unikassel.ann.io.beans.TrainingBean;


public class NetIO {
	
	Logger log = Logger.getAnonymousLogger();

	CsvPreference pref = new CsvPreference('\"', ';', "\r\n");
	String[] header2beanMapping;
	CellProcessor[] processor;

	public void readData(final File file)
			throws IOException, ClassNotFoundException {

		InputStream fis = null;
		try {
		  fis = new FileInputStream(file);
		} catch (final FileNotFoundException e) {
		  log.warning("could not open file "+file);
		}
		final InputStream fileInputStream = new BufferedInputStream(fis);

		if (fileInputStream.markSupported() == false) {
		  log.warning("mark/reset not supported!");
		  System.exit(0);
		}
		InputStreamReader inputStremReader = new InputStreamReader((fileInputStream));
		BufferedReader bufferedReader = new BufferedReader(inputStremReader);
		
		List<TrainingBean> trainigBeanList = null;
		List<SynapseBean> topoBeanList = null;
		List<TrainingBean> synapsesBanList = null;
		

		String line;

		while ((line = bufferedReader.readLine()) != null) {
			
			if (line != null && line.startsWith("#<training")) {
				trainigBeanList = TrainingReader.readData(bufferedReader);
			} else if (line != null && line.startsWith("#<topology")) {
				topoBeanList = null;
			} else if (line != null && line.startsWith("#<synapses")) {
				synapsesBanList = null;
			} 
		}
		
		System.out.println(trainigBeanList);
		
	}

	
	
	

}
