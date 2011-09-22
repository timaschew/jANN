/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.Settings;

/**
 * @author Sofia
 * 
 */
public class SOMTrainingPanel extends JPanel {

	private JComboBox comboAlgorithmSOM;
	private JSpinner spinnerNeighborhoodRadius;
	private JComboBox comboLernFunction;
	private JSpinner spinnerIterationsSOM;

	/**
	 * Create the panel.
	 */
	public SOMTrainingPanel() {

		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.trainingSOM"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setPreferredSize(new Dimension(400, 215));

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

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addComponent(lblIterations)
														.addPreferredGap(ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
														.addComponent(spinnerIterationsSOM, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addComponent(lblAlgorithmSOM)
														.addPreferredGap(ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
														.addComponent(comboAlgorithmSOM, GroupLayout.PREFERRED_SIZE, 65,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addComponent(lblNeighborhoodRadius)
														.addPreferredGap(ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
														.addComponent(spinnerNeighborhoodRadius, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addComponent(lblLernfunction)
														.addPreferredGap(ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
														.addComponent(comboLernFunction, GroupLayout.PREFERRED_SIZE, 100,
																GroupLayout.PREFERRED_SIZE))).addContainerGap(136, Short.MAX_VALUE)));
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
														.addComponent(lblIterations)).addContainerGap(14, Short.MAX_VALUE)));
		setLayout(groupLayout);

	}

}
