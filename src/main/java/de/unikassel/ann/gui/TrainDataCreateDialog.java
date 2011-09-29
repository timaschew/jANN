/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.Settings;

/**
 * @author Sofia
 * 
 */
public class TrainDataCreateDialog extends JDialog {

	public TrainDataCreateDialog() {

		JPanel trainDataPane = new JPanel();
		setTitle(Settings.i18n.getString("trainDataCreateDialog.titel"));
		setModal(true);
		setSize(411, 402);
		setResizable(false);
		setLocationRelativeTo(null);

		JButton btnSave = new JButton(Settings.i18n.getString("trainDataCreateDialog.btnSave"));
		JButton btnCancel = new JButton(Settings.i18n.getString("trainDataCreateDialog.btnCancel"));

		String information = "X Spalten mit Semikolon (;) getrennt,\n" + "XY für die Inputneuronen, \n" + "XZ für die Outputneuronen\n"
				+ "Dezimaltrennzeichen mit (,) oder (.), \n" + "keine Tausender-Trennzeichen (1.000)! \n"
				+ "pro Datensatz eine Zeile verwenden";

		// Create a text area.
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		JLabel lblInfo = new JLabel(Settings.i18n.getString("trainDataCreateDialog.lblInfo"));
		JTextArea infos = new JTextArea();
		infos.setEditable(false);
		infos.setPreferredSize(new Dimension(353, 120));
		infos.setText(information);

		JScrollPane areaScrollPane = new JScrollPane(textArea);
		areaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(353, 180));

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				// Falls du die Daten für die aktuelle Session brauchst ;-)
				NetConfig netconfig = Settings.getInstance().getCurrentSession().getNetworkConfig();
				if (netconfig.getTrainingData() != null) {
					Object[] option = { "Ja", "Nein" };
					Component frameD = new JFrame();
					int pane = JOptionPane.showOptionDialog(frameD, "Trainingsdaten existieren bereits. \n"
							+ "Möchten Sie diese überschreiben?", "Beenden", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
							option, option[1]);

					if (pane == JOptionPane.YES_OPTION) {
						// TODO Traindata überschreiben
					}
				}
				// Schreib die Daten in dem Inputstream
				writeAreaTextToInputStream(textArea);

			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				dispose();

			}
		});

		trainDataPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		trainDataPane.add(lblInfo);
		trainDataPane.add(infos);
		trainDataPane.add(areaScrollPane);
		trainDataPane.add(btnSave);
		trainDataPane.add(btnCancel);

		add(trainDataPane);

	}

	public InputStream writeAreaTextToInputStream(final JTextArea textArea) {
		return new InputStream() {
			String s = textArea.getText();
			int inPtr = 0;

			@Override
			public int read() // minimum implementation of an InputStream
			{
				if (inPtr >= s.length()) {
					return -1;
				} else {
					inPtr++;
					return s.charAt(inPtr - 1);
				}
			}// read
		};// InputStream
	}// textArea2InputStream

}
