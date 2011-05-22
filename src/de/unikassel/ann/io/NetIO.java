package de.unikassel.ann.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCSVException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.gen.DynamicBeanCreator;
import de.unikassel.ann.io.beans.SynapseBean;
import de.unikassel.ann.io.beans.TopologyBean;
import de.unikassel.ann.io.beans.TrainingBean;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.FlatSynapses;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.strategy.MaxLearnIterationsStrategy;


public class NetIO {
	
	static Logger log = Logger.getAnonymousLogger();
	
	static final int MAX_BUFFER_SIZE = 1024*1024;

	static CsvPreference pref = new CsvPreference('\"', ';', "\r\n");
	static String[] header2beanMapping;
	static CellProcessor[] processor;
	
	List<TrainingBean> trainigBeanList = null;
	List<TopologyBean> topoBeanList = null;
	List<SynapseBean> synapsesBanList = null;

	public void readConfigFile(final File file)
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
		
		String line;

		while ((line = bufferedReader.readLine()) != null) {
			if (line != null && line.startsWith("#<training")) {
				bufferedReader.mark(MAX_BUFFER_SIZE);
				trainigBeanList = TrainingReader.readData(bufferedReader);
				bufferedReader.reset();
			} else if (line != null && line.startsWith("#<topology")) {
				bufferedReader.mark(MAX_BUFFER_SIZE);
				topoBeanList = TopologyBeanReader.readData(bufferedReader);
				bufferedReader.reset();
			} else if (line != null && line.startsWith("#<synapses")) {
				bufferedReader.mark(MAX_BUFFER_SIZE);
				synapsesBanList = SynapseBeanReader.readData(bufferedReader);
				bufferedReader.reset();
			} 
		}
		
		
		if (CollectionUtils.isNotEmpty(topoBeanList)) {
			Collections.sort(topoBeanList);
			int firstId = topoBeanList.get(0).getId();
			if (firstId != 0) {
				throw new IllegalArgumentException("id should start with 0 / zero, but was :"+firstId);
			}

			for (int i=0; i<topoBeanList.size(); i++) {
				int id = topoBeanList.get(i).getId();
				if (id != i) {
					throw new IllegalArgumentException("ids for neurons must be unique, duplicate id found: "+id);
				}
			}
		}
		
	}

	public NetConfig generateNetwork() {
		if (CollectionUtils.isNotEmpty(topoBeanList) && 
				CollectionUtils.isNotEmpty(synapsesBanList)) {
					
			NetConfig config = new NetConfig();
			config.getNetwork().finalizeFromFlatNet(topoBeanList, synapsesBanList);
		
			BackPropagation backProp = new BackPropagation();
			config.addTrainingModule(backProp);
			config.addWorkModule(backProp);
			config.addOrUpdateExisting(new MaxLearnIterationsStrategy(1000));
			
			return config;
			
		}
		return null;
			
		
	}

	public DataPairSet getTrainingSet() {
		if (ArrayUtils.isEmpty(trainigBeanList.toArray()) == false) {
			DataPairSet set = new DataPairSet();
			for (TrainingBean b : trainigBeanList) {
				DataPair pair = new DataPair(b.getInput(), b.getOutput());
				set.addPair(pair);
			}
			return set;
		}
		return null;
	}

	public void writeDataSet(File file, String string, boolean training) {
		
//		resultTrainigBeanList = createTraining(dataSet);
		
		StringBuilder sb = new StringBuilder();
		sb.append('#');
		sb.append(string);
		sb.append('\n');
		if (training) {
			sb.append("#<training\n");
		} else {
			sb.append("#<dataset\n");
		}
		TrainingBean firstBean = trainigBeanList.get(0);
		for (int i=0; i<firstBean.getInputSize(); i++) {
			sb.append("\"i\";");
		}
		for (int i=0; i<firstBean.getOutputSize()-1; i++) {
			sb.append("\"o\";");
		}
		sb.append("\"o\"\n");
		
		for (TrainingBean b : trainigBeanList) {
			sb.append(b.toString());
		}
		sb.append("#>\n\n");
		
		writeToFile(file, sb);
	}
	
	public void writeNet(File file, String string, NetConfig netConfig) {
		
		List<TopologyBean> resultTopologyList = createTopology(netConfig);
		List<SynapseBean> resultSynapseList = createSynapses(netConfig);
		
		StringBuilder sb = new StringBuilder();
		// topology
		sb.append('#');
		sb.append(string);
		sb.append('\n');
		sb.append("#<topology\n");

		sb.append("\"id\";\"layer\";\"bias\";\"function\"\n");
		for (TopologyBean b : resultTopologyList) {
			sb.append(b.toString());
		}
		sb.append("#>\n\n");
		
		// synapses
		sb.append("#<synapses\n");

		sb.append("\"from\";\"to\";\"value\";\"random\"\n");
		for (SynapseBean b : resultSynapseList) {
			sb.append(b.toString());
		}
		sb.append("#>\n\n");
		writeToFile(file, sb);
		
	}

	private List<TopologyBean> createTopology(NetConfig netConfig) {
		List<TopologyBean> list = new ArrayList<TopologyBean>();
		Network net = netConfig.getNetwork();
		List<Neuron> flatNet = net.asFlatNet();
		for (Neuron n : flatNet) {
			TopologyBean b = new TopologyBean();
			b.setBias(n.isBias());
			b.setFunction(n.getFunctionName());
			b.setId(n.getId());
			Layer layer = n.getLayer();
			if (layer.equals(net.getInputLayer())) {
				b.setLayer(-1);
			} else if (layer.equals(net.getOutputLayer())) {
				b.setLayer(-2);
			} else {
				// start hidden layer index with 0 (ignore input)
				b.setLayer(layer.getIndex()-1); 
			}
			list.add(b);
		}
		return list;
	}
	
	private List<SynapseBean> createSynapses(NetConfig netConfig) {
		List<SynapseBean> list = new ArrayList<SynapseBean>();
		Network net = netConfig.getNetwork();
		
		Double[][] fs = net.getSynapseFlatMatrix();
		for (int i=0; i<fs.length; i++) {
			for (int j=0; j<fs[i].length; j++) {
				Double val = fs[i][j];
				if (val != null) {
					SynapseBean b = new SynapseBean();
					b.setFrom(i);
					b.setTo(j);
					b.setRandom(false);
					b.setValue(val);
					list.add(b);
				}
			}
		}
		return list;
	}


	private void writeToFile(File file, StringBuilder sb) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			writer.write(sb.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	
	
	

}
