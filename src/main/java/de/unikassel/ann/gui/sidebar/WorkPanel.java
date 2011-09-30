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

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.util.Logger;

/**
 * @author Sofia
 * 
 */
public class WorkPanel extends JPanel implements PropertyChangeListener, ChangeListener {

	public static WorkPanel instance = new WorkPanel();
	private Integer inputs;

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

		for (int i = 1; i <= inputs; i++) {

			JLabel label = new JLabel(Settings.i18n.getString("workpanel.lblInput") + i);
			inputsP.add(label);
			JTextField field = new JTextField(10);
			fieldsP.add(field);

			add(inputsP);
			add(fieldsP);
			inputsP.revalidate();
			fieldsP.revalidate();
			validate();
			repaint();

		}

		JCheckBox normalizeData = new JCheckBox(Settings.i18n.getString("workpanel.normalizeCB"));
		inputsP.add(normalizeData);

		JButton applyData = new JButton(Settings.i18n.getString("workpanel.btnApplyData"));
		fieldsP.add(applyData);

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
