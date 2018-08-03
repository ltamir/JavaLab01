package org.liortamir.ariel.javalab.handler;

import org.liortamir.ariel.javalab.bl.ContainerBuilder;
import org.liortamir.ariel.javalab.exception.DataFormatException;
import org.liortamir.ariel.javalab.persistency.ReadFile;

public class EmptyCollectionHandler extends AbstractHandler {

	public EmptyCollectionHandler(String fileName) {
		super(fileName);
	}

	@Override
	public String printMenu() {
		return "Empty container";
	}

	@Override
	public void customHandler(ContainerBuilder builder, ReadFile file) throws DataFormatException {}

	@Override
	public void handle() {
		if(holder.get() == null) {
			System.out.println("Container was not setup\nPlease select a container from the menu");
			return;
		}
		
		holder.get().emptyCollection();
		System.out.println("Container is now empty");
	}
	

}
