package de.unikassel.ann.gui.graph;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import de.unikassel.ann.controller.EdgeController;
import de.unikassel.ann.controller.VertexController;
import de.unikassel.ann.factory.EdgeFactory;
import de.unikassel.ann.factory.VertexFactory;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class GraphLayoutViewer {

	private static Properties properties;

	/*
	 * fields
	 */
	protected DirectedGraph<Vertex, Edge> graph;
	protected AbstractLayout<Vertex, Edge> layout;
	protected VisualizationViewer<Vertex, Edge> viewer;
	protected VertexFactory vertexFactory;
	protected EdgeFactory edgeFactory;
	protected GraphMouse<Vertex, Edge> graphMouse;

	private JFrame frame;
	private ResourceBundle i18n;

	/**
	 * Constructor
	 * 
	 * @param Dimension
	 *            dim
	 * @param Container
	 *            parent
	 */
	public GraphLayoutViewer(Dimension dim, Container parent) {
		loadConfig();

		graph = new DirectedSparseGraph<Vertex, Edge>();
		layout = new StaticLayout<Vertex, Edge>(graph, dim);

		viewer = new VisualizationViewer<Vertex, Edge>(layout);
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

		//
		// Controller
		//
		// TODO DISCUSS: Is a GraphController which creates the Vertex- and
		// EdgeController more sensible?

		// Vertex
		VertexController.getInstance().init(graph, viewer);

		// Edge
		EdgeController.getInstance().init(graph, viewer);

		//
		// Panel
		// TODO DISCUSS use really the GraphZoomScollPane or maybe create an own
		//
		final GraphZoomScrollPane panel = new GraphZoomScrollPane(viewer);
		parent.add(panel);

		//
		// Factories
		//
		vertexFactory = new VertexFactory();
		edgeFactory = new EdgeFactory();
	}

	private void loadConfig() {
		if (properties == null) {
			properties = new Properties();
		}
		try {
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream("config.properties");
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public void init() {
		//
		// Graph Mouse
		//
		initGraphMouse();
		addMouseModeMenu();
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setI18n(ResourceBundle i18n) {
		this.i18n = i18n;
	}

	public void refresh() {
		viewer.repaint();
	}

	private void initGraphMouse() {
		// Create Graph Mouse (set in and out parameter to '1f' to disable zoom)
		graphMouse = new GraphMouse<Vertex, Edge>(viewer.getRenderContext(),
				vertexFactory, edgeFactory, 1f, 1f);

		viewer.setGraphMouse(graphMouse);
		viewer.addKeyListener(graphMouse.getModeKeyListener());
		viewer.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(final MouseWheelEvent e) {
			}
		});

		graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
		graphMouse.setZoomAtMouse(false);
	}

	/**
	 * Add MouseMode Menu to the MainMenu of the frame to set the mode of the
	 * Graph Mouse.
	 */
	private void addMouseModeMenu() {
		if (frame == null) {
			// No frame -> No menu -> No fun!
			return;
		}
		JMenu modeMenu = graphMouse.getModeMenu();
		modeMenu.setText(i18n("menu.mousemode", "Mode"));
		modeMenu.setIcon(null);
		modeMenu.setPreferredSize(new Dimension(50, 20));
		JMenuBar menu = frame.getJMenuBar();
		menu.add(modeMenu, menu.getComponentCount() - 1);
		graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
	}

	/**
	 * Wrapper to ease access to the language ressources and to use a default
	 * string.
	 * 
	 * @param key
	 * @param defaultString
	 * @return
	 */
	private String i18n(String key, String defaultString) {
		if (i18n != null) {
			return i18n.getString(key);
		}
		return defaultString;
	}

}
