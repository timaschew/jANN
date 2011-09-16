package de.unikassel.ann.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import de.unikassel.ann.gui.graph.Edge;
import de.unikassel.ann.gui.graph.GraphLayoutViewer;
import de.unikassel.ann.gui.graph.Vertex;

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

	private VisualizationViewer<Vertex, Edge> viewer;
	private Renderer<Vertex, Edge> renderer;
	private RenderContext<Vertex, Edge> renderContext;
	private PickedState<Vertex> vertexPickedState;

	public void init() {
		this.viewer = GraphLayoutViewer.getInstance().getViewer();
		this.renderer = viewer.getRenderer();
		this.renderContext = viewer.getRenderContext();
		this.vertexPickedState = viewer.getPickedVertexState();

		setVertexLabel();
		setVertexStrokeHighlight();
		setVertexShape();
		setVertexColor();
		setVertexTooltip();
		setVertexPickListener();
	}

	/**
	 * Set Vertex Label and Position
	 */
	private void setVertexLabel() {
		renderContext.setVertexLabelTransformer(VertexInfo.getInstance());
		renderer.getVertexLabelRenderer().setPosition(
				Renderer.VertexLabel.Position.S);
	}

	/**
	 * Set Vertex Highlight (on Picked)
	 */
	private void setVertexStrokeHighlight() {
		VertexStrokeHighlight<Vertex, Number> vsh = new VertexStrokeHighlight<Vertex, Number>(
				vertexPickedState);
		renderContext.setVertexStrokeTransformer(vsh);
	}

	/**
	 * Set Vertex Shape (Scaling/size)
	 */
	private void setVertexShape() {
		Graph<Vertex, Edge> graph = GraphLayoutViewer.getInstance().getGraph();
		VertexTransformer<Vertex, Edge> vt = new VertexTransformer<Vertex, Edge>(
				graph);
		renderContext.setVertexShapeTransformer(vt);
	}

	/**
	 * Set Vertex Color
	 */
	private void setVertexColor() {
		renderContext
				.setVertexFillPaintTransformer(new Transformer<Vertex, Paint>() {
					// TODO Define colors and their percent range
					private final Color[] palette = { Color.GREEN, Color.BLUE,
							Color.RED };

					// Define the delimiter of the color percent ranges
					private final int delimiter = (int) (100 / palette.length) + 1;

					public Paint transform(Vertex v) {
						// TODO
						// Je nachdem welchen Wert das Neuron hat, soll es mit
						// einer Farbe zu dem entsprechendem Prozentwert
						// befuellt werden.
						// Sigmoid [0,1] --> faktor = value * 100
						// TanH [-1,1] --> faktor = (value+1) * 50
						int factor = (int) (v.getValue() * 100);
						int range = (int) (factor / delimiter);
						return palette[range];
					}
				});
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
			public void itemStateChanged(ItemEvent e) {
				Set<Vertex> picked = vertexPickedState.getPicked();
				if (picked.isEmpty()) {
					// No vertex picked
					return;
				}
				// TODO Show value of the picked vertex in the Sidebar
				System.out.println(picked);
			}
		});

	}

	/*
	 * Vertex Info (Label) class
	 */
	private final static class VertexInfo<V> implements
			Transformer<Vertex, String> {

		private static VertexInfo<Vertex> instance;

		public static VertexInfo<Vertex> getInstance() {
			if (instance == null) {
				instance = new VertexInfo<Vertex>();
			}
			return instance;
		}

		@Override
		public String transform(Vertex v) {
			return v.toString();
		}
	}

	/*
	 * Vertex Size class
	 */
	private final class VertexTransformer<V, E> extends
			AbstractVertexShapeTransformer<Vertex> implements
			Transformer<Vertex, Shape> {

		protected Graph<Vertex, Edge> graph;

		public VertexTransformer(Graph<Vertex, Edge> graphIn) {
			this.graph = graphIn;

			// Size
			setSizeTransformer(new Transformer<Vertex, Integer>() {

				public Integer transform(Vertex v) {
					return (int) (v.getValue() * 30) + 20;
				}
			});

		}

		public Shape transform(Vertex v) {
			return factory.getEllipse(v);
		}
	}

	/*
	 * Vertex Stroke Highlight class
	 */
	private final class VertexStrokeHighlight<V, E> implements
			Transformer<Vertex, Stroke> {
		protected boolean highlight = false;
		protected Stroke heavy = new BasicStroke(5);
		protected Stroke medium = new BasicStroke(3);
		protected Stroke light = new BasicStroke(1);
		protected PickedInfo<Vertex> pi;

		public VertexStrokeHighlight(PickedInfo<Vertex> pi) {
			this.pi = pi;

			// Default enable highlighting
			setHighlight(true);
		}

		public void setHighlight(boolean highlight) {
			this.highlight = highlight;
		}

		public Stroke transform(Vertex v) {
			Graph<Vertex, Edge> graph = GraphLayoutViewer.getInstance()
					.getGraph();
			if (highlight) {
				if (pi.isPicked(v)) {
					return heavy;
				} else {
					for (Vertex w : graph.getNeighbors(v)) {
						if (pi.isPicked(w)) {
							return medium;
						}
					}
					return light;
				}
			}
			return light;
		}

	}

	/*
	 * Vertex Tooltip class
	 */
	private final static class VertexTooltip<V> implements
			Transformer<Vertex, String> {

		private static VertexTooltip<Vertex> instance;

		public static VertexTooltip<Vertex> getInstance() {
			if (instance == null) {
				instance = new VertexTooltip<Vertex>();
			}
			return instance;
		}

		@Override
		public String transform(Vertex v) {
			return v.toString();
		}
	}
}
