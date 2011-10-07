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
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.SOMGui;
import de.unikassel.ann.io.tasks.SomWorker;
import de.unikassel.ann.model.SomNetwork;
import de.unikassel.ann.threeD.model.GridCube;
import de.unikassel.ann.threeD.model.GridHypercube;
import de.unikassel.ann.threeD.model.GridPlane;
import de.unikassel.ann.threeD.model.RenderGeometry;

/**
 * @author Sofia
 * 
 */
public class SOMTrainingPanel extends JPanel {

	private JComboBox comboAlgorithmSOM;
	public JSpinner spinnerNeighborhoodRadius;
	public JSpinner spinnerIterationsSOM;
	private JButton btnStartTraining;
	private SOMGui parent;
	public JSpinner lernRateSpinner;

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
				somNet.setInitalLearningRate((Double) lernRateSpinner.getValue());
				somNet.setInitialNeighbor((Integer) spinnerNeighborhoodRadius.getValue());
				somNet.setMaxIterations((Integer) spinnerIterationsSOM.getValue());
				boolean square = parent.somTopPanel.somPatternCombo.getSelectedIndex() == 0;
				somNet.setSquare(square);

				parent.somNetwork = somNet;
				SomWorker oldWorker = parent.getWorker();

				SomWorker worker = new SomWorker(somNet, iterations);

				RenderGeometry geometry = null;
				switch (dimensions.length) {
				case 2:
					geometry = new GridPlane(outputSize, outputSize, patternRange);
					break;
				case 3:
					geometry = new GridCube(outputSize, outputSize, outputSize, patternRange);
					break;
				case 4:
					geometry = new GridHypercube(outputSize, outputSize, outputSize, outputSize, patternRange, patternRange, patternRange,
							patternRange);
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
		setPreferredSize(new Dimension(400, 251));

		JLabel lblAlgorithmSOM = new JLabel(Settings.i18n.getString("sidebar.trainingSOM.lblAlgorithmSOM"));

		comboAlgorithmSOM = new JComboBox();
		comboAlgorithmSOM.setModel(new DefaultComboBoxModel(new String[] { "SOM" }));

		JLabel lblNeighborhoodRadius = new JLabel(Settings.i18n.getString("sidebar.trainingSOM.lblNeighborhoodRadius"));

		spinnerNeighborhoodRadius = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		spinnerNeighborhoodRadius.setEnabled(true);

		JLabel lblLernfunction = new JLabel("Lernrate");

		JLabel lblIterations = new JLabel(Settings.i18n.getString("sidebar.trainingSOM.lblIterations"));

		spinnerIterationsSOM = new JSpinner(new SpinnerNumberModel(10000, 1, 10000000, 100));
		spinnerIterationsSOM.setEnabled(true);

		btnStartTraining = new JButton("Training starten");

		lernRateSpinner = new JSpinner(new SpinnerNumberModel(0.01, 0.0001, 1.0, 0.01));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGroup(
										groupLayout
												.createParallelGroup(Alignment.TRAILING)
												.addGroup(
														groupLayout
																.createSequentialGroup()
																.addComponent(lblAlgorithmSOM)
																.addPreferredGap(ComponentPlacement.RELATED, 232, Short.MAX_VALUE)
																.addComponent(comboAlgorithmSOM, GroupLayout.PREFERRED_SIZE, 65,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														groupLayout
																.createSequentialGroup()
																.addComponent(lblNeighborhoodRadius)
																.addPreferredGap(ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
																.addComponent(spinnerNeighborhoodRadius, GroupLayout.PREFERRED_SIZE, 60,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														groupLayout
																.createSequentialGroup()
																.addGroup(
																		groupLayout.createParallelGroup(Alignment.LEADING)
																				.addComponent(lblLernfunction).addComponent(lblIterations))
																.addGap(62)
																.addGroup(
																		groupLayout
																				.createParallelGroup(Alignment.LEADING)
																				.addComponent(lernRateSpinner, GroupLayout.DEFAULT_SIZE,
																						242, Short.MAX_VALUE)
																				.addComponent(spinnerIterationsSOM, Alignment.TRAILING, 0,
																						0, Short.MAX_VALUE)))).addGap(103))
				.addGroup(groupLayout.createSequentialGroup().addComponent(btnStartTraining).addContainerGap()));
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
														.addComponent(lblLernfunction)
														.addComponent(lernRateSpinner, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(spinnerIterationsSOM, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblIterations)).addGap(18).addComponent(btnStartTraining)
										.addContainerGap(22, Short.MAX_VALUE)));
		setLayout(groupLayout);

		initActions();

	}
}
