package org.liortamir.ariel.javalab.persistency;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a vector of integers.
 * Allows iteration on the members via {@link Iterable}
 * @author liort
 *
 */
public class Vector implements Iterable<MutableInteger>{

	/**
	 * MutableInteger will allow {@link org.liortamir.ariel.javalab.logic.VectorHelper}<br>
	 * to modify each list member directly
	 */
	private List<MutableInteger> vector = new ArrayList<>();
	
	
	// ***** add get & set ***** //
	
	public void addItem(Integer num) {
		MutableInteger mInt = new MutableInteger(num);
		vector.add(mInt);
	}

	public void addItem(MutableInteger mInt) {
		vector.add(mInt);
	}
	
	public Integer get(int index) {
		return this.vector.get(index).getValue();
	}
	
	public void set(int index, Integer value) {
		this.vector.get(index).setValue(value);
	}
	
	
	// ***** size and iteration ***** //
	
	public int size() {
		return this.vector.size();
	}
	
	@Override
	public Iterator<MutableInteger> iterator() {
		return vector.iterator();
	}
	
}
