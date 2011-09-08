/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;

/**
 * @author anton
 * 
 */
public class ActionController implements ActionListener, PropertyChangeListener {

	private static ActionController instance;

	public static ActionController getInstance() {
		if (instance == null) {
			instance = new ActionController();
		}
		return instance;
	}

	/**
	 * Adds this listener for the specific property to the spinner
	 * 
	 * @param property
	 * @param spinner
	 */
	public void listenOnSpinnerPropertyChanges(final String property, final JSpinner spinner) {
		if (spinner.getEditor() instanceof JSpinner.DefaultEditor) {
			JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
			editor.getTextField().addPropertyChangeListener(property, this);
		} else {
			throw new IllegalArgumentException("the spinner has not a 'DefaultEditor': " + spinner.getEditor());
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		System.out.println(e);

	}

	@Override
	public void propertyChange(final PropertyChangeEvent e) {
		System.out.println(e);

		JSpinner spinner = getSpinner(e);
		JComboBox comboBox = null;// = getComboBox(e);

		if (spinner != null) {
			if (spinner.getName().equals("spinner1")) {

			} else if (spinner.getName().equals("spinner2")) {

			} else if (spinner.getName().equals("spinner3")) {

			} else {
				System.err.println("not implemented for name: " + spinner.getName());
			}
		} else if (comboBox != null) {

		} else {
			System.err.println("not implemented for this source type: " + e.getSource());
		}

	}

	/**
	 * @param e
	 * @return
	 */
	private JSpinner getSpinner(final PropertyChangeEvent e) {
		if (e.getSource() instanceof JFormattedTextField) {
			JFormattedTextField ftf = (JFormattedTextField) e.getSource();
			if (ftf.getParent() != null && ftf.getParent() instanceof NumberEditor) {
				NumberEditor ne = (NumberEditor) ftf.getParent();
				if (ne.getParent() != null && ne.getParent() instanceof JSpinner) {
					return (JSpinner) ne.getParent();
				}
			}
		}
		return null;
	}

}
