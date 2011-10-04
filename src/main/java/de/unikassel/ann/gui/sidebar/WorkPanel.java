/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.util.Logger;

/**
 * @author Sofia
 * 
 */
public class WorkPanel extends JPanel implements PropertyChangeListener, ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6425592984651769443L;

	private static final String dataSeparator = ";";

	private Integer inputs;
	private JButton applyData;

	private JTextField testDataField;

	public WorkPanel() {

		setBorder(new TitledBorder(null, Settings.i18n.getString("workpanel.titel"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout());

	}

	/**
	 * @param inputs
	 * 
	 */
	public void updateWorkPanel() {
		inputs = Settings.getInstance().getCurrentSession().getNetworkConfig().getNetwork().getInputSizeIgnoringBias();
		System.out.println("inputs " + inputs);
		if (inputs == 0) {
			JLabel label = new JLabel("Es existieren keine Input Neuronen");
			add(label, BorderLayout.CENTER);
		}

		JPanel inputsP = new JPanel(new GridLayout(0, 1, 20, 20));
		JPanel fieldsP = new JPanel(new GridLayout(0, 1, 20, 20));

		removeAll();

		JLabel label = new JLabel(Settings.i18n.getString("workpanel.lblInput"));
		inputsP.add(label);
		testDataField = new JTextField(10);
		fieldsP.add(testDataField);
		add(inputsP);
		add(fieldsP);
		inputsP.revalidate();
		fieldsP.revalidate();
		validate();
		repaint();

		JCheckBox normalizeData = new JCheckBox(Settings.i18n.getString("workpanel.normalizeCB"));
		// inputsP.add(normalizeData);

		applyData = new JButton(Settings.i18n.getString("workpanel.btnApplyData"));
		fieldsP.add(applyData);

		initListeners();
	}

	/**
	 * 
	 */
	private void initListeners() {
		applyData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NetConfig net = Settings.getInstance().getCurrentSession().getNetworkConfig();

				// Get input data
				String text = testDataField.getText();
				if (text == null || text.isEmpty()) {
					// no input available!
					return;
				}

				// Split input by the data separator
				String[] data = text.split(dataSeparator);
				Double[] input = new Double[data.length];
				for (int i = 0; i < data.length; i++) {
					input[i] = Double.valueOf(data[i]);
				}

				// Create test data
				DataPairSet testData = new DataPairSet();
				testData.addPair(new DataPair(input, new Double[] { Double.NaN }));

				// Validate test data
				int trainInLenght = testData.getPairs().get(0).getInput().length;
				int trainOutLenght = testData.getPairs().get(0).getIdeal().length;
				int netInLenght = net.getNetwork().getInputSizeIgnoringBias();
				int netOutLength = net.getNetwork().getOutputSize();
				if (trainInLenght != netInLenght || trainOutLenght != netOutLength) {
					Logger.warn(this.getClass(), "Trainingsdaten {} Input {} Output passten nicht zur Topology {} Input und {} Output",
							trainInLenght, trainOutLenght, netInLenght, netOutLength);
					return;
				}

				Logger.info(this.getClass(), "start working module");
				net.getWorkingModule().work(net.getNetwork(), testData);

				Logger.info(this.getClass(), testData.toString());
				GraphController.getInstance().repaint();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		updateWorkPanel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(final ChangeEvent e) {
		Logger.debug(this.getClass(), "tab was changed");
		updateWorkPanel();
	}
}
