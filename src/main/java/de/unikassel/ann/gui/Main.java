package de.unikassel.ann.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Network;
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

	private JFrame frame;
	private JTextPane textPane;

	/**
	 * Graph, Layout and Viewer
	 */
	private DirectedGraph<Number, Number> graph;
	private AbstractLayout<Number, Number> layout;
	private VisualizationViewer<Number, Number> viewer;
	private ResourceBundle i18n;

	private static Main instance;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		instance = this;
		initialize();
		redirectSystemStreams();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		i18n = ResourceBundle.getBundle("langpack", new XMLResourceBundleControl());

		//
		// Frame
		//
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//
		// MenuBar
		//
		JMenuBar menuBar = new JMenuBar();

		JMenu mnDatei = getDateiMenu();
		menuBar.add(mnDatei);

		JMenu mnBearbeiten = getBearbeitenMenu();
		menuBar.add(mnBearbeiten);

		JMenu mnAnsicht = getAnsichtMenu();
		menuBar.add(mnAnsicht);

		JMenu mnHilfe = getHilfeMenu();
		menuBar.add(mnHilfe);

		frame.setJMenuBar(menuBar);

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

		JButton prototypeButton = new JButton("start");
		final TrainErrorPlot tep = new TrainErrorPlot();
		tep.init();
		JFrame f = new JFrame();
		Component plot = tep.getPlotPanel();
		f.getContentPane().add(plot);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setSize(tep.getPlotPanel().getPreferredSize());
		prototypeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				final NetConfig netConfig = NetworkFactory.createXorNet(2000, true);
				netConfig.addTrainErrorListener(tep);
				final Network net = netConfig.getNetwork();

				// XOR training data
				final DataPairSet trainSet = new DataPairSet();
				trainSet.addPair(new DataPair(new Double[] { 0.0, 0.0 }, new Double[] { 0.0 }));
				trainSet.addPair(new DataPair(new Double[] { 0.0, 1.0 }, new Double[] { 1.0 }));
				trainSet.addPair(new DataPair(new Double[] { 1.0, 0.0 }, new Double[] { 1.0 }));
				trainSet.addPair(new DataPair(new Double[] { 1.0, 1.0 }, new Double[] { 0.0 }));

				// XOR test data
				final DataPairSet testSet = new DataPairSet();
				testSet.addPair(new DataPair(new Double[] { 0.0, 0.0 }, new Double[] { Double.NaN }));
				testSet.addPair(new DataPair(new Double[] { 0.0, 1.0 }, new Double[] { Double.NaN }));
				testSet.addPair(new DataPair(new Double[] { 1.0, 0.0 }, new Double[] { Double.NaN }));
				testSet.addPair(new DataPair(new Double[] { 1.0, 1.0 }, new Double[] { Double.NaN }));

				final Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						netConfig.getTrainingModule().train(trainSet);
						netConfig.getWorkingModule().work(net, testSet);
						netConfig.printStats();
						System.out.println(testSet);
						tep.nextRun();
					}
				});
				t.start();
				Thread t2 = new Thread(new Runnable() {
					@Override
					public void run() {
						while (t.isAlive()) {
							tep.updateUI();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						tep.updateUI();
					}
				});
				t2.start();

			}
		});
		sideBar.add(prototypeButton);

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
		// (Simple) Graph
		//
		graph = new DirectedSparseGraph<Number, Number>();
		layout = new StaticLayout<Number, Number>(graph, new Dimension(600 - 16, 400 - 16));
		// The Dimension is given by the DividerLocation of the mainSplitPane and the jungConsoleSplitPane minus the scrollbar size

		viewer = new VisualizationViewer<Number, Number>(layout);
		viewer.setBackground(Color.white);
		viewer.addPreRenderPaintable(new VisualizationViewer.Paintable() {
			@Override
			public void paint(final Graphics g) {
				final int height = viewer.getHeight();
				final int width = viewer.getWidth();
			}

			@Override
			public boolean useTransform() {
				return false;
			}
		});

		viewer.getRenderContext().setVertexLabelTransformer(
				MapTransformer.<Number, String> getInstance(LazyMap.<Number, String> decorate(new HashMap<Number, String>(),
						new ToStringLabeller<Number>())));

		viewer.getRenderContext().setEdgeLabelTransformer(
				MapTransformer.<Number, String> getInstance(LazyMap.<Number, String> decorate(new HashMap<Number, String>(),
						new ToStringLabeller<Number>())));

		viewer.setVertexToolTipTransformer(viewer.getRenderContext().getVertexLabelTransformer());

		Container content = jungPanel;
		final GraphZoomScrollPane panel = new GraphZoomScrollPane(viewer);
		content.add(panel);
		Factory<Number> vertexFactory = new VertexFactory();
		Factory<Number> edgeFactory = new EdgeFactory();

		final EditingModalGraphMouse<Number, Number> graphMouse = new EditingModalGraphMouse<Number, Number>(viewer.getRenderContext(),
				vertexFactory, edgeFactory);

		viewer.setGraphMouse(graphMouse);
		viewer.addKeyListener(graphMouse.getModeKeyListener());
		viewer.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(final MouseWheelEvent e) {
				// TODO Auto-generated method stub

			}
		});

		graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
		graphMouse.setZoomAtMouse(false);
	}

	private JMenu getDateiMenu() {
		JMenu mnDatei = new JMenu(i18n.getString("menu.file"));

		JMenuItem mntmNeu = new ActionMenuItem(i18n.getString("menu.file.new"), Action.NEW);
		mnDatei.add(mntmNeu);

		JMenuItem mntmOeffnen = new ActionMenuItem(i18n.getString("menu.file.open"), Action.OPEN);
		mnDatei.add(mntmOeffnen);

		JMenuItem mntmSpeichern = new ActionMenuItem(i18n.getString("menu.file.save"), Action.SAVE);
		mnDatei.add(mntmSpeichern);

		mnDatei.addSeparator();

		JMenuItem mntmBeenden = new ActionMenuItem(i18n.getString("menu.file.exit"), Action.EXIT);
		mnDatei.add(mntmBeenden);

		return mnDatei;
	}

	private JMenu getBearbeitenMenu() {
		JMenu mnBearbeiten = new JMenu(i18n.getString("menu.edit"));
		return mnBearbeiten;
	}

	private JMenu getAnsichtMenu() {
		JMenu mnAnsicht = new JMenu(i18n.getString("menu.view"));

		JMenuItem mntmDatenvisualisierung = new ActionMenuItem(i18n.getString("menu.view.showTrainingData"), Action.VIEW_DATA);
		mnAnsicht.add(mntmDatenvisualisierung);

		JMenuItem mntmTrainingfehlerverlauf = new ActionMenuItem(i18n.getString("menu.view.showTrainingError"), Action.VIEW_TRAINING);
		mnAnsicht.add(mntmTrainingfehlerverlauf);

		return mnAnsicht;
	}

	private JMenu getHilfeMenu() {
		JMenu mnHilfe = new JMenu(i18n.getString("menu.help"));

		JMenuItem mntmUeber = new ActionMenuItem(i18n.getString("menu.help.about"), Action.ABOUT);
		mnHilfe.add(mntmUeber);

		return mnHilfe;
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

		System.setOut(new PrintStream(out, true));
		// System.setErr(new PrintStream(errorOut, true));
	}

	private enum Action {
		NONE, NEW, OPEN, SAVE, EXIT, VIEW_DATA, VIEW_TRAINING, ABOUT
	}

	private class ActionMenuItem extends JMenuItem implements ActionListener {
		Action action;

		public ActionMenuItem(final String text) {
			this(text, Action.NONE);
		}

		public ActionMenuItem(final String text, final Action action) {
			super(text);
			this.action = action;
			addActionListener(this);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			System.out.println(action);
			switch (action) {
			case NEW:
				// TODO
				break;
			case OPEN:
				// TODO
				break;
			case SAVE:
				// TODO
				break;
			case VIEW_DATA:
				// TODO
				break;
			case VIEW_TRAINING:
				// TODO
				break;
			case ABOUT:
				// TODO
				break;
			case EXIT:
				System.exit(e.getID());
				break;
			case NONE:
			default:
				System.out.println("Unknown command: " + action);
				break;
			}
		}

	}

	class VertexFactory implements Factory<Number> {

		int i = 0;

		@Override
		public Number create() {
			return i++;
		}
	}

	class EdgeFactory implements Factory<Number> {

		int i = 0;

		@Override
		public Number create() {
			return i++;
		}
	}
}
