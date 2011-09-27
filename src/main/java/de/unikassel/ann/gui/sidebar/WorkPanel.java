/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.Settings;

/**
 * @author Sofia
 * 
 */
public class WorkPanel extends JPanel {

	// public static void main(final String[] args) {
	// ManualTestTrain test = new ManualTestTrain();
	// JFrame frame = new JFrame();
	// frame.add(test);
	// frame.setVisible(true);
	// }

	public WorkPanel() {

		setBorder(new TitledBorder(null, "Manueller Test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setPreferredSize(new Dimension(400, 342));

		int inputs = Settings.getInstance().getCurrentSession().getNetworkConfig().getNetwork().getInputSizeIgnoringBias();
		System.out.println(inputs);

		if (inputs == 0) {
			JLabel label = new JLabel("Es existieren keine Input Neuronen");
			add(label);
		}

		for (int i = 1; i <= inputs; i++) {
			JLabel label = new JLabel("Input" + i);
			System.out.println("drin");
			add(label);
		}

	}

}
