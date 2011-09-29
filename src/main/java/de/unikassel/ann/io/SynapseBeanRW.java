package de.unikassel.ann.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.Token;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.io.beans.SynapseBean;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Synapse;
import de.unikassel.ann.model.SynapseMatrix;

public class SynapseBeanRW extends BasicCsvReader {

	/* CSV */
	/* "from";"to";"value";"random" */

	static String[] header2beanMapping = new String[] { "from", "to", "value", "random" };
	static CellProcessor[] readProcessor = new CellProcessor[] { new ParseInt(), new ParseInt(),
			new Token("", Double.NaN, new ParseDoubleUni()), new Token("", false, new ParseBool()) };
	static CellProcessor[] writeProcessor = new CellProcessor[] { new ConvertNullTo("null"), new ConvertNullTo("null"),
			new ConvertNullTo(""), new ConvertNullTo("null") };

	public static List<SynapseBean> readData(final BufferedReader bufferedReader) {
		final List<SynapseBean> list = new ArrayList<SynapseBean>();

		final ICsvBeanReader reader = new CsvBeanReader(bufferedReader, pref);
		try {
			String[] header = reader.getCSVHeader(true);
			if (ArrayUtils.isEquals(header2beanMapping, header) == false) {
				throw new IllegalArgumentException("wrong header\n" + header);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		SynapseBean bean;

		try {
			while ((bean = reader.read(SynapseBean.class, header2beanMapping, readProcessor)) != null) {
				list.add(bean);
			}
		} catch (Exception e) {
			checkEndOfFile(e, bufferedReader);
		}
		return list;
	}

	public static void writeData(final NetConfig netConfig, final File f) {

		List<SynapseBean> synapseList = convertToSnypaseList(netConfig);

		FileWriter writer = null;
		try {
			writer = new FileWriter(f, true);
			writer.write(NetIO.OPEN_TAG + NetIO.SYNAPSE_TAG + "\n");
			writer.flush();
			ICsvBeanWriter beanWriter = new CsvBeanWriter(writer, pref);

			beanWriter.writeHeader(header2beanMapping);

			for (SynapseBean b : synapseList) {
				beanWriter.write(b, header2beanMapping, writeProcessor);
			}
			// force to write the content to the file, flush only doesn' exist
			beanWriter.close();
			writer = new FileWriter(f, true);
			writer.write(NetIO.CLOSE_TAG + "\n\n");
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static List<SynapseBean> convertToSnypaseList(final NetConfig netConfig) {
		List<SynapseBean> list = new ArrayList<SynapseBean>();
		Network net = netConfig.getNetwork();

		SynapseMatrix sm = net.getSynapseMatrix();
		Synapse[][] fs = sm.getSynapses();
		if (fs == null) {
			return list;
		}
		for (int i = 0; i < fs.length; i++) {
			for (int j = 0; j < fs[i].length; j++) {
				Synapse s = fs[i][j];
				if (s != null) {
					SynapseBean b = new SynapseBean();
					b.setFrom(i);
					b.setTo(j);
					b.setRandom(false);
					b.setValue(s.getWeight());
					list.add(b);
				}
			}
		}
		return list;
	}

}
