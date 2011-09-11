package de.unikassel.ann.controller;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

import de.unikassel.ann.gui.graph.Edge;
import de.unikassel.ann.gui.graph.Vertex;
import de.unikassel.ann.model.Synapse;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class EdgeController<E> {
	private static EdgeController<Edge> instance;

	public static EdgeController<Edge> getInstance() {
		if (instance == null) {
			instance = new EdgeController<Edge>();
		}
		return instance;
	}

	private Graph<Vertex, Edge> graph;
	private VisualizationViewer<Vertex, Edge> viewer;
	private Renderer<Vertex, Edge> renderer;
	private RenderContext<Vertex, Edge> renderContext;

	public void init(Graph<Vertex, Edge> graph,
			VisualizationViewer<Vertex, Edge> viewer) {
		this.graph = graph;
		this.viewer = viewer;
		this.renderer = viewer.getRenderer();
		this.renderContext = viewer.getRenderContext();

		setEdgeLabel();
		setEdgeShape();
		setEdgeStroke();
		setEdgeTooltip();
	}

	/**
	 * Set Edge Label
	 */
	private void setEdgeLabel() {
		renderContext.setEdgeLabelTransformer(EdgeInfo.getInstance());
	}

	/**
	 * Set Edge Shape (Scaling/size)
	 */
	private void setEdgeShape() {
		renderContext
				.setEdgeShapeTransformer(new EdgeShape.Line<Vertex, Edge>());
	}

	/**
	 * Set Edge Stroke
	 */
	private void setEdgeStroke() {
		EdgeWeightStroke<Edge> ews = new EdgeWeightStroke<Edge>();
		renderContext.setEdgeStrokeTransformer(ews);
	}

	/**
	 * Set Edge Tooltip
	 */
	private void setEdgeTooltip() {
		viewer.setEdgeToolTipTransformer(EdgeTooltip.getInstance());
	}

	/*
	 * Edge Info class
	 */
	private static final class EdgeInfo<E> implements Transformer<Edge, String> {

		private static EdgeInfo<Edge> instance;

		public static EdgeInfo<Edge> getInstance() {
			if (instance == null) {
				instance = new EdgeInfo<Edge>();
			}
			return instance;
		}

		@Override
		public String transform(Edge e) {
			return e.toString();
		}
	}

	/*
	 * Edge Tooltip class
	 */
	private static final class EdgeTooltip<E> implements
			Transformer<Edge, String> {

		private static EdgeTooltip<Edge> instance;

		public static EdgeTooltip<Edge> getInstance() {
			if (instance == null) {
				instance = new EdgeTooltip<Edge>();
			}
			return instance;
		}

		@Override
		public String transform(Edge e) {
			// TODO get value of the Edge by its model
			// e.getModel().getWeight();
			return "The amazing Tooltip of the Edge #" + e.getIndex();
		}
	}

	/*
	 * Edge Stroke class
	 */
	private static final class EdgeWeightStroke<E> implements
			Transformer<Edge, Stroke> {
		protected static final Stroke basic = new BasicStroke(1);
		protected static final Stroke heavy = new BasicStroke(2);
		protected static final Stroke dotted = RenderContext.DOTTED;

		protected boolean weighted = false;

		public EdgeWeightStroke() {
			this.weighted = true;
		}

		public void setWeighted(boolean weighted) {
			this.weighted = weighted;
		}

		public Stroke transform(Edge e) {
			if (weighted) {
				if (drawHeavy(e)) {
					return heavy;
				} else {
					return dotted;
				}
			} else {
				return basic;
			}
		}

		protected boolean drawHeavy(Edge e) {
			Synapse model = e.getModel();
			if (model == null) {
				return false;
			}
			double value = model.getWeight();
			return (value > 0.7);
		}

	}
}
