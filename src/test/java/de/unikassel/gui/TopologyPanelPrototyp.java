package de.unikassel.gui;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.accessibility.AccessibleContext;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;

public class TopologyPanelPrototyp extends JFrame implements PropertyChangeListener {
	private static final Dimension jspinnerDimension = new Dimension(55, 22);
	private JSplitPane splitPane;
	private JPanel leftMainPane;
	private JPanel rightMainPanel;
	private Box topologyBox;
	private Box mouseModeBox;
	private JRadioButton insertInputLayerRB;
	private JRadioButton insertOutputLayerRB;
	private JRadioButton insertHiddenRB;
	private JComboBox hiddenModeSpinner;

	private ButtonGroup insertGroup;
	private Box layerConfigIn;
	private Box layerConfigHidden;
	private Box layerConfigOut;
	private JLabel inputConfigLabel;
	private JLabel hiddenConfigLabel;
	private JLabel outputConfigLabel;
	private JSpinner inputLayerSpinner;
	private JComboBox hiddenLayerDropdown;
	private JSpinner[] hiddenNeuronSpinner;
	private JSpinner outputLayerSpinner;
	private JPanel controlPanel;
	private Box hiddenLayerBox;
	private JSpinner hiddenLayerCountSpinner;
	private JPanel netConfigPanel;
	private JCheckBox inputBiasCB;
	private JCheckBox[] hiddenBiasCB;
	private JPanel defaultConfigPanel;
	private Box activationBox;
	private JComboBox activationFunctionComboBox;
	private Box synapseValueBox;
	private SpinnerNumberModel hiddenLayerCountModel;
	private JPanel hiddenCheckBoxPanel;


	public static void main (String args[]) {
		TopologyPanelPrototyp sb = new TopologyPanelPrototyp();
		sb.setVisible(true);
		sb.setDefaultCloseOperation(EXIT_ON_CLOSE);
		sb.pack();
	}
	
