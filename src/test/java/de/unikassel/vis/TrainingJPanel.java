package de.unikassel.vis;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import java.awt.Color;



public class TrainingJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TrainingJPanel() {
		
		setBorder(new TitledBorder(null, "Training", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(400, 260);
		
		JLabel lblAlgorithm = new JLabel("Algorithmus");
		
		JLabel lblLernrate = new JLabel("Lernrate");
		
		JLabel lblMomentum = new JLabel("Momentum");
		
		JComboBox comboBoxAlgorithm = new JComboBox();
		comboBoxAlgorithm.setModel(new DefaultComboBoxModel(new String[] {"Backpropagation", "Test"}));
		
		JSpinner spinnerLernRate = new JSpinner();
		
		JSpinner spinnerMomentum = new JSpinner();
		
		JLabel lblTrainingsmodus = new JLabel("Trainingsmodus");
		
		JRadioButton rdbtnOnline = new JRadioButton("Online");
		rdbtnOnline.setSelected(true);
		
		JRadioButton rdbtnOffline = new JRadioButton("Offline");
		
		JPanel strategiePanel = new JPanel();
		strategiePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Strategien", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(strategiePanel, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblMomentum)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(spinnerMomentum, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblLernrate)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(spinnerLernRate, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblAlgorithm)
								.addGap(52)
								.addComponent(comboBoxAlgorithm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnOnline)
								.addComponent(rdbtnOffline)))
						.addComponent(lblTrainingsmodus))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAlgorithm)
						.addComponent(comboBoxAlgorithm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLernrate)
						.addComponent(spinnerLernRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMomentum)
						.addComponent(spinnerMomentum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTrainingsmodus)
						.addComponent(rdbtnOnline))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnOffline)
					.addGap(18)
					.addComponent(strategiePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		
		JLabel lblTypStrategien = new JLabel("Typ");
		
		JComboBox comboBoxTypStrategien = new JComboBox();
		comboBoxTypStrategien.setModel(new DefaultComboBoxModel(new String[] {"MaxIterations"}));
		
		JCheckBox chckbxActivateStrategie = new JCheckBox("Strategie aktivieren");
		chckbxActivateStrategie.setSelected(true);
		
		JLabel lblMaxIterations = new JLabel("Max. Iterations");
		
		JSpinner spinnerMaxIterations = new JSpinner();
		GroupLayout gl_strategiePanel = new GroupLayout(strategiePanel);
		gl_strategiePanel.setHorizontalGroup(
			gl_strategiePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_strategiePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_strategiePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTypStrategien)
						.addComponent(lblMaxIterations))
					.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
					.addGroup(gl_strategiePanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(comboBoxTypStrategien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinnerMaxIterations, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxActivateStrategie)
					.addGap(21))
		);
		gl_strategiePanel.setVerticalGroup(
			gl_strategiePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_strategiePanel.createSequentialGroup()
					.addGroup(gl_strategiePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTypStrategien)
						.addComponent(chckbxActivateStrategie)
						.addComponent(comboBoxTypStrategien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_strategiePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaxIterations)
						.addComponent(spinnerMaxIterations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		strategiePanel.setLayout(gl_strategiePanel);
		setLayout(groupLayout);
	}
}
