package org.liortamir.ariel.javalab.container;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class QueueContainer<O> implements Containable<O> {

	private Queue<O> queue = new LinkedList<>();
	
	@Override
	public void addElement(O element) {
		queue.offer(element);
	}

	@Override
	public boolean removeElement(O element) {
		return queue.remove(element);
	}

	@Override
	public O getHead() {
		return queue.peek();
	}
	
	@Override
	public Iterator<O> iterator() {
		return queue.iterator();
	}


	@Override
	public void emptyCollection() {
		queue.clear();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}


	

}
