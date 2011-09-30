package de.unikassel.ann.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import de.unikassel.ann.factory.VertexFactory;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.func.ActivationFunction;
import de.unikassel.ann.model.func.SigmoidFunction;
import de.unikassel.ann.model.func.TanHFunction;
import de.unikassel.ann.util.ColorHelper;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;
import edu.uci.ics.jung.visualization.picking.PickedInfo;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class VertexController<V> {

	private static VertexController<Vertex> instance;

	private VertexController() {
	};

	public static VertexController<Vertex> getInstance() {
		if (instance == null) {
			instance = new VertexController<Vertex>();
		}
		return instance;
	}

	//
	// Settings
	//
	private static boolean adjustSizeByValue = false;
	private static boolean highlightNeighbours = false;

	private VisualizationViewer<Vertex, Edge> viewer;
	private Renderer<Vertex, Edge> renderer;
	private RenderContext<Vertex, Edge> renderContext;
	private PickedState<Vertex> vertexPickedState;
	private VertexFactory vertexFactory;

	public void init() {
		this.viewer = GraphController.getInstance().getViewer();
		this.renderer = viewer.getRenderer();
		this.renderContext = viewer.getRenderContext();
		this.vertexPickedState = viewer.getPickedVertexState();
		this.vertexFactory = new VertexFactory();

		setVertexLabel();
		setVertexBorder();
		setVertexStrokeHighlight();
		setVertexShape();
		setVertexColor();
		// setVertexGradient();
		setVertexTooltip();
		setVertexPickListener();
	}

	/**
	 * Clear picked state and reset the factory.
	 */
	public void clear() {
		vertexPickedState.clear();
		vertexFactory.reset();
	}

	/**
	 * Reset controller to its initial state.
	 */
	public void reset() {
		clear();
	}

	public VertexFactory getVertexFactory() {
		return vertexFactory;
	}

	/**
	 * Get the vertex by its model (neuron)
	 * 
	 * @param neuron
	 * @return Vertex
	 */
	public Vertex getVertexByModel(final Neuron neuron) {
		Collection<Vertex> vertices = GraphController.getInstance().getGraph().getVertices();
		for (Vertex vertex : vertices) {
			if (vertex.getModel().equals(neuron)) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Check if the vertex is located in a hidden layer.
	 * 
	 * @param vertex
	 * @return boolean
	 */
	public boolean isVertexInHiddenLayer(final Vertex vertex) {
		int layerSize = Network.getNetwork().getLayers().size();
		if (vertex.getLayerIndex() > 0 && vertex.getLayerIndex() < layerSize - 1) {
			// Vertex is in a hidden layer
			return true;
		}
		return false;
	}

	/**
	 * Set Vertex Label and Position
	 */
	private void setVertexLabel() {
		renderContext.setVertexLabelTransformer(VertexInfo.getInstance());
		renderer.getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.S);
	}

	/**
	 * Set Vertex Paint (Border)
	 */
	private void setVertexBorder() {
		VertexDrawPaint<Vertex, Paint> vdp = new VertexDrawPaint<Vertex, Paint>(vertexPickedState);
		renderContext.setVertexDrawPaintTransformer(vdp);
	}

	/**
	 * Set Vertex Highlight (on Picked)
	 */
	private void setVertexStrokeHighlight() {
		VertexStrokeHighlight<Vertex, Number> vsh = new VertexStrokeHighlight<Vertex, Number>(vertexPickedState);
		renderContext.setVertexStrokeTransformer(vsh);
	}

	/**
	 * Set Vertex Shape (Scaling/size)
	 */
	private void setVertexShape() {
		Graph<Vertex, Edge> graph = GraphController.getInstance().getGraph();
		VertexTransformer<Vertex, Edge> vt = new VertexTransformer<Vertex, Edge>(graph);
		renderContext.setVertexShapeTransformer(vt);
	}

	/**
	 * Set Vertex Color
	 */
	private void setVertexColor() {
		renderContext.setVertexFillPaintTransformer(new Transformer<Vertex, Paint>() {
			@Override
			public Paint transform(final Vertex vertex) {
				ActivationFunction activationFunction = vertex.getModel().getActivationFunction();
				int factor = 0;
				if (activationFunction instanceof SigmoidFunction) {
					// Sigmoid [0,1] --> factor = value * 100
					factor = (int) (vertex.getValue() * 100);
				} else if (activationFunction instanceof TanHFunction) {
					// TanH [-1,1] --> factor = (value+1) * 50
					factor = (int) (vertex.getValue() + 1) * 50;
				}

				// Normalize factor
				factor = factor > 100 ? 100 : factor < 0 ? 0 : factor;

				// Get factor depending color
				Color color = ColorHelper.numberToColor(factor);
				color = color.brighter().brighter().brighter();

				int alpha = (int) (255 * 0.8);
				if (VertexController.getInstance().isVertexInHiddenLayer(vertex)) {
					for (int i = 0; i < 100; i++) {
						alpha = (int) (255 * 0.4);
					}
				}
				return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
			}
		});
	}

	/**
	 * Set Vertex Gradient
	 */
	private void setVertexGradient() {
		// viewer.getRenderer().setVertexRenderer(new GradientVertexRenderer<Vertex, Edge>(Color.white, Color.gray, false));
	}

	/**
	 * Set Vertex Tooltip
	 */
	private void setVertexTooltip() {
		viewer.setVertexToolTipTransformer(VertexTooltip.getInstance());
	}

	/**
	 * Set Vertex Pick Listener
	 */
	private void setVertexPickListener() {
		vertexPickedState.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				Set<Vertex> picked = vertexPickedState.getPicked();
				if (picked.isEmpty()) {
					// No vertex picked
					Main.instance.sidebar.selectedSymbolsPanel.reset();
					return;
				}
				// Show picked vertex in the Sidebar
				Main.instance.sidebar.selectedSymbolsPanel.updateVertex(picked);
			}
		});

	}

	/*
	 * Vertex Info (Label) class
	 */
	private final static class VertexInfo<V> implements Transformer<Vertex, String> {

		private static VertexInfo<Vertex> instance;

		public static VertexInfo<Vertex> getInstance() {
			if (instance == null) {
				instance = new VertexInfo<Vertex>();
			}
			return instance;
		}

		@Override
		public String transform(final Vertex v) {
			return v.toString();
		}
	}

	/*
	 * Vertex Size class
	 */
	@SuppressWarnings("hiding")
	private final class VertexTransformer<V, E> extends AbstractVertexShapeTransformer<Vertex> implements Transformer<Vertex, Shape> {
		@SuppressWarnings("unused")
		protected Graph<Vertex, Edge> graph;

		public VertexTransformer(final Graph<Vertex, Edge> graph) {
			this.graph = graph;

			// Size
			setSizeTransformer(new Transformer<Vertex, Integer>() {
				@Override
				public Integer transform(final Vertex v) {
					if (VertexController.adjustSizeByValue) {
						return (int) (v.getValue() * 30) + 20;
					}
					return 20;
				}
			});
		}

		@Override
		public Shape transform(final Vertex v) {
			return factory.getEllipse(v);
		}
	}

	/*
	 * Vertex Draw Paint class
	 */
	@SuppressWarnings("hiding")
	private final class VertexDrawPaint<V, P> implements Transformer<Vertex, Paint> {
		protected PickedInfo<Vertex> pi;

		public VertexDrawPaint(final PickedInfo<Vertex> pi) {
			this.pi = pi;
		}

		@Override
		public Paint transform(final Vertex vertex) {
			if (pi.isPicked(vertex)) {
				return Color.CYAN;
			}
			return Color.BLACK;
		}
	}

	/*
	 * Vertex Stroke Highlight class
	 */
	@SuppressWarnings("hiding")
	private final class VertexStrokeHighlight<V, E> implements Transformer<Vertex, Stroke> {
		protected boolean highlight = false;
		protected Stroke heavy = new BasicStroke(4);
		protected Stroke medium = new BasicStroke(2);
		protected Stroke light = new BasicStroke(1);
		// protected Stroke dotted = RenderContext.DASHED;

		protected PickedInfo<Vertex> pi;

		public VertexStrokeHighlight(final PickedInfo<Vertex> pi) {
			this.pi = pi;

			// Default enable highlighting
			setHighlight(true);
		}

		public void setHighlight(final boolean highlight) {
			this.highlight = highlight;
		}

		@Override
		public Stroke transform(final Vertex v) {
			if (highlight) {
				if (pi.isPicked(v)) {
					return heavy;
				}
				if (highlightNeighbours) {
					Graph<Vertex, Edge> graph = GraphController.getInstance().getGraph();
					for (Vertex w : graph.getNeighbors(v)) {
						if (pi.isPicked(w)) {
							return medium;
						}
					}
				}
				return light;
			}
			return light;
		}

	}

	/*
	 * Vertex Tooltip class
	 */
	private final static class VertexTooltip<V> implements Transformer<Vertex, String> {

		private static VertexTooltip<Vertex> instance;

		public static VertexTooltip<Vertex> getInstance() {
			if (instance == null) {
				instance = new VertexTooltip<Vertex>();
			}
			return instance;
		}

		@Override
		public String transform(final Vertex v) {
			return v.toString();
		}
	}

}
