package org.liortamir.ariel.javalab.handler;

import java.util.Scanner;

import org.liortamir.ariel.javalab.bl.ContainerBuilder;
import org.liortamir.ariel.javalab.exception.DataFormatException;
import org.liortamir.ariel.javalab.persistency.ReadFile;

public class RemoveElementHandler extends AbstractHandler {

	public RemoveElementHandler(String fileName, Scanner scanner) {
		super(fileName, scanner);
	}
	
	

	@Override
	public String printMenu() {
		return "Remove element";
	}

	@Override
	public void customHandler(ContainerBuilder builder, ReadFile file) throws DataFormatException {

	}
	
	@Override
	public void handle() {
		int valueToRemove = 0;
		String rawValue;
		boolean isValid = false;
		
		if(holder.get() == null) {
			System.out.println("Container was not setup\nPlease select a container from the menu");
			return;
		}
		
		do {
			System.out.print("Enter the value to remove: ");
			rawValue = scanner.nextLine();
			try {
				valueToRemove = Integer.valueOf(rawValue);
				isValid = true;
			}catch(NumberFormatException e) {
				System.out.println("Invalid value. Only integers are allowed");
			}
		}while(!isValid);

		isValid = holder.get().removeElement(valueToRemove);
		if(isValid)
			System.out.println("Value " + rawValue + " removed");
		else
			System.out.println("Value " + rawValue + " was not removed since it does not exist");
	}

}
