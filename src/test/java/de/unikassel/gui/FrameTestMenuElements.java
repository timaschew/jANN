package de.unikassel.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.unikassel.ann.gui.ImportFilePanel;
import de.unikassel.ann.gui.TrainDataPanel;
import de.unikassel.ann.gui.sidebar.SideConfigurationPanel;

public class FrameTestMenuElements extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					FrameTestMenuElements frame = new FrameTestMenuElements();
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
	public FrameTestMenuElements() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnImportmenu = new JButton("Import-Menu");
		btnImportmenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				ImportFilePanel panel = new ImportFilePanel();
				panel.pack();
				panel.setVisible(true);
			}
		});
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		contentPane.add(btnImportmenu);

		JButton btnTrainingsDatenNormalisieren = new JButton("TrainingsDaten normalisieren");
		btnTrainingsDatenNormalisieren.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				TrainDataPanel datenPanel = TrainDataPanel.getTrainDataPanelInstance();
				datenPanel.pack();
				datenPanel.setVisible(true);
			}
		});
		contentPane.add(btnTrainingsDatenNormalisieren);

		SideConfigurationPanel test = new SideConfigurationPanel();
		contentPane.add(test);
	}

}
