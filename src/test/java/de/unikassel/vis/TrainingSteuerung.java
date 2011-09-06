package de.unikassel.vis;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
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


public class TrainingSteuerung extends JPanel {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainingSteuerung panel = new TrainingSteuerung();
					panel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrainingSteuerung() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 265, 255);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
//		JPanel trainingSteuerungPanel = new JPanel();
		setBorder(new TitledBorder(null, "Training-Steuerung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		add(trainingSteuerungPanel);
		
		JButton btnSchritte = new JButton("Schritt(e)");
		
		JButton btnTrainingsPattern = new JButton("Trainingspattern");
		
		JButton btnIterationen = new JButton("Iterationen");
		
		JLabel lblVerzoegerung = new JLabel("Verz\u00F6gerung");
		
		JSpinner spinner = new JSpinner();
		
		JSpinner spinner_1 = new JSpinner();
		
		JSpinner spinner_2 = new JSpinner();
		
		JSpinner spinner_3 = new JSpinner();
		
		JRadioButton rdbtnSchritte = new JRadioButton("");
		
		JRadioButton rdbtnTraininingsPattern = new JRadioButton("");
		
		JRadioButton rdbtnIterationen = new JRadioButton("");
		
		JLabel lblMs = new JLabel("ms");
		
		JButton btnPlay = new JButton("Play");
		
		JButton btnPause = new JButton("Pause");
		
		JButton btnStop = new JButton("Stop");
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
								.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
									.addGap(10)
									.addComponent(lblVerzoegerung)))
							.addGap(26)
							.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
									.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rdbtnTraininingsPattern))
								.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rdbtnSchritte))
								.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
									.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(spinner_3)
										.addComponent(spinner_2, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
									.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
											.addGap(10)
											.addComponent(lblMs))
										.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(rdbtnIterationen))))))
						.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
							.addComponent(btnPlay)
							.addGap(10)
							.addComponent(btnPause)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnStop)))
					.addContainerGap(171, Short.MAX_VALUE))
		);
		gl_trainingSteuerungPanel.setVerticalGroup(
			gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_trainingSteuerungPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSchritte)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnSchritte))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnTrainingsPattern)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(rdbtnTraininingsPattern))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnIterationen)
							.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(rdbtnIterationen))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMs)
						.addComponent(lblVerzoegerung))
					.addGap(18)
					.addGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPlay)
						.addComponent(btnPause)
						.addComponent(btnStop))
					.addContainerGap(51, Short.MAX_VALUE))
		);
		setLayout(gl_trainingSteuerungPanel);
	}

}
