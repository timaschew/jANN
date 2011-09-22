/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * @author Sofia
 * 
 */
public class SidebarSOM extends JPanel {

	private static final long serialVersionUID = 1L;
	public SOMTopologyPanel somTopPanel;
	public SOMTrainingPanel somTrainPanel;

	/**
	 * Create the panel.
	 */
	public SidebarSOM() {
		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.setBorder(null);
		add(tabbedPane);

		JPanel wrapper = new JPanel();
		GridBagLayout gbl_wrapper = new GridBagLayout();
		gbl_wrapper.columnWidths = new int[] { 412, 0 };
		gbl_wrapper.rowHeights = new int[] { 254, 215 };
		gbl_wrapper.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_wrapper.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		wrapper.setLayout(gbl_wrapper);

		// topology Panel
		somTopPanel = new SOMTopologyPanel();
		GridBagConstraints gbc_somTopPanel = new GridBagConstraints();
		gbc_somTopPanel.anchor = GridBagConstraints.NORTH;
		gbc_somTopPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_somTopPanel.insets = new Insets(0, 0, 5, 0);
		gbc_somTopPanel.gridx = 0;
		gbc_somTopPanel.gridy = 0;
		wrapper.add(somTopPanel, gbc_somTopPanel);

		// Trainstrategy Panel
		somTrainPanel = new SOMTrainingPanel();
		GridBagConstraints gbc_somTrainPanel = new GridBagConstraints();
		gbc_somTrainPanel.anchor = GridBagConstraints.NORTH;
		gbc_somTrainPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_somTrainPanel.insets = new Insets(0, 0, 5, 0);
		gbc_somTrainPanel.gridx = 0;
		gbc_somTrainPanel.gridy = 1;
		wrapper.add(somTrainPanel, gbc_somTrainPanel);

		JScrollPane scrollPane = new JScrollPane(wrapper);

		tabbedPane.addTab("Konfiguration", null, scrollPane, null);
		tabbedPane.addTab("Training", null, new JPanel(), null);

	}
}
