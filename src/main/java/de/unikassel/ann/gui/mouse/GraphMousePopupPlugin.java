package de.unikassel.ann.gui.mouse;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;

import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

/**
 * a plugin that uses popup menus to create vertices, undirected edges, and directed edges.
 */
public class GraphMousePopupPlugin<V, E> extends AbstractPopupGraphMousePlugin {

	protected Factory<Vertex> vertexFactory;
	protected Factory<Edge> edgeFactory;
	protected JPopupMenu popup = new JPopupMenu();

	public GraphMousePopupPlugin(final Factory<Vertex> vertexFactory, final Factory<Edge> edgeFactory) {
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
	}

	@Override
	@SuppressWarnings({ "unchecked", "serial" })
	protected void handlePopup(final MouseEvent e) {
		final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>) e.getSource();
		final Layout<Vertex, Edge> layout = vv.getGraphLayout();
		final Graph<Vertex, Edge> graph = layout.getGraph();
		final Point2D p = e.getPoint();
		final Point2D ivp = p;
		GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
		if (pickSupport != null) {

			popup.removeAll();

			final Vertex vertex = pickSupport.getVertex(layout, ivp.getX(), ivp.getY());
			final Edge edge = pickSupport.getEdge(layout, ivp.getX(), ivp.getY());
			final PickedState<Vertex> pickedVertexState = vv.getPickedVertexState();
			final PickedState<Edge> pickedEdgeState = vv.getPickedEdgeState();

			if (vertex != null) {
				Set<Vertex> picked = pickedVertexState.getPicked();
				if (picked.size() > 0) {
					if (graph instanceof UndirectedGraph == false) {
						JMenu directedMenu = new JMenu("Create Directed Edge");
						popup.add(directedMenu);
						for (final Vertex other : picked) {
							directedMenu.add(new AbstractAction("[" + other + "," + vertex + "]") {
								@Override
								public void actionPerformed(final ActionEvent e) {
									GraphController.getInstance().createEdge(edgeFactory, other, vertex);
								}
							});
						}
					}
					if (graph instanceof DirectedGraph == false) {
						JMenu undirectedMenu = new JMenu("Create Undirected Edge");
						popup.add(undirectedMenu);
						for (final Vertex other : picked) {
							undirectedMenu.add(new AbstractAction("[" + other + "," + vertex + "]") {
								@Override
								public void actionPerformed(final ActionEvent e) {
									GraphController.getInstance().createEdge(edgeFactory, other, vertex);
								}
							});
						}
					}
				}
				popup.add(new AbstractAction("Delete Vertex") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						pickedVertexState.pick(vertex, false);
						GraphController.getInstance().removeVertex(vertex);
					}
				});
			} else if (edge != null) {
				popup.add(new AbstractAction("Delete Edge") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						pickedEdgeState.pick(edge, false);
						GraphController.getInstance().removeEdge(edge);
					}
				});
			} else {
				popup.add(new AbstractAction("Create Vertex") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						GraphController.getInstance().createVertex(vertexFactory);
					}
				});
			}
			if (popup.getComponentCount() > 0) {
				popup.show(vv, e.getX(), e.getY());
			}
		}
	}

}
