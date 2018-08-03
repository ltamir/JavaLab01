package org.liortamir.ariel.javalab.container;

public interface Containable <O> extends Iterable<O> {


	public void addElement(O element);
	
	public boolean removeElement(O element);
	
	public O getHead();
	
	public void emptyCollection();
		
	public boolean isEmpty();
	

}
