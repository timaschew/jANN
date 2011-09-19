package de.unikassel.ann.gui.sidebar;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.Settings;

public class TrainStrategyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JComboBox comboBoxAlgorithm;
	public JSpinner spinnerLernRate;
	public JSpinner spinnerMomentum;
	public AbstractButton rdbtnOnline;
	public JRadioButton rdbtnOffline;
	public final ButtonGroup buttonGroup = new ButtonGroup();
	
	public JPanel strategiePanel;
	public JComboBox comboBoxTypStrategien;
	public JCheckBox chckbxActivateStrategie;
	public JSpinner spinnerMaxIterations;
	public JSpinner spinnerMaxIterations2;
	
	final static String MAX_ITERATIONSTRATEGY = "MaxIteration";
	final static String MIN_ERRORSTRATEGY = "MinError";

	/**
	 * Create the panel.
	 */
	public TrainStrategyPanel() {

		setBorder(new TitledBorder(null,Settings.i18n.getString("sidebar.trainingsStrategy") , TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(400, 359);

		JLabel lblAlgorithm = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.algorithm")); 

		JLabel lblLernrate = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.lernRate"));

		JLabel lblMomentum = new JLabel(Settings.i18n.getString("sidebar.trainingsStretegy.momentum"));

		comboBoxAlgorithm = new JComboBox();
		comboBoxAlgorithm.setModel(new DefaultComboBoxModel(new String[] { "Backpropagation", "Test" }));

		spinnerLernRate = new JSpinner();
		spinnerMomentum = new JSpinner();

		JLabel lblTrainingsmodus = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.trainingmodus"));//Settings.i18n.getString("sidebar.trainingsStrategy.trainingmodus")

		rdbtnOnline = new JRadioButton();
		rdbtnOnline.setText(Settings.i18n.getString("sidebar.trainingsStrategy.onlineRB"));
		buttonGroup.add(rdbtnOnline);
		rdbtnOnline.setSelected(true);

		rdbtnOffline = new JRadioButton(Settings.i18n.getString("sidebar.trainingsStrategy.offlineRB"));
		buttonGroup.add(rdbtnOffline);

		strategiePanel = new JPanel(new GridLayout(2, 1));
		strategiePanel.setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.trainingsStrategy.strategy"), TitledBorder.LEADING,TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		// Layout TrainStrategy Pane
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(strategiePanel, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.TRAILING, false)
														.addGroup(
																groupLayout.createSequentialGroup().addComponent(lblMomentum)
																		.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(spinnerMomentum, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout.createSequentialGroup().addComponent(lblLernrate)
																		.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(spinnerLernRate, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(lblAlgorithm)
																		.addGap(52)
																		.addComponent(comboBoxAlgorithm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout.createParallelGroup(Alignment.LEADING).addComponent(rdbtnOnline).addComponent(rdbtnOffline)))
										.addComponent(lblTrainingsmodus)).addContainerGap(20, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblAlgorithm)
										.addComponent(comboBoxAlgorithm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblLernrate)
										.addComponent(spinnerLernRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblMomentum)
										.addComponent(spinnerMomentum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblTrainingsmodus).addComponent(rdbtnOnline))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(rdbtnOffline).addGap(18)
						.addComponent(strategiePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		JLabel lblTypStrategien = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.strategy.type"));
		String comboBoxItems[] = { MAX_ITERATIONSTRATEGY, MIN_ERRORSTRATEGY };
//		comboBoxTypStrategien.setModel(new DefaultComboBoxModel(new String[] { "MaxIterations", "MinError" }));
		comboBoxTypStrategien = new JComboBox(comboBoxItems);
		chckbxActivateStrategie = new JCheckBox("");
		chckbxActivateStrategie.setSelected(true);



		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		comboBoxTypStrategien.setEditable(false);

	
		
		// Panel that uses CardLayout
		final JPanel cards = new JPanel(new CardLayout());

		JLabel lblMaxIterations = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.strategy.maxIterations"));
		spinnerMaxIterations = new JSpinner();
		spinnerMaxIterations.setSize(new Dimension(45, 20));
		JLabel label = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.strategy.maxIterations2"));
		spinnerMaxIterations2 = new JSpinner();
		
		JLabel lblMinError = new JLabel("MinError");
		JSpinner spinnerMinError = new JSpinner();
		
		//Panel for MaxIteration Strategy
		JPanel maxIter = new JPanel();
		
		//Panel for minError Strategy
		JPanel minError = new JPanel();
		
		cards.add(maxIter, MAX_ITERATIONSTRATEGY);
		cards.add(minError, MIN_ERRORSTRATEGY);//comboBoxTypStrategien.getItemAt(1)
		
		//Layout for MaxIterationen Strategy Card
		GroupLayout gl_maxIter = new GroupLayout(maxIter);
		gl_maxIter.setHorizontalGroup(
			gl_maxIter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_maxIter.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMaxIterations)
					.addGap(73)
					.addComponent(spinnerMaxIterations, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(127))
		);
		gl_maxIter.setVerticalGroup(
			gl_maxIter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_maxIter.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_maxIter.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaxIterations)
						.addComponent(spinnerMaxIterations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		maxIter.setLayout(gl_maxIter);
		
		
		//Layout for MinError Strategy Card
		GroupLayout gl_minError = new GroupLayout(minError);
		gl_minError.setHorizontalGroup(
			gl_minError.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_minError.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMinError)
					.addGap(113)
					.addComponent(spinnerMinError, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(120))
		);
		gl_minError.setVerticalGroup(
			gl_minError.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_minError.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_minError.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMinError)
						.addComponent(spinnerMinError, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		minError.setLayout(gl_minError);
		
		 // Layout for comboBoxPanel -->typ and comboboxStrategyTyp
		 GroupLayout gl_comboBoxPane = new GroupLayout(comboBoxPane);
		 gl_comboBoxPane.setHorizontalGroup(
		 	gl_comboBoxPane.createParallelGroup(Alignment.LEADING)
		 		.addGroup(gl_comboBoxPane.createSequentialGroup()
		 			.addContainerGap()
		 			.addComponent(lblTypStrategien)
		 			.addGap(92)
		 			.addComponent(comboBoxTypStrategien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		 			.addPreferredGap(ComponentPlacement.RELATED)
		 			.addComponent(chckbxActivateStrategie))
		 );
		 gl_comboBoxPane.setVerticalGroup(
		 	gl_comboBoxPane.createParallelGroup(Alignment.LEADING)
		 		.addGroup(gl_comboBoxPane.createSequentialGroup()
		 			.addGap(5)
		 			.addGroup(gl_comboBoxPane.createParallelGroup(Alignment.BASELINE)
		 				.addComponent(lblTypStrategien)
		 				.addComponent(comboBoxTypStrategien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		 		.addGroup(gl_comboBoxPane.createSequentialGroup()
		 			.addGap(9)
		 			.addComponent(chckbxActivateStrategie))
		 );
		 comboBoxPane.setLayout(gl_comboBoxPane);
		 
		 //Listener for cardLayout
		 comboBoxTypStrategien.addItemListener(new ItemListener() {
			 @Override
			 public void itemStateChanged(ItemEvent evt) {
				 CardLayout cl = (CardLayout)(cards.getLayout());
				 cl.show(cards, (String)evt.getItem());
			 }
		 });
		 
		 strategiePanel.add(comboBoxPane);
		 strategiePanel.add(cards);
		
		setLayout(groupLayout);
	}
}
