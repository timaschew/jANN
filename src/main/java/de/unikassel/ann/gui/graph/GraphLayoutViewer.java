package de.unikassel.ann.gui.graph;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import de.unikassel.ann.controller.EdgeController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.controller.VertexController;
import de.unikassel.ann.factory.EdgeFactory;
import de.unikassel.ann.factory.VertexFactory;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class GraphLayoutViewer {

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

	/**
	 * Constructor
	 * 
	 * @param Dimension
	 *          dim
	 * @param Container
	 *          parent
	 */
	public GraphLayoutViewer(final Dimension dim, final Container parent) {
		graph = new DirectedSparseGraph<Vertex, Edge>();
		layout = new StaticLayout<Vertex, Edge>(graph, dim);

		viewer = new VisualizationViewer<Vertex, Edge>(layout);
		viewer.setBackground(Color.white);
		viewer.addPreRenderPaintable(new VisualizationViewer.Paintable() {
			@Override
			public void paint(final Graphics g) {
				final int height = viewer.getHeight();
				final int width = viewer.getWidth();

				// Distance between two layers
				final int rowHeight = 80;

				// Position of the vertex depends on the number of vertices that
				// are already in the same layer.
				// Since new vertices are always added at the last position, get
				// the number of vertices to compute the position for the new
				// vertex.
				Collection<Vertex> vertices = graph.getVertices();

				// Map to store the number of vertices (value) in each layer
				// (key)
				Map<Number, Number> layerMap = new HashMap<Number, Number>();

				// List to sort the vertices by their indexes
				List<Vertex> verticesSorted = new ArrayList<Vertex>();

				int count = 0, layer = 0, index = 0, dist, num;
				Point2D location;

				// Count the number of vertices in each layer
				for (Vertex v : vertices) {
					if (v.getLayer() == layer) {
						count++;
					} else {
						layer = v.getLayer();
						count = 1;
					}
					layerMap.put(layer, count);
					verticesSorted.add(v);
				}

				// Sort the vertices accordingly to their index.
				Collections.sort(verticesSorted);

				// This is where the magic happens...
				for (Vertex v : verticesSorted) {
					// Get layer of the vertex and its index within this layer
					layer = v.getLayer();
					if (v.getLayer() == layer) {
						index++;
					} else {
						index = 0;
					}
					// Number of spaces between the vertices in one layer
					num = (Integer) layerMap.get(layer) + 1;

					// Distance between two vertices
					dist = width / num;

					// Set the location of the current vertex
					location = new Point2D.Double(index * dist, layer * rowHeight);
					layout.setLocation(v, location);
					layout.lock(v, true);
				}
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

	public void init() {
		//
		// Graph Mouse
		//
		initGraphMouse();
		addMouseModeMenu();
	}

	public void setFrame(final JFrame frame) {
		this.frame = frame;
	}

	public void refresh() {
		viewer.repaint();
	}

	public void renderNetwork(final Network network) {
		// TODO
		List<Layer> layers = network.getLayers();
		System.out.println(layers);
	}

	private void initGraphMouse() {
		// Create Graph Mouse (set in and out parameter to '1f' to disable zoom)
		graphMouse = new GraphMouse<Vertex, Edge>(viewer.getRenderContext(), vertexFactory, edgeFactory, 1f, 1f);

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
	 * Add MouseMode Menu to the MainMenu of the frame to set the mode of the Graph Mouse.
	 */
	private void addMouseModeMenu() {
		if (frame == null) {
			// No frame -> No menu -> No fun!
			return;
		}
		JMenu modeMenu = graphMouse.getModeMenu();
		modeMenu.setText(Settings.getI18n("menu.mousemode", "Mode"));
		modeMenu.setIcon(null);
		modeMenu.setPreferredSize(new Dimension(50, 20));
		JMenuBar menu = frame.getJMenuBar();
		menu.add(modeMenu, menu.getComponentCount() - 1);
		graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
	}

}
