/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * @author anton
 * 
 */
public class ActionControllerTest extends JFrame {

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					frame = new ActionControllerTest();
					frame.setVisible(true);
					frame.setBounds(100, 100, 400, 400);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static ActionControllerTest frame;

	public SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 10, 1);
	public ComboBoxModel comboBoxModel = new DefaultComboBoxModel(new String[] { "-1", "-2" });
	public String jlabelString = "<empty>";

	public ActionControllerTest() {

		JSpinner spinner = new JSpinner(spinnerModel);
		getContentPane().add(spinner, BorderLayout.CENTER);

		JComboBox comboBox = new JComboBox(comboBoxModel);
		comboBox.setActionCommand(Actions.TEST.name());
		getContentPane().add(comboBox, BorderLayout.EAST);

		JLabel label = new JLabel(jlabelString);
		getContentPane().add(label, BorderLayout.SOUTH);

		ActionController ac = ActionController.getInstance();
		ac.listenOnSpinnerPropertyChanges("value", spinner);
		comboBox.addPropertyChangeListener("value", ac);
		comboBox.addActionListener(ac);

	}

}
