/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
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

	// public static void main(final String[] args) {
	// ManualTestTrain test = new ManualTestTrain();
	// JFrame frame = new JFrame();
	// frame.add(test);
	// frame.setVisible(true);
	// }
	public static WorkPanel instance = new WorkPanel();
	private Integer inputs;

	public WorkPanel() {

		setBorder(new TitledBorder(null, "Manueller Test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setPreferredSize(new Dimension(400, 342));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

	}

	/**
	 * @param inputs
	 * 
	 */
	public void updateWorkPanel() {
		inputs = Settings.getInstance().getCurrentSession().getNetworkConfig().getNetwork().getInputSizeIgnoringBias();
		System.out.println("inputs " + inputs);

		//
		// JPanel textfields = new JPanel();
		// textfields.setLayout(new BoxLayout(textfields, BoxLayout.Y_AXIS));
		// add(textfields, BorderLayout.EAST);

		if (inputs == 0) {
			JLabel label = new JLabel("Es existieren keine Input Neuronen");
			add(label, BorderLayout.CENTER);
		}

		// GroupLayout layout = new GroupLayout(this);
		// setLayout(layout);
		// layout.setAutoCreateGaps(true);
		// layout.setAutoCreateContainerGaps(true);

		removeAll();
		for (int i = 1; i <= inputs; i++) {
			JPanel inputsP = new JPanel();
			inputsP.setLayout(new BoxLayout(inputsP, BoxLayout.Y_AXIS));
			add(inputsP, BorderLayout.WEST);
			JLabel label = new JLabel("Input" + i);
			label.setAlignmentY(BOTTOM_ALIGNMENT);
			JTextField field = new JTextField(10);
			field.setAlignmentY(BOTTOM_ALIGNMENT);
			System.out.println("drin");
			inputsP.add(label);
			inputsP.add(field);

			inputsP.revalidate();
			validate();
			repaint();

		}

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
