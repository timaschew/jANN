/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.threeD.misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author anton
 * 
 */
public class WuerfelGUI extends JFrame {
	private JTextField minCorner;
	private JTextField maxCorner;
	private JTextField worldXoffset;
	private JTextField worldYoffset;
	private JTextField BackLeftTop;
	private JTextField frontRightTop;
	private JTextField frontLeftBot;
	private JTextField frontRightBot;
	private JTextField backRightTop;
	private JTextField BackLeftBot;
	private JTextField backRightBot;
	private JLabel lblXrotation;
	private JLabel lblYrotation;
	private JTextField xRotField;
	private JTextField yRotField;
	private JLabel lblZrotation;
	private JTextField zRotField;
	private JButton btnStart;
	private JButton btnStop;
	private JTextField textField;
	private JLabel lblDelayms;
	private JPanel panel;
	private JTextField frontLeftTopX;
	private JTextField frontLeftTopY;
	private JTextField frontLeftTopZ;

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					WuerfelGUI w = new WuerfelGUI();
					w.setVisible(true);
					w.setBounds(100, 100, 900, 700); // statt 800 1060
					w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WuerfelGUI() {
		getContentPane().setLayout(new BorderLayout());

		Wuerfel w3d = new Wuerfel();
		// JPanel w3d = new JPanel();
		getContentPane().add(w3d);

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

		textField = new JTextField();
		southPanel.add(textField);
		textField.setColumns(3);

		GridBagLayout gbl_easetBottomPanel = new GridBagLayout();
		gbl_easetBottomPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_easetBottomPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_easetBottomPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_easetBottomPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		easetBottomPanel.setLayout(gbl_easetBottomPanel);

		BackLeftTop = new JTextField();
		BackLeftTop.setForeground(Color.WHITE);
		BackLeftTop.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_BackLeftTop = new GridBagConstraints();
		gbc_BackLeftTop.fill = GridBagConstraints.HORIZONTAL;
		gbc_BackLeftTop.insets = new Insets(0, 0, 5, 5);
		gbc_BackLeftTop.gridx = 1;
		gbc_BackLeftTop.gridy = 1;
		easetBottomPanel.add(BackLeftTop, gbc_BackLeftTop);
		BackLeftTop.setColumns(3);

		backRightTop = new JTextField();
		backRightTop.setForeground(Color.WHITE);
		backRightTop.setBackground(Color.DARK_GRAY);
		backRightTop.setColumns(3);
		GridBagConstraints gbc_backRightTop = new GridBagConstraints();
		gbc_backRightTop.insets = new Insets(0, 0, 5, 0);
		gbc_backRightTop.fill = GridBagConstraints.HORIZONTAL;
		gbc_backRightTop.gridx = 3;
		gbc_backRightTop.gridy = 1;
		easetBottomPanel.add(backRightTop, gbc_backRightTop);

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

		frontRightTop = new JTextField();
		frontRightTop.setBackground(Color.LIGHT_GRAY);
		frontRightTop.setColumns(3);
		GridBagConstraints gbc_frontRightTop = new GridBagConstraints();
		gbc_frontRightTop.insets = new Insets(0, 0, 5, 5);
		gbc_frontRightTop.fill = GridBagConstraints.HORIZONTAL;
		gbc_frontRightTop.gridx = 2;
		gbc_frontRightTop.gridy = 2;
		easetBottomPanel.add(frontRightTop, gbc_frontRightTop);

		BackLeftBot = new JTextField();
		BackLeftBot.setForeground(Color.WHITE);
		BackLeftBot.setBackground(Color.DARK_GRAY);
		BackLeftBot.setColumns(3);
		GridBagConstraints gbc_BackLeftBot = new GridBagConstraints();
		gbc_BackLeftBot.insets = new Insets(0, 0, 5, 5);
		gbc_BackLeftBot.fill = GridBagConstraints.HORIZONTAL;
		gbc_BackLeftBot.gridx = 1;
		gbc_BackLeftBot.gridy = 3;
		easetBottomPanel.add(BackLeftBot, gbc_BackLeftBot);

		backRightBot = new JTextField();
		backRightBot.setForeground(Color.WHITE);
		backRightBot.setBackground(Color.DARK_GRAY);
		backRightBot.setColumns(3);
		GridBagConstraints gbc_backRightBot = new GridBagConstraints();
		gbc_backRightBot.insets = new Insets(0, 0, 5, 0);
		gbc_backRightBot.fill = GridBagConstraints.HORIZONTAL;
		gbc_backRightBot.gridx = 3;
		gbc_backRightBot.gridy = 3;
		easetBottomPanel.add(backRightBot, gbc_backRightBot);

		frontLeftBot = new JTextField();
		frontLeftBot.setBackground(Color.LIGHT_GRAY);
		frontLeftBot.setColumns(3);
		GridBagConstraints gbc_frontLeftBot = new GridBagConstraints();
		gbc_frontLeftBot.insets = new Insets(0, 0, 0, 5);
		gbc_frontLeftBot.fill = GridBagConstraints.HORIZONTAL;
		gbc_frontLeftBot.gridx = 0;
		gbc_frontLeftBot.gridy = 4;
		easetBottomPanel.add(frontLeftBot, gbc_frontLeftBot);

		frontRightBot = new JTextField();
		frontRightBot.setBackground(Color.LIGHT_GRAY);
		frontRightBot.setColumns(3);
		GridBagConstraints gbc_frontRightBot = new GridBagConstraints();
		gbc_frontRightBot.insets = new Insets(0, 0, 0, 5);
		gbc_frontRightBot.fill = GridBagConstraints.HORIZONTAL;
		gbc_frontRightBot.gridx = 2;
		gbc_frontRightBot.gridy = 4;
		easetBottomPanel.add(frontRightBot, gbc_frontRightBot);

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		eastTopPanel.setLayout(gbl_panel);

		JLabel lblMinCorner = new JLabel("Min Corner");
		GridBagConstraints gbc_lblMinCorner = new GridBagConstraints();
		gbc_lblMinCorner.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinCorner.anchor = GridBagConstraints.EAST;
		gbc_lblMinCorner.gridx = 1;
		gbc_lblMinCorner.gridy = 0;
		eastTopPanel.add(lblMinCorner, gbc_lblMinCorner);

		minCorner = new JTextField();
		GridBagConstraints gbc_minCorner = new GridBagConstraints();
		gbc_minCorner.insets = new Insets(0, 0, 5, 0);
		gbc_minCorner.fill = GridBagConstraints.HORIZONTAL;
		gbc_minCorner.gridx = 2;
		gbc_minCorner.gridy = 0;
		eastTopPanel.add(minCorner, gbc_minCorner);
		minCorner.setColumns(10);

		JLabel lblMaxCorner = new JLabel("Max Corner");
		GridBagConstraints gbc_lblMaxCorner = new GridBagConstraints();
		gbc_lblMaxCorner.anchor = GridBagConstraints.EAST;
		gbc_lblMaxCorner.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxCorner.gridx = 1;
		gbc_lblMaxCorner.gridy = 1;
		eastTopPanel.add(lblMaxCorner, gbc_lblMaxCorner);

		maxCorner = new JTextField();
		GridBagConstraints gbc_maxCorner = new GridBagConstraints();
		gbc_maxCorner.insets = new Insets(0, 0, 5, 0);
		gbc_maxCorner.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxCorner.gridx = 2;
		gbc_maxCorner.gridy = 1;
		eastTopPanel.add(maxCorner, gbc_maxCorner);
		maxCorner.setColumns(10);

		JLabel lblWorldXOffset = new JLabel("World x-offset");
		GridBagConstraints gbc_lblWorldXOffset = new GridBagConstraints();
		gbc_lblWorldXOffset.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorldXOffset.anchor = GridBagConstraints.EAST;
		gbc_lblWorldXOffset.gridx = 1;
		gbc_lblWorldXOffset.gridy = 2;
		eastTopPanel.add(lblWorldXOffset, gbc_lblWorldXOffset);

		worldXoffset = new JTextField();
		GridBagConstraints gbc_worldXoffset = new GridBagConstraints();
		gbc_worldXoffset.insets = new Insets(0, 0, 5, 0);
		gbc_worldXoffset.fill = GridBagConstraints.HORIZONTAL;
		gbc_worldXoffset.gridx = 2;
		gbc_worldXoffset.gridy = 2;
		eastTopPanel.add(worldXoffset, gbc_worldXoffset);
		worldXoffset.setColumns(10);

		JLabel lblWorldYoffset = new JLabel("World y-offset");
		GridBagConstraints gbc_lblWorldYoffset = new GridBagConstraints();
		gbc_lblWorldYoffset.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorldYoffset.anchor = GridBagConstraints.EAST;
		gbc_lblWorldYoffset.gridx = 1;
		gbc_lblWorldYoffset.gridy = 3;
		eastTopPanel.add(lblWorldYoffset, gbc_lblWorldYoffset);

		worldYoffset = new JTextField();
		GridBagConstraints gbc_worldYoffset = new GridBagConstraints();
		gbc_worldYoffset.insets = new Insets(0, 0, 5, 0);
		gbc_worldYoffset.fill = GridBagConstraints.HORIZONTAL;
		gbc_worldYoffset.gridx = 2;
		gbc_worldYoffset.gridy = 3;
		eastTopPanel.add(worldYoffset, gbc_worldYoffset);
		worldYoffset.setColumns(10);

		lblXrotation = new JLabel("X-Rotation");
		GridBagConstraints gbc_lblXrotation = new GridBagConstraints();
		gbc_lblXrotation.anchor = GridBagConstraints.EAST;
		gbc_lblXrotation.insets = new Insets(0, 0, 5, 5);
		gbc_lblXrotation.gridx = 1;
		gbc_lblXrotation.gridy = 4;
		eastTopPanel.add(lblXrotation, gbc_lblXrotation);

		xRotField = new JTextField();
		xRotField.setColumns(10);
		GridBagConstraints gbc_xRotField = new GridBagConstraints();
		gbc_xRotField.insets = new Insets(0, 0, 5, 0);
		gbc_xRotField.fill = GridBagConstraints.HORIZONTAL;
		gbc_xRotField.gridx = 2;
		gbc_xRotField.gridy = 4;
		eastTopPanel.add(xRotField, gbc_xRotField);

		lblYrotation = new JLabel("Y-Rotation");
		GridBagConstraints gbc_lblYrotation = new GridBagConstraints();
		gbc_lblYrotation.anchor = GridBagConstraints.EAST;
		gbc_lblYrotation.insets = new Insets(0, 0, 5, 5);
		gbc_lblYrotation.gridx = 1;
		gbc_lblYrotation.gridy = 5;
		eastTopPanel.add(lblYrotation, gbc_lblYrotation);

		yRotField = new JTextField();
		yRotField.setColumns(10);
		GridBagConstraints gbc_yRotField = new GridBagConstraints();
		gbc_yRotField.insets = new Insets(0, 0, 5, 0);
		gbc_yRotField.fill = GridBagConstraints.HORIZONTAL;
		gbc_yRotField.gridx = 2;
		gbc_yRotField.gridy = 5;
		eastTopPanel.add(yRotField, gbc_yRotField);

		lblZrotation = new JLabel("Z-Rotation");
		GridBagConstraints gbc_lblZrotation = new GridBagConstraints();
		gbc_lblZrotation.anchor = GridBagConstraints.EAST;
		gbc_lblZrotation.insets = new Insets(0, 0, 5, 5);
		gbc_lblZrotation.gridx = 1;
		gbc_lblZrotation.gridy = 6;
		eastTopPanel.add(lblZrotation, gbc_lblZrotation);

		zRotField = new JTextField();
		GridBagConstraints gbc_zRotField = new GridBagConstraints();
		gbc_zRotField.insets = new Insets(0, 0, 5, 0);
		gbc_zRotField.fill = GridBagConstraints.HORIZONTAL;
		gbc_zRotField.gridx = 2;
		gbc_zRotField.gridy = 6;
		eastTopPanel.add(zRotField, gbc_zRotField);
		zRotField.setColumns(10);
	}

}
