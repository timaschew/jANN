package de.unikassel.ann.gui.sidebar;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.ActionController;
import de.unikassel.ann.controller.Actions;
import de.unikassel.ann.controller.Settings;

public class TrainStrategyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JComboBox comboBoxAlgorithm;
	public JSpinner spinnerLearnRate;
	public JSpinner spinnerMomentum;
	public AbstractButton rdbtnOnline;
	public JRadioButton rdbtnOffline;
	public final ButtonGroup buttonGroup = new ButtonGroup();

	// CardLayout Swing-Elements
	public JPanel strategiePanel;
	public JComboBox comboBoxTypStrategien;
	public JCheckBox chckbxActivateStrategie;

	private ActionController ac = ActionController.get();
	private JLabel lblAlgorithm;
	private JLabel lblLearnrate;
	private JLabel lblMomentum;
	private JLabel lblTrainingsmodus;
	private JLabel lblTypStrategien;

	// Cards Panes
	public JPanel maxIter;
	public JPanel minError;
	public JPanel restartError;
	public JPanel restartImprovement;

	// Cards panes elements
	private JPanel comboBoxPane;
	private JPanel cards;
	public JSpinner spinnerMaxIterations;
	public JSpinner spinnerMaxIterations2;
	public JSpinner spinnerMinError;
	public JSpinner spinnerMaxErrorForRestart;
	public JSpinner spinnerIterationsForRestart;
	public JSpinner spinnerImprIterationsForRestart;
	public JSpinner spinnerMinImprovementForRestart;

	final static String MAX_ITERATIONSTRATEGY = "MaxIteration";
	final static String MIN_ERRORSTRATEGY = "MinError";
	final static String RESTART_ERRORSTRATEGY = "RestartError";
	final static String RESTART_IMPROVEMENTSTRATEGY = "RestartImprovement";

	/**
	 * Create the panel.
	 */
	public TrainStrategyPanel() {

		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.trainingsStrategy"), TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		setSize(400, 367);

		lblAlgorithm = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.algorithm"));
		lblLearnrate = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.learnRate"));
		lblMomentum = new JLabel(Settings.i18n.getString("sidebar.trainingsStretegy.momentum"));

		comboBoxAlgorithm = new JComboBox();
		comboBoxAlgorithm.setModel(new DefaultComboBoxModel(new String[] { "Backpropagation", "Test" }));

		spinnerLearnRate = new JSpinner();
		spinnerLearnRate.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));
		spinnerMomentum = new JSpinner();
		spinnerMomentum.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));

		lblTrainingsmodus = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.trainingmodus"));// Settings.i18n.getString("sidebar.trainingsStrategy.trainingmodus")

		rdbtnOnline = new JRadioButton();
		rdbtnOnline.setText(Settings.i18n.getString("sidebar.trainingsStrategy.onlineRB"));
		buttonGroup.add(rdbtnOnline);
		rdbtnOnline.setSelected(true);

		rdbtnOffline = new JRadioButton(Settings.i18n.getString("sidebar.trainingsStrategy.offlineRB"));
		buttonGroup.add(rdbtnOffline);

		strategiePanel = new JPanel();
		strategiePanel.setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.trainingsStrategy.strategy"),
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		lblTypStrategien = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.strategy.type"));
		String comboBoxItems[] = { MAX_ITERATIONSTRATEGY, MIN_ERRORSTRATEGY, RESTART_ERRORSTRATEGY, RESTART_IMPROVEMENTSTRATEGY };
		comboBoxTypStrategien = new JComboBox(comboBoxItems);
		chckbxActivateStrategie = new JCheckBox("");
		// chckbxActivateStrategie.setSelected(true);

		comboBoxPane = new JPanel();
		comboBoxPane.setSize(200, 100);
		comboBoxTypStrategien.setEditable(false);

		cards = new JPanel(new CardLayout());

		JLabel label = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.strategy.maxIterations2"));
		spinnerMaxIterations2 = new JSpinner();

		/**
		 * Cards Panels
		 */
		maxIter = new JPanel();
		minError = new JPanel();
		restartError = new JPanel();
		restartImprovement = new JPanel();

		// MaxIteration Card Elements
		JLabel lblMaxIterations = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.strategy.maxIterations"));
		spinnerMaxIterations = new JSpinner();
		spinnerMaxIterations.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spinnerMaxIterations.setSize(new Dimension(45, 20));

		// MinError Card Elements
		JLabel lblMinError = new JLabel("MinError");
		spinnerMinError = new JSpinner();
		spinnerMinError.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));

		// RestartError Card Elements
		JLabel lblMaxErrorFor = new JLabel("Max. Error for restart");
		spinnerMaxErrorForRestart = new JSpinner();
		spinnerMaxErrorForRestart.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));

		JLabel lblIterationsForRestart = new JLabel("Iterations for Restart");
		spinnerIterationsForRestart = new JSpinner();

		// RestartImprovement Elements
		JLabel lblMinImprovementFor = new JLabel("Min. Improvement for restart");
		spinnerMinImprovementForRestart = new JSpinner();
		spinnerMinImprovementForRestart.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));

		JLabel lblItearation = new JLabel("Iterations for restart");
		spinnerImprIterationsForRestart = new JSpinner();

		// add the Pane to the Cardlayout
		cards.add(maxIter, MAX_ITERATIONSTRATEGY);
		cards.add(minError, MIN_ERRORSTRATEGY);// comboBoxTypStrategien.getItemAt(1)
		cards.add(restartError, RESTART_ERRORSTRATEGY);
		cards.add(restartImprovement, RESTART_IMPROVEMENTSTRATEGY);

		// Listener for cardLayout
		comboBoxTypStrategien.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent evt) {
				CardLayout cl = (CardLayout) cards.getLayout();
				cl.show(cards, (String) evt.getItem());
			}
		});
		/**
		 * set the layout for the content pane
		 */
		GroupLayout gl_comboBoxPane = new GroupLayout(comboBoxPane);
		gl_comboBoxPane.setHorizontalGroup(gl_comboBoxPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_comboBoxPane
						.createSequentialGroup()
						.addGap(6)
						.addComponent(lblTypStrategien)
						.addGap(92)
						.addComponent(comboBoxTypStrategien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(6).addComponent(chckbxActivateStrategie)));
		gl_comboBoxPane.setVerticalGroup(gl_comboBoxPane
				.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_comboBoxPane.createSequentialGroup().addGap(10).addComponent(lblTypStrategien))
				.addGroup(
						gl_comboBoxPane
								.createSequentialGroup()
								.addGap(5)
								.addComponent(comboBoxTypStrategien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_comboBoxPane.createSequentialGroup().addGap(9).addComponent(chckbxActivateStrategie)));
		comboBoxPane.setLayout(gl_comboBoxPane);

		/**
		 * set the layout for the Restart improvement Error Strategy card
		 */
		GroupLayout gl_restartImprovement = new GroupLayout(restartImprovement);
		gl_restartImprovement.setHorizontalGroup(gl_restartImprovement.createParallelGroup(Alignment.LEADING).addGroup(
				gl_restartImprovement
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_restartImprovement.createParallelGroup(Alignment.LEADING).addComponent(lblMinImprovementFor)
										.addComponent(lblItearation))
						.addGap(42)
						.addGroup(
								gl_restartImprovement
										.createParallelGroup(Alignment.LEADING)
										.addComponent(spinnerImprIterationsForRestart, GroupLayout.PREFERRED_SIZE, 60,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(spinnerMinImprovementForRestart, GroupLayout.PREFERRED_SIZE, 60,
												GroupLayout.PREFERRED_SIZE)).addContainerGap(75, Short.MAX_VALUE)));
		gl_restartImprovement.setVerticalGroup(gl_restartImprovement.createParallelGroup(Alignment.LEADING).addGroup(
				gl_restartImprovement
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_restartImprovement
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblMinImprovementFor)
										.addComponent(spinnerMinImprovementForRestart, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_restartImprovement
										.createParallelGroup(Alignment.LEADING)
										.addComponent(lblItearation)
										.addComponent(spinnerImprIterationsForRestart, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		restartImprovement.setLayout(gl_restartImprovement);
		/**
		 * set the layout for Restart Error Strategy card
		 */
		GroupLayout gl_restartError = new GroupLayout(restartError);
		gl_restartError.setHorizontalGroup(gl_restartError.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_restartError
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_restartError.createParallelGroup(Alignment.LEADING).addComponent(lblMaxErrorFor)
												.addComponent(lblIterationsForRestart))
								.addGap(47)
								.addGroup(
										gl_restartError
												.createParallelGroup(Alignment.LEADING)
												.addComponent(spinnerMaxErrorForRestart, GroupLayout.PREFERRED_SIZE, 60,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(spinnerIterationsForRestart, GroupLayout.PREFERRED_SIZE, 60,
														GroupLayout.PREFERRED_SIZE)).addContainerGap(111, Short.MAX_VALUE)));
		gl_restartError.setVerticalGroup(gl_restartError.createParallelGroup(Alignment.LEADING).addGroup(
				gl_restartError
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_restartError
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblMaxErrorFor)
										.addComponent(spinnerMaxErrorForRestart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_restartError
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblIterationsForRestart)
										.addComponent(spinnerIterationsForRestart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		restartError.setLayout(gl_restartError);

		/**
		 * Set the Layout for MinError Strategy Card
		 */
		GroupLayout gl_minError = new GroupLayout(minError);
		gl_minError.setHorizontalGroup(gl_minError.createParallelGroup(Alignment.LEADING).addGroup(
				gl_minError.createSequentialGroup().addContainerGap().addComponent(lblMinError).addGap(113)
						.addComponent(spinnerMinError, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addGap(120)));
		gl_minError.setVerticalGroup(gl_minError.createParallelGroup(Alignment.LEADING).addGroup(
				gl_minError
						.createSequentialGroup()
						.addGap(5)
						.addGroup(
								gl_minError
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblMinError)
										.addComponent(spinnerMinError, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))));
		minError.setLayout(gl_minError);

		/**
		 * Set the Layout for MaxIterationen Strategy Card
		 */
		GroupLayout gl_maxIter = new GroupLayout(maxIter);
		gl_maxIter.setHorizontalGroup(gl_maxIter.createParallelGroup(Alignment.LEADING).addGroup(
				gl_maxIter.createSequentialGroup().addContainerGap().addComponent(lblMaxIterations).addGap(73)
						.addComponent(spinnerMaxIterations, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addGap(127)));
		gl_maxIter.setVerticalGroup(gl_maxIter.createParallelGroup(Alignment.LEADING).addGroup(
				gl_maxIter
						.createSequentialGroup()
						.addGap(5)
						.addGroup(
								gl_maxIter
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblMaxIterations)
										.addComponent(spinnerMaxIterations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))));
		maxIter.setLayout(gl_maxIter);

		/**
		 * Set the layout for strategy panel
		 */
		GroupLayout gl_strategiePanel = new GroupLayout(strategiePanel);
		gl_strategiePanel.setHorizontalGroup(gl_strategiePanel.createParallelGroup(Alignment.LEADING)
				.addComponent(comboBoxPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(cards, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE));
		gl_strategiePanel.setVerticalGroup(gl_strategiePanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_strategiePanel.createSequentialGroup()
						.addComponent(comboBoxPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cards, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)));
		strategiePanel.setLayout(gl_strategiePanel);

		/**
		 * Set the Layout for this Panel
		 */
		setLayout(getTrainPanelLayout());

		initActions();
	}

	/**
	 * 
	 */
	private void initActions() {
		// Activate the selected Strategy
		chckbxActivateStrategie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Boolean newVal = chckbxActivateStrategie.isSelected();
				ac.doAction(Actions.SET_THE_STRATEGY, new PropertyChangeEvent(chckbxActivateStrategie, "checkbox", !newVal, newVal));
			}
		});
		comboBoxTypStrategien.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				ac.doAction(Actions.SET_THE_STRATEGY,
						new PropertyChangeEvent(comboBoxTypStrategien, "item", "", comboBoxTypStrategien.getSelectedItem()));
			}
		});

		JSpinner spinners[] = new JSpinner[] { spinnerMaxIterations, spinnerMaxIterations2, spinnerMinError, spinnerMaxErrorForRestart,
				spinnerIterationsForRestart, spinnerImprIterationsForRestart, spinnerMinImprovementForRestart };

		for (JSpinner s : spinners) {
			DefaultEditor editor = (JSpinner.DefaultEditor) s.getEditor();
			editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent evt) {
					ac.doAction(Actions.SET_THE_STRATEGY, evt);
				}
			});
		}

	}

	/**
	 * @return the grouplayout for this panel
	 */
	private LayoutManager getTrainPanelLayout() {
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
																groupLayout
																		.createSequentialGroup()
																		.addComponent(lblMomentum)
																		.addPreferredGap(ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(spinnerMomentum, GroupLayout.PREFERRED_SIZE, 60,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(lblLearnrate)
																		.addPreferredGap(ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(spinnerLearnRate, GroupLayout.PREFERRED_SIZE, 60,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(lblAlgorithm)
																		.addGap(52)
																		.addComponent(comboBoxAlgorithm, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout.createParallelGroup(Alignment.LEADING)
																		.addComponent(rdbtnOnline).addComponent(rdbtnOffline)))
										.addComponent(lblTrainingsmodus)).addContainerGap(8, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblAlgorithm)
										.addComponent(comboBoxAlgorithm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblLearnrate)
										.addComponent(spinnerLearnRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblMomentum)
										.addComponent(spinnerMomentum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblTrainingsmodus)
										.addComponent(rdbtnOnline)).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(rdbtnOffline).addGap(18)
						.addComponent(strategiePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		return groupLayout;
	}

}
