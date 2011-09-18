package de.unikassel.ann.gui.sidebar;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TrainControlPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	private static TrainControlPanel trainControlPanelInstance;
	

	public static TrainControlPanel getTrainControlPanelInstance() {
		if (trainControlPanelInstance == null) {
			trainControlPanelInstance = new TrainControlPanel();
		}
		return trainControlPanelInstance;
	}

	private JButton btnSchritte;
	private JButton btnTrainingsPattern;
	private JButton btnIterationen;
	private JSpinner stepsSpinner;
	private JSpinner trainPatternspinner;
	private JSpinner iterationSpinner;
	private JSpinner delaySpinner;
	private JRadioButton rdbtnSteps;
	private JRadioButton rdbtnTrainPattern;
	private JRadioButton rdbtnIterations;
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnStop;

	/**
	 * Create the frame.
	 */
	private TrainControlPanel() {
		
		setBorder(new TitledBorder(null, "Training-Steuerung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(400, 235);
		
		btnSchritte = new JButton("Schritt(e)");
		btnTrainingsPattern = new JButton("Trainingspattern");
		btnIterationen = new JButton("Iterationen");
		
		JLabel lblVerzoegerung = new JLabel("Verz\u00F6gerung");
		stepsSpinner = new JSpinner();
		trainPatternspinner = new JSpinner();
		iterationSpinner = new JSpinner();
		delaySpinner = new JSpinner();
		rdbtnSteps = new JRadioButton("");
		rdbtnTrainPattern = new JRadioButton("");
		rdbtnIterations = new JRadioButton("");
		
		JLabel lblMs = new JLabel("ms");
		btnPlay = new JButton("Play");
		btnPause = new JButton("Pause");
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GroupLayout gl_trainingSteuerungPanel = new GroupLayout(this);
		gl_trainingSteuerungPanel.setHorizontalGroup(
			gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
							.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSchritte)
								.addComponent(btnTrainingsPattern)
								.addComponent(btnIterationen)
								.addComponent(lblVerzoegerung))
							.addGap(86)
							.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(stepsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(trainPatternspinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(iterationSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(delaySpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnTrainPattern)
								.addComponent(rdbtnIterations)
								.addComponent(rdbtnSteps)
								.addComponent(lblMs)))
						.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
							.addComponent(btnPlay)
							.addGap(18)
							.addComponent(btnPause)
							.addGap(18)
							.addComponent(btnStop)))
					.addContainerGap(106, Short.MAX_VALUE))
		);
		gl_trainingSteuerungPanel.setVerticalGroup(
			gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
					.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnSchritte)
									.addComponent(stepsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(rdbtnSteps))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnTrainingsPattern)
								.addComponent(trainPatternspinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnTrainPattern))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(rdbtnIterations))
						.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
							.addGap(71)
							.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnIterationen)
								.addComponent(iterationSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
							.addComponent(lblVerzoegerung)
							.addGap(18)
							.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnPlay)
								.addComponent(btnPause)
								.addComponent(btnStop)))
						.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(delaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblMs)))
					.addContainerGap(68, Short.MAX_VALUE))
		);
		setLayout(gl_trainingSteuerungPanel);
	}

}
