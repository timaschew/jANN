package de.unikassel.ann.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.io.beans.TrainingBean;
import de.unikassel.ann.model.DataPairSet;

public class NetIOTest {
	
	@Test
	public void testImportAndExportConfig() throws IOException, ClassNotFoundException {
		
		TrainingRW.WRITE_INDEX_IN_HEADER = false;
		
//		File f = new File("/Users/anton/Develop/Projekte/ANNtool/test/xor.csv");
		File file = new File("/Users/anton/Develop/Projekte/ANNtool/test/net_cfg.csv");
		File antoherFile = new File("/Users/anton/Develop/Projekte/ANNtool/test/net_export.csv");
		
		deleteFile(file);
		
		NetIO netIO = new NetIO();
		netIO.readConfigFile(file); // read and parse file
		
		// create network (topology and synapses)
		NetConfig netConfig = netIO.generateNetwork();
		// create dataset
		DataPairSet dataSet = netIO.getTrainingSet();
		
		DataPairSet copyDataSet = new DataPairSet(dataSet);
		// start training
		netConfig.getTrainingModule().train(copyDataSet);
		
		// reset output
		copyDataSet.resetIdeal();
		// let ann work on input
		netConfig.getWorkingModule().work(null, copyDataSet);
		
		// print output result of work
		System.out.println(dataSet);
		
		// write everything to new file
		netIO.writeNet(antoherFile, "xor network", netConfig);
		netIO.writeDataSet(antoherFile, "xor training", true, dataSet);
		netIO.writeDataSet(antoherFile, "xor result", false, copyDataSet);
		
	}
	
	@Test
	public void testImportExportedConfig() throws IOException, ClassNotFoundException {
		
		File f = new File("/Users/anton/Develop/Projekte/ANNtool/test/net_export.csv");
		
		NetIO netIO = new NetIO();
		netIO.readConfigFile(f); // read and parse file
		
		// create network (topology and synapses)
		NetConfig netConfig = netIO.generateNetwork();
		// create dataset
		DataPairSet dataSet = netIO.getTrainingSet();
		
		// reset output
		dataSet.resetIdeal();
		// let ann work on input
		netConfig.getWorkingModule().work(null, dataSet);
		
		// print output result of work
		System.out.println(dataSet);
		
	}
	
	
	private void deleteFile(File file) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, false);
			writer.write("");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
