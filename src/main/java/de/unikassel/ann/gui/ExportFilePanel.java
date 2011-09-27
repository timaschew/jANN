/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * @author Sofia
 * 
 */
public class ExportFilePanel extends JDialog {

	private JPanel exportFilePanel;
	private JTextField textFieldSelectedFile;
	private JFileChooser fileopen;
	private AbstractButton btnSearchs;
	private JCheckBox topologyCB;
	private JCheckBox trainingDataCB;
	private JCheckBox synapseCB;
	private JButton btnExport;

	/**
	 * 
	 */
	public ExportFilePanel() {

		exportFilePanel = new JPanel();
		exportFilePanel.setLayout(new BorderLayout());

		setTitle("Export");
		setSize(270, 210);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);

		JLabel lblFile = new JLabel("File Exportieren");
		JLabel lblTopology = new JLabel("Topology");
		JLabel lblSynapse = new JLabel("Synapse");
		JLabel lblTrainingData = new JLabel("Training Data");

		textFieldSelectedFile = new JTextField(10);

		btnSearchs = new JButton("");
		btnSearchs.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("img/search-icon.png")));
		btnSearchs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO sessionList auslesen, um auswählen zu können
				JFrame frame = new JFrame();
				JDialog selectFileToExport = new JDialog(frame, "Datei zum Exportieren auswählen");
				JList list = new JList();
				frame.add(list);
				selectFileToExport.setVisible(true);

			}
		});

		topologyCB = new JCheckBox("");
		topologyCB.setEnabled(false);
		synapseCB = new JCheckBox("");
		synapseCB.setEnabled(false);
		trainingDataCB = new JCheckBox("");
		trainingDataCB.setEnabled(false);

		topologyCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (topologyCB.isSelected()) {
					synapseCB.setEnabled(true);
				} else {
					if (synapseCB.isSelected()) {
						synapseCB.setSelected(false);
						synapseCB.setEnabled(false);
					}
					synapseCB.setEnabled(false);
				}
			}
		});

		synapseCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (synapseCB.isSelected() && !topologyCB.isSelected()) {
					topologyCB.setSelected(true);
				}
			}
		});

		btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				openFileChooser();
			}

		});

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				dispose();
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										groupLayout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(
														groupLayout
																.createSequentialGroup()
																.addGroup(
																		groupLayout.createParallelGroup(Alignment.LEADING)
																				.addComponent(lblTopology).addComponent(lblSynapse)
																				.addComponent(lblTrainingData))
																.addGap(80)
																.addGroup(
																		groupLayout.createParallelGroup(Alignment.TRAILING)
																				.addComponent(synapseCB).addComponent(topologyCB)
																				.addComponent(trainingDataCB)))
												.addGroup(
														groupLayout
																.createSequentialGroup()
																.addGroup(
																		groupLayout
																				.createParallelGroup(Alignment.TRAILING)
																				.addComponent(btnAbbrechen)
																				.addGroup(
																						groupLayout
																								.createSequentialGroup()
																								.addComponent(lblFile)
																								.addGap(18)
																								.addComponent(textFieldSelectedFile,
																										GroupLayout.PREFERRED_SIZE, 110,
																										GroupLayout.PREFERRED_SIZE)))
																.addGap(6)
																.addComponent(btnSearchs, GroupLayout.PREFERRED_SIZE, 33,
																		GroupLayout.PREFERRED_SIZE))).addGap(12)
								.addContainerGap(6, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGap(22).addComponent(btnExport).addContainerGap(180, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(14)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addGroup(
																groupLayout
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(lblFile)
																		.addComponent(textFieldSelectedFile, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(
																groupLayout.createParallelGroup(Alignment.TRAILING)
																		.addComponent(lblTopology).addComponent(topologyCB))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(
																groupLayout
																		.createParallelGroup(Alignment.LEADING)
																		.addGroup(
																				groupLayout.createSequentialGroup()
																						.addComponent(lblSynapse).addGap(14)
																						.addComponent(lblTrainingData))
																		.addGroup(
																				groupLayout.createSequentialGroup().addComponent(synapseCB)
																						.addPreferredGap(ComponentPlacement.UNRELATED)
																						.addComponent(trainingDataCB))))
										.addComponent(btnSearchs)).addGap(16)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnExport).addComponent(btnAbbrechen))
						.addContainerGap(12, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);

	}

	private void openFileChooser() {

		fileopen = new JFileChooser();
		// FileFilter filter = new FileNameExtensionFilter("csv files", "csv");
		// fileopen.addChoosableFileFilter(filter);

		int ret = fileopen.showDialog(exportFilePanel, "Save file");
	}
}
