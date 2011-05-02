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
import javax.swing.SwingConstants;

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
				JSlider eyeSliderX = new JSlider(SwingConstants.HORIZONTAL, -100, 100, 0);
				JSlider eyeSliderY = new JSlider(SwingConstants.HORIZONTAL, -100, 100, 0);
				JSlider eyeSliderZ = new JSlider(SwingConstants.HORIZONTAL, -100, 100, 0);
				eyeSliderX.setName("eyeX");
				eyeSliderY.setName("eyeY");
				eyeSliderZ.setName("eyeZ");
				eyeSliderX.addChangeListener(Board3D.instance);
				eyeSliderY.addChangeListener(Board3D.instance);
				eyeSliderZ.addChangeListener(Board3D.instance);
				
//				p.add(eyeSliderX);
//				p.add(eyeSliderY);
//				p.add(eyeSliderZ);
				
				JSpinner camXSpinner = new JSpinner();
				camXSpinner.setPreferredSize(new Dimension(80, 20));
				camXSpinner.setName("camXspinner");
				camXSpinner.addChangeListener(Board3D.instance);
				p.add(new JLabel("eye distance"));
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
				
				JButton reset = new JButton("reset");
				reset.setActionCommand("reset");
				reset.addActionListener(Board3D.instance);
				p.add(reset);
				
				JButton refresh = new JButton("refresh");
				refresh.setActionCommand("refresh");
				refresh.addActionListener(Board3D.instance);
//				p.add(refresh);
				
				JSlider camSliderX = new JSlider(SwingConstants.HORIZONTAL, -20, 20, 0);
				JSlider camSliderY = new JSlider(SwingConstants.HORIZONTAL, -20, 20, 0);
				JSlider camSliderZ = new JSlider(SwingConstants.HORIZONTAL, -20, 20, 0);
				camSliderX.setName("camX");
				camSliderY.setName("camY");
				camSliderZ.setName("camZ");
				camSliderX.addChangeListener(Board3D.instance);
				camSliderY.addChangeListener(Board3D.instance);
				camSliderZ.addChangeListener(Board3D.instance);
				
//				p.add(camSliderX);
//				p.add(camSliderY);
//				p.add(camSliderZ);

				
				return p;
			}
		});
	}

}
