package de.unikassel.ann.gui.sidebar;


import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;


public class SideConfigurationPanel extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private static SideConfigurationPanel sideBarPanelInstance;
	
	
	public static SideConfigurationPanel getInstance(){
		if (sideBarPanelInstance == null) {
			sideBarPanelInstance = new SideConfigurationPanel();
		}
		return sideBarPanelInstance;
	}

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SideBarPanel panel = new SideBarPanel();
//					JFrame frame = new JFrame();
//					frame.setContentPane(panel);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public SideConfigurationPanel() {
//		JSplitPane mainSplitPane = new JSplitPane();
//		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);
//		mainSplitPane.setSize(580, 600);
//		mainSplitPane.setAutoscrolls(true);
		setSize(450, 600);
		/**
		 * TabPanel and Components
		 */
//		JTabbedPane tabbedPane = new JTabbedPane();
		 //The following line enables to use scrolling tabs.
//        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
        
        /*
         * Tab 1 Elements
         */
		JComponent panelTab1 = new JPanel();
		JScrollPane scrollPaneTab1 = new JScrollPane(panelTab1);
		scrollPaneTab1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.addTab("Netzwerkkonfiguration",scrollPaneTab1);
	    this.setMnemonicAt(0, KeyEvent.VK_1);
		panelTab1.setLayout(new GridBagLayout());
		
		
		TopologyPanel topologiePanel = TopologyPanel.getTopologyPanelInstance();
		GridBagConstraints gbc_topologiePanel = new GridBagConstraints();
		gbc_topologiePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_topologiePanel.gridx = 0;
		gbc_topologiePanel.gridy = 0;
		panelTab1.add(topologiePanel, gbc_topologiePanel);
		
		TrainStrategyPanel trainingsPanel = TrainStrategyPanel.getTrainStrategyPanelInstance();
		GridBagConstraints gbc_trainingsPanel = new GridBagConstraints();
		gbc_trainingsPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_trainingsPanel.gridx = 0;
		gbc_trainingsPanel.gridy = 1;
		panelTab1.add(trainingsPanel, gbc_trainingsPanel);
		
		StandardOptionsPanel standardOptionsPanel = StandardOptionsPanel.getStandardOptionsPanelInstance();
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
    	scrollPaneTab2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneTab1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.addTab("Tab 2",scrollPaneTab2);
	    this.setMnemonicAt(1, KeyEvent.VK_2);
		GridBagLayout gbl_panelTab2 = new GridBagLayout();
		gbl_panelTab2.rowHeights = new int[]{0, 0, 98};
		panelTab2.setLayout( gbl_panelTab2);
		
		SelectedSymbolPanel selectedSymbolPanel = SelectedSymbolPanel.getSelectedSymbolPanelInstance();
		GridBagConstraints gbc_selectedSymbolPanel = new GridBagConstraints();
		gbc_selectedSymbolPanel.fill = GridBagConstraints.BOTH;
		gbc_selectedSymbolPanel.gridx = 0;
		gbc_selectedSymbolPanel.gridy = 0;
		panelTab2.add(selectedSymbolPanel, gbc_selectedSymbolPanel);
		
		TrainControlPanel trainingsSteuerungPanel = TrainControlPanel.getTrainControlPanelInstance();
		GridBagConstraints gbc_trainingsSteuerungPanel = new GridBagConstraints();
		gbc_trainingsSteuerungPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_trainingsSteuerungPanel.gridx = 0;
		gbc_trainingsSteuerungPanel.gridy = 1;
		panelTab2.add(trainingsSteuerungPanel, gbc_trainingsSteuerungPanel);
		
		JPanel leerPanel = new JPanel();
		leerPanel.setSize(400, 200);
		GridBagConstraints gbc_leerPanel = new GridBagConstraints();
		gbc_leerPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_leerPanel.gridx = 0;
		gbc_leerPanel.gridy = 2;
		panelTab2.add(leerPanel, gbc_leerPanel);
      
        
		// Tab 3 Elements
        JComponent panelTab3 = new JPanel();
        this.addTab("Tab 3", panelTab3);
        this.setMnemonicAt(2, KeyEvent.VK_3);
		
		
		//Add TabbedPanel in the window
//		mainSplitPane.setRightComponent(tabbedPane);
//		add(tabbedPane);

	}

}
