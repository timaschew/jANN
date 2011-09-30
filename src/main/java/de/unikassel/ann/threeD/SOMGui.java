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

import de.unikassel.ann.threeD.model.GridCube;
import de.unikassel.ann.threeD.model.Point3D;
import de.unikassel.ann.threeD.model.RenderGeometry;

/**
 * @author anton
 * 
 */
public class SOMGui extends JFrame {
	private JSpinner sizeSpinner;
	private JSpinner worldXoffset;
	private JSpinner worldYoffset;
	private JLabel lblXrotation;
	private JLabel lblYrotation;
	private JSpinner xRotField;
	private JSpinner yRotField;
	private JLabel lblZrotation;
	private JSpinner zRotField;
	private JSpinner delayTF;
	private JLabel lblDelayms;
	private JPanel coordinate3dPanel;
	private JTextField coordinate3dX;
	private JTextField coordinate3dY;
	private JTextField coordinate3dZ;
	private JPanel inputDataPanel;
	private JTextField inputData1;
	private JTextField inputData2;
	private JTextField inputData3;
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
	private RenderGeometry w3d;
	protected boolean init = false;
	private JLabel lbldKoordinateAbsolut;
	private JLabel lblVertexGewichte;
	private JPanel panel_2;
	private JTextField weight1;
	private JTextField weight2;
	private JTextField weight3;
	private JTextField inputData4;
	private JLabel lblInput;
	private JTextField weight4;
	private FrameRenderer renderer;
	private JLabel lblGrids;
	private JSpinner gridSpinner;

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					SOMGui w = new SOMGui();
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
		getContentPane().add(renderer);
		init = true;

	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		long newTime = System.currentTimeMillis();
		fpsTF.setText("" + (newTime - lastUpdate));

		if (init) {
			Point3D p = w3d.points.get(0);
			coordinate3dX.setText(p.x.intValue() + "");
			coordinate3dY.setText(p.y.intValue() + "");
			coordinate3dZ.setText(p.z.intValue() + "");

		}

