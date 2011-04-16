package de.unikassel.ann.model;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author anton
 * 
 * Klasse um ein Datensatz von Ein- und Ausgaben für das KNN zu strukturieren.<br>
 * Anzahl an Eingab- und Ausgabedaten bzw. Datensätzen (äußere Dimension) muss gleich sein.<br>
 * Die innere Dimension (Anzahl der Neuronen) kann sich unterscheiden
 *
 */
public class DataSet {

	/**
	 * Eingaben für das KNN<br>
	 * Äußere Dimension entspricht einem Datensatz<br>
	 * Innere Dimension entspricht einer Neuronenzuordnung
	 */
	private Double[][] input;
	
	/**
	 * Ausgaben für das KNN<br>
	 * Äußere Dimension entspricht einem Datensatz<br>
	 * Innere Dimension entspricht einer Neuronenzuordnung
	 */
	private Double[][] output;
	
	/**
	 * Erzeugt eine neue Instanz von Datensätzen für Ein- und Ausgaben<br>
	 * Arrrays müssen außen gleich lang und nicht nicht leer sein
	 * @param input
	 * @param output
	 */
	public DataSet(Double[][] input, Double[][] output) {
		if (isNotEmptyNested(input, output) && sameOuterSize(input, output)) {
			this.input = input;
			this.output = output;
		} else {
			System.err.println("could not set empty data");
		}
	}
	
	/**
	 * Fügt zu den bestehenden Datensätzen neue hinzu<br>
	 * Arrrays müssen außen gleich lang und nicht nicht leer sein<br>
	 * 
	 * @param inputRows
	 * @param outputRows
	 */
	public void addRows(Double[][] inputRows, Double[][] outputRows) {
		if (sameOuterSize(inputRows, outputRows)) {
			if (isNotEmptyNested(inputRows, outputRows)) {
				if (sameSize(inputRows[0], outputRows[0])) {
					input = (Double[][]) ArrayUtils.addAll(input, inputRows);
					output = (Double[][]) ArrayUtils.addAll(output, outputRows);
				} else {
					throw new IllegalArgumentException("inner array size does not match to");
				}
			} else {
				input = inputRows;
				output = outputRows;
			}
		} else {
			throw new IllegalArgumentException("size of input and output must be equal");
		}
	}


	/**
	 * Fügt einzelnen Datensatz zu den bisherigen hinzu
	 * @param inputRow
	 * @param outputRow
	 */
	public void addRow(Double[] inputRow, Double[] outputRow) {
		if (sameSize(inputRow, outputRow)) {
			input = (Double[][]) ArrayUtils.add(input, inputRow);
			output = (Double[][]) ArrayUtils.add(output, outputRow);
		}
		
	}
	
	/**
	 * {@link #addRow(Double[], Double[])}
	 */
	public void addRow(List<Double> inputRow, List<Double> outputRow) {
		Double[] in = new Double[0];
		Double[] out = new Double[0];
		addRow(inputRow.toArray(in), outputRow.toArray(out));
	}

	/**
	 * @return Äußere Länge von {@link #input} bzw. {@link #output}
	 */
	public Integer getRows() {
		if (input.length == output.length) {
			return input.length;
		}
		throw new IllegalAccessError("input and output size is different");
	}
	
	public Double[][] getInput() {
		return input;
	}
	
	public Double[][] getOutput() {
		return output;
	}
	
	public DataPair getPair(Integer index) {
		return new DataPair(input[index], output[index]);
	}
	
	private boolean sameOuterSize(Double[][] inputRows, Double[][] outputRows) {
		return inputRows.length == outputRows.length;
	}
	
	/**
	 * Prüfe ob die Arrays <b>nicht</b> miteinander gleich lang sind, 
	 * sondern mit jeweils mit der inneren Länge von
	 * {@link #input} und {@link #output}
	 * @param inputRows
	 * @param outputRows
	 * @return true wenn innere Länge gleich, sonst false
	 */
	private boolean sameSize(Double[] inputRows, Double[] outputRows) {
		return inputRows.length == input[0].length && outputRows.length == output[0].length;
	}

	/**
	 * Prüfe ob arrays nicht in äußerer und innerer Dimension leer sind
	 * @param rows
	 * @return true wenn nicht leer, sonst false
	 */
	private boolean isNotEmptyNested(Double[][]... rows) {
		boolean result = true;
		for (Double[][] row : rows) {
			result = result && ArrayUtils.isNotEmpty(row);
			result = result && ArrayUtils.isNotEmpty(row[0]);
		}
		return result;
	}
	
	
}
