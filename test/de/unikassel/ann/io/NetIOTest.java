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
	public void testImportExport() throws IOException, ClassNotFoundException {
		
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
		
		NetIO reader = new NetIO();
		reader.readConfigFile(f);
		
		NetConfig netConfig = reader.generateNetwork();
		DataPairSet dataSet = reader.getTrainingSet();
		
		netConfig.getTrainingModule().train(dataSet);
		
		
		reader.writeDataSet(file, "training", true);
		dataSet.resetIdeal();
		netConfig.getWorkingModule().work(null, dataSet);
		
		netConfig.printStats();
		System.out.println(dataSet);
		
		
		reader.writeNet(file, "xor network config", netConfig);
		reader.writeDataSet(file, "result", false);
		
	}
	
	
	@Test
	public void testImport() throws IOException, ClassNotFoundException {
		
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