	public TopologyPanelPrototyp() {
		
	
		getContentPane().setLayout(new BorderLayout());
		
		leftMainPane = new JPanel(new BorderLayout());
		rightMainPanel = new JPanel(new BorderLayout());
		
        
		// centre split pane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(0.3);
		splitPane.setContinuousLayout(false); 

		splitPane.setLeftComponent(leftMainPane);
		splitPane.setRightComponent(rightMainPanel);
		
		
		controlPanel = new JPanel();
		rightMainPanel.add(controlPanel, BorderLayout.CENTER);
		
		int maxHiddenLayer = 10;
		hiddenLayerCountModel = new SpinnerNumberModel(0, 0, maxHiddenLayer, 1);
		SpinnerNumberModel inputNeuronCountModel = new SpinnerNumberModel(1, 1, 1000, 1);
		SpinnerNumberModel outputNeuronCountModel = new SpinnerNumberModel(1, 1, 1000, 1);
		
		final SpinnerNumberModel[] hiddenNeuronCountModel = new SpinnerNumberModel[maxHiddenLayer];
		hiddenNeuronSpinner = new JSpinner[maxHiddenLayer];
		hiddenBiasCB = new JCheckBox[maxHiddenLayer];
		for (int i=0; i<maxHiddenLayer; i++) {
			hiddenNeuronCountModel[i] = new SpinnerNumberModel(1, 1, 1000, 1);
			hiddenNeuronSpinner[i] = new JSpinner(hiddenNeuronCountModel[i]);
			hiddenNeuronSpinner[i].setName("hidden_"+i);
			addPropertyChangeListener(hiddenNeuronSpinner[i]);
			hiddenBiasCB[i] = new JCheckBox("Bias");
			hiddenBiasCB[i].getAccessibleContext().addPropertyChangeListener(this);
			
		}
		final JPanel hiddenLayerSpinnerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		if (hiddenNeuronSpinner.length > 0) {
			hiddenNeuronSpinner[0].setEnabled(false);
			hiddenLayerSpinnerPanel.add(hiddenNeuronSpinner[0]);
			
			hiddenCheckBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
			hiddenBiasCB[0].setEnabled(false);
			hiddenCheckBoxPanel.add(hiddenBiasCB[0]);
		}
		
		/*
		 * 
		 */
		
		topologyBox = Box.createVerticalBox();
		topologyBox.setBorder(BorderFactory.createTitledBorder("Topologie"));
		
		netConfigPanel = new JPanel(new GridLayout(4,0));
		netConfigPanel.setBorder(BorderFactory.createTitledBorder("Netzkonfiguration"));
		hiddenLayerBox = Box.createHorizontalBox();
		hiddenLayerBox.add(new JLabel("Hidden Layer Anzahl"));
		hiddenLayerCountSpinner = new JSpinner(hiddenLayerCountModel);
		addPropertyChangeListener(hiddenLayerCountSpinner);
		hiddenLayerBox.add(hiddenLayerCountSpinner);
		netConfigPanel.add(hiddenLayerBox);
		
		
		
		inputConfigLabel = new JLabel("Input Neuronen");
		hiddenConfigLabel = new JLabel("Hidden Neuronen");
		outputConfigLabel = new JLabel("Output Neuronen");
		inputLayerSpinner = new JSpinner(inputNeuronCountModel);
		addPropertyChangeListener(inputLayerSpinner);
		hiddenLayerDropdown = new JComboBox(new String[]{"1"});
		hiddenLayerDropdown.setEnabled(false);
		hiddenLayerDropdown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = hiddenLayerDropdown.getSelectedIndex();
				if (i >= 0 && i < hiddenNeuronSpinner.length) {
					hiddenLayerSpinnerPanel.removeAll();
					hiddenLayerSpinnerPanel.add(hiddenNeuronSpinner[i]);
					hiddenLayerSpinnerPanel.updateUI();
					hiddenNeuronSpinner[i].updateUI();
					
					hiddenCheckBoxPanel.removeAll();
					hiddenCheckBoxPanel.add(hiddenBiasCB[i]);
//					hiddenCheckBoxPanel.updateUI();
					hiddenCheckBoxPanel.validate();
					hiddenCheckBoxPanel.repaint();
				}
			}
		});
		
		outputLayerSpinner = new JSpinner(outputNeuronCountModel);
		inputBiasCB = new JCheckBox("Bias");
		
		
		layerConfigIn = Box.createHorizontalBox();
		layerConfigHidden = Box.createHorizontalBox();
		layerConfigOut = Box.createHorizontalBox();
		
		layerConfigIn.add(inputConfigLabel);
		layerConfigIn.add(inputLayerSpinner);
		layerConfigIn.add(inputBiasCB);
		
		layerConfigHidden.add(hiddenConfigLabel);
		layerConfigHidden.add(hiddenLayerDropdown);
		layerConfigHidden.add(hiddenLayerSpinnerPanel);

		layerConfigHidden.add(hiddenCheckBoxPanel);

		
		layerConfigOut.add(outputConfigLabel);
		layerConfigOut.add(outputLayerSpinner);
		
		netConfigPanel.add(layerConfigIn);
		netConfigPanel.add(layerConfigHidden);
		netConfigPanel.add(layerConfigOut);
		
		/*
		 * 
		 */
		
		mouseModeBox = Box.createHorizontalBox();
		mouseModeBox.setBorder(BorderFactory.createTitledBorder("Maus Modus"));
		
		insertInputLayerRB = new JRadioButton("Input");
		insertOutputLayerRB = new JRadioButton("Output");
		insertHiddenRB = new JRadioButton("Hidden:");
		hiddenModeSpinner = new JComboBox(new String[]{"1"});
		hiddenModeSpinner.setEnabled(false);

		insertGroup = new ButtonGroup();
		insertGroup.add(insertInputLayerRB);
		insertGroup.add(insertOutputLayerRB);
		insertGroup.add(insertHiddenRB);
		

		mouseModeBox.add(insertInputLayerRB);
		mouseModeBox.add(insertOutputLayerRB);
		mouseModeBox.add(insertHiddenRB);
		mouseModeBox.add(hiddenModeSpinner);
		
		
		
	
		/*
		 * 
		 */
		

		defaultConfigPanel = new JPanel(new GridLayout(2,0));
		defaultConfigPanel.setBorder(BorderFactory.createTitledBorder("Standard Werte"));
		activationBox = Box.createHorizontalBox();
		activationBox.add(new JLabel("Aktivierungsfunktion"));
		activationFunctionComboBox = new JComboBox(new String[]{"Sigmoid", "TanH"});
		activationBox.add(activationFunctionComboBox);
		
		synapseValueBox = Box.createHorizontalBox();
		synapseValueBox.add(new JLabel("Synapsenwert"));
		synapseValueBox.add(new JCheckBox("Zufällig"));
		
		defaultConfigPanel.add(activationBox);
		defaultConfigPanel.add(synapseValueBox);
		
		topologyBox.add(netConfigPanel);
		topologyBox.add(mouseModeBox);
		topologyBox.add(defaultConfigPanel);
		
		controlPanel.add(topologyBox);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
	}

	private void addPropertyChangeListener(JSpinner spinner) {
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", this);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		JComponent src = null;
		Container spinner = null;
		try {
			src = (JComponent)evt.getSource();
			spinner = src.getParent().getParent();
		} catch (ClassCastException e) {
			if (evt.getSource() instanceof AccessibleContext) {
				if (evt.getPropertyName().equals("AccessibleValue")) {
					System.out.println(evt.getPropertyName()+": "+evt.getOldValue()+" -> "+evt.getNewValue());
				}
			}
			return;
		} catch (NullPointerException e) {
			// there are no 2 parents of src
			// create an empty spinner to don't let check null in the next lines
			spinner = new Container();
		}
		
		System.out.println(src);
		// parnet of a  JFormattedTextField is a NumberEditor and its parent is a JSpinner
		if (spinner == hiddenLayerCountSpinner) {
			int hiddenLayerCount = hiddenLayerCountModel.getNumber().intValue();
			if (hiddenLayerCount == 0) {
				hiddenLayerDropdown.setEnabled(false);
				hiddenNeuronSpinner[0].setEnabled(false);
				hiddenModeSpinner.setEnabled(false);
				hiddenBiasCB[0].setEnabled(false);
			} else {
				hiddenLayerDropdown.setEnabled(true);
				hiddenModeSpinner.setEnabled(true);
				hiddenNeuronSpinner[0].setEnabled(true);
				hiddenCheckBoxPanel.removeAll();
				hiddenBiasCB[0].setEnabled(true);
				
				Integer[] comboBoxModel = new Integer[hiddenLayerCount];
				for (Integer i=1; i<= hiddenLayerCount; i++) {
					comboBoxModel[i-1] = i;
				}
				// set index to old position and check if the old index stil exists
				int oldHiddenIndex = hiddenLayerDropdown.getSelectedIndex();
				hiddenLayerDropdown.setModel(new DefaultComboBoxModel(comboBoxModel));
				if (oldHiddenIndex+1 > hiddenLayerCount) {
					// decrement index, because the selected layer was removed
					oldHiddenIndex--;
				} else if (oldHiddenIndex == -1) {
					oldHiddenIndex++;
				}
				hiddenLayerDropdown.setSelectedIndex(oldHiddenIndex);
				
				int oldMouseIndex = hiddenModeSpinner.getSelectedIndex();
				hiddenModeSpinner.setModel(new DefaultComboBoxModel(comboBoxModel));
				if (oldMouseIndex+1 > hiddenLayerCount) {
					// decrement index, because the selected layer was removed
					oldMouseIndex--;
				} else if (oldMouseIndex == -1) {
					oldMouseIndex++;
				}
				hiddenModeSpinner.setSelectedIndex(oldMouseIndex);
			}
		} else if (spinner.getName().startsWith("hidden")) {
			String layer = spinner.getName().split("_")[0];
			System.out.println("hidden layer "+layer+" : "+evt.getNewValue());
		} 
	}
}
