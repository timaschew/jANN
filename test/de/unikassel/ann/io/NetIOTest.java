package de.unikassel.ann.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import de.unikassel.ann.io.beans.TrainingBean;

public class NetIOTest {
	
	@Test
	public void test() throws IOException, ClassNotFoundException {
		
//		File f = new File("/Users/anton/Develop/Projekte/ANNtool/test/xor.csv");
		File f = new File("/Users/anton/Develop/Projekte/ANNtool/test/net_cfg.csv");

		
		NetIO reader = new NetIO();
		reader.readData(f);
		
		
	}

}
