/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author anton
 * @param <T>
 * 
 */
public class EdgeMap<T> {

	/**
	 * A synapse which have a connection from neuron id 2 to neuron id 6 and the weight value 1.23 can put into the map this way: <code>
	 * synapseMap.put(new FromTo(2,6), 1.23);
	 * </code
	 */
	private Map<FromTo, T> edgeMap;

	public EdgeMap() {
		edgeMap = new HashMap<FromTo, T>();
	}

	public Map<FromTo, T> getMap() {
		return edgeMap;
	}

	public void put(final FromTo key, final T value) {
		edgeMap.put(key, value);
	}

	public T get(final FromTo key) {
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
	public Set<Integer> getToForFrom(final Integer from) {
		Map map = getSynapseMap(from, false, true);
		return map.keySet();
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
	public Set<Integer> getFromForTo(final Integer to) {
		Map map = getSynapseMap(to, false, false);
		return map.keySet();
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
	public Map<Integer, T> getSynapsesToForFrom(final Integer from) {
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
	public Map<FromTo, T> getSynapsesFromToForFrom(final Integer from) {
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
	public Map<FromTo, T> getSynapsesFromForTo(final Integer to) {
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
	public Map<FromTo, T> getSynapsesFromToForTo(final Integer to) {
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
		Map<Integer, T> from2toMap = new HashMap<Integer, T>();
		Map<FromTo, T> from2fromToMap = new HashMap<FromTo, T>();
		Map<Integer, T> to2fromMap = new HashMap<Integer, T>();
		Map<FromTo, T> to2fromToMap = new HashMap<FromTo, T>();

		for (Entry<FromTo, T> e : edgeMap.entrySet()) {
			FromTo ft = e.getKey();
			T value = e.getValue();
			if (forward) {
				if (ft.from.equals(searchFor)) {
					from2toMap.put(ft.to, value);
					from2fromToMap.put(ft, value);
				}
			} else {
				if (ft.to.equals(searchFor)) {
					to2fromMap.put(ft.from, value);
					to2fromToMap.put(ft, value);
				}
			}

		}
		if (forward) {
			if (both) {
				return from2fromToMap;
			}
			return from2toMap;
		}
		// backward
		if (both) {
			return to2fromToMap;
		}
		return to2fromMap;

	}

}
