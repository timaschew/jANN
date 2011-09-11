package de.unikassel.vis;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

public class TestSideBar extends JFrame {

	private JPanel contentPane;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestSideBar frame = new TestSideBar();
					frame.setVisible(true);
//					TestSideBar window = new TestSideBar();
//					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestSideBar() {
		JSplitPane mainSplitPane = new JSplitPane();
		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);
		mainSplitPane.setSize(580, 600);
		mainSplitPane.setAutoscrolls(true);
		
		/**
		 * TabPanel and Components
		 */
		JTabbedPane tabbedPane = new JTabbedPane();
		 //The following line enables to use scrolling tabs.
//        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
        
        /*
         * Tab 1 Elements
         */
		JComponent panelTab1 = new JPanel();
		JScrollPane scrollPaneTab1 = new JScrollPane(panelTab1);
		scrollPaneTab1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tabbedPane.addTab("Tab 1",scrollPaneTab1);
	    tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		panelTab1.setLayout(new GridBagLayout());
		
		
		Topologie topologiePanel = new Topologie();
		GridBagConstraints gbc_topologiePanel = new GridBagConstraints();
		gbc_topologiePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_topologiePanel.gridx = 0;
		gbc_topologiePanel.gridy = 0;
		panelTab1.add(topologiePanel, gbc_topologiePanel);
		
		TrainingJPanel trainingsPanel = new TrainingJPanel();
		GridBagConstraints gbc_trainingsPanel = new GridBagConstraints();
		gbc_trainingsPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_trainingsPanel.gridx = 0;
		gbc_trainingsPanel.gridy = 1;
		panelTab1.add(trainingsPanel, gbc_trainingsPanel);
		
		StandardOptions standardOptionsPanel = new StandardOptions();
		GridBagConstraints gbc_standardOptionsPanel = new GridBagConstraints();
		gbc_standardOptionsPanel.anchor = GridBagConstraints.WEST; // Wichtig dass es nach West verankert ist
		gbc_standardOptionsPanel.gridx = 0;
		gbc_standardOptionsPanel.gridy = 2;
		panelTab1.add(standardOptionsPanel, gbc_standardOptionsPanel);
		
		
	    /*
	     * Tab 2 Elements
	     */
        JComponent panelTab2 = new JPanel();
    	JScrollPane scrollPaneTab2 = new JScrollPane(panelTab2);
		scrollPaneTab1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tabbedPane.addTab("Tab 2",scrollPaneTab2);
	    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		GridBagLayout gbl_panelTab2 = new GridBagLayout();
		gbl_panelTab2.maximumLayoutSize(new Container());
		panelTab2.setLayout( gbl_panelTab2);
		
		SelectedSymbol selectedSymbolPanel = new SelectedSymbol();
		GridBagConstraints gbc_selectedSymbolPanel = new GridBagConstraints();
		gbc_selectedSymbolPanel.fill = GridBagConstraints.NONE;
		gbc_selectedSymbolPanel.gridx = 0;
		gbc_selectedSymbolPanel.gridy = 0;
		panelTab2.add(selectedSymbolPanel, gbc_selectedSymbolPanel);
		
		TrainingSteuerung trainingsSteuerungPanel = new TrainingSteuerung();
		GridBagConstraints gbc_trainingsSteuerungPanel = new GridBagConstraints();
		gbc_trainingsSteuerungPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_trainingsSteuerungPanel.gridx = 0;
		gbc_trainingsSteuerungPanel.gridy = 1;
		panelTab2.add(trainingsSteuerungPanel, gbc_trainingsSteuerungPanel);
		
		TrainingJPanel trainingsPanels = new TrainingJPanel();
		GridBagConstraints gbc_trainingsPanels = new GridBagConstraints();
		gbc_trainingsPanels.fill = GridBagConstraints.HORIZONTAL;
		gbc_trainingsPanels.gridx = 0;
		gbc_trainingsPanels.gridy = 2;
		panelTab2.add(trainingsPanels, gbc_trainingsPanels);
		
		StandardOptions standardOptionsPanels = new StandardOptions();
		GridBagConstraints gbc_standardOptionsPanels = new GridBagConstraints();
		gbc_standardOptionsPanels.anchor = GridBagConstraints.WEST; // Wichtig dass es nach West verankert ist
		gbc_standardOptionsPanels.gridx = 0;
		gbc_standardOptionsPanels.gridy = 3;
		panelTab2.add(standardOptionsPanels, gbc_standardOptionsPanels);
		
		
		
      
        
		// Tab 3 Elements
        JComponent panelTab3 = new JPanel();
        tabbedPane.addTab("Tab 3", panelTab3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
//		SelectedSymbol selectedSymbolPanel = new SelectedSymbol();
//		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
//		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
//		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
//		gbc_panel_3.fill = GridBagConstraints.BOTH;
//		gbc_panel_3.gridx = 0;
//		gbc_panel_3.gridy = 3;
//		panelTab1.add(selectedSymbolPanel, gbc_panel_3);
		
		
		//Add TabbedPanel in the window
		mainSplitPane.setRightComponent(tabbedPane);
		
		JSplitPane jungConsoleSplitPane = new JSplitPane();
		jungConsoleSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setLeftComponent(jungConsoleSplitPane);
		
		JPanel jungPanel = new JPanel(new BorderLayout());
		jungConsoleSplitPane.setLeftComponent(jungPanel);
		
//		JButton prototypeButton = new JButton("start");
//		final TrainErrorPlot tep = new TrainErrorPlot();
//		tep.init();
//		JFrame f = new JFrame();
//		Component plot = tep.getPlotPanel();
//		f.getContentPane().add(plot);
//		f.setVisible(true);
//		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		f.setSize(tep.getPlotPanel().getPreferredSize());
	}

}
