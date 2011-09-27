/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.model.SidebarModel;

/**
 * @author Sofia
 * 
 */
public class ManualTestPanel extends JPanel {

	SidebarModel sidebarModel = Settings.getInstance().getCurrentSession().sidebarModel;

	public ManualTestPanel() {
		setBorder(new TitledBorder(null, "Manueller Test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// setSize(400, 240);
		setPreferredSize(new Dimension(400, 342));
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Integer inputs = sidebarModel.getInputNeurons();
		for (int i = 0; i < inputs; i++) {
			JLabel label = new JLabel("Input" + i);
			add(label);
		}
	}
}
