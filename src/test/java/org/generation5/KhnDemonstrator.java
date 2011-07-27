package org.generation5;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class KhnDemonstrator extends JApplet {
	private Kohonen kohonen = new Kohonen();
	private KohonenPanel panel = new KohonenPanel();

	final private int ITER1_250 = 250;
	final private int ITER2_100 = 100;
	private static final int INTER2_INNER_75 = 75;
	private static final long WAIT = 20;


	JButton startButton = new JButton("Start Training");
	JButton restartButton = new JButton("Restart");
//	boolean m_bTrainingRunning;
	KhnDemonstrator.TrainingThread tt = null;

	KhnDemonstrator.ThreadListener threadListener = new KhnDemonstrator.ThreadListener();
	KhnDemonstrator.ResetListener resetListener = new KhnDemonstrator.ResetListener();

	public void init() {
		Container localContainer = getContentPane();

		localContainer.setLayout(new BorderLayout());
		localContainer.add(this.panel, "Center");

		JPanel localJPanel = new JPanel();
		localContainer.add(localJPanel, "South");
		localJPanel.add(this.startButton);
		localJPanel.add(this.restartButton);

		this.startButton.addActionListener(this.threadListener);
		this.restartButton.addActionListener(this.resetListener);

		this.kohonen.setDimensions(2, 12, 12);
		this.kohonen.init();

		this.panel.setBorder(new EtchedBorder());
		this.panel.setKohonen(this.kohonen, 12, 12);
	}

	void RunTraining(Kohonen paramKohonen, KohonenPanel paramKohonenPanel) {
		System.err.println("starting run");
		int neighborRadius = 6;
		double factor = 0.9D;
		double factorDecrementor;
		int i;
		int k;
		double inp1;
		double inp2;
		double inp3;
		if (ITER1_250 > 0) {
			factorDecrementor = 0.8D / ITER1_250; // = 0.0032

			for (i = 0; i < ITER1_250; i++) {
//				neighborRadius = 6;
				for (k = 0; k < 50; k++) {
					inp1 = Math.random() * 2.0D - 1.0D; // -1 / +1
					inp2 = Math.random() * 2.0D - 1.0D; // -1 / +1
					inp3 = Math.random() * 2.0D - 1.0D; // -1 / +1

					paramKohonen.run(factor, neighborRadius, inp1, inp2);
					
				}

				factor -= factorDecrementor;
				
				// TODO: sinnlos, weil immer != 0
				// 6/5/4/3/2/1 % 62 != 0
//				if ((ITER1_250 > 4) && (neighborRadius % (ITER1_250 / 4) != 0)) {
//					neighborRadius--;
//				}
				neighborRadius--;

				paramKohonenPanel.setPhaseInfo(1, i);
				paramKohonenPanel.repaint();
				try {
					Thread.sleep(WAIT);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.err.println("finished part 1");
		}
		
		
		// 2nd phase
		// neighbor = 1
		if (this.ITER2_100 > 0) {
			factor = 0.1D; 
			factorDecrementor = 0.1F / this.ITER2_100; // 0.08

			for (i = 0; i < this.ITER2_100; i++) {
				for (k = 0; k < INTER2_INNER_75; k++) {
					inp1 = Math.random() * 2.0D - 1.0D;
					inp2 = Math.random() * 2.0D - 1.0D;
					inp3 = Math.random() * 2.0D - 1.0D;

					paramKohonen.run(factor, 1, inp1, inp2);
				}

				factor -= factorDecrementor;

				paramKohonenPanel.setPhaseInfo(2, i);
				paramKohonenPanel.repaint();
				try {
					Thread.sleep(WAIT);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.err.println("finished part 2");
		}
		

	}

	class ResetListener implements ActionListener {
		ResetListener() {
		}

		public void actionPerformed(ActionEvent paramActionEvent) {
			KhnDemonstrator.this.kohonen.init();
			KhnDemonstrator.this.panel.setPhaseInfo(0, 0);
			KhnDemonstrator.this.panel.repaint();
		}
	}

	class ThreadListener implements ActionListener {
		ThreadListener() {
		}

		public void actionPerformed(ActionEvent paramActionEvent) {
			KhnDemonstrator.this.tt = new KhnDemonstrator.TrainingThread(
					KhnDemonstrator.this);
		}
	}

	private class TrainingThread extends Thread {
		TrainingThread(KhnDemonstrator khnDemonstrator) {
			start();
		}

		public void run() {
			KhnDemonstrator.this.RunTraining(KhnDemonstrator.this.kohonen,
					KhnDemonstrator.this.panel);
		}
	}
}