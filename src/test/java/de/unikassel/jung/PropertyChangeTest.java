package de.unikassel.jung;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.supercsv.cellprocessor.Token;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCSVException;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

public class PropertyChangeTest implements PropertyChangeListener {
	
	public static void main(String[] args) throws SuperCSVException, IOException {
	
		new PropertyChangeTest();
	}

	public PropertyChangeTest() {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int init = 1;
		int min = 0;
		int max = 10;
		int step = 1;
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(init, min, max, step);
		
		PropertyChangeSupport pcs = new PropertyChangeSupport(spinnerModel);
		
		pcs.addPropertyChangeListener("value", this);
		
		JSpinner spinner = new JSpinner(spinnerModel);
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", this);

		frame.getContentPane().add(spinner);
		frame.setVisible(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getOldValue()+" -> "+evt.getNewValue());
	}

}
