package de.unikassel.ann.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import de.unikassel.ann.controller.Settings;

/**
 * @author anton
 * 
 *         Klasse um ein Datensatz von Ein- und Ausgaben für das KNN zu strukturieren.<br>
 *         Kann sowohl für als Trainingdata set, als auch als Testdata set verwendet werden.<br>
 *         Anzahl an Eingabe- und Ausgabedaten bzw. Datensätzen (äußere Dimension) muss gleich sein.<br>
 *         Die innere Dimension (Anzahl der Neuronen) kann sich unterscheiden
 * 
 */
public class DataPairSet {

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
	private Double[][] ideal;

	/**
	 * Erzeugt eine neue Instanz von Datensätzen für Ein- und Ausgaben<br>
	 * Arrrays müssen außen gleich lang und nicht nicht leer sein
	 * 
	 * @param input
	 * @param ideal
	 */
	public DataPairSet(final Double[][] input, final Double[][] ideal) {
		if (isNotEmptyNested(input, ideal) && sameOuterSize(input, ideal)) {
			this.input = input;
			this.ideal = ideal;
		} else {
			System.err.println("could not set empty data");
		}
	}

	// not tested
	// public DataPairSet(final List<DataPair> pairs) {
	// int i = 0;
	// int rows = pairs.size();
	// input = new Double[rows][0];
	// ideal = new Double[rows][0];
	//
	// for (DataPair p : pairs) {
	// input[i] = p.getInput();
	// ideal[i] = p.getIdeal();
	// i++;
	// }
	// }

	public DataPairSet() {
	}

	public DataPairSet(final DataPairSet copy) {
		if (copy == null || ArrayUtils.isEmpty(copy.ideal) || ArrayUtils.isEmpty(copy.input)) {
			throw new NullPointerException("dataset cannot be null");
		}
		input = new Double[copy.input.length][copy.input[0].length];
		ideal = new Double[copy.ideal.length][copy.ideal[0].length];
		for (int i = 0; i < copy.input.length; i++) {
			for (int j = 0; j < copy.input[i].length; j++) {
				input[i][j] = copy.input[i][j];
			}
		}
		for (int i = 0; i < copy.ideal.length; i++) {
			for (int j = 0; j < copy.ideal[i].length; j++) {
				ideal[i][j] = copy.ideal[i][j];
			}
		}
	}

	/**
	 * Fügt zu den bestehenden Datensätzen neue hinzu<br>
	 * Arrrays müssen außen gleich lang und nicht nicht leer sein<br>
	 * 
	 * @param inputRows
	 * @param idealRows
	 */
	public void addRows(final Double[][] inputRows, final Double[][] idealRows) {
		if (sameOuterSize(inputRows, idealRows)) {
			if (isNotEmptyNested(inputRows, idealRows)) {
				if (sameSize(inputRows[0], idealRows[0])) {
					input = (Double[][]) ArrayUtils.addAll(input, inputRows);
					ideal = (Double[][]) ArrayUtils.addAll(ideal, idealRows);
				} else {
					throw new IllegalArgumentException("inner array size does not match to");
				}
			} else {
				input = inputRows;
				ideal = idealRows;
			}
		} else {
			throw new IllegalArgumentException("size of input and ideal must be equal");
		}
	}

	/**
	 * Fügt einzelnen Datensatz zu den bisherigen hinzu
	 * 
	 * @param inputRow
	 * @param idealRow
	 */
	public void addRow(final Double[] inputRow, final Double[] idealRow) {
		if (sameSize(inputRow, idealRow)) {
			input = (Double[][]) ArrayUtils.add(input, inputRow);
			ideal = (Double[][]) ArrayUtils.add(ideal, idealRow);
		}

	}

	/**
	 * {@link #addRow(Double[], Double[])}
	 */
	public void addRow(final List<Double> inputRow, final List<Double> idealRow) {
		Double[] in = new Double[0];
		Double[] out = new Double[0];
		addRow(inputRow.toArray(in), idealRow.toArray(out));
	}

