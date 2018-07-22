package org.liortamir.ariel.javalab.persistency;

/**
 * Since Integer is immutable, this class wraps it to behave as mutable
 * @author liort
 *
 */
public class MutableInteger {

	private Integer value;
	
	
	public MutableInteger(Integer value) {
		super();
		this.value = value;
	}
	
	public MutableInteger() {
		
	}
	
	// ***** getters and setters ***** //

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
}
