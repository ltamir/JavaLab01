package org.liortamir.ariel.javalab.container;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListContainer<O> implements Containable<O> {

	private List<O> list = new LinkedList<O>();

	@Override
	public void addElement(O element) {
		list.add(element);
	}

	@Override
	public boolean removeElement(O element) {
		return list.remove(element);
	}

	@Override
	public O getHead() {
		if(isEmpty())
			return null;
		
		return list.get(0);
	}
	
	@Override
	public Iterator<O> iterator() {
		return list.iterator();
	}



	@Override
	public void emptyCollection() {
		list.clear();
		
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
	


}
