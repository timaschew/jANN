/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Way
 */
package de.unikassel.ann.gui.mouse;

import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.JComponent;

import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

/**
 * @author Way
 * 
 */
public class GraphMouseConnectPlugin<V, E> extends AbstractGraphMousePlugin implements MouseListener, MouseMotionListener {

	/**
	 * the picked Vertex
	 */
	protected Vertex vertex;

	/**
	 * create an instance with default modifiers
	 * 
	 */
	public GraphMouseConnectPlugin() {
		this(InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK);
	}

	/**
	 * create an instance, overriding the default modifiers
	 * 
	 * @param selectionModifiers
	 */
	public GraphMouseConnectPlugin(final int selectionModifiers) {
		super(selectionModifiers);
		cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	}

	/**
	 * If the event occurs on a Vertex, pick that single Vertex
	 * 
	 * @param e
	 *            the event
	 */
	@Override
	public void mousePressed(final MouseEvent e) {
		if (e.getModifiers() == modifiers) {
			e.consume();
		}
	}

	/**
	 * If a Vertex was picked in the mousePressed event, start a Thread to animate the translation of the graph so that the picked Vertex
	 * moves to the center of the view
	 * 
	 * @param e
	 *            the event
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void mouseReleased(final MouseEvent e) {
		if (e.getModifiers() == modifiers) {
			final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>) e.getSource();
			GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
			PickedState<Vertex> pickedVertexState = vv.getPickedVertexState();
			Layout<Vertex, Edge> layout = vv.getGraphLayout();
			if (pickSupport != null && pickedVertexState != null) {
				Set<Vertex> picked = pickedVertexState.getPicked();
				if (picked.size() > 0) {
					Point2D p = e.getPoint();
					vertex = pickSupport.getVertex(layout, p.getX(), p.getY());
					if (vertex != null) {
						// Connect picked vertices with the just picked vertex
						for (Vertex v : picked) {
							GraphController.getInstance().createEdge(v, vertex);
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	/**
	 * show a special cursor while the mouse is inside the window
	 */
	@Override
	public void mouseEntered(final MouseEvent e) {
		JComponent c = (JComponent) e.getSource();
		c.setCursor(cursor);
	}

	/**
	 * revert to the default cursor when the mouse leaves this window
	 */
	@Override
	public void mouseExited(final MouseEvent e) {
		JComponent c = (JComponent) e.getSource();
		c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
	}

	@Override
	public void mouseDragged(final MouseEvent arg0) {
	}
}
