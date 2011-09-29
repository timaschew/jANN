package de.unikassel.ann.gui.mouse;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.ItemSelectable;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import org.apache.commons.collections15.Factory;

import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import edu.uci.ics.jung.visualization.MultiLayerTransformer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.annotations.AnnotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.LabelEditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;

public class GraphMouse<V, E> extends AbstractModalGraphMouse implements ModalGraphMouse, ItemSelectable {

	protected Factory<Vertex> vertexFactory;
	protected Factory<Edge> edgeFactory;

	protected GraphMousePlugin<Vertex, Edge> editingPlugin;
	protected LabelEditingGraphMousePlugin<Vertex, Edge> labelEditingPlugin;
	protected GraphMousePopupPlugin<Vertex, Edge> popupEditingPlugin;
	protected AnnotatingGraphMousePlugin<Vertex, Edge> annotatingPlugin;
	protected GraphMouseConnectPlugin<Vertex, Edge> connectingPlugin;

	protected MultiLayerTransformer basicTransformer;
	protected RenderContext<Vertex, Edge> rc;

	/**
	 * create an instance with default values
	 * 
	 */
	public GraphMouse(final RenderContext<Vertex, Edge> rc, final Factory<Vertex> vertexFactory, final Factory<Edge> edgeFactory) {
		this(rc, vertexFactory, edgeFactory, 1.1f, 1 / 1.1f);
	}

	/**
	 * create an instance with passed values
	 * 
	 * @param in
	 *            override value for scale in
	 * @param out
	 *            override value for scale out
	 */
	public GraphMouse(final RenderContext<Vertex, Edge> rc, final Factory<Vertex> vertexFactory, final Factory<Edge> edgeFactory,
			final float in, final float out) {
		super(in, out);
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
		this.rc = rc;
		this.basicTransformer = rc.getMultiLayerTransformer();
		loadPlugins();
		setModeKeyListener(new ModeKeyAdapter(this));
	}

	/**
	 * create the plugins, and load the plugins for TRANSFORMING mode
	 * 
	 */
	@Override
	protected void loadPlugins() {
		pickingPlugin = new PickingGraphMousePlugin<Vertex, Edge>();
		// animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<Vertex, Edge>();
		translatingPlugin = new TranslatingGraphMousePlugin(InputEvent.BUTTON1_MASK);
		scalingPlugin = new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, in, out);
		// rotatingPlugin = new RotatingGraphMousePlugin();
		// shearingPlugin = new ShearingGraphMousePlugin();
		labelEditingPlugin = new LabelEditingGraphMousePlugin<Vertex, Edge>();
		// annotatingPlugin = new AnnotatingGraphMousePlugin<Vertex, Edge>(rc);
		editingPlugin = new GraphMousePlugin<Vertex, Edge>(vertexFactory, edgeFactory);
		popupEditingPlugin = new GraphMousePopupPlugin<Vertex, Edge>(vertexFactory, edgeFactory);
		connectingPlugin = new GraphMouseConnectPlugin<Vertex, Edge>();

