package de.unikassel.ann.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class FunctionOverviewjANN extends JPanel implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JEditorPane htmlPane;
	private JTree tree;
	private URL helpURL;
	private static boolean DEBUG = false;

	public FunctionOverviewjANN() {
		super(new GridLayout(1, 0));

		// Create the nodes.
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("jANN-Funktionen");
		createNodes(top);

		// Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		// Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);

		// Create the HTML viewing pane.
		htmlPane = new JEditorPane();
		htmlPane.setEditable(false);
		// initHelp();
		JScrollPane htmlView = new JScrollPane(htmlPane);

		// Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(100, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(100);
		splitPane.setPreferredSize(new Dimension(500, 300));

		// Add the split pane to this panel.
		add(splitPane);
	}

	/** Required by TreeSelectionListener interface. */
	@Override
	public void valueChanged(final TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			UnderFunctionsInfo fInfo = (UnderFunctionsInfo) nodeInfo;
			displayURL(fInfo.underFuntionsURL);
			if (DEBUG) {
				System.out.print(fInfo.underFuntionsURL + ":  \n    ");
			}
		} else {
			displayURL(helpURL);
		}
		if (DEBUG) {
			System.out.println(nodeInfo.toString());
		}
	}

	// private void initHelp() {
	// String s = "TreeDemoHelp.html";
	// helpURL = getClass().getResource(s);
	// if (helpURL == null) {
	// System.err.println("Couldn't open help file: " + s);
	// } else if (DEBUG) {
	// System.out.println("Help URL is " + helpURL);
	// }
	//
	// displayURL(helpURL);
	// }

	private void displayURL(final URL url) {
		try {
			if (url != null) {
				htmlPane.setPage(url);
			} else { // null url
				// htmlPane.setText("File Not Found");
				if (DEBUG) {
					System.out.println("Attempted to display a null URL.");
				}
			}
		} catch (IOException e) {
			System.err.println("Attempted to read a bad URL: " + url);
		}
	}

	private void createNodes(final DefaultMutableTreeNode top) {
		DefaultMutableTreeNode funktions = null; // HauptFunktion
		DefaultMutableTreeNode under_functions = null; // UnterFunktion
		DefaultMutableTreeNode u_UnderFunctions = null; // Unter-Unter-Funktion

		funktions = new DefaultMutableTreeNode("Allgemein");
		top.add(funktions);

		// jANN Projekt und Menu
		under_functions = new DefaultMutableTreeNode(new UnderFunctionsInfo("Das jANN-Projekt", "jANN.html"));
		funktions.add(under_functions);

		under_functions = new DefaultMutableTreeNode(new UnderFunctionsInfo("Menu-Funktionen", "general.html"));
		funktions.add(under_functions);

		u_UnderFunctions = new DefaultMutableTreeNode(new UnderFunctionsInfo("Trainingdaten & Trainingsfehler", "trainDataAndError.html"));
		under_functions.add(u_UnderFunctions);

		u_UnderFunctions = new DefaultMutableTreeNode(new UnderFunctionsInfo("Trainingsdaten Normalisierung", "normalizeData.html"));
		under_functions.add(u_UnderFunctions);

		// Sidebar
		funktions = new DefaultMutableTreeNode("Sidebar");
		top.add(funktions);

		under_functions = new DefaultMutableTreeNode(new UnderFunctionsInfo("Konfiguration", "konfiguration.html"));
		funktions.add(under_functions);

		under_functions = new DefaultMutableTreeNode(new UnderFunctionsInfo("Training", "training.html"));
		funktions.add(under_functions);

		// Jung
		funktions = new DefaultMutableTreeNode("Jung");
		top.add(funktions);

		under_functions = new DefaultMutableTreeNode(new UnderFunctionsInfo("Jung Funktionen", "jung.html"));
		funktions.add(under_functions);

		under_functions = new DefaultMutableTreeNode(new UnderFunctionsInfo("Jung Implementierung", "jungImpl.html"));
		funktions.add(under_functions);

		// Self Organized Network
		funktions = new DefaultMutableTreeNode("Self Organized Network");
		top.add(funktions);

		under_functions = new DefaultMutableTreeNode(new UnderFunctionsInfo("3DBoard View", "3DBoardView.html"));
		funktions.add(under_functions);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked from the event dispatch thread.
	 */
	public static void createAndShowGUI() {

		// Create and set up the window.
		JFrame frame = new JFrame("jANN-Funktionen Übersicht");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new FunctionOverviewjANN());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private class UnderFunctionsInfo {
		public String underFunctionsName;
		public URL underFuntionsURL;

		public UnderFunctionsInfo(final String function, final String filename) {
			underFunctionsName = function;
			underFuntionsURL = this.getClass().getClassLoader().getResource(filename);
			if (underFuntionsURL == null) {
				System.err.println("Couldn't find file: " + filename);
			}
		}

		@Override
		public String toString() {
			return underFunctionsName;
		}
	}
}
