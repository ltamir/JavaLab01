package org.liortamir.ariel.javalab.container;

import java.util.Iterator;
import java.util.Stack;

public class StackContainer<O> implements Containable<O> {

	private Stack<O> list = new Stack<O>();


	@Override
	public void addElement(O element) {
		list.push(element);
	}

	@Override
	public boolean removeElement(O element) {
		return list.remove(element);
		
	}
	
	@Override
	public O getHead() {
		if(isEmpty())
			return null;
		
		return list.peek();
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
		return list.isEmpty();
	}

}
