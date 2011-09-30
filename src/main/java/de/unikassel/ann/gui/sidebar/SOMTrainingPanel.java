/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.SOMGui;
import de.unikassel.ann.io.tasks.SomWorker;
import de.unikassel.ann.model.SomNetwork;
import de.unikassel.ann.threeD.model.GridCube;
import de.unikassel.ann.threeD.model.RenderGeometry;

/**
 * @author Sofia
 * 
 */
public class SOMTrainingPanel extends JPanel {

	private JComboBox comboAlgorithmSOM;
	private JSpinner spinnerNeighborhoodRadius;
	private JComboBox comboLernFunction;
	private JSpinner spinnerIterationsSOM;
	private JButton btnStartTraining;
	private JButton btnReset;
	private SOMGui parent;

	/**
	 * 
	 */
	private void initActions() {
		btnStartTraining.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				int inputSize = parent.somTopPanel.inputModel.getNumber().intValue();
				int outputSize = parent.somTopPanel.outputModel.getNumber().intValue();
				int dimensionSize = parent.somTopPanel.dimensionModel.getNumber().intValue();
				int dimensions[] = new int[dimensionSize];
				int patternRange = parent.somTopPanel.visualSize.getNumber().intValue();
				int iterations = 1000;

				// all dimension have equals size
				for (int i = 0; i < dimensionSize; i++) {
					dimensions[i] = outputSize;
				}

				SomNetwork somNet = new SomNetwork(patternRange, inputSize, dimensions);
				parent.somNetwork = somNet;
				SomWorker worker = new SomWorker(somNet, iterations);

				RenderGeometry geometry = null;
				switch (dimensions.length) {
				case 2:
					// geometry = new GridPlane(outputSize, outputSize, outputSize, patternRange);
					break;
				case 3:
					geometry = new GridCube(outputSize, outputSize, outputSize, patternRange);
					break;
				case 4:
					// geometry = new GridHypercube(outputSize, outputSize, outputSize, patternRange);
					break;
				default:
					return;
				}
				geometry.random();
				parent.setRenderModel(geometry);
				parent.setWorker(worker);
				worker.execute();

			}
		});

	}

	/**
	 * Create the panel.
	 * 
	 * @param somGui
	 */
	public SOMTrainingPanel(final SOMGui parent) {

		this.parent = parent;

		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.trainingSOM"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setPreferredSize(new Dimension(400, 243));

		JLabel lblAlgorithmSOM = new JLabel(Settings.i18n.getString("sidebar.trainingSOM.lblAlgorithmSOM"));

		comboAlgorithmSOM = new JComboBox();
		comboAlgorithmSOM.setModel(new DefaultComboBoxModel(new String[] { "SOM", "LVQ" }));

		JLabel lblNeighborhoodRadius = new JLabel(Settings.i18n.getString("sidebar.trainingSOM.lblNeighborhoodRadius"));

		spinnerNeighborhoodRadius = new JSpinner();

		JLabel lblLernfunction = new JLabel(Settings.i18n.getString("sidebar.trainingSOM.lblLernfunction"));

		comboLernFunction = new JComboBox();
		comboLernFunction.setModel(new DefaultComboBoxModel(new String[] { "1+e^(-x)" }));

		JLabel lblIterations = new JLabel(Settings.i18n.getString("sidebar.trainingSOM.lblIterations"));

		spinnerIterationsSOM = new JSpinner();

		btnStartTraining = new JButton("Training starten");

		btnReset = new JButton("Reset");

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(lblIterations)
																		.addPreferredGap(ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
																		.addComponent(spinnerIterationsSOM, GroupLayout.PREFERRED_SIZE, 60,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(lblAlgorithmSOM)
																		.addPreferredGap(ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
																		.addComponent(comboAlgorithmSOM, GroupLayout.PREFERRED_SIZE, 65,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(lblNeighborhoodRadius)
																		.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
																		.addComponent(spinnerNeighborhoodRadius,
																				GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(lblLernfunction)
																		.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
																		.addComponent(comboLernFunction, GroupLayout.PREFERRED_SIZE, 100,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(126, Short.MAX_VALUE))
						.addGroup(
								groupLayout.createSequentialGroup().addComponent(btnStartTraining)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE).addGap(128)));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblAlgorithmSOM)
														.addComponent(comboAlgorithmSOM, GroupLayout.PREFERRED_SIZE, 29,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNeighborhoodRadius)
														.addComponent(spinnerNeighborhoodRadius, GroupLayout.PREFERRED_SIZE, 29,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(comboLernFunction, GroupLayout.PREFERRED_SIZE, 29,
																GroupLayout.PREFERRED_SIZE).addComponent(lblLernfunction))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(spinnerIterationsSOM, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblIterations))
										.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
										.addGroup(
												groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnStartTraining)
														.addComponent(btnReset))));
		setLayout(groupLayout);

		initActions();

	}

}
