/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * @author anton
 * 
 */
public class Sidebar extends JPanel {

	private static final long serialVersionUID = 3451251923560791391L;
	public TopologyPanel topolgyPanel;
	private TrainStrategyPanel trainStrategyPanel;
	private StandardOptionsPanel standardOptionsPanel;
	private SelectedSymbolPanel selectedSymbolsPanel;

	/**
	 * Create the panel.
	 */
	public Sidebar() {
		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.setBorder(null);
		add(tabbedPane);
		
		JPanel wrapper = new JPanel();
		GridBagLayout gbl_wrapper = new GridBagLayout();
		gbl_wrapper.columnWidths = new int[]{412, 0};
		gbl_wrapper.rowHeights = new int[]{339, 350, 160, 246, 0};
		gbl_wrapper.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_wrapper.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		wrapper.setLayout(gbl_wrapper);
		
		topolgyPanel = new TopologyPanel();
		GridBagConstraints gbc_topolgyPanel = new GridBagConstraints();
		gbc_topolgyPanel.anchor = GridBagConstraints.NORTH;
		gbc_topolgyPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_topolgyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_topolgyPanel.gridx = 0;
		gbc_topolgyPanel.gridy = 0;
		wrapper.add(topolgyPanel, gbc_topolgyPanel);
		trainStrategyPanel = new TrainStrategyPanel();
		GridBagConstraints gbc_trainStrategyPanel = new GridBagConstraints();
		gbc_trainStrategyPanel.anchor = GridBagConstraints.NORTH;
		gbc_trainStrategyPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_trainStrategyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_trainStrategyPanel.gridx = 0;
		gbc_trainStrategyPanel.gridy = 1;
		wrapper.add(trainStrategyPanel, gbc_trainStrategyPanel);
		standardOptionsPanel = new StandardOptionsPanel();
		GridBagConstraints gbc_standardOptionsPanel = new GridBagConstraints();
		gbc_standardOptionsPanel.anchor = GridBagConstraints.NORTH;
		gbc_standardOptionsPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_standardOptionsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_standardOptionsPanel.gridx = 0;
		gbc_standardOptionsPanel.gridy = 2;
		wrapper.add(standardOptionsPanel, gbc_standardOptionsPanel);
		selectedSymbolsPanel = new SelectedSymbolPanel();
		GridBagConstraints gbc_selectedSymbolsPanel = new GridBagConstraints();
		gbc_selectedSymbolsPanel.anchor = GridBagConstraints.NORTH;
		gbc_selectedSymbolsPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectedSymbolsPanel.gridx = 0;
		gbc_selectedSymbolsPanel.gridy = 3;
		wrapper.add(selectedSymbolsPanel, gbc_selectedSymbolsPanel);

		JScrollPane scrollPane = new JScrollPane(wrapper);

		tabbedPane.addTab("Konfiguration", null, scrollPane, null);
		tabbedPane.addTab("Training", null, new JPanel(), null);

	}

}
