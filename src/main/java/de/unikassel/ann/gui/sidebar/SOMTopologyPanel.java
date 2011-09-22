/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
public class SOMTopologyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JSpinner spinnerInputNeuronSOM;
	public JSpinner spinnerOutputDimensionSOM;
	public JComboBox comboOutputNeuronSOM;
	public JSpinner spinnerOutputNeuronSOM;
	public JComboBox comboNeighborhoodTop;
	public JComboBox comboPatternSOM;

//	public static void main(final String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					SOMTopologyPanel panel = new SOMTopologyPanel();
//					JFrame frame = new JFrame();
//					frame.setContentPane(panel);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the panel.
	 */
	public SOMTopologyPanel() {
		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.topologySOM"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setPreferredSize(new Dimension(400, 256));

		JLabel lblInputNeuronSOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblInputNeuronSOM"));

		spinnerInputNeuronSOM = new JSpinner();

		JLabel lblOutputDimesionSOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblOutputDimesionSOM"));

		spinnerOutputDimensionSOM = new JSpinner();

		comboOutputNeuronSOM = new JComboBox();

		JLabel lblOutputNeuronSOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblOutputNeuronSOM"));

		spinnerOutputNeuronSOM = new JSpinner();

		JLabel lblNeighborhoodTopologySOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblNeighborhoodTopologySOM"));

		comboNeighborhoodTop = new JComboBox();
		comboNeighborhoodTop.setModel(new DefaultComboBoxModel(new String[] { "Linien (1D)", "Quadrate (2D)", "Würfel (3D)",
				"Hyperwürfel (4D)" }));

		JLabel lblPatternSOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblPatternSOM"));

		comboPatternSOM = new JComboBox();
		comboPatternSOM.setModel(new DefaultComboBoxModel(new String[] { "Eckig", "Rund" }));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
						groupLayout
								.createSequentialGroup()
								.addGroup(
										groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblOutputNeuronSOM)
												.addComponent(lblInputNeuronSOM).addComponent(lblOutputDimesionSOM)
												.addComponent(lblNeighborhoodTopologySOM).addComponent(lblPatternSOM))
								.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
								.addGroup(
										groupLayout
												.createParallelGroup(Alignment.LEADING)
												.addComponent(spinnerInputNeuronSOM, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(spinnerOutputDimensionSOM, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(
														groupLayout
																.createSequentialGroup()
																.addComponent(spinnerOutputNeuronSOM, GroupLayout.PREFERRED_SIZE, 50,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18)
																.addComponent(comboOutputNeuronSOM, GroupLayout.PREFERRED_SIZE, 50,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														groupLayout
																.createParallelGroup(Alignment.TRAILING)
																.addComponent(comboPatternSOM, GroupLayout.PREFERRED_SIZE, 120,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(comboNeighborhoodTop, GroupLayout.PREFERRED_SIZE, 120,
																		GroupLayout.PREFERRED_SIZE))).addContainerGap(84, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblInputNeuronSOM)
										.addComponent(spinnerInputNeuronSOM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblOutputDimesionSOM)
										.addComponent(spinnerOutputDimensionSOM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblOutputNeuronSOM)
										.addComponent(spinnerOutputNeuronSOM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(comboOutputNeuronSOM, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGap(11)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(comboNeighborhoodTop, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNeighborhoodTopologySOM))
						.addGap(11)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(comboPatternSOM, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPatternSOM)).addContainerGap(17, Short.MAX_VALUE)));
		setLayout(groupLayout);

	}
}
