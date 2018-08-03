package org.liortamir.ariel.javalab.handler;

import org.liortamir.ariel.javalab.bl.ContainerBuilder;
import org.liortamir.ariel.javalab.exception.DataFormatException;
import org.liortamir.ariel.javalab.persistency.ReadFile;

public class EmptyCheckerHandler extends AbstractHandler {
	
	public EmptyCheckerHandler(String fileName) {
		super(fileName);
	}

	@Override
	public String printMenu() {
		return "Check if the container is empty";
	}

	@Override
	public void customHandler(ContainerBuilder builder, ReadFile file) throws DataFormatException {}

	@Override
	public void handle() {
		if(holder.get() == null) {
			System.out.println("Container was not setup\nPlease select a container from the menu");
			return;
		}
		
		boolean isEmpty = holder.get().isEmpty();
		if(isEmpty)
			System.out.println("Collection is empty");
		else
			System.out.println("Collection is not empty");
	}

}
