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
		File f = new File("/Users/anton/Develop/Projekte/ANNtool/test/net_cfg.csv");
		File file = new File("/Users/anton/Develop/Projekte/ANNtool/test/net_export.csv");
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, false);
			writer.write("");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		NetIO netIO = new NetIO();
		netIO.readConfigFile(f);
		
		NetConfig netConfig = netIO.generateNetwork();
		DataPairSet dataSet = netIO.getTrainingSet();
		
		netConfig.getTrainingModule().train(dataSet);
		
		
		netIO.writeDataSet(file, "xor dataset", true, dataSet);
		dataSet.resetIdeal();
		netConfig.getWorkingModule().work(null, dataSet);
		
		netConfig.printStats();
		System.out.println(dataSet);
		
		
		netIO.writeNet(file, "xor network config", netConfig);
		netIO.writeDataSet(file, "result", false, dataSet);
		
	}
	
	
	@Test
	public void testImportExportedConfig() throws IOException, ClassNotFoundException {
		
		File f = new File("/Users/anton/Develop/Projekte/ANNtool/test/net_export.csv");
		
		NetIO reader = new NetIO();
		reader.readConfigFile(f);
		
		NetConfig netConfig = reader.generateNetwork();
		DataPairSet dataSet = reader.getTrainingSet();
		
		dataSet.resetIdeal();
		netConfig.getWorkingModule().work(null, dataSet);
		
		netConfig.printStats();
		System.out.println(dataSet);
		

		
		
		
	}
}
