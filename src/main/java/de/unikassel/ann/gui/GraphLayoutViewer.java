package de.unikassel.ann.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;

import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

import de.unikassel.ann.factory.EdgeFactory;
import de.unikassel.ann.factory.VertexFactory;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class GraphLayoutViewer {

	/*
	 * private fields
	 */
	private DirectedGraph<Number, Number> graph;
	private AbstractLayout<Number, Number> layout;
	private VisualizationViewer<Number, Number> viewer;
	private VertexFactory vertexFactory;
	private EdgeFactory edgeFactory;
	private EditingModalGraphMouse<Number, Number> graphMouse;

	/**
	 * Constructor
	 * 
	 * @param parent
	 */
	public GraphLayoutViewer(Dimension dim, Container parent) {
		graph = new DirectedSparseGraph<Number, Number>();
		layout = new StaticLayout<Number, Number>(graph, dim);
		// The Dimension is given by the DividerLocation of the mainSplitPane
		// and the jungConsoleSplitPane minus the scrollbar size

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
				MapTransformer.<Number, String> getInstance(LazyMap
						.<Number, String> decorate(
								new HashMap<Number, String>(),
								new ToStringLabeller<Number>())));

		viewer.getRenderContext().setEdgeLabelTransformer(
				MapTransformer.<Number, String> getInstance(LazyMap
						.<Number, String> decorate(
								new HashMap<Number, String>(),
								new ToStringLabeller<Number>())));

		viewer.setVertexToolTipTransformer(viewer.getRenderContext()
				.getVertexLabelTransformer());

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

		//
		// Graph Mouse
		//
		initGraphMouse();
	}

	private void initGraphMouse() {
		graphMouse = new EditingModalGraphMouse<Number, Number>(
				viewer.getRenderContext(), vertexFactory, edgeFactory);

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

}
