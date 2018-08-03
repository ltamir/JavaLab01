package org.liortamir.ariel.javalab.bl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.liortamir.ariel.javalab.handler.AbstractHandler;
import org.liortamir.ariel.javalab.handler.EmptyCheckerHandler;
import org.liortamir.ariel.javalab.handler.EmptyCollectionHandler;
import org.liortamir.ariel.javalab.handler.ExitHandler;
import org.liortamir.ariel.javalab.handler.HeadElementHandler;
import org.liortamir.ariel.javalab.handler.ListHandler;
import org.liortamir.ariel.javalab.handler.PrintContainerHandler;
import org.liortamir.ariel.javalab.handler.QueueHandler;
import org.liortamir.ariel.javalab.handler.RemoveElementHandler;
import org.liortamir.ariel.javalab.handler.StackHandler;

/**
 * This class is responsible for menu handling and executing the {@link AbstractHandler} <br>
 * corresponding to the menu item selected by the user.
 * @author liort
 *
 */
public class BusinessLogic {

	private Scanner scanner = new Scanner(System.in);
	private List<AbstractHandler> menuLst = new ArrayList<>();
	private int exitMenuNum;	// holds the exit menu handler position in the menu list
	private String dataFile = "Data.txt";
	
	/**
	 * Constructor
	 * Build the menu and set the Exit menu handler position.
	 */
	public BusinessLogic() {
		AbstractHandler exitHandler = new ExitHandler(null);
		
		// TODO add menu handlers
		menuLst.add(new ListHandler(dataFile));
		menuLst.add(new QueueHandler(dataFile));
		menuLst.add(new StackHandler(dataFile));
		menuLst.add(new RemoveElementHandler(null, this.scanner));
		menuLst.add(new EmptyCheckerHandler(null));
		menuLst.add(new EmptyCollectionHandler(null));
		menuLst.add(new HeadElementHandler(null));
		menuLst.add(new PrintContainerHandler(null));
		menuLst.add(exitHandler);
		
		exitMenuNum = menuLst.indexOf(exitHandler);
	}
	
	public BusinessLogic(String dataFile) {
		this.dataFile = dataFile;
	}
	
	/**
	 * Print the menu to screen<br>
	 * Execute the handler corresponding to the menu item the user selected
	 */
	public void menuHandler() {
		int selectedMenuItem;
		
		do {
			printMenu();
			selectedMenuItem = handleUserSelection();
			menuLst.get(selectedMenuItem).handle();
			System.out.println("*****  Press any key to continue   *****");
//			try {
			scanner.nextLine();
//			}catch(java.util.NoSuchElementException e) {}
		}while(true);
	}
	
	
	/**
	 * Print the menu to the screen
	 */
	private void printMenu() {
		System.out.println("Select an option from the menu");
		
		Integer menuNum = 1;
		for(AbstractHandler item : menuLst)
			System.out.println(menuNum++ + ". " + item.printMenu());
		
		System.out.print("Enter you selection: ");
	}
	
	
	/**
	 * Read the user input, validate it and decrease by 1 since<br>
	 * the ArrayList of handlers {@link #menuLst} starts with position 0
	 * @return menu item the user selected
	 */
	private int handleUserSelection() {
		int userInput = 0;
		String strUserInput = scanner.nextLine();
		
		try {
			userInput = Integer.parseInt(strUserInput);
			
			if(userInput > menuLst.size() || userInput < 1)
				throw new IllegalArgumentException();
			userInput--;
		}catch(NumberFormatException e) {
			System.out.println("Invalid menu selection\nPlease try again:");
		}catch(IllegalArgumentException e) {
			userInput = exitMenuNum;
			System.out.println("Selected menu item does not exist\nExiting program");
		}
		
		return userInput;
		
	}
}
