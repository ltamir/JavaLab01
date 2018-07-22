package org.liortamir.ariel.javalab.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.liortamir.ariel.javalab.menuhandler.AbstractMenuHandler;
import org.liortamir.ariel.javalab.menuhandler.ExitMenuHandler;
import org.liortamir.ariel.javalab.menuhandler.VectorAdditionHandler;
import org.liortamir.ariel.javalab.menuhandler.VectorCartesianMultiplicationHandler;
import org.liortamir.ariel.javalab.menuhandler.VectorScalarMultiplicationHandler;

/**
 * This class is responsible for menu handling and executing the {@link AbstractMenuHandler} <br>
 * corresponding to the menu item selected by the user.
 * @author liort
 *
 */
public class BusinessLogic {

	private Scanner scanner = new Scanner(System.in);
	private List<AbstractMenuHandler> menuLst = new ArrayList<>();
	private int exitMenuNum;	// holds the exit menu handler position in the menu list
	
	/**
	 * Constructor
	 * Build the menu and set the Exit menu handler position.
	 */
	public BusinessLogic() {
		AbstractMenuHandler exitHandler = new ExitMenuHandler(null);
		
		menuLst.add(new VectorAdditionHandler("additionData.txt"));
		menuLst.add(new VectorScalarMultiplicationHandler("scalarMultiplicationData.txt"));
		menuLst.add(new VectorCartesianMultiplicationHandler("cartesianMultiplicationData.txt"));
		menuLst.add(exitHandler);
		
		exitMenuNum = menuLst.indexOf(exitHandler);
		
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
		}while(true);
	}
	
	
	/**
	 * Print the menu to the screen
	 */
	private void printMenu() {
		System.out.println("Select an option from the menu");
		
		Integer menuNum = 1;
		for(AbstractMenuHandler item : menuLst)
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
