package de.unikassel.ann.gui.sidebar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.func.ActivationFunction;
import de.unikassel.ann.model.func.SigmoidFunction;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

public class TopologyPanel extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private static final int MAX_NEURONS = 99;

	private static final int MAX_HIDDEN_LAYER = 10;

	public JSpinner inputNeuroSpinner;
	public SpinnerNumberModel inputSpinnerModel = new SpinnerNumberModel(0, 0, MAX_NEURONS, 1);
	public JSpinner outputNeuroSpinner;
	public JSpinner hiddenLayerCountSpinner;
	public JSpinner hiddenNeuronSpinner;
	public JComboBox hiddenLayerDropDown;
	public JComboBox comboBoxHiddenMausModus;
	public JCheckBox inputBiasCB;
	public JCheckBox hiddenBiasCB;
	public JComboBox comboBoxMouseModes;
	/**
	 * Model for hiddenLayerDropDown and comboBoxHiddenMausModus
	 */
	public DefaultComboBoxModel hiddenLayerComboModel = new DefaultComboBoxModel();

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private NetConfig netConfig = Settings.getInstance().getCurrentSession().getNetworkConfig();

	public JRadioButton mouseInputRB;
	public JRadioButton mouseOutputRB;
	public JRadioButton mouseHiddenRB;

	public JCheckBox chckbxAllNeuronsBind;

	public JButton btnCreateNetwork;
	private JLabel lblJungModis;

	private boolean ignoreHiddenLayerCombo;

	private Sidebar sidebar;

	/**
	 * Create the frame.
	 */
	public TopologyPanel(final JPanel parentSidebar) {
		if (parentSidebar instanceof Sidebar == false) {
			throw new IllegalArgumentException("parent should be Sidebar but is " + parentSidebar.getClass().getName());
		}
		sidebar = (Sidebar) parentSidebar;
		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.topology"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// setSize(400, 240);
		setPreferredSize(new Dimension(400, 342));

		JLabel lblInputNeuronen = new JLabel(Settings.i18n.getString("sidebar.topology.inputNeurons"));
		inputNeuroSpinner = new JSpinner(inputSpinnerModel);

		JLabel lblOutputNeuronen = new JLabel(Settings.i18n.getString("sidebar.topology.outputNeurons"));
		outputNeuroSpinner = new JSpinner(new SpinnerNumberModel(0, 0, MAX_NEURONS, 1));

		JLabel lblHiddenLayer = new JLabel(Settings.i18n.getString("sidebar.topology.hiddenlayers"));
		hiddenLayerCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, MAX_HIDDEN_LAYER, 1));

		JLabel lblHiddenNeuronen = new JLabel(Settings.i18n.getString("sidebar.topology.hiddenNeurons"));
		hiddenNeuronSpinner = new JSpinner(new SpinnerNumberModel(1, 1, MAX_NEURONS, 1));

		// is with the hiddenLayerCountSpinner associated

		hiddenLayerDropDown = new JComboBox(hiddenLayerComboModel);
		hiddenLayerDropDown.setEnabled(false);
		hiddenNeuronSpinner.setEnabled(false);

		JPanel mouseModusPanel = new JPanel();
		mouseModusPanel.setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.topology.mouse"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		inputBiasCB = new JCheckBox(Settings.i18n.getString("sidebar.topology.biasInputCB"));
		hiddenBiasCB = new JCheckBox(Settings.i18n.getString("sidebar.topology.biasHiddenCB"));
		hiddenBiasCB.setEnabled(false);

		chckbxAllNeuronsBind = new JCheckBox(Settings.i18n.getString("sidebar.topology.chckbxAllNeuronsBind"));

		btnCreateNetwork = new JButton(Settings.i18n.getString("sidebar.topology.btnCreateNetwork"));

		GroupLayout gl_topologiePanel = new GroupLayout(this);
		gl_topologiePanel.setHorizontalGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_topologiePanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_topologiePanel
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_topologiePanel
														.createSequentialGroup()
														.addComponent(mouseModusPanel, GroupLayout.PREFERRED_SIZE, 360,
																GroupLayout.PREFERRED_SIZE).addContainerGap())
										.addGroup(
												gl_topologiePanel
														.createSequentialGroup()
														.addGroup(
																gl_topologiePanel.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblInputNeuronen).addComponent(lblOutputNeuronen)
																		.addComponent(lblHiddenLayer).addComponent(lblHiddenNeuronen))
														.addGap(78)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.LEADING, false)
																		.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 60,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(hiddenLayerCountSpinner, GroupLayout.PREFERRED_SIZE,
																				60, GroupLayout.PREFERRED_SIZE)
																		.addComponent(hiddenNeuronSpinner, Alignment.TRAILING,
																				GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
																		.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 60,
																				GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.LEADING)
																		.addGroup(
																				gl_topologiePanel
																						.createSequentialGroup()
																						.addComponent(hiddenLayerDropDown,
																								GroupLayout.PREFERRED_SIZE, 60,
																								GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addComponent(hiddenBiasCB))
																		.addComponent(inputBiasCB)).addContainerGap(15, Short.MAX_VALUE))
										.addGroup(
												gl_topologiePanel.createSequentialGroup().addGap(6).addComponent(chckbxAllNeuronsBind)
														.addPreferredGap(ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
														.addComponent(btnCreateNetwork).addGap(15)))));
		gl_topologiePanel.setVerticalGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_topologiePanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_topologiePanel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(lblInputNeuronen)
										.addGroup(
												gl_topologiePanel
														.createSequentialGroup()
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(inputBiasCB)
																		.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addGap(9)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addComponent(lblOutputNeuronen))
														.addGap(11)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(hiddenLayerCountSpinner, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addComponent(lblHiddenLayer))
														.addGap(11)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(hiddenNeuronSpinner, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addComponent(lblHiddenNeuronen)
																		.addComponent(hiddenLayerDropDown, GroupLayout.PREFERRED_SIZE, 28,
																				GroupLayout.PREFERRED_SIZE).addComponent(hiddenBiasCB))))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(mouseModusPanel, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_topologiePanel.createParallelGroup(Alignment.BASELINE).addComponent(chckbxAllNeuronsBind)
										.addComponent(btnCreateNetwork)).addGap(24)));
		/**
		 * internal MausModus-Panel Elements
		 */
		mouseInputRB = new JRadioButton(Settings.i18n.getString("sidebar.topology.mouse.input"));
		mouseInputRB.setSelected(true);
		mouseInputRB.setEnabled(false);
		buttonGroup.add(mouseInputRB);
		mouseOutputRB = new JRadioButton(Settings.i18n.getString("sidebar.topology.mouse.output"));
		buttonGroup.add(mouseOutputRB);
		mouseOutputRB.setEnabled(false);
		mouseHiddenRB = new JRadioButton(Settings.i18n.getString("sidebar.topology.mouse.hidden"));
		buttonGroup.add(mouseHiddenRB);
		mouseHiddenRB.setEnabled(false);
		comboBoxHiddenMausModus = new JComboBox(hiddenLayerComboModel);
		comboBoxHiddenMausModus.setEnabled(false);

		lblJungModis = new JLabel(Settings.i18n.getString("sidebar.topology.mouse.lblJungModes"));
		comboBoxMouseModes = new JComboBox();
		comboBoxMouseModes.setModel(new DefaultComboBoxModel(new String[] { "Picking", "Editing", "Transforming" }));

		GroupLayout gl_mouseModusPanel = new GroupLayout(mouseModusPanel);
		gl_mouseModusPanel.setHorizontalGroup(gl_mouseModusPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_mouseModusPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_mouseModusPanel
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_mouseModusPanel
														.createSequentialGroup()
														.addComponent(lblJungModis)
														.addGap(18)
														.addComponent(comboBoxMouseModes, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(
												gl_mouseModusPanel
														.createSequentialGroup()
														.addComponent(mouseInputRB)
														.addGap(26)
														.addComponent(mouseOutputRB)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(mouseHiddenRB)
														.addGap(18)
														.addComponent(comboBoxHiddenMausModus, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE))).addContainerGap(43, Short.MAX_VALUE)));
		gl_mouseModusPanel.setVerticalGroup(gl_mouseModusPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_mouseModusPanel
						.createSequentialGroup()
						.addGroup(
								gl_mouseModusPanel
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblJungModis)
										.addComponent(comboBoxMouseModes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_mouseModusPanel.createParallelGroup(Alignment.BASELINE).addComponent(mouseInputRB)
										.addComponent(mouseOutputRB).addComponent(mouseHiddenRB)
										.addComponent(comboBoxHiddenMausModus, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		mouseModusPanel.setLayout(gl_mouseModusPanel);
		setLayout(gl_topologiePanel);

		initActions();
	}

	/**
	 * 
	 */
	private void initActions() {

		btnCreateNetwork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				NetworkFactory factory = new NetworkFactory();
				Network network = netConfig.getNetwork();
				Integer inputNeurons = network.getInputSizeIgnoringBias();
				Boolean inputBias = network.getInputLayer().hasBias();
				Integer outputNeurons = network.getOutputSize();
				List<Integer> hiddenNeuronList = new ArrayList<Integer>();
				List<Boolean> hiddenBiasList = new ArrayList<Boolean>();

				for (int i = 1; i <= network.getSizeOfHiddenLayers(); i++) {
					hiddenNeuronList.add(network.getLayer(i).getNeurons().size());
					hiddenBiasList.add(network.getLayer(i).hasBias());
				}
				String activationFunctionName = (String) sidebar.standardOptionsPanel.funktionToActivateCombo.getSelectedItem();
				ActivationFunction activation = new SigmoidFunction();
				Class<?> clazz;
				try {
					clazz = Class.forName(Neuron.functionPackage + "." + activationFunctionName);
					activation = (ActivationFunction) clazz.newInstance();
				} catch (Exception e) {
					System.err.println("could not instantiate function with name: " + activationFunctionName);
				}

				NetConfig netConfig = factory.createNetwork(inputNeurons, inputBias, hiddenNeuronList, hiddenBiasList, outputNeurons,
						activation);

				boolean connectAll = sidebar.topolgyPanel.chckbxAllNeuronsBind.isSelected();
				if (connectAll) {
					netConfig.getNetwork().finalizeStructure();
				}
				Settings.getInstance().getCurrentSession().setNetworkConfig(netConfig);
			}
		});

		comboBoxMouseModes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				update();
			}
		});
		// input bias
		inputBiasCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				boolean newVal = inputBiasCB.isSelected();
				netConfig.getNetwork().getInputLayer().setBias(newVal);
			}
		});

		// hidden bias
		hiddenBiasCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				boolean newVal = hiddenBiasCB.isSelected();
				Integer selectedHiddenLayer = (Integer) hiddenLayerDropDown.getSelectedItem();
				netConfig.getNetwork().getLayer(selectedHiddenLayer).setBias(newVal);
			}
		});

		hiddenLayerDropDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Integer selectedHiddenLayer = (Integer) hiddenLayerDropDown.getSelectedItem();
				if (selectedHiddenLayer == null || ignoreHiddenLayerCombo) {
					return; // the hiddenLayerDropDown are clearing
				}
				int hiddenLayerSize = netConfig.getNetwork().getTotalLayerSize(selectedHiddenLayer);
				hiddenNeuronSpinner.setValue(hiddenLayerSize);

				// update hidden bias
				Layer layer = netConfig.getNetwork().getLayer(selectedHiddenLayer);
				hiddenBiasCB.setSelected(layer.hasBias());
			}
		});

		// Input Neuronen
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) inputNeuroSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				netConfig.getNetwork().setInputSizeIgnoringBias((Integer) evt.getNewValue());
				WorkPanel test = new WorkPanel();
			}
		});

		// Hidden Neuronen
		editor = (JSpinner.DefaultEditor) hiddenNeuronSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				Integer hiddenLayerIndex = (Integer) hiddenLayerDropDown.getSelectedItem();
				netConfig.getNetwork().setHiddenLayerSizeIgnoreingBias(hiddenLayerIndex, (Integer) evt.getNewValue());
			}
		});

		// Hidden Layer
		editor = (JSpinner.DefaultEditor) hiddenLayerCountSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				netConfig.getNetwork().setSizeOfHiddenLayers((Integer) evt.getNewValue());
				Integer oldSelected = (Integer) hiddenLayerComboModel.getSelectedItem();

				ignoreHiddenLayerCombo = true;
				hiddenLayerComboModel.removeAllElements(); // prepare for adding new

				// Integer hiddenLayers = (Integer) hiddenLayerCountSpinner.getValue(); // that is not the model
				int hiddenLayers = netConfig.getNetwork().getSizeOfHiddenLayers();
				if (hiddenLayers > 0) {
					// start with 1 (its the index for the first hidden layer)
					for (int i = 1; i <= hiddenLayers; i++) {
						hiddenLayerComboModel.addElement(new Integer(i));
					}
					// try to set the old value, if not exist, try to set previous layer
					if (oldSelected != null) { // first initializing time
						while (oldSelected >= 1) {
							hiddenLayerDropDown.setSelectedItem(oldSelected);
							if (hiddenLayerDropDown.getSelectedItem().equals(oldSelected)) {
								break;
							}
							oldSelected--;
						}
					}
				}

				ignoreHiddenLayerCombo = false;
			}
		});

		// Output Neuronen
		editor = (JSpinner.DefaultEditor) outputNeuroSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				netConfig.getNetwork().setOuputLayerSize((Integer) evt.getNewValue());
			}
		});

	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		update();
	}

	/**
	 * Updates the components
	 */
	private void update() {
		// update hdiden layer dependect components
		// selected hidden layer, hidden bias checkbox,
		// neuron spinner for selected hidden layer

		Integer hiddenLayers = netConfig.getNetwork().getSizeOfHiddenLayers();

		if (hiddenLayers > 0) {
			hiddenLayerDropDown.setEnabled(true);
			hiddenBiasCB.setEnabled(true);
			hiddenNeuronSpinner.setEnabled(true);
		} else {
			hiddenLayerDropDown.setEnabled(false);
			hiddenBiasCB.setEnabled(false);
			hiddenNeuronSpinner.setEnabled(false);
		}

		// update input spinner
		int inputSizeWithoutBias = netConfig.getNetwork().getInputSizeIgnoringBias();
		inputSpinnerModel.setValue(inputSizeWithoutBias);

		// update output spinner
		int outputSize = netConfig.getNetwork().getOutputSize();
		outputNeuroSpinner.setValue(outputSize);

		// update hidden neurons
		Integer selectedHiddenLayer = (Integer) hiddenLayerDropDown.getSelectedItem();
		if (selectedHiddenLayer != null) {
			Layer layer = netConfig.getNetwork().getLayer(selectedHiddenLayer);
			// ignore bias neuron for size
			int sizeForSelectedHiddenLayer = layer.getNeurons().size() - (layer.hasBias() ? 1 : 0);
			hiddenNeuronSpinner.setValue(sizeForSelectedHiddenLayer);

			// hidden bias
			hiddenBiasCB.setSelected(layer.hasBias());
		}

		// input bias
		if (netConfig.getNetwork().getInputLayer() != null) {
			boolean inputBias = netConfig.getNetwork().getInputLayer().hasBias();
			inputBiasCB.setSelected(inputBias);
		}

		// update mouse mode
		String selected = (String) comboBoxMouseModes.getSelectedItem();
		if (selected.equals("Picking")) {
			GraphController.getInstance().graphMouse.setMode(Mode.PICKING);
			mouseHiddenRB.setEnabled(false);
			mouseInputRB.setEnabled(false);
			comboBoxHiddenMausModus.setEnabled(false);
			mouseOutputRB.setEnabled(false);
		} else if (selected.equals("Editing")) {
			GraphController.getInstance().graphMouse.setMode(Mode.EDITING);
			if (hiddenLayers > 0) {
				mouseHiddenRB.setEnabled(true);
				comboBoxHiddenMausModus.setEnabled(true);
			} else {
				mouseHiddenRB.setEnabled(false);
				comboBoxHiddenMausModus.setEnabled(false);
			}
			mouseInputRB.setEnabled(true);
			mouseOutputRB.setEnabled(true);
		} else if (selected.equals("Transforming")) {
			GraphController.getInstance().graphMouse.setMode(Mode.TRANSFORMING);
			mouseHiddenRB.setEnabled(false);
			comboBoxHiddenMausModus.setEnabled(false);
			mouseInputRB.setEnabled(false);
			mouseOutputRB.setEnabled(false);
		}

		System.out.println(netConfig.getNetwork().getLayers());

	}

}
