/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author anton
 * 
 */
public class EdgeMap {

	/**
	 * A synapse which have a connection from neuron id 2 to neuron id 6 and the weight value 1.23 can put into the map this way: <code>
	 * synapseMap.put(new FromTo(2,6), 1.23);
	 * </code
	 */
	private Map<FromTo, Double> edgeMap;

	public EdgeMap() {
		edgeMap = new HashMap<FromTo, Double>();
	}

	public Map<FromTo, Double> getMap() {
		return edgeMap;
	}

	public void put(final FromTo key, final Double value) {
		edgeMap.put(key, value);
	}

	public Double get(final FromTo key) {
		return edgeMap.get(key);
	}

	/**
	 * <pre>
	 * from->to
	 * key => value
	 * {1->2 => 1.23}
	 * 
	 * <pre>
	 * getToForFrom(1) returns [2]
	 * 
	 * @param from
	 * @return
	 */
	public List<Integer> getToForFrom(final Integer from) {
		Map map = getSynapseMap(from, false, true);
		return new ArrayList<Integer>(map.keySet());
	}

	/**
	 * <pre>
	 * from->to
	 * key => value
	 * {1->2 => 1.23}
	 * 
	 * <pre>
	 * getFromForTo(2) returns [1]
	 * @param to
	 * @return
	 */
	public List<Integer> getFromForTo(final Integer to) {
		Map map = getSynapseMap(to, false, false);
		return new ArrayList<Integer>(map.keySet());
	}

	/**
	 * <pre>
	 * from->to
	 * key => value
	 * {1->2 => 1.23}
	 * 
	 * <pre>
	 * getSynapsesToForFrom(1) returns {2 => 1.23}
	 * 
	 * @param from
	 * @return
	 */
	public Map<Integer, Double> getSynapsesToForFrom(final Integer from) {
		return getSynapseMap(from, false, true);
	}

	/**
	 * <pre>
	 * from->to
	 * key => value
	 * {1->2 => 1.23}
	 * 
	 * <pre>
	 * getSynapsesFromToForFrom(1) returns {1->2 => 1.23}
	 * 
	 * @param from
	 * @return
	 */
	public Map<FromTo, Double> getSynapsesFromToForFrom(final Integer from) {
		return getSynapseMap(from, true, true);
	}

	/**
	 * <pre>
	 * from->to
	 * key => value
	 * {1->2 => 1.23}
	 * 
	 * <pre>
	 * getSynapsesFromForTo(2) returns {1 => 1.23}
	 * 
	 * @param to
	 * @return
	 */
	public Map<FromTo, Double> getSynapsesFromForTo(final Integer to) {
		return getSynapseMap(to, false, false);
	}

	/**
	 * <pre>
	 * from->to
	 * key => value
	 * {1->2 => 1.23}
	 * 
	 * <pre>
	 * getSynapsesFromToForTo(2) returns {1->2 => 1.23}
	 * 
	 * @param to
	 * @return
	 */
	public Map<FromTo, Double> getSynapsesFromToForTo(final Integer to) {
		return getSynapseMap(to, true, false);
	}

	/**
	 * Returns multiple values for the searching index, which can be the 'from' or 'to'
	 * 
	 * @param searchFor
	 * @param both
	 *            returning only the opposite of what you searching for or both (from and to)
	 * @param forward
	 *            searching for as 'from' if true, otherwise searching as 'to'
	 */
	private Map getSynapseMap(final Integer searchFor, final boolean both, final boolean forward) {
		Map<Integer, Double> from2toMap = new HashMap<Integer, Double>();
		Map<FromTo, Double> from2fromToMap = new HashMap<FromTo, Double>();
		Map<Integer, Double> to2fromMap = new HashMap<Integer, Double>();
		Map<FromTo, Double> to2fromToMap = new HashMap<FromTo, Double>();

		for (Entry<FromTo, Double> e : edgeMap.entrySet()) {
			FromTo ft = e.getKey();
			Double weight = e.getValue();
			if (forward) {
				if (ft.from.equals(searchFor)) {
					from2toMap.put(ft.to, weight);
					from2fromToMap.put(ft, weight);
				}
			} else {
				if (ft.to.equals(searchFor)) {
					to2fromMap.put(ft.from, weight);
					to2fromToMap.put(ft, weight);
				}
			}

		}
		if (forward) {
			if (both) {
				return from2toMap;
			}
			return from2fromToMap;
		}
		// backward
		if (both) {
			return to2fromMap;
		}
		return to2fromToMap;

	}

}
