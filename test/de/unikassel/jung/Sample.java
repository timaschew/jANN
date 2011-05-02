package de.unikassel.jung;


import java.io.IOException;

import edu.uci.ics.jung.samples.GraphEditorDemo;
import edu.uci.ics.jung.samples.UnicodeLabelDemo;
import edu.uci.ics.jung.samples.PluggableRendererDemo; // very nice

public class Sample {
	public static void main (String args[]) throws IOException {
		PluggableRendererDemo.main(null);
		UnicodeLabelDemo.main(null);
		GraphEditorDemo.main(null);
	}
}
