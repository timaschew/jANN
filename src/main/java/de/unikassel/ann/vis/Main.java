package de.unikassel.ann.vis;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyleContext.NamedStyle;
import javax.swing.text.StyledDocument;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		initialize();
		redirectSystemStreams();
		Thread printThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				 for( int i = 0 ; i < 100 ; i++ ) {
					 	if (i % 10 == 0) {
					 		new NullPointerException("test").printStackTrace();
					 	} else {
					 		System.out.println(i);
					 	}
			            
			            try {
							Thread.sleep( 500 );
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			        }
			}
		});
//		printThread.start();
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
		this.layout = new StaticLayout<Number, Number>(graph, new Dimension(600-16, 400-16));
		// The Dimension is given by the DividerLocation of the mainSplitPane and the jungConsoleSplitPane minus the scrollbar size 
		
		viewer = new VisualizationViewer<Number, Number>(layout);
		viewer.setBackground(Color.white);
		viewer.addPreRenderPaintable(new VisualizationViewer.Paintable(){
			public void paint(Graphics g) {
				final int height = viewer.getHeight();
				final int width = viewer.getWidth();
			}
			public boolean useTransform() { return false; }
		});
		
        viewer.getRenderContext().setVertexLabelTransformer(MapTransformer.<Number,String>getInstance(
        		LazyMap.<Number,String>decorate(new HashMap<Number,String>(), new ToStringLabeller<Number>())));
        
        viewer.getRenderContext().setEdgeLabelTransformer(MapTransformer.<Number,String>getInstance(
        		LazyMap.<Number,String>decorate(new HashMap<Number,String>(), new ToStringLabeller<Number>())));

        viewer.setVertexToolTipTransformer(viewer.getRenderContext().getVertexLabelTransformer());
        
        Container content = jungPanel;
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(viewer);
        content.add(panel);
        Factory<Number> vertexFactory = new VertexFactory();
        Factory<Number> edgeFactory = new EdgeFactory();
        
        final EditingModalGraphMouse<Number,Number> graphMouse = 
        	new EditingModalGraphMouse<Number,Number>(viewer.getRenderContext(), vertexFactory, edgeFactory);
        
        viewer.setGraphMouse(graphMouse);
        viewer.addKeyListener(graphMouse.getModeKeyListener());
        viewer.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        
        graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
        graphMouse.setZoomAtMouse(false);
	}

	private JMenu getDateiMenu() {
		JMenu mnDatei = new JMenu("Datei");
		
		JMenuItem mntmNeu = new ActionMenuItem("Neu", Action.NEW);
		mnDatei.add(mntmNeu);
		
		JMenuItem mntmOeffnen = new ActionMenuItem("\u00D6ffnen", Action.OPEN);
		mnDatei.add(mntmOeffnen);
		
		JMenuItem mntmSpeichern = new ActionMenuItem("Speichern", Action.SAVE);
		mnDatei.add(mntmSpeichern);

		mnDatei.addSeparator();
		
		JMenuItem mntmBeenden = new ActionMenuItem("Beenden", Action.EXIT);
		mnDatei.add(mntmBeenden);
		
		return mnDatei;
	}

	private JMenu getBearbeitenMenu() {
		JMenu mnBearbeiten = new JMenu("Bearbeiten");
		return mnBearbeiten;
	}

	private JMenu getAnsichtMenu() {
		JMenu mnAnsicht = new JMenu("Ansicht");
		
		JMenuItem mntmDatenvisualisierung = new ActionMenuItem("Daten-Visualisierung", Action.VIEW_DATA);
		mnAnsicht.add(mntmDatenvisualisierung);
		
		JMenuItem mntmTrainingfehlerverlauf = new ActionMenuItem("Training-Fehler-Verlauf", Action.VIEW_TRAINING);
		mnAnsicht.add(mntmTrainingfehlerverlauf);

		return mnAnsicht;
	}
	
	private JMenu getHilfeMenu() {
		JMenu mnHilfe = new JMenu("Hilfe");
		
		JMenuItem mntmUeber = new ActionMenuItem("\u00DCber", Action.ABOUT);
		mnHilfe.add(mntmUeber);
		
		return mnHilfe;
	}
	
	private void updateTextArea(final String text) {
		updateTextArea(text, "regular");
	}
	private void updateTextArea(final String text, final String styleName) {
		SwingUtilities.invokeLater(new Runnable() {
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
	
    protected void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

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
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};
		
		OutputStream errorOut = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b), "error");
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len), "error");
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(errorOut, true));
	}

	private enum Action {
		NONE,
		NEW,
		OPEN,
		SAVE,
		EXIT,
		VIEW_DATA,
		VIEW_TRAINING,
		ABOUT
	}
	
	private class ActionMenuItem extends JMenuItem implements ActionListener {
		Action action;
		
		public ActionMenuItem(String text) {
			this(text, Action.NONE);
		}
		
		public ActionMenuItem(String text, Action action) {
			super(text);
			this.action = action;
			addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e) {
			System.out.println(action);
			switch(action) {
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
		
		int i=0;
	
		public Number create() {
			return i++;
		}
	}
	
	class EdgeFactory implements Factory<Number> {
	
		int i=0;
		
		public Number create() {
			return i++;
		}
	}
}
