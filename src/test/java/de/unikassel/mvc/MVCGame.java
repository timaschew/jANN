/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @see http://stackoverflow.com/questions/3066590 15-Mar2011 http://stackoverflow.com/questions/5274962
 */
public class MVCGame implements Runnable {

	public static void main(final String[] args) {
		EventQueue.invokeLater(new MVCGame());
	}

	@Override
	public void run() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new MainPanel());
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}

class MainPanel extends JPanel {

	public MainPanel() {
		super(new BorderLayout());
		Model model = new Model();
		View view = new View(model);
		Control control = new Control(model, view);
		JLabel label = new JLabel("Guess what color!", SwingConstants.CENTER);
		this.add(label, BorderLayout.NORTH);
		this.add(view, BorderLayout.CENTER);
		this.add(control, BorderLayout.SOUTH);
	}
}

class Control extends JPanel {

	private Model model;
	private View view;
	private JButton reset = new JButton("Reset");

	public Control(final Model model, final View view) {
		this.model = model;
		this.view = view;
		this.add(reset);
		reset.addActionListener(new ButtonHandler());
	}

	private class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			String cmd = e.getActionCommand();
			if ("Reset".equals(cmd)) {
				view.reset();
				model.reset();
			}
		}
	}
}

class View extends JPanel {

	private static final String s = "Click a button.";
	private Model model;
	private ColorIcon icon = new ColorIcon(80, Color.gray);
	private JLabel label = new JLabel(s, icon, JLabel.CENTER);

	public View(final Model model) {
		super(new BorderLayout());
		this.model = model;
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		this.add(label, BorderLayout.CENTER);
		this.add(genButtonPanel(), BorderLayout.SOUTH);
		model.addObserver(new ModelObserver());
	}

	public void reset() {
		icon.color = Color.gray;
	}

	private JPanel genButtonPanel() {
		JPanel panel = new JPanel();
		for (Piece p : Piece.values()) {
			PieceButton pb = new PieceButton(p);
			pb.addActionListener(new ButtonHandler());
			panel.add(pb);
		}
		return panel;
	}

	private class ModelObserver implements Observer {

		@Override
		public void update(final Observable o, final Object arg) {
			if (arg == null) {
				label.setText(s);
			} else {
				if ((Boolean) arg) {
					label.setText("Win!");
				} else {
					label.setText("Keep trying.");
				}
			}
		}
	}

	private class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			PieceButton pb = (PieceButton) e.getSource();
			icon.color = pb.piece.color;
			label.repaint();
			model.check(pb.piece);
		}
	}

	private static class PieceButton extends JButton {

		Piece piece;

		public PieceButton(final Piece piece) {
			this.piece = piece;
			setIcon(new ColorIcon(16, piece.color));
		}
	}

	private static class ColorIcon implements Icon {

		private int size;
		private Color color;

		public ColorIcon(final int size, final Color color) {
			this.size = size;
			this.color = color;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(color);
			g2d.fillOval(x, y, size, size);
		}

		@Override
		public int getIconWidth() {
			return size;
		}

		@Override
		public int getIconHeight() {
			return size;
		}
	}
}

class Model extends Observable {

	private static final Random rnd = new Random();
	private static final Piece[] pieces = Piece.values();
	private Piece hidden = init();

	private Piece init() {
		return pieces[rnd.nextInt(pieces.length)];
	}

	public void reset() {
		hidden = init();
		setChanged();
		notifyObservers();
	}

	public void check(final Piece guess) {
		setChanged();
		notifyObservers(guess.equals(hidden));
	}
}

enum Piece {

	Red(Color.red), Green(Color.green), Blue(Color.blue);
	public Color color;

	private Piece(final Color color) {
		this.color = color;
	}
}
