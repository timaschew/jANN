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


public class Trainingsdaten extends JPanel {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Trainingsdaten panel = new Trainingsdaten();
//					
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
	public Trainingsdaten() {
//		setTitle("Trainingsdaten normalisieren\r\n");
////		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 321, 281);
//		contentPane = new JPanel();
		setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
		
//		JPanel panel = new JPanel();
//		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblWhat = new JLabel("Was");
		
		JButton btnShowTrainingsdata = new JButton("Trainingsdaten anzeigen");
		
		JLabel lblNormalize = new JLabel("Normalisieren");
		
		JLabel lblfrom = new JLabel("von");
		
		JLabel lblUp = new JLabel("bis");
		
		JLabel lblInput = new JLabel("Input");
		
		JLabel lblOutput = new JLabel("Output");
		
		JCheckBox checkBoxInput = new JCheckBox("");
		
		JCheckBox checkBoxOutput = new JCheckBox("");
		
		JSpinner spinnerInputFrom = new JSpinner();
		
		JSpinner spinnerInputUp = new JSpinner();
		
		JSpinner spinnerOutputFrom = new JSpinner();
		
		JSpinner spinnerOutputUp = new JSpinner();
		
		JLabel lblInvertOutput = new JLabel("Ausgabe wieder umkehren");
		
		JRadioButton rdbtnInvertOutputYes = new JRadioButton("Ja");
		rdbtnInvertOutputYes.setSelected(true);
		
		JRadioButton rdbtnInvertOutputNo = new JRadioButton("Nein");
		
		JButton btnPreviewData = new JButton("Vorschau");
		
		JButton btnApplyData = new JButton("Anwenden");
		GroupLayout gl_panel = new GroupLayout(this);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnShowTrainingsdata)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblInput)
										.addComponent(lblOutput))
									.addGap(47)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(checkBoxOutput)
										.addComponent(checkBoxInput)))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblWhat)
									.addGap(41)
									.addComponent(lblNormalize))
								.addComponent(lblInvertOutput)
								.addComponent(btnPreviewData))
							.addGap(13)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnApplyData)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(rdbtnInvertOutputYes, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
											.addComponent(spinnerOutputFrom, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
											.addComponent(spinnerInputFrom, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblfrom)))
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(14)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(spinnerInputUp, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblUp)
												.addComponent(spinnerOutputUp, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(rdbtnInvertOutputNo)))))))
					.addContainerGap(205, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addComponent(btnShowTrainingsdata)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWhat)
						.addComponent(lblNormalize)
						.addComponent(lblfrom)
						.addComponent(lblUp))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblInput)
						.addComponent(checkBoxInput)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(spinnerInputFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(spinnerInputUp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblOutput)
							.addComponent(checkBoxOutput))
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(spinnerOutputFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(spinnerOutputUp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInvertOutput)
						.addComponent(rdbtnInvertOutputYes)
						.addComponent(rdbtnInvertOutputNo))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPreviewData)
						.addComponent(btnApplyData))
					.addContainerGap(90, Short.MAX_VALUE))
		);
		setLayout(gl_panel);
	}

}
