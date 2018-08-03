package org.liortamir.ariel.javalab.handler;

import org.liortamir.ariel.javalab.bl.ContainerBuilder;
import org.liortamir.ariel.javalab.exception.DataFormatException;
import org.liortamir.ariel.javalab.persistency.ReadFile;

public class PrintContainerHandler extends AbstractHandler {

	public PrintContainerHandler(String fileName) {
		super(fileName);
	}

	@Override
	public String printMenu() {
		return "Print all container";
	}

	@Override
	public void customHandler(ContainerBuilder builder, ReadFile file) throws DataFormatException {}

	@Override
	public void handle() {
		if(holder.get() == null) {
			System.out.println("Container was not setup\nPlease select a container from the menu");
			return;
		}
		
		printData(holder.get());
	}
	

}
