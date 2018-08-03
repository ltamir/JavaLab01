package org.liortamir.ariel.javalab.handler;

import org.liortamir.ariel.javalab.container.ContainerType;

public class QueueHandler extends AbstractContainerHandler {

	public QueueHandler(String fileName) {
		super(fileName);
	}

	@Override
	public String printMenu() {
		return "Create Queue container";
	}

	@Override
	protected void customFactory() {
		holder.set(containerFactory.getContainable(ContainerType.QUEUE));
		System.out.println("Queue container created successfully.");
	}

	

}
