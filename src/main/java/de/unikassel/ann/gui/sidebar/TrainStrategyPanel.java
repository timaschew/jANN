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
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.io.tasks.TrainWorker;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.strategy.MaxLearnIterationsStrategy;
import de.unikassel.ann.strategy.MinErrorStrategy;
import de.unikassel.ann.strategy.RestartErrorStrategy;
import de.unikassel.ann.strategy.RestartImprovementStrategy;
import de.unikassel.ann.strategy.Strategy;
import de.unikassel.ann.util.Logger;

public class TrainStrategyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox comboBoxAlgorithm;
	public JSpinner spinnerLearnRate;
	public JSpinner spinnerMomentum;
	public JCheckBox chbBatchMode;

	// CardLayout Swing-Elements
	private JPanel strategiePanel;
	private JComboBox comboBoxTypStrategien;
	private JCheckBox chckbxActivateStrategie;

	private JLabel lblAlgorithm;
	private JLabel lblLearnrate;
	private JLabel lblMomentum;
	private JLabel lblTrainingsmodus;
	private JLabel lblTypStrategien;

	// Cards Panes
	private JPanel maxIter;
	private JPanel minError;
	private JPanel restartError;
	private JPanel restartImprovement;

	// Cards panes elements
	private JPanel comboBoxPane;
	private JPanel cards;
	private JSpinner spinnerMaxIterations;
	private JSpinner spinnerMinError;
	private JSpinner spinnerMaxErrorForRestart;
	private JSpinner spinnerIterationsForRestart;
	private JSpinner spinnerImprIterationsForRestart;
	private JSpinner spinnerMinImprovementForRestart;
	public JButton btnStartTraining;

	final static String MAX_ITERATIONSTRATEGY = "MaxIteration";
	final static String MIN_ERRORSTRATEGY = "MinError";
	final static String RESTART_ERRORSTRATEGY = "RestartError";
	final static String RESTART_IMPROVEMENTSTRATEGY = "RestartImprovement";
	private JLabel lblDelay;
	private JSpinner delaySpinner;
	private JButton btnReset;

	/**
	 * Create the panel.
	 */
	public TrainStrategyPanel() {

		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.trainingsStrategy"), TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		setSize(400, 375);

		lblAlgorithm = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.algorithm"));
		lblLearnrate = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.learnRate"));
		lblMomentum = new JLabel(Settings.i18n.getString("sidebar.trainingsStretegy.momentum"));

		comboBoxAlgorithm = new JComboBox();
		comboBoxAlgorithm.setModel(new DefaultComboBoxModel(new String[] { "Backpropagation" }));

		spinnerLearnRate = new JSpinner();
		spinnerLearnRate.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));
		spinnerMomentum = new JSpinner();
		spinnerMomentum.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));

		lblTrainingsmodus = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.trainingmodus"));

		chbBatchMode = new JCheckBox();
		chbBatchMode.setText(Settings.i18n.getString("sidebar.trainingsStrategy.onlineRB"));
		chbBatchMode.setSelected(true);

		strategiePanel = new JPanel();
		strategiePanel.setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.trainingsStrategy.strategy"),
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		lblTypStrategien = new JLabel(Settings.i18n.getString("sidebar.trainingsStrategy.strategy.type"));
		String comboBoxItems[] = { MAX_ITERATIONSTRATEGY, MIN_ERRORSTRATEGY, RESTART_ERRORSTRATEGY, RESTART_IMPROVEMENTSTRATEGY };
		comboBoxTypStrategien = new JComboBox(comboBoxItems);
		chckbxActivateStrategie = new JCheckBox("");
		btnStartTraining = new JButton(Settings.i18n.getString("sidebar.trainingsStrategy.strategy.start"));

		comboBoxPane = new JPanel();
		comboBoxPane.setSize(200, 100);
		comboBoxTypStrategien.setEditable(false);

		cards = new JPanel(new CardLayout());

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

		lblDelay = new JLabel(Settings.i18n.getString("sidebar.trainControl.lblVerzoegerung"));

		delaySpinner = new JSpinner();
		delaySpinner.setEnabled(false);

		JLabel lblMs = new JLabel("ms");
		GroupLayout gl_maxIter = new GroupLayout(maxIter);
		gl_maxIter.setHorizontalGroup(gl_maxIter.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_maxIter
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_maxIter
												.createParallelGroup(Alignment.LEADING, false)
												.addComponent(lblMaxIterations, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(lblDelay, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
								.addGap(18)
								.addGroup(
										gl_maxIter.createParallelGroup(Alignment.LEADING, false).addComponent(delaySpinner)
												.addComponent(spinnerMaxIterations, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblMs, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(47, Short.MAX_VALUE)));
		gl_maxIter.setVerticalGroup(gl_maxIter.createParallelGroup(Alignment.LEADING).addGroup(
				gl_maxIter
						.createSequentialGroup()
						.addGap(5)
						.addGroup(
								gl_maxIter
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblMaxIterations)
										.addComponent(spinnerMaxIterations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_maxIter
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(delaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblMs, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDelay)).addGap(14)));
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
				updatePanel(newVal);
			}

		});
		comboBoxTypStrategien.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updatePanel(null, true);
			}
		});

		JSpinner spinners[] = new JSpinner[] { spinnerMaxIterations, spinnerMinError, spinnerMaxErrorForRestart,
				spinnerIterationsForRestart, spinnerImprIterationsForRestart, spinnerMinImprovementForRestart };

		for (JSpinner s : spinners) {
			DefaultEditor editor = (JSpinner.DefaultEditor) s.getEditor();
			editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent evt) {
					updatePanel(null, true);
				}
			});
		}

		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NetConfig net = Settings.getInstance().getCurrentSession().getNetworkConfig();
				StandardOptionsPanel panel = Main.instance.sidebar.standardOptionsPanel;
				double min = panel.getInitialWeightMin();
				double max = panel.getInitialWeightMax();
				net.setInitMaxWeight(max);
				net.setInitMinWeight(min);
				net.initWeights();
				GraphController.getInstance().repaint();
			}
		});

		btnStartTraining.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NetConfig net = Settings.getInstance().getCurrentSession().getNetworkConfig();

				if (net.getTrainingData() == null || net.getTrainingData().getRows() == 0) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Es existieren keine Trainingsdaten ", "Warnung", JOptionPane.WARNING_MESSAGE);
				} else {
					// copy of train data
					DataPairSet trainingData = new DataPairSet(net.getTrainingData());

					int trainInLenght = trainingData.getPairs().get(0).getInput().length;
					int trainOutLenght = trainingData.getPairs().get(0).getIdeal().length;
					int netInLenght = net.getNetwork().getInputSizeIgnoringBias();
					int netOutLength = net.getNetwork().getOutputSize();
					if (trainInLenght != netInLenght || trainOutLenght != netOutLength) {
						Logger.warn(this.getClass(), "Trainingsdaten {} Input {} Output passten nicht zur Topology {} Input und {} Output",
								trainInLenght, trainOutLenght, netInLenght, netOutLength);
						return;
					}

					BackPropagation train = (BackPropagation) net.getTrainingModule();
					Double learnRate = (Double) Main.instance.sidebar.trainStrategyPanel.spinnerLearnRate.getValue();
					Double momentum = (Double) Main.instance.sidebar.trainStrategyPanel.spinnerMomentum.getValue();
					Boolean batchMode = Main.instance.sidebar.trainStrategyPanel.chbBatchMode.isSelected();
					train.setBatchMode(batchMode);
					train.setLearnRate(learnRate);
					train.setMomentum(momentum);

					// Start worker
					TrainWorker trainWorker = new TrainWorker(net, train, trainingData);
					TrainGuiUpdater jungUpdateWorker = new TrainGuiUpdater(trainWorker);
					jungUpdateWorker.execute();
					trainWorker.execute();
					btnStartTraining.setEnabled(false);

				}
			}
		});

	}

	/**
	 * @return the grouplayout for this panel
	 */
	private LayoutManager getTrainPanelLayout() {

		btnReset = new JButton("Gewichte initialisieren");
		// Layout TrainStrategy Pane
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										groupLayout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(
														groupLayout
																.createParallelGroup(Alignment.TRAILING, false)
																.addGroup(
																		groupLayout
																				.createSequentialGroup()
																				.addComponent(lblMomentum)
																				.addPreferredGap(ComponentPlacement.RELATED,
																						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(spinnerMomentum, GroupLayout.PREFERRED_SIZE,
																						60, GroupLayout.PREFERRED_SIZE))
																.addGroup(
																		groupLayout
																				.createSequentialGroup()
																				.addComponent(lblLearnrate)
																				.addPreferredGap(ComponentPlacement.RELATED,
																						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(spinnerLearnRate, GroupLayout.PREFERRED_SIZE,
																						60, GroupLayout.PREFERRED_SIZE))
																.addGroup(
																		groupLayout
																				.createSequentialGroup()
																				.addComponent(lblAlgorithm)
																				.addGap(52)
																				.addComponent(comboBoxAlgorithm,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGroup(
																		groupLayout.createSequentialGroup().addComponent(chbBatchMode)
																				.addGap(1)))
												.addComponent(lblTrainingsmodus)
												.addComponent(strategiePanel, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
												.addGroup(
														groupLayout.createSequentialGroup().addComponent(btnStartTraining).addGap(18)
																.addComponent(btnReset))).addContainerGap(8, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(
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
												.addComponent(chbBatchMode))
								.addGap(18)
								.addComponent(strategiePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnStartTraining)
												.addComponent(btnReset)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		return groupLayout;
	}

	public void updatePanel() {
		updatePanel(null, false);
	}

	private void updatePanel(final Boolean valueFiredFromCheckBox) {
		updatePanel(valueFiredFromCheckBox, true);
	}

	private void updatePanel(final Boolean valueFiredFromCheckBox, final boolean updateFromOwnPanel) {

		/*
		 * update learnRate, momentum, batchTraiing
		 */
		NetConfig net = Settings.getInstance().getCurrentSession().getNetworkConfig();
		BackPropagation train = (BackPropagation) net.getTrainingModule();
		chbBatchMode.setSelected(train.isBatchMode());
		spinnerLearnRate.setValue(train.getLearnRate());
		spinnerMomentum.setValue(train.getMomentum());

		// "MaxIteration", "MinError", "RestartError","RestartImprovement"
		String selectedStrategy = (String) comboBoxTypStrategien.getSelectedItem();
		Strategy strategy;
		List<Strategy> strategyList = net.getStrategies();
		Boolean checkBoxValue = valueFiredFromCheckBox;

		if (selectedStrategy.equals("MaxIteration")) {
			strategy = checkIfExist(strategyList, MaxLearnIterationsStrategy.class);
			updateCBforCombo(strategy);
			Integer maxIterationSpinner = (Integer) spinnerMaxIterations.getValue();
			if (strategy == null) {
				strategy = new MaxLearnIterationsStrategy();
			}
			if (updateFromOwnPanel) {
				((MaxLearnIterationsStrategy) strategy)._maxIteration = maxIterationSpinner;
			} else {
				spinnerMaxIterations.setValue(((MaxLearnIterationsStrategy) strategy)._maxIteration);
			}

			updateStrategy(strategy, net, strategyList, checkBoxValue);

		} else if (selectedStrategy.equals("MinError")) {
			strategy = checkIfExist(strategyList, MinErrorStrategy.class);
			updateCBforCombo(strategy);
			Double minErrorSpinner = (Double) spinnerMinError.getValue();
			if (strategy == null) {
				strategy = new MinErrorStrategy();

			}
			((MinErrorStrategy) strategy)._minerror = minErrorSpinner;
			updateStrategy(strategy, net, strategyList, checkBoxValue);

		} else if (selectedStrategy.equals("RestartError")) {

			strategy = checkIfExist(strategyList, RestartErrorStrategy.class);
			updateCBforCombo(strategy);
			Double maxErrorForRestartSpinner = (Double) spinnerMaxErrorForRestart.getValue();
			Integer iterationsForRestartSpinner = (Integer) spinnerIterationsForRestart.getValue();
			if (strategy == null) {
				strategy = new RestartErrorStrategy();
			}
			((RestartErrorStrategy) strategy)._iterationForRestart = iterationsForRestartSpinner;
			((RestartErrorStrategy) strategy)._error = maxErrorForRestartSpinner;
			updateStrategy(strategy, net, strategyList, checkBoxValue);

		} else if (selectedStrategy.equals("RestartImprovement")) {

			strategy = checkIfExist(strategyList, RestartImprovementStrategy.class);
			updateCBforCombo(strategy);
			Double minImprovForRestartSpinner = (Double) spinnerMinImprovementForRestart.getValue();
			Integer iterImprForRestartSpinner = (Integer) spinnerImprIterationsForRestart.getValue();
			if (strategy == null) {
				strategy = new RestartImprovementStrategy();
			}
			((RestartImprovementStrategy) strategy)._minimalImprovement = minImprovForRestartSpinner;
			((RestartImprovementStrategy) strategy)._iterationForRestart = iterImprForRestartSpinner;
			updateStrategy(strategy, net, strategyList, checkBoxValue);
		}
	}

	private void updateStrategy(final Strategy strategy, final NetConfig net, final List<Strategy> strategyList, final Boolean checkBoxValue) {
		if (checkBoxValue != null) {
			if (checkBoxValue) {
				net.addOrUpdateExisting(strategy);
				chckbxActivateStrategie.setSelected(true);
			} else {
				strategyList.remove(strategy);
				chckbxActivateStrategie.setSelected(false);
			}
		}
	}

	/**
	 * @param sidebar
	 * @param strategy
	 */
	private void updateCBforCombo(final Strategy strategy) {
		if (strategy != null) {
			chckbxActivateStrategie.setSelected(true);
		} else {
			chckbxActivateStrategie.setSelected(false);
		}
	}

	/**
	 * @param strategyList
	 * @param class1
	 * @return
	 */
	private Strategy checkIfExist(final List<Strategy> strategyList, final Class<? extends Strategy> clazz) {
		for (Strategy s : strategyList) {
			if (s.getClass().equals(clazz)) {
				return s;
			}
		}
		return null;
	}
}
