package de.unikassel.ann.gui.mouse;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;

import EDU.oswego.cs.dl.util.concurrent.CopyOnWriteArrayList;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import de.unikassel.ann.gui.sidebar.TopologyPanel;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Network.NetworkLayer;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
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
	private PickedState<Vertex> pickedVertexState;
	private PickedState<Edge> pickedEdgeState;
	private Vertex vertex;
	private Edge edge;
	private Set<Vertex> pickedVertices;
	private Set<Edge> pickedEdges;

	public GraphMousePopupPlugin(final Factory<Vertex> vertexFactory, final Factory<Edge> edgeFactory) {
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
	}

	@SuppressWarnings("serial")
	private void createPopupByState(final int state) {
		final GraphController graphController = GraphController.getInstance();
		String actionText;

		switch (state) {
		//
		// (1) Nothing selected, clicked on empty space
		//
		case 1:
			// New neuron menu
			JMenu newNeuronMenu = new JMenu(Settings.i18n.getString("graph.mouse.createVertex"));

			// Input
			newNeuronMenu.add(new AbstractAction(Settings.i18n.getString("graph.mouse.InputLayer")) {
				@Override
				public void actionPerformed(final ActionEvent e) {
					graphController.createVertex(NetworkLayer.INPUT);
				}
			});

			// Output
			newNeuronMenu.add(new AbstractAction(Settings.i18n.getString("graph.mouse.OutputLayer")) {
				@Override
				public void actionPerformed(final ActionEvent e) {
					graphController.createVertex(NetworkLayer.OUTPUT);
				}
			});

			// Hidden (optional depends on whether there is at least one hidden layer available)
			TopologyPanel topoPanel = Main.instance.sidebar.topolgyPanel;
			final Integer hiddenLayers = (Integer) topoPanel.hiddenLayerCountSpinner.getValue();
			if (hiddenLayers > 0) {
				newNeuronMenu.addSeparator();

				// NOTE: Start at index 1 because hidden layers starts with this index. Index 0 = input layer!
				for (int i = 1; i <= hiddenLayers; i++) {
					final int layerIndex = i;
					actionText = String.format(Settings.i18n.getString("graph.mouse.HiddenLayer") + " %d", layerIndex);
					newNeuronMenu.add(new AbstractAction(actionText) {
						@Override
						public void actionPerformed(final ActionEvent e) {
							graphController.createVertex(NetworkLayer.HIDDEN, layerIndex);
						}
					});
				}
			}

			popup.add(newNeuronMenu);

			// New hidden layer
			popup.add(new AbstractAction(Settings.i18n.getString("graph.mouse.createHiddenLayer")) {
				@Override
				public void actionPerformed(final ActionEvent e) {
					Network.getNetwork().setSizeOfHiddenLayers(hiddenLayers + 1);
				}
			});

			// If there are at least two vertices in the graph
			final Collection<Vertex> vertices = graphController.getGraph().getVertices();
			if (vertices.size() > 1) {
				// add an entry with a submenu to ...
				JMenu allNeuronMenu = new JMenu(Settings.i18n.getString("graph.mouse.allVertices"));

				// select all vertices
				allNeuronMenu.add(new AbstractAction(Settings.i18n.getString("graph.mouse.select")) {
					@Override
					public void actionPerformed(final ActionEvent e) {
						for (Vertex vertex : vertices) {
							pickedVertexState.pick(vertex, true);
						}
					}
				});

				// connect all vertices
				JMenu connectMenu = new JMenu(Settings.i18n.getString("graph.mouse.connect"));
				connectMenu.add(new AbstractAction(Settings.i18n.getString("graph.mouse.intermeshed")) {
					@Override
					public void actionPerformed(final ActionEvent e) {
						graphController.createEdge(vertices);
						pickedVertexState.clear();
					}
				});
				connectMenu.add(new AbstractAction(Settings.i18n.getString("graph.mouse.feedforward")) {
					@Override
					public void actionPerformed(final ActionEvent e) {
						Network.getNetwork().connectFeedForward();
						pickedVertexState.clear();
					}
				});
				allNeuronMenu.add(connectMenu);

				// delete all vertices
				allNeuronMenu.add(new AbstractAction(Settings.i18n.getString("graph.mouse.delete")) {
					@Override
					public void actionPerformed(final ActionEvent e) {
						graphController.removeVertices(vertices);
					}
				});

				popup.addSeparator();
				popup.add(allNeuronMenu);
			}

			break;
		//
		// (2) Nothing selected, clicked on a vertex
		//
		case 2:
			pickedVertexState.pick(vertex, true);
			createPopupByState(12);
			popup.addSeparator();
			createPopupByState(1);
			break;
		//
		// (3) Nothing selected, clicked on an edge
		//
		case 3:
			createPopupByState(20);
			popup.addSeparator();
			createPopupByState(1);
			break;
		//
		// (4) One vertex selected, clicked on empty space
		//
		case 4:
			// get vertex from the set of picked vertices
			if (vertex == null) {
				vertex = pickedVertices.iterator().next();
			}
			createPopupByState(2);
			break;
		//
		// (5) One vertex selected, clicked on selected vertex
		//
		case 5:
			createPopupByState(2);
			break;
		//
		// (6) One vertex selected, clicked on an other vertex
		//
		case 6:
			// unpick vertex (from the set of picked vertices)
			if (vertex == null) {
				vertex = pickedVertices.iterator().next();
			}
			pickedVertexState.pick(vertex, false);
			createPopupByState(2);
			break;
		//
		// (7) More than one vertex selected, clicked on empty space
		//
		case 7:
			createPopupByState(1);

			// Selected vertices menu
			JMenu selectedNeuronMenu = new JMenu(Settings.i18n.getString("graph.mouse.selectedVertices"));

			// connect
			JMenu connectMenu = new JMenu(Settings.i18n.getString("graph.mouse.connect"));
			connectMenu.add(new AbstractAction(Settings.i18n.getString("graph.mouse.intermeshed")) {
				@Override
				public void actionPerformed(final ActionEvent e) {
					graphController.createEdge(pickedVertices);
					pickedVertexState.clear();
				}
			});
			connectMenu.add(new AbstractAction(Settings.i18n.getString("graph.mouse.feedforward")) {
				@Override
				public void actionPerformed(final ActionEvent e) {
					Network.getNetwork().connectFeedForward();
					pickedVertexState.clear();
				}
			});
			selectedNeuronMenu.add(connectMenu);

			// delete
			selectedNeuronMenu.add(new AbstractAction(Settings.i18n.getString("graph.mouse.delete")) {
				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(final ActionEvent e) {
					graphController.removeVertices(new CopyOnWriteArrayList(pickedVertices));
					pickedVertexState.clear();
				}
			});

			// popup.addSeparator();
			popup.add(selectedNeuronMenu);

			break;
		//
		// (8) More than one vertex selected, clicked on selected V
		//
		case 8:
			createPopupByState(12);
			popup.addSeparator();
			createPopupByState(7);
			break;
		//
		// (9) More than one vertex selected, clicked on an other V
		//
		case 9:
			// unpick vertex (from the set of picked vertices)
			if (vertex == null) {
				vertex = pickedVertices.iterator().next();
			}
			pickedVertexState.pick(vertex, false);
			createPopupByState(2);
			break;
		//
		// (10) One vertex selected, clicked on an edge
		//
		case 10:
			// unpick vertex
			pickedVertexState.pick(vertex, false);
			createPopupByState(3);
			break;
		//
		// (11) More than one vertex selected, clicked on an edge
		//
		case 11:
			createPopupByState(10);
			break;
		//
		// (12) One vertex is selected, clicked on the selected vertex OR on empty space
		//
		case 12:
			// Show details
			popup.add(new AbstractAction(Settings.i18n.getString("graph.mouse.showDetails")) {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// unpick vertex
					graphController.showVertex(vertex);
					pickedVertexState.pick(vertex, false);
				}
			});

			// Delete
			actionText = String.format(Settings.i18n.getString("graph.mouse.deleteVertex") + " %d", vertex.getIndex());
			popup.add(new AbstractAction(actionText) {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// unpick vertex
					graphController.removeVertex(vertex);
					pickedVertexState.pick(vertex, false);
				}
			});
			break;
		//
		// No edge picked
		//
		case 20:
			final Collection<Edge> edges = graphController.getGraph().getEdges();
			if (edges.size() > 0) {
				popup.addSeparator();
				popup.add(new AbstractAction(Settings.i18n.getString("graph.mouse.deleteAllEdges")) {
					@Override
					public void actionPerformed(final ActionEvent e) {
						graphController.removeAllEdges();
					}
				});
			}
			break;
		//
		// One edge picked
		//
		case 21:
			if (edge == null) {
				edge = pickedEdges.iterator().next();
			}
			pickedEdgeState.pick(edge, true);
			popup.add(new AbstractAction(Settings.i18n.getString("graph.mouse.deleteEdge")) {
				@Override
				public void actionPerformed(final ActionEvent e) {
					graphController.removeEdge(edge);
					pickedEdgeState.clear();
				}
			});
			break;
		//
		// More than one edge picked
		//
		case 22:
			popup.add(new AbstractAction(Settings.i18n.getString("graph.mouse.deleteSelectedEdges")) {
				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(final ActionEvent e) {
					graphController.removeEdges(new CopyOnWriteArrayList(pickedEdges));
					pickedEdgeState.clear();
				}
			});
			break;
		default:
			break;
		}
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	protected void handlePopup(final MouseEvent e) {
		final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>) e.getSource();
		final Layout<Vertex, Edge> layout = vv.getGraphLayout();
		// final Graph<Vertex, Edge> graph = layout.getGraph();
		final Point2D p = e.getPoint();
		final Point2D ivp = p;
		GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
		if (pickSupport != null) {
			vertex = pickSupport.getVertex(layout, ivp.getX(), ivp.getY());
			edge = pickSupport.getEdge(layout, ivp.getX(), ivp.getY());
			pickedVertexState = vv.getPickedVertexState();
			pickedEdgeState = vv.getPickedEdgeState();
			pickedVertices = pickedVertexState.getPicked();
			pickedEdges = pickedEdgeState.getPicked();

			// Clear current popup
			popup.removeAll();

			// Handle picked (selected) vertices
			switch (pickedVertices.size()) {
			case 0:
				//
				// No vertex picked
				//
				if (vertex != null) {
					// click on vertex
					createPopupByState(2);
				} else if (edge != null) {
					// click on edge
					createPopupByState(3);
				} else {
					createPopupByState(1);
				}
				break;
			case 1:
				//
				// One vertex picked
				//
				if (vertex != null && pickedVertices.contains(vertex)) {
					// click on selected vertex
					createPopupByState(5);
				} else if (vertex != null && pickedVertices.contains(vertex) == false) {
					// click on an other vertex
					createPopupByState(6);
				} else if (edge != null) {
					// click on edge
					createPopupByState(10);
				} else {
					// click on empty space
					createPopupByState(4);
				}
				break;
			default:
				//
				// More than one vertex picked
				//
				if (vertex != null && pickedVertices.contains(vertex)) {
					// click on selected vertex
					createPopupByState(8);
				} else if (vertex != null && pickedVertices.contains(vertex) == false) {
					// click on an other vertex
					createPopupByState(9);
				} else if (edge != null) {
					// click on edge
					createPopupByState(11);
				} else {
					// click on empty space
					createPopupByState(7);
				}
				break;
			}

			// Handle picked (selected) edges
			switch (pickedEdges.size()) {
			case 0:
				//
				// No edge picked
				//
				createPopupByState(20);
				break;
			case 1:
				//
				// One edge picked
				//
				createPopupByState(21);
				break;
			default:
				//
				// More than one edge picked
				//
				createPopupByState(22);
				break;
			}

			if (popup.getComponentCount() > 0) {
				popup.show(vv, e.getX(), e.getY());
			}
		}
	}
}
