package de.unikassel.vis;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.JCheckBox;


public class TrainingJFrame extends JPanel {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TrainingJFrame panel = new TrainingJFrame();
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
	public TrainingJFrame() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
////		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
//		JPanel panel = new JPanel();
		setBorder(new TitledBorder(null, "Training", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel strategienPanel = new JPanel();
		strategienPanel.setBorder(new TitledBorder(null, "Strategien", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
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
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(strategienPanel, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
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
					.addContainerGap(44, Short.MAX_VALUE))
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
					.addGroup(groupLayout.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(rdbtnOffline))
					.addGap(28)
					.addComponent(strategienPanel, GroupLayout.PREFERRED_SIZE, 79, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblTyp = new JLabel("Typ");
		
		JLabel lblMaxIteration = new JLabel("Maximal Iteration");
		
		JComboBox comboBoxTyp = new JComboBox();
		comboBoxTyp.setModel(new DefaultComboBoxModel(new String[] {"MaxIteration"}));
		
		JSpinner spinnerMaxIteration = new JSpinner();
		
		JCheckBox chckbxStrategieActivation = new JCheckBox("Strategie Aktivieren");
		chckbxStrategieActivation.setSelected(true);
		GroupLayout gl_strategienPanel = new GroupLayout(strategienPanel);
		gl_strategienPanel.setHorizontalGroup(
			gl_strategienPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_strategienPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_strategienPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTyp)
						.addComponent(lblMaxIteration))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_strategienPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(comboBoxTyp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinnerMaxIteration, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxStrategieActivation)
					.addGap(23))
		);
		gl_strategienPanel.setVerticalGroup(
			gl_strategienPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_strategienPanel.createSequentialGroup()
					.addGroup(gl_strategienPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTyp)
						.addComponent(comboBoxTyp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxStrategieActivation))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_strategienPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaxIteration)
						.addComponent(spinnerMaxIteration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		strategienPanel.setLayout(gl_strategienPanel);
		setLayout(groupLayout);
	}
}
