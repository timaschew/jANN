package de.unikassel.vis;

import javax.accessibility.AccessibleContext;
import javax.swing.JPanel;

import java.awt.Container;
import java.awt.Dimension;
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


public class Topologie extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSpinner inputNeuroSpinner;
	private JSpinner outputNeuroSpinner;
	private JSpinner hiddenLayerCountSpinner;
	private JSpinner hiddenNeuronSpinner;
	private JComboBox hiddenLayerDropDown;

	
	private JComboBox comboBoxHiddenMausModus;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Topologie panel = new Topologie();
//					JFrame frame = new JFrame();
//					frame.setContentPane(panel);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Topologie() {
		setBorder(new TitledBorder(null, "Topologie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		setSize(400, 240);
		setPreferredSize(new Dimension(400, 240));

		
		JLabel lblInputNeuronen = new JLabel("Input Neuronen");
		inputNeuroSpinner = new JSpinner();
		
		JLabel lblOutputNeuronen = new JLabel("Output Neuronen");
		outputNeuroSpinner = new JSpinner();
		
		
		JLabel lblHiddenLayer = new JLabel("Hidden Layer");
		hiddenLayerCountSpinner = new JSpinner();
		
		JLabel lblHiddenNeuronen = new JLabel("Hidden Neuronen");
		hiddenNeuronSpinner = new JSpinner();
		
		//is with the hiddenLayerCountSpinner associated
		hiddenLayerDropDown = new JComboBox(new String[]{"1"});
		hiddenLayerDropDown.setEnabled(false);


		
		JPanel mouseModusPanel = new JPanel();
		mouseModusPanel.setBorder(new TitledBorder(null, "Maus-Modus", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JCheckBox inputBiasCB = new JCheckBox("Bias");
		JCheckBox hiddenBiasCB = new JCheckBox("Bias");
		
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
								.addComponent(hiddenNeuronSpinner, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_topologiePanel.createSequentialGroup()
										.addComponent(hiddenLayerDropDown, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(hiddenBiasCB))
									.addComponent(inputBiasCB))))
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
									.addComponent(inputBiasCB)
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
									.addComponent(hiddenNeuronSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblHiddenNeuronen)
									.addComponent(hiddenLayerDropDown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(hiddenBiasCB))))
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
	
}
