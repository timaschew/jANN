package de.unikassel.vis;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
//					frame.setVisible(true);
					TestSideBar window = new TestSideBar();
					window.frame.setVisible(true);
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
		mainSplitPane.setSize(500, 700);
		mainSplitPane.setAutoscrolls(true);
		
		JPanel sideBar = new JPanel();
		mainSplitPane.setRightComponent(sideBar);
		GridBagLayout gbl_sideBar = new GridBagLayout();
		gbl_sideBar.columnWidths = new int[]{348, 0};
		gbl_sideBar.rowHeights = new int[]{367, 171, 117, 54, 0, 0};
		gbl_sideBar.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_sideBar.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		sideBar.setLayout(gbl_sideBar);
		sideBar.setAutoscrolls(true);
		
		JPanel panel = new JPanel();
		Topologie topologiePanel = new Topologie();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		sideBar.add(topologiePanel, gbc_panel);
		
		JPanel panel_1 = new JPanel();
		TrainingJPanel trainingsPanel = new TrainingJPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		sideBar.add(trainingsPanel, gbc_panel_1);
		
		JPanel panel_2 = new JPanel();
		StandardOptions standardOptionsPanel = new StandardOptions();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		sideBar.add(standardOptionsPanel, gbc_panel_2);
		
		JPanel panel_3 = new JPanel();
		SelectedSymbol selectedSymbolPanel = new SelectedSymbol();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		sideBar.add(selectedSymbolPanel, gbc_panel_3);
		

		
		
		
		
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
