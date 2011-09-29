package de.unikassel.ann.controller;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import de.unikassel.ann.factory.EdgeFactory;
import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import de.unikassel.ann.model.EdgeMap;
import de.unikassel.ann.model.Synapse;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class EdgeController<E> {

	private static EdgeController<Edge> instance;

	private EdgeController() {
	}

	public static EdgeController<Edge> getInstance() {
		if (instance == null) {
			instance = new EdgeController<Edge>();
		}
		return instance;
	}

	private VisualizationViewer<Vertex, Edge> viewer;
	private Renderer<Vertex, Edge> renderer;
	private RenderContext<Vertex, Edge> renderContext;
	private PickedState<Edge> edgePickedState;
	private EdgeFactory edgeFactory;
	private EdgeMap<Double> edgeMap;

	public void init() {
		this.viewer = GraphController.getInstance().getViewer();
		this.renderer = viewer.getRenderer();
		this.renderContext = viewer.getRenderContext();
		this.edgePickedState = viewer.getPickedEdgeState();
		this.edgeFactory = new EdgeFactory();
		this.edgeMap = new EdgeMap<Double>();

		setEdgeLabel();
		setEdgeShape();
		setEdgeStroke();
		setEdgeTooltip();
		setEdgePickedListener();
	}

	/**
	 * Clear picked state and reset the factory.
	 */
	public void clear() {
		edgePickedState.clear();
		edgeFactory.reset();
	}

	/**
	 * Reset controller to its initial state.
	 */
	public void reset() {
		clear();
		edgeMap.getMap().clear();
	}

	public EdgeFactory getEdgeFactory() {
		return edgeFactory;
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
		renderContext.setEdgeShapeTransformer(new EdgeShape.Line<Vertex, Edge>());
	}

	/**
	 * Set Edge Stroke
	 */
	private void setEdgeStroke() {
		EdgeWeightStroke<Edge> ews = new EdgeWeightStroke<Edge>();
		// ews.setWeighted(true);
		renderContext.setEdgeStrokeTransformer(ews);
	}

	/**
	 * Set Edge Tooltip
	 */
	private void setEdgeTooltip() {
		viewer.setEdgeToolTipTransformer(EdgeTooltip.getInstance());
	}

	/**
	 * Set Edge Pick Listener
	 */
	private void setEdgePickedListener() {
		edgePickedState.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				Set<Edge> picked = edgePickedState.getPicked();
				if (picked.isEmpty()) {
					// No edge picked
					return;
				}
				// TODO Show value of the picked edge in the Sidebar
				// System.out.println(picked);
			}
		});

	}

	public EdgeMap<Double> getEdgeMap() {
		return edgeMap;
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
		public String transform(final Edge e) {
			return e.toString();
		}
	}

	/*
	 * Edge Tooltip class
	 */
	private static final class EdgeTooltip<E> implements Transformer<Edge, String> {

		private static EdgeTooltip<Edge> instance;

		public static EdgeTooltip<Edge> getInstance() {
			if (instance == null) {
				instance = new EdgeTooltip<Edge>();
			}
			return instance;
		}

		@Override
		public String transform(final Edge e) {
			// TODO get value of the Edge by its model
			// e.getModel().getWeight();
			return "The amazing Tooltip of the Edge #" + e.getIndex();
		}
	}

	/*
	 * Edge Stroke class
	 */
	private static final class EdgeWeightStroke<E> implements Transformer<Edge, Stroke> {
		protected static final Stroke basic = new BasicStroke(1);
		protected static final Stroke heavy = new BasicStroke(2);
		protected static final Stroke dotted = RenderContext.DOTTED;

		protected boolean weighted = false;

		public EdgeWeightStroke() {
		}

		public void setWeighted(final boolean weighted) {
			this.weighted = weighted;
		}

		@Override
		public Stroke transform(final Edge e) {
			if (weighted) {
				return drawWeighted(e);
			}
			return basic;
		}

		protected Stroke drawWeighted(final Edge e) {
			Synapse model = e.getModel();
			if (model == null) {
				return basic;
			}
			// stroke width depends on the edges weight
			double weight = model.getWeight();
			return new BasicStroke((float) weight);
		}

	}

}
