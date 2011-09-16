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

/**
 * @author anton
 * 
 */
public class Sidebar extends JPanel {

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

		topolgyPanel = new TopologyPanel();
		trainStrategyPanel = new TrainStrategyPanel();
		standardOptionsPanel = new StandardOptionsPanel();
		selectedSymbolsPanel = new SelectedSymbolPanel();

		JPanel wrapper = new JPanel(new GridLayout(0, 1));
		wrapper.add(topolgyPanel);
		wrapper.add(trainStrategyPanel);
		wrapper.add(standardOptionsPanel);
		wrapper.add(selectedSymbolsPanel);

		JScrollPane scrollPane = new JScrollPane(wrapper);

		tabbedPane.addTab("Konfiguration", null, scrollPane, null);
		tabbedPane.addTab("Training", null, new JPanel(), null);

	}

}
