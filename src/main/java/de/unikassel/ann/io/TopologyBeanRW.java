package de.unikassel.ann.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.Token;
import org.supercsv.cellprocessor.constraint.StrMinMax;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.io.beans.TopologyBean;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;

public class TopologyBeanRW extends BasicCsvReader {

	/* CSV */
	/* "id";"layer";"bias";"function" */
	
	static String[] header2beanMapping = new String[]{"id", "layer", "bias", "function"};
	static CellProcessor[] readProcessor = new CellProcessor[] {new ParseInt(), new ParseInt(), new ParseBool(), new StrMinMax(1, 50)};
	static CellProcessor[] writeProcessor = new CellProcessor[] {new ConvertNullTo("null"), new ConvertNullTo("null"), new ConvertNullTo("null"), new ConvertNullTo("null")};
	
	public static List<TopologyBean> readData(BufferedReader bufferedReader) {
		final List<TopologyBean> list = new ArrayList<TopologyBean>();
		final ICsvBeanReader reader = new CsvBeanReader(bufferedReader, pref);
		try {
			String[] header = reader.getCSVHeader(true);
			if (ArrayUtils.isEquals(header2beanMapping, header) == false) {
				throw new IllegalArgumentException("wrong header\n"+header);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		TopologyBean bean;
		
		try {
			while ((bean = reader.read(TopologyBean.class, header2beanMapping, readProcessor)) != null) {
				list.add(bean);
			}
		} catch (Exception e) {
			checkEndOfFile(e, bufferedReader);
		}
		return list;
	}
	
	public static void writeData(NetConfig netConfig, File f, String title) {
		
		List<TopologyBean> beanList = convertToBeanList(netConfig);
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(f, true);
			writer.write("# "+title+"\n");
			writer.write(NetIO.OPEN_TAG+NetIO.TOPOLOGY_TAG+"\n");
			writer.flush();
			ICsvBeanWriter beanWriter = new CsvBeanWriter(writer, pref);
			
			beanWriter.writeHeader(header2beanMapping);
			
			for (TopologyBean b : beanList) {
				beanWriter.write(b, header2beanMapping, writeProcessor);
			}
			// force to write the content to the file, flush only doesn' exist
			beanWriter.close(); 
			writer = new FileWriter(f, true);
			writer.write(NetIO.CLOSE_TAG+"\n");
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static List<TopologyBean> convertToBeanList(NetConfig netConfig) {
		List<TopologyBean> list = new ArrayList<TopologyBean>();
		Network net = netConfig.getNetwork();
		List<Neuron> flatNet = net.getFlatNet();
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
	
	

}
