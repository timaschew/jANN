package de.unikassel.vis;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Dialog.ModalExclusionType;


public class Trainingsdaten extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Trainingsdaten frame = new Trainingsdaten();
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
	public Trainingsdaten() {
		setTitle("Trainingsdaten normalisieren\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 321, 281);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblWas = new JLabel("Was");
		
		JButton btnTrainingsdatenAnzeigen = new JButton("Trainingsdaten anzeigen");
		
		JLabel lblNormalisieren = new JLabel("Normalisieren");
		
		JLabel lblVon = new JLabel("von");
		
		JLabel lblBis = new JLabel("bis");
		
		JLabel lblInput = new JLabel("Input");
		
		JLabel lblOutput = new JLabel("Output");
		
		JCheckBox checkBox = new JCheckBox("");
		
		JCheckBox checkBox_1 = new JCheckBox("");
		
		JSpinner spinner = new JSpinner();
		
		JSpinner spinner_1 = new JSpinner();
		
		JSpinner spinner_2 = new JSpinner();
		
		JSpinner spinner_3 = new JSpinner();
		
		JLabel lblAusgabeWiederUmkehren = new JLabel("Ausgabe wieder umkehren");
		
		JRadioButton rdbtnJa = new JRadioButton("Ja");
		rdbtnJa.setSelected(true);
		
		JRadioButton rdbtnNein = new JRadioButton("Nein");
		
		JButton btnVorschau = new JButton("Vorschau");
		
		JButton btnAnwenden = new JButton("Anwenden");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnTrainingsdatenAnzeigen)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblInput)
										.addComponent(lblOutput))
									.addGap(47)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(checkBox_1)
										.addComponent(checkBox)))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblWas)
									.addGap(41)
									.addComponent(lblNormalisieren))
								.addComponent(lblAusgabeWiederUmkehren)
								.addComponent(btnVorschau))
							.addGap(13)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAnwenden)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(rdbtnJa, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
											.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblVon)))
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(14)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblBis)
												.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(rdbtnNein)))))))
					.addContainerGap(157, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addComponent(btnTrainingsdatenAnzeigen)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWas)
						.addComponent(lblNormalisieren)
						.addComponent(lblVon)
						.addComponent(lblBis))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblInput)
						.addComponent(checkBox)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblOutput)
							.addComponent(checkBox_1))
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAusgabeWiederUmkehren)
						.addComponent(rdbtnJa)
						.addComponent(rdbtnNein))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnVorschau)
						.addComponent(btnAnwenden))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}

}