	/**
	 * @return Äußere Länge von {@link #input} bzw. {@link #ideal}
	 */
	public Integer getRows() {
		if (input.length == ideal.length) {
			return input.length;
		}
		throw new IllegalAccessError("input and ideal size is different");
	}

	public Double[][] getInput() {
		return input;
	}

	public Double[][] getIdeal() {
		return ideal;
	}

	public Double[][] getInputAndIdeal() {
		Double[][] mixed = new Double[input.length][input[0].length + ideal[0].length];
		for (int i = 0; i < input.length; i++) {
			mixed[i] = (Double[]) ArrayUtils.addAll(input[i], ideal[i]);
		}
		return mixed;
	}

	public void setInputAndIdeal(final Double[][] inAndOut, final int inputLenght, final int outputLength) {
		if (inAndOut.length == 0) {
			return;
		}
		if (inAndOut[0].length != inputLenght + outputLength) {
			return;
		}
		input = null;
		ideal = null;
		for (int i = 0; i < inAndOut.length; i++) {
			addRow((Double[]) ArrayUtils.subarray(inAndOut[i], 0, inputLenght),
					(Double[]) ArrayUtils.subarray(inAndOut[i], inputLenght, inputLenght + outputLength));
		}
	}

	public DataPair getPair(final Integer index) {
		return new DataPair(input[index], ideal[index]);
	}

	public List<DataPair> getPairs() {
		List<DataPair> list = new ArrayList<DataPair>();
		for (int i = 0; i < input.length; i++) {
			list.add(getPair(i));
		}
		return list;
	}

	private boolean sameOuterSize(final Double[][] inputRows, final Double[][] idealRows) {
		return inputRows.length == idealRows.length;
	}

	/**
	 * Prüfe ob die Arrays <b>nicht</b> miteinander gleich lang sind, sondern mit jeweils mit der inneren Länge von {@link #input} und
	 * {@link #ideal}
	 * 
	 * @param inputRows
	 * @param idealRows
	 * @return true wenn innere Länge gleich, sonst false
	 */
	private boolean sameSize(final Double[] inputRows, final Double[] idealRows) {
		if (input == null && ideal == null) {
			return true;
		}
		return inputRows.length == input[0].length && idealRows.length == ideal[0].length;
	}

	/**
	 * Prüfe ob arrays nicht in äußerer und innerer Dimension leer sind
	 * 
	 * @param rows
	 * @return true wenn nicht leer, sonst false
	 */
	private boolean isNotEmptyNested(final Double[][]... rows) {
		boolean result = true;
		for (Double[][] row : rows) {
			result = result && ArrayUtils.isEmpty(row) == false;
			result = result && ArrayUtils.isEmpty(row[0]) == false;
		}
		return result;
	}

	public void addPair(final DataPair pair) {
		addRow(pair.getInput(), pair.getIdeal());
	}

	/**
	 * Resets every ideal value in the set to Double.NaN
	 */
	public void resetIdeal() {
		for (int i = 0; i < ideal.length; i++) {
			for (int j = 0; j < ideal[i].length; j++) {
				ideal[i][j] = Double.NaN;
			}
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof DataPairSet) {
			DataPairSet o = (DataPairSet) other;
			return ArrayUtils.isEquals(input, o.input) && ArrayUtils.isEquals(ideal, o.ideal);
		}
		return false;
	}

	@Override
	public String toString() {
		NumberFormat fmt = new DecimalFormat("#.####", Settings.decimalSymbols);
		StringBuilder sb = new StringBuilder();
		for (DataPair p : getPairs()) {
			for (Double d : p.getInput()) {
				sb.append(fmt.format(d));
				sb.append(" ");
			}
			sb.append(" --> ");
			for (Double d : p.getIdeal()) {
				sb.append(fmt.format(d));
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
