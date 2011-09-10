package de.unikassel.ann.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

import de.unikassel.ann.factory.EdgeFactory;
import de.unikassel.ann.factory.VertexFactory;
import de.unikassel.ann.gui.graph.GraphLayoutViewer;
import de.unikassel.ann.util.XMLResourceBundleControl;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					instance = new Main();
					instance.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * public fields
	 */
	public static Main instance;
	public ResourceBundle i18n;

	/*
	 * private fields
	 */
	private JFrame frame;
	private JTextPane textPane;
	private GraphLayoutViewer glv;

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		redirectSystemStreams();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		i18n = ResourceBundle.getBundle("langpack",
				new XMLResourceBundleControl());

		//
		// Frame
		//
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//
		// Main Menu(Bar)
		//
		JMenuBar mainMenu = new MainMenu(i18n);
		frame.setJMenuBar(mainMenu);

		//
		// Panes
		//
		JSplitPane mainSplitPane = new JSplitPane();
		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);

		JPanel sideBar = new JPanel(new BorderLayout());
		mainSplitPane.setRightComponent(sideBar);

		JSplitPane jungConsoleSplitPane = new JSplitPane();
		jungConsoleSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setLeftComponent(jungConsoleSplitPane);

		JPanel jungPanel = new JPanel(new BorderLayout());
		jungConsoleSplitPane.setLeftComponent(jungPanel);

		JPanel consolePanel = new JPanel(new BorderLayout());
		jungConsoleSplitPane.setRightComponent(consolePanel);

		textPane = new JTextPane();
		textPane.setEditable(false);
		addStylesToDocument(textPane.getStyledDocument());
		consolePanel.add(textPane);
		JScrollPane scrollPane = new JScrollPane(textPane);
		consolePanel.add(scrollPane, BorderLayout.CENTER);

		mainSplitPane.setContinuousLayout(true);
		mainSplitPane.setDividerLocation(600);
		mainSplitPane.setBorder(BorderFactory.createEmptyBorder());
		jungConsoleSplitPane.setContinuousLayout(true);
		jungConsoleSplitPane.setDividerLocation(400);
		jungConsoleSplitPane.setBorder(BorderFactory.createEmptyBorder());

		//
		// Graph-Layout-Viewer
		//
		Dimension dim = new Dimension(600 - 16, 400 - 16);
		glv = new GraphLayoutViewer(dim, jungPanel);
		glv.setFrame(frame);
		glv.setI18n(i18n);
		glv.init();
	}

	private void updateTextArea(final String text) {
		updateTextArea(text, "regular");
	}

	private void updateTextArea(final String text, final String styleName) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Document doc = textPane.getDocument();
				try {
					StyledDocument style = textPane.getStyledDocument();
					doc.insertString(doc.getLength(), text,
							style.getStyle(styleName));
				} catch (BadLocationException e) {
					throw new RuntimeException(e);
				}
				textPane.setCaretPosition(doc.getLength() - 1);
			}
		});
	}

	protected void addStylesToDocument(final StyledDocument doc) {
		// Initialize some styles.
		Style def = StyleContext.getDefaultStyleContext().getStyle(
				StyleContext.DEFAULT_STYLE);

		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "SansSerif");

		Style s = doc.addStyle("error", regular);
		StyleConstants.setForeground(s, Color.red);

		s = doc.addStyle("success", regular);
		StyleConstants.setForeground(s, Color.green);

		s = doc.addStyle("warn", regular);
		StyleConstants.setForeground(s, Color.orange);

		s = doc.addStyle("italic", regular);
		StyleConstants.setItalic(s, true);

		s = doc.addStyle("bold", regular);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("small", regular);
		StyleConstants.setFontSize(s, 10);

		s = doc.addStyle("large", regular);
		StyleConstants.setFontSize(s, 16);

	}

	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(final byte[] b, final int off, final int len)
					throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(final byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		OutputStream errorOut = new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				updateTextArea(String.valueOf((char) b), "error");
			}

			@Override
			public void write(final byte[] b, final int off, final int len)
					throws IOException {
				updateTextArea(new String(b, off, len), "error");
			}

			@Override
			public void write(final byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		// System.setErr(new PrintStream(errorOut, true));
	}

}
