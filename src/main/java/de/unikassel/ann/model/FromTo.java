/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.model;

/**
 * @author anton
 * 
 */
public class FromTo {

	public Integer from;
	public Integer to;

	public FromTo() {

	}

	public FromTo(final Integer from, final Integer to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		return from + "->" + to;
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof FromTo) {
			FromTo other = (FromTo) o;
			if (from.equals(other.from) && to.equals(other.to)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 7 * from.hashCode() + 11 * to.hashCode();
	}

}
