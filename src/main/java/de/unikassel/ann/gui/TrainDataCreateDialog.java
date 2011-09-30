/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultEditorKit;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main.Panel;
import de.unikassel.ann.io.NetIO;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.util.Logger;
import de.unikassel.ann.util.StringReplaceHelper;

/**
 * @author Sofia
 * 
 */
public class TrainDataCreateDialog extends JDialog {

	// private TrainDataCreateDialog

	private String firstLine;

	public TrainDataCreateDialog() {

		// lh = new ListTransferHandler();
		setModal(false);

		JPanel trainDataPane = new JPanel();
		setTitle(Settings.i18n.getString("trainDataCreateDialog.titel"));
		setModal(true);
		setSize(411, 402);
		setResizable(false);
		setLocationRelativeTo(null);

		JButton btnSave = new JButton(Settings.i18n.getString("trainDataCreateDialog.btnSave"));
		JButton btnCancel = new JButton(Settings.i18n.getString("trainDataCreateDialog.btnCancel"));

		String descriptionVar = "{} Spalten mit Semikolon (;) getrennt<br>" + "{} für die Inputneuronen<br>"
				+ "{} für die Outputneuronen<br>" + "Dezimaltrennzeichen mit (,) oder (.)<br>"
				+ "keine Tausender-Trennzeichen (1.000)! <br>" + "pro Datensatz eine Zeile verwenden";

		NetConfig netconfig = Settings.getInstance().getCurrentSession().getNetworkConfig();
		int inputSize = netconfig.getNetwork().getInputSizeIgnoringBias();
		int outputSize = netconfig.getNetwork().getOutputSize();
		int sumSize = inputSize + outputSize;
		String description = StringReplaceHelper.replace(descriptionVar, sumSize, inputSize, outputSize);
		JLabel lblThisIsA = new JLabel("<html><body>" + description + "</body></html>");

		// Create a text area.
		final JTextArea userDataArea = new JTextArea();
		userDataArea.setLineWrap(true);
		userDataArea.setWrapStyleWord(true);

		copyPasteActionsForTextArea(userDataArea);

		JLabel lblInfo = new JLabel(Settings.i18n.getString("trainDataCreateDialog.lblInfo"));
		JTextArea firstLineArea = new JTextArea();
		firstLineArea.setEditable(false);
		firstLineArea.setPreferredSize(new Dimension(353, 30));
		StringBuilder sb = new StringBuilder();
		String prefix = "";
		for (int i = 0; i < inputSize; i++) {
			sb.append(prefix);
			prefix = ";";
			sb.append("i");
		}
		for (int i = 0; i < outputSize; i++) {
			sb.append(prefix);
			sb.append("o");
		}
		firstLine = sb.toString();
		firstLineArea.setText(firstLine);

		JScrollPane areaScrollPane = new JScrollPane(userDataArea);
		areaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(353, 180));

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				// Falls du die Daten für die aktuelle Session brauchst ;-)
				NetConfig netconfig = Settings.getInstance().getCurrentSession().getNetworkConfig();
				if (netconfig.getTrainingData() != null) {
					Object[] option = { "Ja", "Nein" };
					Component frameD = new JFrame();

					int pane = JOptionPane.showOptionDialog(frameD, "Trainingsdaten existieren bereits. \n"
							+ "Möchten Sie diese überschreiben?", "Beenden", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
							option, option[1]);

					if (pane == JOptionPane.YES_OPTION) {
						InputStream inputStream = writeAreaTextToInputStream(userDataArea);
						NetIO io = new NetIO();
						try {
							io.readTraininData(inputStream);
							DataPairSet trainData = io.getTrainingSet();
							netconfig.setTrainingData(trainData);
							Main.instance.trainingDataChartPanel.updateTrainingData();
						} catch (Exception e1) {
							Main.instance.switchBottomPanel(Panel.CONSOLE);
							Logger.error(this.getClass(), "Trainingsdaten konnten nicht erzeugt werden, Grund: {}", e1.getMessage());
						}
					}
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				dispose();

			}
		});

		trainDataPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		trainDataPane.add(lblInfo);

		trainDataPane.add(lblThisIsA);
		trainDataPane.add(firstLineArea);
		trainDataPane.add(areaScrollPane);
		trainDataPane.add(btnSave);
		trainDataPane.add(btnCancel);

		getContentPane().add(trainDataPane);

	}

	/**
	 * @param userDataArea
	 */
	private void copyPasteActionsForTextArea(final JTextArea userDataArea) {
		JPopupMenu copyPaste = new JPopupMenu();
		JMenuItem copyItem = new JMenuItem(new DefaultEditorKit.CopyAction());
		copyItem.setText(Settings.i18n.getString("trainDataCreateDialog.copy"));
		copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

		JMenuItem pasteItem = new JMenuItem(new DefaultEditorKit.PasteAction());
		pasteItem.setText(Settings.i18n.getString("trainDataCreateDialog.paste"));
		pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		copyPaste.add(copyItem);
		copyPaste.add(pasteItem);

		MouseListener popupListener = new PopupListener(copyPaste);
		userDataArea.addMouseListener(popupListener);
	}

	public InputStream writeAreaTextToInputStream(final JTextArea textArea) {
		return new InputStream() {

			String s = firstLine + "\n" + textArea.getText();
			int inPtr = 0;

			@Override
			public int read() // minimum implementation of an InputStream
			{
				if (inPtr >= s.length()) {
					return -1;
				}
				inPtr++;
				return s.charAt(inPtr - 1);
			}
		};
	}

	// MouseListener for Copy and Paste Action
	class PopupListener extends MouseAdapter {
		JPopupMenu popup;

		PopupListener(final JPopupMenu popupMenu) {
			popup = popupMenu;
		}

		@Override
		public void mousePressed(final MouseEvent e) {
			maybeShowPopup(e);
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(final MouseEvent e) {
			if (e.isPopupTrigger()) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

}