		repaint();
		lastUpdate = newTime;
	}

	public SOMGui() {

		renderer = new FrameRenderer(this);
		// w3d = new SimpleCube();
		w3d = new GridCube(5, 5, 5, 200);
		renderer.setModel(w3d);

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

		DefaultEditor rotateEditor = (JSpinner.DefaultEditor) rotStepSpinner.getEditor();
		rotateEditor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				System.out.println("set step to" + evt.getNewValue());
				xRotModel.setStepSize((Number) evt.getNewValue());
				yRotModel.setStepSize((Number) evt.getNewValue());
				zRotModel.setStepSize((Number) evt.getNewValue());
			}
		});

		sizeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		sizeSpinner.setValue(w3d.getGeometrySize());
		DefaultEditor editorSize = (JSpinner.DefaultEditor) sizeSpinner.getEditor();
		editorSize.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				Integer size = (Integer) evt.getNewValue();
				w3d.setGeometrySize(size, null);
			}
		});

		gridSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 20, 1));
		gridSpinner.setValue(w3d.getGridSize());
		DefaultEditor editorGrid = (JSpinner.DefaultEditor) gridSpinner.getEditor();
		editorGrid.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				Integer grids = (Integer) evt.getNewValue();
				w3d.setGeometrySize(null, grids);
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

		lblInput = new JLabel("Input");
		GridBagConstraints gbc_lblInput = new GridBagConstraints();
		gbc_lblInput.insets = new Insets(0, 0, 5, 5);
		gbc_lblInput.gridx = 2;
		gbc_lblInput.gridy = 1;
		easetBottomPanel.add(lblInput, gbc_lblInput);

		inputDataPanel = new JPanel();
		inputDataPanel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_inputDataPanel = new GridBagConstraints();
		gbc_inputDataPanel.insets = new Insets(0, 0, 5, 0);
		gbc_inputDataPanel.fill = GridBagConstraints.BOTH;
		gbc_inputDataPanel.gridx = 3;
		gbc_inputDataPanel.gridy = 1;
		easetBottomPanel.add(inputDataPanel, gbc_inputDataPanel);

		inputData1 = new JTextField();
		inputData1.setColumns(2);
		inputDataPanel.add(inputData1);

		inputData2 = new JTextField();
		inputData2.setColumns(2);
		inputDataPanel.add(inputData2);

		inputData3 = new JTextField();
		inputData3.setColumns(2);
		inputDataPanel.add(inputData3);

		inputData4 = new JTextField();
		inputData4.setColumns(2);
		inputDataPanel.add(inputData4);

		lblVertexGewichte = new JLabel("Gewichte");
		GridBagConstraints gbc_lblVertexGewichte = new GridBagConstraints();
		gbc_lblVertexGewichte.insets = new Insets(0, 0, 5, 5);
		gbc_lblVertexGewichte.gridx = 2;
		gbc_lblVertexGewichte.gridy = 2;
		easetBottomPanel.add(lblVertexGewichte, gbc_lblVertexGewichte);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 3;
		gbc_panel_2.gridy = 2;
		easetBottomPanel.add(panel_2, gbc_panel_2);

		weight1 = new JTextField();
		weight1.setColumns(2);
		panel_2.add(weight1);

		weight2 = new JTextField();
		weight2.setColumns(2);
		panel_2.add(weight2);

		weight3 = new JTextField();
		weight3.setColumns(2);
		panel_2.add(weight3);

		weight4 = new JTextField();
		weight4.setColumns(2);
		panel_2.add(weight4);

		lbldKoordinateAbsolut = new JLabel("3D Koordinate absolut");
		GridBagConstraints gbc_lbldKoordinateAbsolut = new GridBagConstraints();
		gbc_lbldKoordinateAbsolut.insets = new Insets(0, 0, 5, 5);
		gbc_lbldKoordinateAbsolut.gridx = 2;
		gbc_lbldKoordinateAbsolut.gridy = 3;
		easetBottomPanel.add(lbldKoordinateAbsolut, gbc_lbldKoordinateAbsolut);

		coordinate3dPanel = new JPanel();
		coordinate3dPanel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_coordinate3dPanel = new GridBagConstraints();
		gbc_coordinate3dPanel.insets = new Insets(0, 0, 5, 0);
		gbc_coordinate3dPanel.fill = GridBagConstraints.BOTH;
		gbc_coordinate3dPanel.gridx = 3;
		gbc_coordinate3dPanel.gridy = 3;
		easetBottomPanel.add(coordinate3dPanel, gbc_coordinate3dPanel);

		coordinate3dX = new JTextField();
		coordinate3dPanel.add(coordinate3dX);
		coordinate3dX.setColumns(2);

		coordinate3dY = new JTextField();
		coordinate3dPanel.add(coordinate3dY);
		coordinate3dY.setColumns(2);

		coordinate3dZ = new JTextField();
		coordinate3dPanel.add(coordinate3dZ);
		coordinate3dZ.setColumns(2);

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		eastTopPanel.setLayout(gbl_panel);

		JLabel lblSize = new JLabel("Größe");
		GridBagConstraints gbc_lblSize = new GridBagConstraints();
		gbc_lblSize.anchor = GridBagConstraints.EAST;
		gbc_lblSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblSize.gridx = 1;
		gbc_lblSize.gridy = 1;
		eastTopPanel.add(lblSize, gbc_lblSize);

		GridBagConstraints gbc_sizeSpinner = new GridBagConstraints();
		gbc_sizeSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_sizeSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_sizeSpinner.gridx = 3;
		gbc_sizeSpinner.gridy = 1;
		eastTopPanel.add(sizeSpinner, gbc_sizeSpinner);

		lblGrids = new JLabel("Grids");
		GridBagConstraints gbc_lblGrids = new GridBagConstraints();
		gbc_lblGrids.anchor = GridBagConstraints.EAST;
		gbc_lblGrids.insets = new Insets(0, 0, 5, 5);
		gbc_lblGrids.gridx = 1;
		gbc_lblGrids.gridy = 2;
		eastTopPanel.add(lblGrids, gbc_lblGrids);

		GridBagConstraints gbc_gridSpinner = new GridBagConstraints();
		gbc_gridSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_gridSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_gridSpinner.gridx = 3;
		gbc_gridSpinner.gridy = 2;
		eastTopPanel.add(gridSpinner, gbc_gridSpinner);
		// maxCorner.setColumns(10);

		JLabel lblWorldXOffset = new JLabel("World Position (x,y)");
		GridBagConstraints gbc_lblWorldXOffset = new GridBagConstraints();
		gbc_lblWorldXOffset.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorldXOffset.anchor = GridBagConstraints.EAST;
		gbc_lblWorldXOffset.gridx = 1;
		gbc_lblWorldXOffset.gridy = 3;
		eastTopPanel.add(lblWorldXOffset, gbc_lblWorldXOffset);

		worldYoffset = new JSpinner(worldYoffsetModel);
		GridBagConstraints gbc_worldYoffset = new GridBagConstraints();
		gbc_worldYoffset.fill = GridBagConstraints.HORIZONTAL;
		gbc_worldYoffset.insets = new Insets(0, 0, 5, 5);
		gbc_worldYoffset.gridx = 2;
		gbc_worldYoffset.gridy = 3;
		eastTopPanel.add(worldYoffset, gbc_worldYoffset);

		worldXoffset = new JSpinner(worldXoffsetModel);
		GridBagConstraints gbc_worldXoffset = new GridBagConstraints();
		gbc_worldXoffset.fill = GridBagConstraints.HORIZONTAL;
		gbc_worldXoffset.insets = new Insets(0, 0, 5, 0);
		gbc_worldXoffset.gridx = 3;
		gbc_worldXoffset.gridy = 3;
		eastTopPanel.add(worldXoffset, gbc_worldXoffset);

		lblRotationStep = new JLabel("Rotation step");
		GridBagConstraints gbc_lblRotationStep = new GridBagConstraints();
		gbc_lblRotationStep.anchor = GridBagConstraints.EAST;
		gbc_lblRotationStep.insets = new Insets(0, 0, 5, 5);
		gbc_lblRotationStep.gridx = 1;
		gbc_lblRotationStep.gridy = 4;
		eastTopPanel.add(lblRotationStep, gbc_lblRotationStep);

		GridBagConstraints gbc_rotStepSpinner = new GridBagConstraints();
		gbc_rotStepSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_rotStepSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_rotStepSpinner.gridx = 3;
		gbc_rotStepSpinner.gridy = 4;
		eastTopPanel.add(rotStepSpinner, gbc_rotStepSpinner);
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
		gbc_xRotField.gridx = 3;
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
		gbc_yRotField.gridx = 3;
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
		gbc_zRotField.gridx = 3;
		gbc_zRotField.gridy = 7;
		eastTopPanel.add(zRotField, gbc_zRotField);
		// zRotField.setColumns(10);
	}

}
