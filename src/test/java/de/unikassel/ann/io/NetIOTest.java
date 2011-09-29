package de.unikassel.ann.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.model.DataPairSet;

public class NetIOTest {

	private static final ClassLoader CL = NetIOTest.class.getClassLoader();
	private static final String PATH = CL.getResource("").getFile();

	// takes too much time
	@Ignore
	public void testWerner() throws IOException, ClassNotFoundException {

		File importFile = new File(PATH + "werner.csv");

		NetIO netIO = new NetIO();
		netIO.readConfigFile(importFile);// read and parse file

		// create network (topology and synapses)
		NetConfig netConfig = netIO.generateNetwork();
		// create dataset
		DataPairSet trainDataSet = netIO.getTrainingSet();

		DataPairSet testDataSet = new DataPairSet();
		for (int i = -20; i <= 20; i++) {
			for (int j = -20; j <= 20; j++) {
				double x = (i * 0.1 + 1) / 2;
				double y = (j * 0.1 + 1) / 2;
				testDataSet.addRow(new Double[] { Double.valueOf(x), Double.valueOf(y) }, new Double[] { Double.NaN });
			}
		}
		// start training
		netConfig.getTrainingModule().train(trainDataSet);

		// reset output
		testDataSet.resetIdeal();
		// let ann work on input
		netConfig.getWorkingModule().work(null, testDataSet);

		// print output result of work
		System.out.println(trainDataSet);

		// print output result of work
		System.out.println(testDataSet);

		netConfig.printStats();

		File exportFile = new File(PATH + "werner_export1.csv");

		netIO.writeNet(exportFile, "xor network", netConfig);
		netIO.writeDataSet(exportFile, "xor training", true, trainDataSet);
		netIO.writeDataSet(exportFile, "xor result", false, testDataSet);

	}

	// takes too much time
	@Ignore
	public void testWaldemar() throws IOException, ClassNotFoundException {

		File importFile = new File(PATH + "waldemar.csv");

		NetIO netIO = new NetIO();

		netIO.readConfigFile(importFile);
		// read and parse file

		// create network (topology and synapses)
		NetConfig netConfig = netIO.generateNetwork();
		// create dataset
		DataPairSet originalDataSet = netIO.getTrainingSet();

		DataPairSet copyDataSet = new DataPairSet(originalDataSet);
		// start training
		netConfig.getTrainingModule().train(copyDataSet);

		// reset output
		copyDataSet.resetIdeal();
		// let ann work on input
		netConfig.getWorkingModule().work(null, copyDataSet);

		// print output result of work
		System.out.println(originalDataSet);

		// print output result of work
		System.out.println(copyDataSet);

		netConfig.printStats();

	}

	@Test
	public void testImportAndExportConfig() throws IOException, ClassNotFoundException {

		TrainingRW.WRITE_INDEX_IN_HEADER = false;

		File importFile = new File(PATH + "net_cfg.csv");
		File exportFile = new File(PATH + "net_export.csv");

		deleteFile(exportFile);

		NetIO netIO = new NetIO();

		netIO.readConfigFile(importFile);
		// read and parse file

		// create network (topology and synapses)
		NetConfig netConfig = netIO.generateNetwork();
		// create dataset
		DataPairSet originalDataSet = netIO.getTrainingSet();

		DataPairSet copyDataSet = new DataPairSet(originalDataSet);
		// start training
		netConfig.getTrainingModule().train(copyDataSet);

		// reset output
		copyDataSet.resetIdeal();
		// let ann work on input
		netConfig.getWorkingModule().work(null, copyDataSet);

		// print output result of work
		System.out.println(originalDataSet);

		// write everything to new file
		netIO.writeNet(exportFile, "xor network", netConfig);
		netIO.writeDataSet(exportFile, "xor training", true, originalDataSet);
		netIO.writeDataSet(exportFile, "xor result", false, copyDataSet);

	}

	@Test
	public void testImportExportedConfig() throws IOException, ClassNotFoundException {

		File f = new File(PATH + "net_export.csv");

		NetIO netIO = new NetIO();

		netIO.readConfigFile(f);
		// read and parse file

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

	private void deleteFile(final File file) {
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
