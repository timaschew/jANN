package de.unikassel.ann.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;

import org.apache.commons.lang.ArrayUtils;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.dataset.ArrayDataSet;
import com.panayotis.gnuplot.dataset.DataSet;
import com.panayotis.gnuplot.dataset.PointDataSet;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.plot.Plot;
import com.panayotis.gnuplot.swing.JPlot;

public class TrainErrorPlot implements TrainErrorListener {


	private PointDataSet<Double> numberSet;
	private JPlot plot;
	private JavaPlot p;
	private int run = 0;
	private int iteration = 0;
	private ReentrantLock rl;
	
	
	public void updateUI() {
		if(rl.tryLock()) {
			plot.plot();
			rl.unlock();
			plot.repaint();
		}
	}
	
	public void init() {
		rl = new ReentrantLock();
		numberSet = new PointDataSet<Double>();
		DataSetPlot dataSetPlot = new DataSetPlot(numberSet);
		dataSetPlot.set("with", "lines");
		dataSetPlot.setTitle("run no #"+run);
		Properties property = new Properties();
		String path = "";
		try {
			InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("config.properties");
			property.load(inputStream);
			path = property.getProperty("gnuplot.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JavaPlot javaPlot = new JavaPlot(path, false);
		javaPlot.setTitle("Trainingsfehler");
		plot = new JPlot(javaPlot);
		p = plot.getJavaPlot();
		p.addPlot(new int[][]{{0,0}});
		plot.plot();
		p.addPlot(dataSetPlot);
	}
	
	
	public void nextRun() {
		iteration = 0;
		run++;
		numberSet = new PointDataSet<Double>();
		DataSetPlot dataSetPlot = new DataSetPlot(numberSet);
		dataSetPlot.set("with", "lines");
		dataSetPlot.setTitle("run no #"+run);
		rl.lock();
		p.addPlot(dataSetPlot);
		rl.unlock();
	}
	
	@Override
	public void addError(Integer iter, Double error) {
		numberSet.addPoint(Integer.valueOf(iter).doubleValue(), error);
	}
	
	public Component getPlotPanel() {
		return plot;
	}

}
