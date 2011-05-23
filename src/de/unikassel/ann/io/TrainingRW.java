package de.unikassel.ann.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;

import de.unikassel.ann.gen.DynamicBeanCreator;
import de.unikassel.ann.gen.GeneratedXorBeanExample;
import de.unikassel.ann.io.beans.TopologyBean;
import de.unikassel.ann.io.beans.TrainingBean;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;

public class TrainingRW extends BasicCsvReader {

//	static String[] header2beanMapping;
//	static CellProcessor[] processor;
	
	/**
	 * header will looks like this if true: <pre>i0,i1,i2,o0,o1</pre><br>
	 * or like this if false: <pre>i,i,i,o,o</pre>
	 */
	public static boolean WRITE_INDEX_IN_HEADER = true;
	
	public static Class<?> generateBean(int inputSize, int outputSize) {
		String className = null;
		try {
			className = DynamicBeanCreator.createAndWriteClass(inputSize, outputSize, null);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// load class file
		ClassLoader classLoader = TrainingRW.class.getClassLoader();
		
		Class<?> loadedClass = null;
		try {
			loadedClass = classLoader.loadClass(className);
			Object dynamicBean = loadedClass.newInstance();
			TrainingBean bean = (TrainingBean) dynamicBean;
			bean.setStaticSize(inputSize, outputSize);
		} catch (ClassNotFoundException e) {
			System.err.println("could not load class: "+className);
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return loadedClass;
	}
	
	public static List<TrainingBean> readData(final BufferedReader bufferedReader)
			throws IOException, ClassNotFoundException {

		final List<TrainingBean> list = new ArrayList<TrainingBean>();
		
		final ICsvBeanReader reader = new CsvBeanReader(bufferedReader, pref);
		String[] header2beanMapping = reader.getCSVHeader(true);
		
		
		
		boolean wihtoutIndex = true;
		for (String s : header2beanMapping) {
			s = s.trim();
			wihtoutIndex = wihtoutIndex && (s.length() == 1);
		}
		
		int inputSize = 0;
		int outputSize = 0;
		int index = 0;
		for (String i_o : header2beanMapping) {
			if (i_o.charAt(0) == 'i' || i_o.charAt(0) == 'I') {
				header2beanMapping[index] = wihtoutIndex ? i_o+inputSize : i_o;
				inputSize++;
			} else if (i_o.charAt(0) == 'o' || i_o.charAt(0) == 'O' ) {
				header2beanMapping[index] = wihtoutIndex ? i_o+outputSize : i_o;
				outputSize++;
			}
			index++;
		}	
		
		CellProcessor[] processor = new CellProcessor[inputSize+outputSize];
		for (int i=0; i<inputSize+outputSize; i++) {
			processor[i] = new ParseDoubleUni();
		}
	
		Class<?> loadedClass = generateBean(inputSize, outputSize);
		TrainingBean instance;
		
		try {
			while ((instance = (TrainingBean) reader.read(loadedClass, header2beanMapping, processor)) != null) {
				list.add(instance);
			}
		} catch (Exception e) {
			checkEndOfFile(e, bufferedReader);
		}
		return list;
	}

	public static void writeData(DataPairSet set, File f, String title, boolean training) {
		
		List<TrainingBean> traininBeanList = convertToTrainingBeanList(set);
		
		int inputSize = set.getPairs().get(0).getInput().length;
		int outputSize = set.getPairs().get(0).getIdeal().length;
		CellProcessor[] processor = new CellProcessor[inputSize+outputSize];
		String[] header2beanMapping = new String[inputSize+outputSize];
		String[] header2Write = new String[inputSize+outputSize];
		for (int i=0; i<inputSize; i++) {
			processor[i] = new ConvertNullTo("null");
			header2beanMapping[i] = "i"+ i;
			header2Write[i] = "i"+ (WRITE_INDEX_IN_HEADER ? i : "");
		}
		for (int i=inputSize; i<inputSize+outputSize; i++) {
			processor[i] = new ConvertNullTo("null");
			header2beanMapping[i] = "o"+ (i-inputSize);
			header2Write[i] = "o"+ (WRITE_INDEX_IN_HEADER ? i : "");
		}

		FileWriter writer = null;
		try {
			writer = new FileWriter(f, true);
			writer.write("# "+title+"\n");
			if (training) {
				writer.write(NetIO.OPEN_TAG+NetIO.TRAINING_TAG+"\n");
			} else {
				writer.write(NetIO.OPEN_TAG+NetIO.DATASET+"\n");
			}
			
			writer.flush();
			ICsvBeanWriter beanWriter = new CsvBeanWriter(writer, pref);
			
			
			beanWriter.writeHeader(header2Write);
			
			for (TrainingBean b : traininBeanList) {
				beanWriter.write(b, header2beanMapping, processor);
			}
			// force to write the content to the file, flush only doesn' exist
			beanWriter.close(); 
			writer = new FileWriter(f, true);
			writer.write(NetIO.CLOSE_TAG+"\n\n");
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static List<TrainingBean> convertToTrainingBeanList(DataPairSet set) {
		int inputSize = set.getPairs().get(0).getInput().length;
		int outputSize = set.getPairs().get(0).getIdeal().length;
		
		List<TrainingBean> trainingBeanList = new ArrayList<TrainingBean>();
		
		String className = null;
		try {
			className = DynamicBeanCreator.createAndWriteClass(inputSize, outputSize, null);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// load class file
		ClassLoader classLoader = TrainingRW.class.getClassLoader();
		
		Class<?> loadedClass = null;
		try {
			loadedClass = classLoader.loadClass(className);
			Object dynamicBean = loadedClass.newInstance();
			TrainingBean bean = (TrainingBean) dynamicBean;
			bean.setStaticSize(inputSize, outputSize);
		} catch (ClassNotFoundException e) {
			System.err.println("could not load class: "+className);
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		for (DataPair p : set.getPairs()) {
			TrainingBean bean = null;
			try {
				bean = (TrainingBean) loadedClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			for (int i=0; i<p.getInput().length; i++) {
				bean.setInput(i, p.getInput()[i]);
			}
			for (int i=0; i<p.getIdeal().length; i++) {
				bean.setOutput(i, p.getIdeal()[i]);
			}
			trainingBeanList.add(bean);
		}
		return trainingBeanList;
	}
	
}
