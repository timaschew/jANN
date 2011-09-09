package de.unikassel.vis;

import javax.accessibility.AccessibleContext;
import javax.swing.JPanel;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.SpinnerNumberModel;

import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;


public class Topologie extends JPanel implements PropertyChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSpinner inputNeuroSpinner;
	private JSpinner outputNeuroSpinner;
	private JSpinner hiddenLayerCountSpinner;
	private JSpinner[] hiddenNeuronSpinner;
	private JComboBox hiddenLayerDropDown;
	
	private SpinnerNumberModel hiddenLayerCountModel;
	private SpinnerNumberModel inputNeuronCountModel;
	private SpinnerNumberModel outputNeuronCountModel;
	private SpinnerNumberModel[] hiddenNeuronCountModel;
	
	private JComboBox comboBoxHiddenMausModus;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Topologie panel = new Topologie();
					JFrame frame = new JFrame();
					frame.setContentPane(panel);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Topologie() {
		setBorder(new TitledBorder(null, "Topologie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(400, 240);
		
		
		int maxHiddenLayer = 10;
		//Model Hidden Layer Count for hiddenLayerCountSpinner
		hiddenLayerCountModel = new SpinnerNumberModel(0, 0, maxHiddenLayer, 1); 
		// CountModel for inputNeuroSpinner
		inputNeuronCountModel = new SpinnerNumberModel(1, 1, 1000, 1);
		//Countmodel for outputNeuroSpinner
		outputNeuronCountModel = new SpinnerNumberModel(1, 1, 1000, 1);
		//Countmodel-> count of Neurons in different/certain Layers
		hiddenNeuronCountModel = new SpinnerNumberModel[maxHiddenLayer];
		
		JLabel lblInputNeuronen = new JLabel("Input Neuronen");
		inputNeuroSpinner = new JSpinner(inputNeuronCountModel);
		addPropertyChangeListener(inputNeuroSpinner);
		
		JLabel lblOutputNeuronen = new JLabel("Output Neuronen");
		outputNeuroSpinner = new JSpinner();
		
		
		JLabel lblHiddenLayer = new JLabel("Hidden Layer");
		hiddenLayerCountSpinner = new JSpinner();
		hiddenLayerCountSpinner.setModel(hiddenLayerCountModel);
		addPropertyChangeListener(hiddenLayerCountSpinner);
		
		JLabel lblHiddenNeuronen = new JLabel("Hidden Neuronen");
		hiddenNeuronSpinner = new JSpinner[maxHiddenLayer];
//		hiddenNeuronSpinner.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		
		//is with the hiddenLayerCountSpinner associated
		hiddenLayerDropDown = new JComboBox(new String[]{"1"});
		hiddenLayerDropDown.setEnabled(false);
		hiddenLayerDropDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = hiddenLayerDropDown.getSelectedIndex();
				if (i >= 0 && i < hiddenNeuronSpinner.length) {
//					hiddenLayerSpinnerPanel.removeAll();
//					hiddenLayerSpinnerPanel.add(hiddenNeuronSpinner[i]);
//					hiddenLayerSpinnerPanel.updateUI();
					updateUI();
					validate();
					repaint();
					hiddenNeuronSpinner[i].updateUI();
					
//					hiddenCheckBoxPanel.removeAll();
//					hiddenCheckBoxPanel.add(hiddenBiasCB[i]);
////					hiddenCheckBoxPanel.updateUI();
//					hiddenCheckBoxPanel.validate();
//					hiddenCheckBoxPanel.repaint();
				}
			}
		});
		
		for (int i=0; i<maxHiddenLayer; i++) {
			hiddenNeuronCountModel[i] = new SpinnerNumberModel(1, 1, 1000, 1);
			hiddenNeuronSpinner[i] = new JSpinner(hiddenNeuronCountModel[i]);
			hiddenNeuronSpinner[i].setName("hidden_"+i);
			addPropertyChangeListener(hiddenNeuronSpinner[i]);
		}
		
		if (hiddenNeuronSpinner.length > 0) {
			hiddenNeuronSpinner[0].setEnabled(false);
		}
		
		
		JPanel mouseModusPanel = new JPanel();
		mouseModusPanel.setBorder(new TitledBorder(null, "Maus-Modus", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JCheckBox chckbxInputBias = new JCheckBox("Bias");
		
		GroupLayout gl_topologiePanel = new GroupLayout(this);
		gl_topologiePanel.setHorizontalGroup(
			gl_topologiePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topologiePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(mouseModusPanel, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
						.addGroup(gl_topologiePanel.createSequentialGroup()
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInputNeuronen)
								.addComponent(lblOutputNeuronen)
								.addComponent(lblHiddenLayer)
								.addComponent(lblHiddenNeuronen))
							.addGap(78)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(hiddenLayerCountSpinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(hiddenNeuronSpinner[0], Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(hiddenLayerDropDown, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(chckbxInputBias))))
					.addContainerGap())
		);
		gl_topologiePanel.setVerticalGroup(
			gl_topologiePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topologiePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblInputNeuronen)
						.addGroup(gl_topologiePanel.createSequentialGroup()
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(chckbxInputBias)
								.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(9)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblOutputNeuronen))
							.addGap(11)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(hiddenLayerCountSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHiddenLayer))
							.addGap(11)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(hiddenNeuronSpinner[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHiddenNeuronen)
								.addComponent(hiddenLayerDropDown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(11)
					.addComponent(mouseModusPanel, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		/**
		 * internal MausModus-Panel Elements
		 */
		JRadioButton rdbtnInput = new JRadioButton("Input");
		JRadioButton rdbtnOutput = new JRadioButton("Output");
		JRadioButton rdbtnHidden = new JRadioButton("Hidden:");
		comboBoxHiddenMausModus = new JComboBox();
		comboBoxHiddenMausModus.setEnabled(false);
		
		
		GroupLayout gl_mouseModusPanel = new GroupLayout(mouseModusPanel);
		gl_mouseModusPanel.setHorizontalGroup(
			gl_mouseModusPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mouseModusPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnInput)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnOutput)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnHidden)
					.addGap(18)
					.addComponent(comboBoxHiddenMausModus, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(95, Short.MAX_VALUE))
		);
		gl_mouseModusPanel.setVerticalGroup(
			gl_mouseModusPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mouseModusPanel.createSequentialGroup()
					.addGroup(gl_mouseModusPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnInput)
						.addComponent(rdbtnOutput)
						.addComponent(rdbtnHidden)
						.addComponent(comboBoxHiddenMausModus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		mouseModusPanel.setLayout(gl_mouseModusPanel);
		setLayout(gl_topologiePanel);
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
				hiddenLayerDropDown.setEnabled(false);
				hiddenNeuronSpinner[0].setEnabled(false);
				comboBoxHiddenMausModus.setEnabled(false);
//				hiddenBiasCB[0].setEnabled(false);
			} else {
				hiddenLayerDropDown.setEnabled(true);
				comboBoxHiddenMausModus.setEnabled(true);
				hiddenNeuronSpinner[0].setEnabled(true);
//				hiddenCheckBoxPanel.removeAll();
//				hiddenBiasCB[0].setEnabled(true);
				
				Integer[] comboBoxModel = new Integer[hiddenLayerCount];
				for (Integer i=1; i<= hiddenLayerCount; i++) {
					comboBoxModel[i-1] = i;
				}
				// set index to old position and check if the old index stil exists
				int oldHiddenIndex = hiddenLayerDropDown.getSelectedIndex();
				hiddenLayerDropDown.setModel(new DefaultComboBoxModel(comboBoxModel));
				if (oldHiddenIndex+1 > hiddenLayerCount) {
					// decrement index, because the selected layer was removed
					oldHiddenIndex--;
				} else if (oldHiddenIndex == -1) {
					oldHiddenIndex++;
				}
				hiddenLayerDropDown.setSelectedIndex(oldHiddenIndex);
				
				int oldMouseIndex = comboBoxHiddenMausModus.getSelectedIndex();
				comboBoxHiddenMausModus.setModel(new DefaultComboBoxModel(comboBoxModel));
				if (oldMouseIndex+1 > hiddenLayerCount) {
					// decrement index, because the selected layer was removed
					oldMouseIndex--;
				} else if (oldMouseIndex == -1) {
					oldMouseIndex++;
				}
				comboBoxHiddenMausModus.setSelectedIndex(oldMouseIndex);
			}
		} else if (spinner.getName().startsWith("hidden")) {
			String layer = spinner.getName().split("_")[0];
			System.out.println("hidden layer "+layer+" : "+evt.getNewValue());
		} 
	}
}
