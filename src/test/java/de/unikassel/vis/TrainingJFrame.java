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


public class TrainingJFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainingJFrame frame = new TrainingJFrame();
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
	public TrainingJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Training", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel);
		
		JLabel lblAlgorithmus = new JLabel("Algorithmus");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Backpropagation", "Test"}));
		
		JLabel lblLernrate = new JLabel("Lernrate");
		
		JSpinner spinner = new JSpinner();
		
		JLabel lblMomentum = new JLabel("Momentum");
		
		JSpinner spinner_1 = new JSpinner();
		
		JLabel lblTrainingsmodus = new JLabel("Trainingsmodus");
		
		JRadioButton rdbtnOnline = new JRadioButton("Online");
		rdbtnOnline.setSelected(true);
		
		JRadioButton rdbtnOffline = new JRadioButton("Offline");
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblLernrate, Alignment.LEADING)
						.addComponent(lblMomentum, Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAlgorithmus)
								.addComponent(lblTrainingsmodus))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnOffline, Alignment.TRAILING)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
									.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
										.addGap(7)
										.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
											.addComponent(spinner_1, Alignment.TRAILING)
											.addComponent(spinner, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
											.addComponent(rdbtnOnline)))))))
					.addGap(174))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAlgorithmus)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLernrate)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMomentum)
								.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(28)
									.addComponent(rdbtnOffline))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addComponent(lblTrainingsmodus))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(98)
							.addComponent(rdbtnOnline)))
					.addContainerGap(81, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}

}
