package org.liortamir.ariel.javalab.handler;

import org.liortamir.ariel.javalab.container.ContainerType;

public class ListHandler extends AbstractContainerHandler {

	public ListHandler(String fileName) {
		super(fileName);
	}
	
	@Override
	public String printMenu() {
		return "Create List container";
	}

	@Override
	protected void customFactory() {
		holder.set(containerFactory.getContainable(ContainerType.LIST));
		System.out.println("List container created successfully");
	}

}
