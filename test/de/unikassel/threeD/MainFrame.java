package de.unikassel.threeD;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -6640029182568522067L;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				
				MainFrame m = new MainFrame();
				m.setLayout(new BorderLayout(5, 10));
//				m.add(new Board());
				m.add(new Board3D(), BorderLayout.CENTER);
				
				JPanel panel = buildToolBox();
				m.add(panel, BorderLayout.PAGE_START);
				
				m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				m.setSize(400, 300);
				m.setLocationRelativeTo(null);
				m.setTitle("Testing Board");
				m.setResizable(true);
				m.setVisible(true);

			}

			private JPanel buildToolBox() {
				JPanel p = new JPanel();
				JButton startStopButton = new JButton("start/stop");
				startStopButton.setActionCommand(""+Action.START_STOP);
				startStopButton.addActionListener(Board3D.instance);
				p.add(startStopButton);
				
				return p;
			}
		});
	}

}
