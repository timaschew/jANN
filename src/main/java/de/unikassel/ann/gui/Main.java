package de.unikassel.ann.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.chart.ChartTrainingDataPanel;
import de.unikassel.ann.gui.chart.ChartTrainingErrorPanel;
import de.unikassel.ann.gui.sidebar.Sidebar;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.util.Logger;

public class Main {

	public enum Panel {
		CONSOLE, TRAINERROR_CHART, TRAIN_DATA_CHART
	};

	public static Main instance = getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					instance.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static Main getInstance() {
		if (instance == null) {
			instance = new Main();
		}
		return instance;
	}

	/*
	 * private fields
	 */
	private JFrame frame;
	private JTextPane textPane = new JTextPane();
	private JTextPane textPaneSOM;
	private JPanel jungPanel;
	private JSplitPane jungConsoleSplitPane;
	private JPanel consolePanel;
	private JPanel consolePanelSOM;
	private boolean initialized = false;

	/*
	 * public fields
	 */
	public MainMenu mainMenu;
	public Sidebar sidebar;
	public JSplitPane mainSplitPane;
	public JPanel consoleOrChartPanel;
	public ChartTrainingErrorPanel trainingErrorChartPanel;
	public ChartTrainingDataPanel trainingDataChartPanel;

	/**
	 * Create the application.
	 */
	private Main() {
		instance = this;
		Logger.init();
		try {
			// Set look and feel by the properties file
			String lookAndFeel = Settings.properties.getProperty("gui.lookandfeel");
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			Logger.info(this.getClass(), "Look&Feel auf Defaultwert gesetzt: {} ", UIManager.getLookAndFeel().getName());
		}

		Logger.debug(this.getClass(), "logging debug");
		Logger.info(this.getClass(), "initializing app...");
		Logger.warn(this.getClass(), "test");
		Logger.error(this.getClass(), "BOOOM!");

		initialize();
		initialized = true;
		Logger.info(this.getClass(), "initializing finished at {}", new Date().toString());
	}

	public void switchBottomPanel(final Panel panel) {
		switch (panel) {
		case CONSOLE:
			consoleOrChartPanel.removeAll();
			consoleOrChartPanel.add(consolePanel);
			break;
		case TRAIN_DATA_CHART:
			consoleOrChartPanel.removeAll();
			consoleOrChartPanel.add(trainingDataChartPanel);
			break;
		case TRAINERROR_CHART:
			consoleOrChartPanel.removeAll();
			consoleOrChartPanel.add(trainingErrorChartPanel);
			break;
		}
		consoleOrChartPanel.revalidate();
		consoleOrChartPanel.repaint();
	}

	/**
	 * @return
	 */
	public boolean isInit() {
		return initialized;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		//
		// Frame
		//
		frame = new JFrame();
		frame.setBounds(100, 100, 1060, 600); // statt 800 1060
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		//
		// Main Menu(Bar)
		//
		mainMenu = new MainMenu();
		frame.setJMenuBar(mainMenu);

		//
		// Panes
		//
		mainSplitPane = new JSplitPane();
		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);
		// makes the right component's size remain fixed
		mainSplitPane.setResizeWeight(1.0);

		initSidebarPanel();

		jungConsoleSplitPane = new JSplitPane();
		jungConsoleSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		// jungConsoleSplitPane.setMinimumSize(new Dimension(625, 50));
		jungPanel = new JPanel(new BorderLayout());

		consolePanel = new JPanel(new BorderLayout());
		trainingErrorChartPanel = new ChartTrainingErrorPanel();
		trainingDataChartPanel = new ChartTrainingDataPanel();

		textPane.setEditable(false);
		addStylesToDocument(textPane.getStyledDocument());
		JScrollPane scrollPane = new JScrollPane(textPane);
		consolePanel.add(scrollPane, BorderLayout.CENTER);

		consoleOrChartPanel = new JPanel(new BorderLayout());
		consoleOrChartPanel.add(consolePanel);

		// SplitPane for Backpropagation with JUNG
		jungConsoleSplitPane.setContinuousLayout(true);
		jungConsoleSplitPane.setDividerLocation(400);
		jungConsoleSplitPane.setBorder(BorderFactory.createEmptyBorder());
		jungConsoleSplitPane.setRightComponent(consoleOrChartPanel);
		jungConsoleSplitPane.setLeftComponent(jungPanel);

		// SplitPane for SOM with 3DBoard
		consolePanelSOM = new JPanel(new BorderLayout());
		textPaneSOM = new JTextPane();
		textPaneSOM.setEditable(false);
		addStylesToDocument(textPaneSOM.getStyledDocument());
		consolePanelSOM.add(textPaneSOM);
		JScrollPane scrollPanes = new JScrollPane(textPaneSOM);
		consolePanelSOM.add(scrollPanes, BorderLayout.CENTER);

		mainSplitPane.setLeftComponent(jungConsoleSplitPane);
		mainSplitPane.setContinuousLayout(true);
		mainSplitPane.setDividerLocation(600);
		mainSplitPane.setBorder(BorderFactory.createEmptyBorder());

		//
		// Graph-Layout-Viewer
		//
		Dimension dim = new Dimension(600 - 16, 400 - 16);
		// The Dimension is given by the DividerLocation of the mainSplitPane
		// and the jungConsoleSplitPane minus the scrollbar size

		GraphController graphController = GraphController.getInstance();
		graphController.setDimension(dim);
		graphController.setParent(jungPanel);
		graphController.init();

		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem mtSession = new JRadioButtonMenuItem(Settings.getInstance().getCurrentSession().getName());
		group.add(mtSession);
		mtSession.setSelected(true);
		mainMenu.subMenuSession.add(mtSession);

	}

	public void initSidebarPanel() {
		sidebar = new Sidebar();
		Network net = Settings.getInstance().getCurrentSession().getNetworkConfig().getNetwork();
		// old listeners are already removed in UserSession constructor
		net.addPropertyChangeListener(Network.PropertyChanges.NEURONS.name(), sidebar.topolgyPanel);

		// Provide minimum sizes for the components in the split pane
		sidebar.setMinimumSize(new Dimension(435, 50));
		mainSplitPane.setRightComponent(sidebar);
	}

	public void updateTextArea(final String text) {
		updateTextArea(text, "regular");
	}

	public void updateTextArea(final String text, final String styleName) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Document doc = textPane.getDocument();
				try {
					StyledDocument style = textPane.getStyledDocument();
					doc.insertString(doc.getLength(), text, style.getStyle(styleName));
				} catch (BadLocationException e) {
					throw new RuntimeException(e);
				}
				textPane.setCaretPosition(doc.getLength() - 1);
			}
		});
	}

	protected void addStylesToDocument(final StyledDocument doc) {
		// Initialize some styles.
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "SansSerif");

		Style s = doc.addStyle("error", regular);
		StyleConstants.setForeground(s, Color.red);

		s = doc.addStyle("success", regular);
		StyleConstants.setForeground(s, Color.green);

		s = doc.addStyle("warn", regular);
		StyleConstants.setForeground(s, new Color(255, 140, 0));

		s = doc.addStyle("italic", regular);
		StyleConstants.setItalic(s, true);

		s = doc.addStyle("bold", regular);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("small", regular);
		StyleConstants.setFontSize(s, 10);

		s = doc.addStyle("large", regular);
		StyleConstants.setFontSize(s, 16);

	}

	/**
	 * 
	 */
	public void clearConsole() {
		textPane.setText("");

	}

}
