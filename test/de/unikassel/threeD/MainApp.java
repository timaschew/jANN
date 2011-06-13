package de.unikassel.threeD;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class MainApp extends JFrame {

	private static final long serialVersionUID = -6640029182568522067L;
	
	private static SpinnerNumberModel createDoubleModel() {
		return createDoubleModel(0.0, -360.0, 360.0, 1.0);
	}
	
	private static SpinnerNumberModel createDoubleModel(Double init, Double min, Double max, Double step) {
		return new SpinnerNumberModel(init, min, max, step);
	}
	
	public static void main(String[] args) {

		
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				
				MainApp m = new MainApp();
				m.setLayout(new BorderLayout(5, 10));
//				m.add(new Board());
				m.add(new Board3D(), BorderLayout.CENTER);
				
				JPanel panel = buildToolBox();
				JPanel bottomPanel = buildBottomPanel();
				m.add(panel, BorderLayout.PAGE_START);
				m.add(bottomPanel, BorderLayout.PAGE_END);
				
				m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				m.setSize(800, 600);
				m.setLocationRelativeTo(null);
				m.setTitle("Testing Board");
				m.setResizable(true);
				m.setVisible(true);

			}

			private JPanel buildBottomPanel() {
				JPanel p = new JPanel();
				
				JSpinner eyeSliderX = new JSpinner(createDoubleModel());
				JSpinner eyeSliderY = new JSpinner(createDoubleModel());
				JSpinner eyeSliderZ = new JSpinner(createDoubleModel());
				eyeSliderX.setName("eyeX");
				eyeSliderY.setName("eyeY");
				eyeSliderZ.setName("eyeZ");
				eyeSliderX.setPreferredSize(new Dimension(80, 20));
				eyeSliderY.setPreferredSize(new Dimension(80, 20));
				eyeSliderZ.setPreferredSize(new Dimension(80, 20));
				eyeSliderX.addChangeListener(Board3D.instance);
				eyeSliderY.addChangeListener(Board3D.instance);
				eyeSliderZ.addChangeListener(Board3D.instance);
				
				p.add(eyeSliderX);
				p.add(eyeSliderY);
				p.add(eyeSliderZ);
				
				JSpinner camXSpinner = new JSpinner(createDoubleModel());
				camXSpinner.setPreferredSize(new Dimension(80, 20));
				camXSpinner.setName("camXspinner");
				camXSpinner.addChangeListener(Board3D.instance);
				p.add(new JLabel("cam distance"));
				p.add(camXSpinner);
				JButton eyeSwitch = new JButton("switch");
				eyeSwitch.setActionCommand("eyeSwitch");
				eyeSwitch.addActionListener(Board3D.instance);
				p.add(eyeSwitch);
				
				
				JCheckBox anaglyph = new JCheckBox("anaglyph");
				anaglyph.setActionCommand("anaglyph");
				anaglyph.addActionListener(Board3D.instance);
				p.add(anaglyph);
				
				return p;
			}

			private JPanel buildToolBox() {
				JPanel p = new JPanel();
				JButton startStopButton = new JButton("I/O");
				startStopButton.setActionCommand("start-stop");
				startStopButton.addActionListener(Board3D.instance);
				p.add(startStopButton);
				
				JButton restart = new JButton("restart");
				restart.setActionCommand("restart");
				restart.addActionListener(Board3D.instance);
				p.add(restart);
				
				JButton resetView = new JButton("reset view");
				resetView.setActionCommand("resetview");
				resetView.addActionListener(Board3D.instance);
				p.add(resetView);
				
				JButton applyRot = new JButton("setRot");
				applyRot.setActionCommand("setrot");
				applyRot.addActionListener(Board3D.instance);
				p.add(applyRot);
				
				JButton trainButton = new JButton("train");
				trainButton.setName("train");
				trainButton.addActionListener(Board3D.instance);
				p.add(trainButton);
				
				JSpinner rotSpinnerX = new JSpinner(createDoubleModel());
				JSpinner rotSpinnerY = new JSpinner(createDoubleModel());
				JSpinner rotSpinnerZ = new JSpinner(createDoubleModel());
				rotSpinnerX.setPreferredSize(new Dimension(80, 20));
				rotSpinnerY.setPreferredSize(new Dimension(80, 20));
				rotSpinnerZ.setPreferredSize(new Dimension(80, 20));
				rotSpinnerX.setName("rotX");
				rotSpinnerY.setName("rotY");
				rotSpinnerZ.setName("rotZ");
				rotSpinnerX.addChangeListener(Board3D.instance);
				rotSpinnerY.addChangeListener(Board3D.instance);
				rotSpinnerZ.addChangeListener(Board3D.instance);
				p.add(rotSpinnerX);
				p.add(rotSpinnerY);
				p.add(rotSpinnerZ);

				
				return p;
			}
		});
	}

}
