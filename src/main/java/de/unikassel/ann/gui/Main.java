package de.unikassel.ann.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
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

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.graph.GraphLayoutViewer;
import de.unikassel.ann.gui.sidebar.Sidebar;

public class Main {

	public static Main instance = new Main();

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

	/*
	 * private fields
	 */
	private JFrame frame;
	private JTextPane textPane;
	private GraphLayoutViewer glv;
	public Sidebar sideBar;

	/**
	 * Create the application.
	 */
	private Main() {
		initialize();
		redirectSystemStreams();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		//
		// Frame
		//
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//
		// Main Menu(Bar)
		//
		JMenuBar mainMenu = new MainMenu(this);
		frame.setJMenuBar(mainMenu);

		//
		// Panes
		//
		JSplitPane mainSplitPane = new JSplitPane();
		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);

		// SideConfigurationPanel sideBar = new SideConfigurationPanel();
		sideBar = new Sidebar();
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
		// The Dimension is given by the DividerLocation of the mainSplitPane
		// and the jungConsoleSplitPane minus the scrollbar size

		glv = new GraphLayoutViewer(dim, jungPanel);
		glv.setFrame(frame);
		glv.init();
	}

	public GraphLayoutViewer getGraphLayoutViewer() {
		return glv;
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
			public void write(final byte[] b, final int off, final int len) throws IOException {
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
			public void write(final byte[] b, final int off, final int len) throws IOException {
				updateTextArea(new String(b, off, len), "error");
			}

			@Override
			public void write(final byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		if (Settings.properties.get("redirect.std.out").equals("1")) {
			System.setOut(new PrintStream(out, true));
		}
		if (Settings.properties.get("redirect.std.err").equals("1")) {
			System.setErr(new PrintStream(errorOut, true));
		}

	}

}
