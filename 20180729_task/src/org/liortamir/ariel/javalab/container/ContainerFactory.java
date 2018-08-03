package org.liortamir.ariel.javalab.container;

public class ContainerFactory <O>{
	
	public Containable<O> getContainable(ContainerType type){
		switch(type) {
		case STACK:
			return new StackContainer<O>();
		case LIST:
			return new ListContainer<O>();
		case QUEUE:
			return new QueueContainer<O>();
			default:
				return null;
		}
	}


}
