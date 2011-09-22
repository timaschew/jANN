package de.unikassel.ann.gui.sidebar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.ActionController;
import de.unikassel.ann.controller.Actions;
import de.unikassel.ann.controller.Settings;

public class TrainControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static TrainControlPanel trainControlPanelInstance;

	public static TrainControlPanel getTrainControlPanelInstance() {
		if (trainControlPanelInstance == null) {
			trainControlPanelInstance = new TrainControlPanel();
		}
		return trainControlPanelInstance;
	}

	public JButton btnSteps;
	public JButton btnTrainingsPattern;
	public JButton btnIterations;
	public JSpinner stepsSpinner;
	public JSpinner trainPatternspinner;
	public JSpinner iterationSpinner;
	public JSpinner delaySpinner;
	public JRadioButton rdbtnSteps;
	public JRadioButton rdbtnTrainPattern;
	public JRadioButton rdbtnIterations;
	public JButton btnPlay;
	public JButton btnPause;
	public JButton btnStop;

	private ActionController ac = ActionController.get();

	/**
	 * Create the frame.
	 */
	private TrainControlPanel() {

		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.trainControl"), TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		setSize(400, 235);

		btnSteps = new JButton(Settings.i18n.getString("sidebar.trainControl.btnSteps"));
		btnTrainingsPattern = new JButton(Settings.i18n.getString("sidebar.trainControl.btnTrainingsPattern"));
		btnIterations = new JButton(Settings.i18n.getString("sidebar.trainControl.btnIterations"));

		JLabel lblVerzoegerung = new JLabel(Settings.i18n.getString("sidebar.trainControl.lblVerzoegerung"));
		stepsSpinner = new JSpinner();
		trainPatternspinner = new JSpinner();
		iterationSpinner = new JSpinner();
		delaySpinner = new JSpinner();
		rdbtnSteps = new JRadioButton("");
		rdbtnTrainPattern = new JRadioButton("");
		rdbtnIterations = new JRadioButton("");

		JLabel lblMs = new JLabel(Settings.i18n.getString("sidebar.trainControl.lblMs"));
		btnPlay = new JButton(Settings.i18n.getString("sidebar.trainControl.btnPlay"));
		btnPause = new JButton(Settings.i18n.getString("sidebar.trainControl.btnPause"));
		btnStop = new JButton(Settings.i18n.getString("sidebar.trainControl.btnStop"));
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				ac.doAction(Actions.PLAY_TRAINING, new PropertyChangeEvent(btnPlay, "button", 0, 1));
			}
		});
		GroupLayout gl_trainingSteuerungPanel = new GroupLayout(this);
		gl_trainingSteuerungPanel.setHorizontalGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_trainingSteuerungPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_trainingSteuerungPanel
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_trainingSteuerungPanel
														.createSequentialGroup()
														.addGroup(
																gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
																		.addComponent(btnSteps).addComponent(btnTrainingsPattern)
																		.addComponent(btnIterations).addComponent(lblVerzoegerung))
														.addGap(86)
														.addGroup(
																gl_trainingSteuerungPanel
																		.createParallelGroup(Alignment.TRAILING)
																		.addComponent(stepsSpinner, GroupLayout.PREFERRED_SIZE, 60,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(trainPatternspinner, GroupLayout.PREFERRED_SIZE, 60,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(iterationSpinner, GroupLayout.PREFERRED_SIZE, 60,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(delaySpinner, GroupLayout.PREFERRED_SIZE, 60,
																				GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(
																gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
																		.addComponent(rdbtnTrainPattern).addComponent(rdbtnIterations)
																		.addComponent(rdbtnSteps).addComponent(lblMs)))
										.addGroup(
												gl_trainingSteuerungPanel.createSequentialGroup().addComponent(btnPlay).addGap(18)
														.addComponent(btnPause).addGap(18).addComponent(btnStop)))
						.addContainerGap(87, Short.MAX_VALUE)));
		gl_trainingSteuerungPanel.setVerticalGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_trainingSteuerungPanel
								.createSequentialGroup()
								.addGroup(
										gl_trainingSteuerungPanel
												.createParallelGroup(Alignment.LEADING, false)
												.addGroup(
														gl_trainingSteuerungPanel
																.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		gl_trainingSteuerungPanel
																				.createParallelGroup(Alignment.LEADING)
																				.addGroup(
																						gl_trainingSteuerungPanel
																								.createParallelGroup(Alignment.BASELINE)
																								.addComponent(btnSteps)
																								.addComponent(stepsSpinner,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE))
																				.addComponent(rdbtnSteps))
																.addPreferredGap(ComponentPlacement.RELATED)
																.addGroup(
																		gl_trainingSteuerungPanel
																				.createParallelGroup(Alignment.TRAILING)
																				.addComponent(btnTrainingsPattern)
																				.addComponent(trainPatternspinner,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(rdbtnTrainPattern))
																.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE).addComponent(rdbtnIterations))
												.addGroup(
														gl_trainingSteuerungPanel
																.createSequentialGroup()
																.addGap(71)
																.addGroup(
																		gl_trainingSteuerungPanel
																				.createParallelGroup(Alignment.TRAILING)
																				.addComponent(btnIterations)
																				.addComponent(iterationSpinner, GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(
										gl_trainingSteuerungPanel
												.createParallelGroup(Alignment.LEADING)
												.addGroup(
														gl_trainingSteuerungPanel
																.createSequentialGroup()
																.addComponent(lblVerzoegerung)
																.addGap(18)
																.addGroup(
																		gl_trainingSteuerungPanel.createParallelGroup(Alignment.BASELINE)
																				.addComponent(btnPlay).addComponent(btnPause)
																				.addComponent(btnStop)))
												.addGroup(
														gl_trainingSteuerungPanel
																.createParallelGroup(Alignment.BASELINE)
																.addComponent(delaySpinner, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(lblMs))).addContainerGap(18, Short.MAX_VALUE)));
		setLayout(gl_trainingSteuerungPanel);
	}

}
