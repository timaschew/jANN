/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.threeD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * @author anton
 * 
 */
public class CubeGUI extends JFrame {
	private JSpinner minCorner;
	private JSpinner maxCorner;
	private JSpinner worldXoffset;
	private JSpinner worldYoffset;
	private JLabel lblXrotation;
	private JLabel lblYrotation;
	private JSpinner xRotField;
	private JSpinner yRotField;
	private JLabel lblZrotation;
	private JSpinner zRotField;
	private JButton btnStart;
	private JButton btnStop;
	private JSpinner delayTF;
	private JLabel lblDelayms;
	private JPanel panel;
	private JTextField frontLeftTopX;
	private JTextField frontLeftTopY;
	private JTextField frontLeftTopZ;
	private JPanel panel_1;
	private JTextField frontLeftBotX;
	private JTextField frontLeftBotY;
	private JTextField frontLeftBotZ;
	private JPanel panel_2;
	private JTextField frontRightTopX;
	private JTextField frontRightTopY;
	private JTextField frontRightTopZ;
	private JPanel panel_3;
	private JTextField frontRightBotX;
	private JTextField frontRightBotY;
	private JTextField frontRightBotZ;
	private JPanel panel_4;
	private JTextField backLeftTopX;
	private JTextField backLeftTopY;
	private JTextField backLeftTopZ;
	private JPanel panel_5;
	private JTextField backLeftBotX;
	private JTextField backLeftBotY;
	private JTextField backLeftBotZ;
	private JPanel panel_6;
	private JTextField backRightTopX;
	private JTextField backRightTopY;
	private JTextField backRightTopZ;
	private JPanel panel_7;
	private JTextField backRightBotX;
	private JTextField backRightBotY;
	private JTextField backRightBotZ;
	public SpinnerNumberModel worldXoffsetModel;
	public SpinnerNumberModel worldYoffsetModel;
	public SpinnerNumberModel zRotModel;
	public SpinnerNumberModel yRotModel;
	public SpinnerNumberModel xRotModel;
	public SpinnerNumberModel delayModel;

	private Integer worldXOffset = 200;
	private Integer worldYOffset = 200;
	// Rotationswinkel in rad
	private Double angle_x = 0.01;
	private Double angle_y = 0.0075;
	private Double angle_z = 0.005;

	private Double angle_step = 0.01;
	private JButton btnUpdate;
	private JLabel lblRotationStep;
	private JSpinner rotStepSpinner;
	private JLabel lblFps;
	private JTextField fpsTF;
	private SpinnerModel rotationStepModel;
	public JCheckBox chckbxAutoRotation;
	private int cunter = 0;
	private long lastUpdate = System.currentTimeMillis();
	private CubeRenderer w3d;
	protected boolean init = false;

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					CubeGUI w = new CubeGUI();
					w.init();
					w.setVisible(true);
					w.setBounds(100, 100, 1000, 700); // statt 800 1060
					w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 */
	protected void init() {
		w3d = new CubeRenderer(this);
		getContentPane().add(w3d);
		init = true;

	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		long newTime = System.currentTimeMillis();
		fpsTF.setText("" + (newTime - lastUpdate));

		if (init) {
			frontLeftTopX.setText(w3d.p[4][1].intValue() + "");
			frontLeftTopY.setText(w3d.p[4][2].intValue() + "");
			frontLeftTopZ.setText(w3d.p[4][3].intValue() + "");

		}

		repaint();
		lastUpdate = newTime;
	}

