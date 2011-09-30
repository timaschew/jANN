/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.io.tasks;

import java.util.List;

import javax.swing.SwingWorker;

import de.unikassel.ann.gui.SOMGui;
import de.unikassel.ann.model.SomNetwork;

/**
 * @author anton
 * 
 */
public class SomWorker extends SwingWorker<Void, Void> {

	private SomNetwork somNet;
	private int iterations;

	public SomWorker(final SomNetwork somNet, final int iterations) {
		this.somNet = somNet;
		this.iterations = iterations;
	}

	@Override
	protected Void doInBackground() throws Exception {

		somNet.train();

		return null;
	}

	@Override
	protected void process(final List<Void> chunks) {
		// TODO Auto-generated method stub
		super.process(chunks);
	}

	@Override
	protected void done() {
		SOMGui.getInstance().somNetwork = null;
		super.done();
	}

}
