/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.vis;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.gui.TrainErrorPlot;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Network;

/**
 * @author anton
 * 
 */
public class GnuPlotTet {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final TrainErrorPlot tep = new TrainErrorPlot();
		tep.init();
		JFrame f = new JFrame();
		Component plot = tep.getPlotPanel();
		f.getContentPane().add(plot);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setSize(tep.getPlotPanel().getPreferredSize());
		ActionListener al = createAL(tep);
		al.actionPerformed(null);
	}

	public static ActionListener createAL(final TrainErrorPlot tep) {
		return new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				final NetConfig netConfig = NetworkFactory.createXorNet(10000, true);
				netConfig.addTrainErrorListener(tep);
				final Network net = netConfig.getNetwork();

				// XOR training data
				final DataPairSet trainSet = new DataPairSet();
				trainSet.addPair(new DataPair(new Double[] { 0.0, 0.0 }, new Double[] { 0.0 }));
				trainSet.addPair(new DataPair(new Double[] { 0.0, 1.0 }, new Double[] { 1.0 }));
				trainSet.addPair(new DataPair(new Double[] { 1.0, 0.0 }, new Double[] { 1.0 }));
				trainSet.addPair(new DataPair(new Double[] { 1.0, 1.0 }, new Double[] { 0.0 }));

				// XOR test data
				final DataPairSet testSet = new DataPairSet();
				testSet.addPair(new DataPair(new Double[] { 0.0, 0.0 }, new Double[] { Double.NaN }));
				testSet.addPair(new DataPair(new Double[] { 0.0, 1.0 }, new Double[] { Double.NaN }));
				testSet.addPair(new DataPair(new Double[] { 1.0, 0.0 }, new Double[] { Double.NaN }));
				testSet.addPair(new DataPair(new Double[] { 1.0, 1.0 }, new Double[] { Double.NaN }));

				final Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						netConfig.getTrainingModule().train(trainSet);
						netConfig.getWorkingModule().work(net, testSet);
						netConfig.printStats();
						System.out.println(testSet);
						tep.nextRun();
					}
				});
				t.start();
				Thread t2 = new Thread(new Runnable() {
					@Override
					public void run() {
						while (t.isAlive()) {
							tep.updateUI();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						tep.updateUI();
					}
				});
				t2.start();

			}
		};
	}

}
