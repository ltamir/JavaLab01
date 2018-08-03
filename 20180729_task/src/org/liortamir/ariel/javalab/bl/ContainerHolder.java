package org.liortamir.ariel.javalab.bl;

import org.liortamir.ariel.javalab.container.Containable;

public class ContainerHolder {

	private static ContainerHolder instance = new ContainerHolder();
	private Containable<Integer> container;
	
	private ContainerHolder() {}
	
	public static ContainerHolder getInstance() {
		return ContainerHolder.instance;
	}
	
	public Containable<Integer> get() {
		return this.container;
	}
	
	public void set(Containable<Integer> container) {
		this.container = container;
	}
	
}
