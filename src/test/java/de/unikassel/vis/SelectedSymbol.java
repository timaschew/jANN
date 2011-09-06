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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;


public class SelectedSymbol extends JPanel {

	private JPanel contentPane;
	private JTextField fieldAusgewaehlt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectedSymbol panel = new SelectedSymbol();
					JFrame frame = new JFrame();
					frame.setContentPane(panel);
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
	public SelectedSymbol() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 294, 225);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//		
//		JPanel SelectedSymbolPanel = new JPanel();
		setBorder(new TitledBorder(null, "Ausgew\u00E4hltes Symbol", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		contentPane.add(SelectedSymbolPanel, BorderLayout.CENTER);
		
		JLabel lblAusgewhlt = new JLabel("Ausgew\u00E4hlt");
		
		JLabel lblSynapsengewicht = new JLabel("Synapsengewicht");
		
		JLabel lblNeuroneninput = new JLabel("Neuroneninput");
		
		JLabel lblAktivierungsfunktion = new JLabel("Aktivierungsfunktion");
		
		fieldAusgewaehlt = new JTextField();
		fieldAusgewaehlt.setEditable(false);
		fieldAusgewaehlt.setColumns(10);
		
		JComboBox aktivFunktioncomboBox = new JComboBox();
		aktivFunktioncomboBox.setModel(new DefaultComboBoxModel(new String[] {"Sigmoid", "Test"}));
		
		JSpinner neuroInputBeiAuswahlSpinner = new JSpinner();
		neuroInputBeiAuswahlSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		
		JSpinner spinner = new JSpinner();
		GroupLayout gl_SelectedSymbolPanel = new GroupLayout(this);
		gl_SelectedSymbolPanel.setHorizontalGroup(
			gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SelectedSymbolPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAktivierungsfunktion)
						.addComponent(lblNeuroneninput)
						.addComponent(lblAusgewhlt)
						.addComponent(lblSynapsengewicht))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(aktivFunktioncomboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(neuroInputBeiAuswahlSpinner)
						.addComponent(spinner)
						.addComponent(fieldAusgewaehlt))
					.addContainerGap(187, Short.MAX_VALUE))
		);
		gl_SelectedSymbolPanel.setVerticalGroup(
			gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SelectedSymbolPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAusgewhlt)
						.addComponent(fieldAusgewaehlt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSynapsengewicht)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNeuroneninput)
						.addComponent(neuroInputBeiAuswahlSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAktivierungsfunktion)
						.addComponent(aktivFunktioncomboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(135, Short.MAX_VALUE))
		);
		setLayout(gl_SelectedSymbolPanel);
	}

}