		add(scalingPlugin);
		setMode(Mode.EDITING);
	}

	/**
	 * setter for the Mode.
	 */
	@Override
	public void setMode(final Mode mode) {
		if (this.mode != mode) {
			fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, this.mode, ItemEvent.DESELECTED));
			this.mode = mode;
			if (mode == Mode.TRANSFORMING) {
				setTransformingMode();
			} else if (mode == Mode.PICKING) {
				setPickingMode();
			} else if (mode == Mode.EDITING) {
				setEditingMode();
			} else if (mode == Mode.ANNOTATING) {
				setAnnotatingMode();
			}
			if (modeBox != null) {
				modeBox.setSelectedItem(mode);
			}
			fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, mode, ItemEvent.SELECTED));
		}
	}

	@Override
	protected void setPickingMode() {
		remove(translatingPlugin);
		// remove(rotatingPlugin);
		// remove(shearingPlugin);
		remove(editingPlugin);
		// remove(annotatingPlugin);
		add(pickingPlugin);
		// add(animatedPickingPlugin);
		add(labelEditingPlugin);
		add(popupEditingPlugin);
		add(connectingPlugin);
	}

	@Override
	protected void setTransformingMode() {
		remove(pickingPlugin);
		// remove(animatedPickingPlugin);
		remove(editingPlugin);
		// remove(annotatingPlugin);
		remove(connectingPlugin);
		add(translatingPlugin);
		// add(rotatingPlugin);
		// add(shearingPlugin);
		add(labelEditingPlugin);
		add(popupEditingPlugin);
	}

	protected void setEditingMode() {
		remove(pickingPlugin);
		// remove(animatedPickingPlugin);
		remove(translatingPlugin);
		// remove(rotatingPlugin);
		// remove(shearingPlugin);
		remove(labelEditingPlugin);
		// remove(annotatingPlugin);
		remove(connectingPlugin);
		add(editingPlugin);
		add(popupEditingPlugin);
	}

	protected void setAnnotatingMode() {
		remove(pickingPlugin);
		// remove(animatedPickingPlugin);
		remove(translatingPlugin);
		// remove(rotatingPlugin);
		// remove(shearingPlugin);
		remove(labelEditingPlugin);
		remove(editingPlugin);
		remove(popupEditingPlugin);
		// add(annotatingPlugin);
	}

	/**
	 * @return the modeBox.
	 */
	@Override
	public JComboBox getModeComboBox() {
		if (modeBox == null) {
			modeBox = new JComboBox(new Mode[] { Mode.TRANSFORMING, Mode.PICKING, Mode.EDITING, Mode.ANNOTATING });
			modeBox.addItemListener(getModeListener());
		}
		modeBox.setSelectedItem(mode);
		return modeBox;
	}

	/**
	 * create (if necessary) and return a menu that will change the mode
	 * 
	 * @return the menu
	 */
	@Override
	public JMenu getModeMenu() {
		if (modeMenu == null) {
			modeMenu = new JMenu();// {
			// Icon icon = BasicIconFactory.getMenuArrowIcon();
			// modeMenu.setIcon(BasicIconFactory.getMenuArrowIcon());
			// modeMenu.setPreferredSize(new Dimension(icon.getIconWidth() + 10,
			// icon.getIconHeight() + 10));

			final JRadioButtonMenuItem transformingButton = new JRadioButtonMenuItem(Mode.TRANSFORMING.toString());
			transformingButton.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(final ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						setMode(Mode.TRANSFORMING);
					}
				}
			});

			final JRadioButtonMenuItem pickingButton = new JRadioButtonMenuItem(Mode.PICKING.toString());
			pickingButton.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(final ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						setMode(Mode.PICKING);
					}
				}
			});

			final JRadioButtonMenuItem editingButton = new JRadioButtonMenuItem(Mode.EDITING.toString());
			editingButton.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(final ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						setMode(Mode.EDITING);
					}
				}
			});

			ButtonGroup radio = new ButtonGroup();
			radio.add(transformingButton);
			radio.add(pickingButton);
			radio.add(editingButton);
			transformingButton.setSelected(true);
			modeMenu.add(transformingButton);
			modeMenu.add(pickingButton);
			modeMenu.add(editingButton);
			modeMenu.setToolTipText("Menu for setting Mouse Mode");
			addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(final ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						if (e.getItem() == Mode.TRANSFORMING) {
							transformingButton.setSelected(true);
						} else if (e.getItem() == Mode.PICKING) {
							pickingButton.setSelected(true);
						} else if (e.getItem() == Mode.EDITING) {
							editingButton.setSelected(true);
						}
					}
				}
			});
		}
		return modeMenu;
	}

	public static class ModeKeyAdapter extends KeyAdapter {
		private char t = 't';
		private char p = 'p';
		private char e = 'e';
		private char a = 'a';
		protected ModalGraphMouse graphMouse;

		public ModeKeyAdapter(final ModalGraphMouse graphMouse) {
			this.graphMouse = graphMouse;
		}

		public ModeKeyAdapter(final char t, final char p, final char e, final char a, final ModalGraphMouse graphMouse) {
			this.t = t;
			this.p = p;
			this.e = e;
			this.a = a;
			this.graphMouse = graphMouse;
		}

		@Override
		public void keyTyped(final KeyEvent event) {
			char keyChar = event.getKeyChar();
			if (keyChar == t) {
				((Component) event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				graphMouse.setMode(Mode.TRANSFORMING);
			} else if (keyChar == p) {
				((Component) event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				graphMouse.setMode(Mode.PICKING);
			} else if (keyChar == e) {
				((Component) event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				graphMouse.setMode(Mode.EDITING);
			} else if (keyChar == a) {
				((Component) event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				graphMouse.setMode(Mode.ANNOTATING);
			}
		}
	}

	/**
	 * @return the annotatingPlugin
	 */
	public AnnotatingGraphMousePlugin<Vertex, Edge> getAnnotatingPlugin() {
		return annotatingPlugin;
	}

	/**
	 * @return the editingPlugin
	 */
	public GraphMousePlugin<Vertex, Edge> getEditingPlugin() {
		return editingPlugin;
	}

	/**
	 * @return the labelEditingPlugin
	 */
	public LabelEditingGraphMousePlugin<Vertex, Edge> getLabelEditingPlugin() {
		return labelEditingPlugin;
	}

	/**
	 * @return the popupEditingPlugin
	 */
	public GraphMousePopupPlugin<Vertex, Edge> getPopupEditingPlugin() {
		return popupEditingPlugin;
	}
}
