package org.liortamir.ariel.javalab.handler;

import org.liortamir.ariel.javalab.container.ContainerType;

public class StackHandler extends AbstractContainerHandler {

	public StackHandler(String fileName) {
		super(fileName);
	}

	@Override
	protected void customFactory() {
		holder.set(containerFactory.getContainable(ContainerType.STACK));
		System.out.println("Stack container created successfully");
	}

	@Override
	public String printMenu() {
		return "Create Stack container";
	}

}