	public CubeGUI() {
		getContentPane().setLayout(new BorderLayout());

		chckbxAutoRotation = new JCheckBox("Auto-Rotation");
		chckbxAutoRotation.setSelected(true);

		rotationStepModel = new SpinnerNumberModel(angle_step, new Double(-1), new Double(1), new Double(0.001));
		rotStepSpinner = new JSpinner(rotationStepModel);

		worldXoffsetModel = new SpinnerNumberModel(worldXOffset, new Integer(0), new Integer(1000), new Integer(1));
		worldYoffsetModel = new SpinnerNumberModel(worldYOffset, new Integer(0), new Integer(1000), new Integer(1));

		xRotModel = new SpinnerNumberModel(angle_x, new Double(-1), new Double(1), angle_step);
		yRotModel = new SpinnerNumberModel(angle_y, new Double(-1), new Double(1), angle_step);
		zRotModel = new SpinnerNumberModel(angle_z, new Double(-1), new Double(1), angle_step);

		delayModel = new SpinnerNumberModel(new Integer(10), new Integer(0), new Integer(5000), new Integer(1));

		DefaultEditor editor = (JSpinner.DefaultEditor) rotStepSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				System.out.println("set step to" + evt.getNewValue());
				xRotModel.setStepSize((Number) evt.getNewValue());
				yRotModel.setStepSize((Number) evt.getNewValue());
				zRotModel.setStepSize((Number) evt.getNewValue());
			}
		});

		JPanel eastPanel = new JPanel(new GridLayout(0, 1));
		JPanel eastTopPanel = new JPanel();
		JPanel easetBottomPanel = new JPanel();

		eastPanel.add(eastTopPanel);
		eastPanel.add(easetBottomPanel);

		getContentPane().add(eastPanel, BorderLayout.EAST);

		JPanel southPanel = new JPanel();
		getContentPane().add(southPanel, BorderLayout.SOUTH);

		btnStart = new JButton("Start");
		southPanel.add(btnStart);

		btnStop = new JButton("Stop");
		southPanel.add(btnStop);

		lblDelayms = new JLabel("Delay [ms]");
		southPanel.add(lblDelayms);

		delayTF = new JSpinner(delayModel);
		southPanel.add(delayTF);

		btnUpdate = new JButton("Update");
		southPanel.add(btnUpdate);

		lblFps = new JLabel("FPS:");
		southPanel.add(lblFps);

		fpsTF = new JTextField();
		fpsTF.setEditable(false);
		southPanel.add(fpsTF);
		fpsTF.setColumns(2);

		southPanel.add(chckbxAutoRotation);
		// delayTF.setColumns(3);

		GridBagLayout gbl_easetBottomPanel = new GridBagLayout();
		gbl_easetBottomPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_easetBottomPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_easetBottomPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_easetBottomPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		easetBottomPanel.setLayout(gbl_easetBottomPanel);

		panel_4 = new JPanel();
		panel_4.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 1;
		easetBottomPanel.add(panel_4, gbc_panel_4);

		backLeftTopX = new JTextField();
		backLeftTopX.setColumns(2);
		panel_4.add(backLeftTopX);

		backLeftTopY = new JTextField();
		backLeftTopY.setColumns(2);
		panel_4.add(backLeftTopY);

		backLeftTopZ = new JTextField();
		backLeftTopZ.setColumns(2);
		panel_4.add(backLeftTopZ);

		panel_6 = new JPanel();
		panel_6.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 3;
		gbc_panel_6.gridy = 1;
		easetBottomPanel.add(panel_6, gbc_panel_6);

		backRightTopX = new JTextField();
		backRightTopX.setColumns(2);
		panel_6.add(backRightTopX);

		backRightTopY = new JTextField();
		backRightTopY.setColumns(2);
		panel_6.add(backRightTopY);

		backRightTopZ = new JTextField();
		backRightTopZ.setColumns(2);
		panel_6.add(backRightTopZ);

		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		easetBottomPanel.add(panel, gbc_panel);

		frontLeftTopX = new JTextField();
		panel.add(frontLeftTopX);
		frontLeftTopX.setColumns(2);

		frontLeftTopY = new JTextField();
		panel.add(frontLeftTopY);
		frontLeftTopY.setColumns(2);

		frontLeftTopZ = new JTextField();
		panel.add(frontLeftTopZ);
		frontLeftTopZ.setColumns(2);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 2;
		gbc_panel_2.gridy = 2;
		easetBottomPanel.add(panel_2, gbc_panel_2);

		frontRightTopX = new JTextField();
		frontRightTopX.setColumns(2);
		panel_2.add(frontRightTopX);

		frontRightTopY = new JTextField();
		frontRightTopY.setColumns(2);
		panel_2.add(frontRightTopY);

		frontRightTopZ = new JTextField();
		frontRightTopZ.setColumns(2);
		panel_2.add(frontRightTopZ);

		panel_5 = new JPanel();
		panel_5.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 5);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 3;
		easetBottomPanel.add(panel_5, gbc_panel_5);

		backLeftBotX = new JTextField();
		backLeftBotX.setColumns(2);
		panel_5.add(backLeftBotX);

		backLeftBotY = new JTextField();
		backLeftBotY.setColumns(2);
		panel_5.add(backLeftBotY);

		backLeftBotZ = new JTextField();
		backLeftBotZ.setColumns(2);
		panel_5.add(backLeftBotZ);

		panel_7 = new JPanel();
		panel_7.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(0, 0, 5, 0);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 3;
		gbc_panel_7.gridy = 3;
		easetBottomPanel.add(panel_7, gbc_panel_7);

		backRightBotX = new JTextField();
		backRightBotX.setColumns(2);
		panel_7.add(backRightBotX);

		backRightBotY = new JTextField();
		backRightBotY.setColumns(2);
		panel_7.add(backRightBotY);

		backRightBotZ = new JTextField();
		backRightBotZ.setColumns(2);
		panel_7.add(backRightBotZ);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 4;
		easetBottomPanel.add(panel_1, gbc_panel_1);

		frontLeftBotX = new JTextField();
		frontLeftBotX.setColumns(2);
		panel_1.add(frontLeftBotX);

		frontLeftBotY = new JTextField();
		frontLeftBotY.setColumns(2);
		panel_1.add(frontLeftBotY);

		frontLeftBotZ = new JTextField();
		frontLeftBotZ.setColumns(2);
		panel_1.add(frontLeftBotZ);

		panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 2;
		gbc_panel_3.gridy = 4;
		easetBottomPanel.add(panel_3, gbc_panel_3);

		frontRightBotX = new JTextField();
		frontRightBotX.setColumns(2);
		panel_3.add(frontRightBotX);

		frontRightBotY = new JTextField();
		frontRightBotY.setColumns(2);
		panel_3.add(frontRightBotY);

		frontRightBotZ = new JTextField();
		frontRightBotZ.setColumns(2);
		panel_3.add(frontRightBotZ);

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		eastTopPanel.setLayout(gbl_panel);

		JLabel lblMinCorner = new JLabel("Min Corner");
		GridBagConstraints gbc_lblMinCorner = new GridBagConstraints();
		gbc_lblMinCorner.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinCorner.anchor = GridBagConstraints.EAST;
		gbc_lblMinCorner.gridx = 1;
		gbc_lblMinCorner.gridy = 0;
		eastTopPanel.add(lblMinCorner, gbc_lblMinCorner);

		minCorner = new JSpinner();
		GridBagConstraints gbc_minCorner = new GridBagConstraints();
		gbc_minCorner.insets = new Insets(0, 0, 5, 0);
		gbc_minCorner.fill = GridBagConstraints.HORIZONTAL;
		gbc_minCorner.gridx = 2;
		gbc_minCorner.gridy = 0;
		eastTopPanel.add(minCorner, gbc_minCorner);
		// minCorner.setColumns(10);

		JLabel lblMaxCorner = new JLabel("Max Corner");
		GridBagConstraints gbc_lblMaxCorner = new GridBagConstraints();
		gbc_lblMaxCorner.anchor = GridBagConstraints.EAST;
		gbc_lblMaxCorner.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxCorner.gridx = 1;
		gbc_lblMaxCorner.gridy = 1;
		eastTopPanel.add(lblMaxCorner, gbc_lblMaxCorner);

		maxCorner = new JSpinner();
		GridBagConstraints gbc_maxCorner = new GridBagConstraints();
		gbc_maxCorner.insets = new Insets(0, 0, 5, 0);
		gbc_maxCorner.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxCorner.gridx = 2;
		gbc_maxCorner.gridy = 1;
		eastTopPanel.add(maxCorner, gbc_maxCorner);
		// maxCorner.setColumns(10);

		JLabel lblWorldXOffset = new JLabel("World x-offset");
		GridBagConstraints gbc_lblWorldXOffset = new GridBagConstraints();
		gbc_lblWorldXOffset.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorldXOffset.anchor = GridBagConstraints.EAST;
		gbc_lblWorldXOffset.gridx = 1;
		gbc_lblWorldXOffset.gridy = 2;
		eastTopPanel.add(lblWorldXOffset, gbc_lblWorldXOffset);

		worldXoffset = new JSpinner(worldXoffsetModel);
		GridBagConstraints gbc_worldXoffset = new GridBagConstraints();
		gbc_worldXoffset.insets = new Insets(0, 0, 5, 0);
		gbc_worldXoffset.fill = GridBagConstraints.HORIZONTAL;
		gbc_worldXoffset.gridx = 2;
		gbc_worldXoffset.gridy = 2;
		eastTopPanel.add(worldXoffset, gbc_worldXoffset);
		// worldXoffset.setColumns(10);

		JLabel lblWorldYoffset = new JLabel("World y-offset");
		GridBagConstraints gbc_lblWorldYoffset = new GridBagConstraints();
		gbc_lblWorldYoffset.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorldYoffset.anchor = GridBagConstraints.EAST;
		gbc_lblWorldYoffset.gridx = 1;
		gbc_lblWorldYoffset.gridy = 3;
		eastTopPanel.add(lblWorldYoffset, gbc_lblWorldYoffset);

		worldYoffset = new JSpinner(worldYoffsetModel);
		GridBagConstraints gbc_worldYoffset = new GridBagConstraints();
		gbc_worldYoffset.insets = new Insets(0, 0, 5, 0);
		gbc_worldYoffset.fill = GridBagConstraints.HORIZONTAL;
		gbc_worldYoffset.gridx = 2;
		gbc_worldYoffset.gridy = 3;
		eastTopPanel.add(worldYoffset, gbc_worldYoffset);

		lblRotationStep = new JLabel("Rotation step");
		GridBagConstraints gbc_lblRotationStep = new GridBagConstraints();
		gbc_lblRotationStep.anchor = GridBagConstraints.EAST;
		gbc_lblRotationStep.insets = new Insets(0, 0, 5, 5);
		gbc_lblRotationStep.gridx = 1;
		gbc_lblRotationStep.gridy = 4;
		eastTopPanel.add(lblRotationStep, gbc_lblRotationStep);

		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 4;
		eastTopPanel.add(rotStepSpinner, gbc_spinner);
		// worldYoffset.setColumns(10);

		lblXrotation = new JLabel("X-Rotation");
		GridBagConstraints gbc_lblXrotation = new GridBagConstraints();
		gbc_lblXrotation.anchor = GridBagConstraints.EAST;
		gbc_lblXrotation.insets = new Insets(0, 0, 5, 5);
		gbc_lblXrotation.gridx = 1;
		gbc_lblXrotation.gridy = 5;
		eastTopPanel.add(lblXrotation, gbc_lblXrotation);

		xRotField = new JSpinner(xRotModel);
		// xRotField.setColumns(10);
		GridBagConstraints gbc_xRotField = new GridBagConstraints();
		gbc_xRotField.insets = new Insets(0, 0, 5, 0);
		gbc_xRotField.fill = GridBagConstraints.HORIZONTAL;
		gbc_xRotField.gridx = 2;
		gbc_xRotField.gridy = 5;
		eastTopPanel.add(xRotField, gbc_xRotField);

		lblYrotation = new JLabel("Y-Rotation");
		GridBagConstraints gbc_lblYrotation = new GridBagConstraints();
		gbc_lblYrotation.anchor = GridBagConstraints.EAST;
		gbc_lblYrotation.insets = new Insets(0, 0, 5, 5);
		gbc_lblYrotation.gridx = 1;
		gbc_lblYrotation.gridy = 6;
		eastTopPanel.add(lblYrotation, gbc_lblYrotation);

		yRotField = new JSpinner(yRotModel);
		// yRotField.setColumns(10);
		GridBagConstraints gbc_yRotField = new GridBagConstraints();
		gbc_yRotField.insets = new Insets(0, 0, 5, 0);
		gbc_yRotField.fill = GridBagConstraints.HORIZONTAL;
		gbc_yRotField.gridx = 2;
		gbc_yRotField.gridy = 6;
		eastTopPanel.add(yRotField, gbc_yRotField);

		lblZrotation = new JLabel("Z-Rotation");
		GridBagConstraints gbc_lblZrotation = new GridBagConstraints();
		gbc_lblZrotation.anchor = GridBagConstraints.EAST;
		gbc_lblZrotation.insets = new Insets(0, 0, 5, 5);
		gbc_lblZrotation.gridx = 1;
		gbc_lblZrotation.gridy = 7;
		eastTopPanel.add(lblZrotation, gbc_lblZrotation);

		zRotField = new JSpinner(zRotModel);
		GridBagConstraints gbc_zRotField = new GridBagConstraints();
		gbc_zRotField.insets = new Insets(0, 0, 5, 0);
		gbc_zRotField.fill = GridBagConstraints.HORIZONTAL;
		gbc_zRotField.gridx = 2;
		gbc_zRotField.gridy = 7;
		eastTopPanel.add(zRotField, gbc_zRotField);
		// zRotField.setColumns(10);
	}

}
