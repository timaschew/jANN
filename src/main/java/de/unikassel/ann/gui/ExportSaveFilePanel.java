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
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.io.NetIO;
import de.unikassel.ann.model.UserSession;

/**
 * @author Sofia
 * 
 */
public class ExportSaveFilePanel extends JDialog {

	private JPanel exportFilePanel;
	private JFileChooser fileopen;
	private AbstractButton btnSearchs;
	private JCheckBox netCB;
	private JCheckBox testDataCB;
	private JCheckBox trainingDataCB;
	private JButton btnExport;
	private JDialog selectFileToExport;

	/**
	 * 
	 */
	public ExportSaveFilePanel() {

		exportFilePanel = new JPanel();
		exportFilePanel.setLayout(new BorderLayout());

		setTitle("Export");
		setSize(278, 202);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);

		JLabel lblFile = new JLabel("File ");
		JLabel lblNetz = new JLabel("Netz");
		JLabel lblTrainingData = new JLabel("Training Daten");
		JLabel lblTestData = new JLabel("Test Daten");

		btnSearchs = new JButton("");
		btnSearchs.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("img/search-icon.png")));
		btnSearchs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO sessionList auslesen, um auswählen zu können
				// selectFileToExport();
			}

		});

		netCB = new JCheckBox("");
		netCB.setEnabled(true);
		trainingDataCB = new JCheckBox("");
		trainingDataCB.setEnabled(false);
		testDataCB = new JCheckBox("");
		testDataCB.setEnabled(false);

		// netCB.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(final ActionEvent e) {
		// if (netCB.isSelected()) {
		// trainingDataCB.setEnabled(true);
		// } else {
		// if (trainingDataCB.isSelected()) {
		// trainingDataCB.setSelected(false);
		// trainingDataCB.setEnabled(false);
		// }
		// trainingDataCB.setEnabled(false);
		// }
		// }
		// });
		//
		// trainingDataCB.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(final ActionEvent e) {
		// if (trainingDataCB.isSelected() && !netCB.isSelected()) {
		// netCB.setSelected(true);
		// }
		// }
		// });

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

		final JComboBox fileSessionsCombo = new JComboBox();
		DefaultComboBoxModel fileSessionsCombomodel = new DefaultComboBoxModel();
		List<UserSession> userSessionsListe = Settings.getInstance().getUserSessions();

		for (int i = 0; i < userSessionsListe.size(); i++) {
			fileSessionsCombomodel.addElement(userSessionsListe.get(i).toString());
			fileSessionsCombo.setModel(fileSessionsCombomodel);
		}
		fileSessionsCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NetIO net = new NetIO();
				// check if the traindata is null and setenable the checkbox, true of false
				if (Settings.getInstance().getCurrentSession().getNetworkConfig().getTrainingData() == null) {
					trainingDataCB.setEnabled(false);
				} else {
					trainingDataCB.setEnabled(true);
				}
				if (Settings.getInstance().getCurrentSession().getNetworkConfig().getTestData() == null) {
					testDataCB.setEnabled(false);
				} else {
					testDataCB.setEnabled(true);
				}
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
																				.addComponent(lblNetz).addComponent(lblTrainingData)
																				.addComponent(lblTestData))
																.addGap(66)
																.addGroup(
																		groupLayout.createParallelGroup(Alignment.TRAILING)
																				.addComponent(trainingDataCB).addComponent(netCB)
																				.addComponent(testDataCB)))
												.addGroup(
														Alignment.TRAILING,
														groupLayout
																.createSequentialGroup()
																.addComponent(lblFile)
																.addGap(51)
																.addComponent(fileSessionsCombo, 0, 147, Short.MAX_VALUE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(btnSearchs, GroupLayout.PREFERRED_SIZE, 33,
																		GroupLayout.PREFERRED_SIZE))).addContainerGap())
				.addGroup(
						groupLayout.createSequentialGroup().addGap(22).addComponent(btnExport)
								.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE).addComponent(btnAbbrechen).addGap(25)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnSearchs)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblFile)
														.addComponent(fileSessionsCombo, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(lblNetz).addComponent(netCB))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												groupLayout.createSequentialGroup().addComponent(lblTrainingData).addGap(14)
														.addComponent(lblTestData))
										.addGroup(
												groupLayout.createSequentialGroup().addComponent(trainingDataCB)
														.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(testDataCB)))
						.addGap(16)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnExport).addComponent(btnAbbrechen))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);

	}

	private void openFileChooser() {

		fileopen = new JFileChooser();
		// FileFilter filter = new FileNameExtensionFilter("csv files", "csv");
		// fileopen.addChoosableFileFilter(filter);

		int ret = fileopen.showDialog(exportFilePanel, "Save file");

	}
}
