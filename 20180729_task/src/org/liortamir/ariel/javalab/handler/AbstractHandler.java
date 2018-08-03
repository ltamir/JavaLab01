package org.liortamir.ariel.javalab.handler;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.liortamir.ariel.javalab.bl.ContainerBuilder;
import org.liortamir.ariel.javalab.bl.ContainerHolder;
import org.liortamir.ariel.javalab.container.Containable;
import org.liortamir.ariel.javalab.exception.DataFormatException;
import org.liortamir.ariel.javalab.persistency.ReadFile;

/**
 * Abstract class for all handlers<br>
 * Supplies {@link ReadFile} {@link VectorBuilder} for {@link #customHandler(VectorBuilder, ReadFile)}<br>
 * and {@link #printVector(Vector)}
 * 
 * @author liort
 *
 */
public abstract class AbstractHandler {

	protected String fileName;
	protected  Scanner scanner = null;
	protected ContainerHolder holder = ContainerHolder.getInstance();
	
	public AbstractHandler(String fileName) {
		this.fileName = fileName;
	}
	
	public AbstractHandler(String fileName, Scanner scanner) {
		this(fileName);
		this.scanner = scanner;
	}
	
	/**
	 * abstract method to implement the menu displayed for the handler
	 * @return String to display in the menu for this handler
	 */
	public abstract String printMenu();
	
	/**
	 * abstract method to implement vector logic
	 * @param builder {@link VectorBuilder} to create a vector
	 * @param file {@link ReadFile} to read the data from
	 * @throws VectorException allows implementing classes to throw vector logic related exception
	 */
	public abstract void customHandler(ContainerBuilder builder, ReadFile file) throws DataFormatException;
	
	
	public void handle() {
		
		ContainerBuilder builder = new ContainerBuilder();
		
		try {
			ReadFile file = new ReadFile(fileName);
			file.prepareFile();
			
			customHandler(builder, file);	// customization exit point
			
		}catch (FileNotFoundException e) {
			System.out.println("Error opening the file");
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}catch(DataFormatException e) {
			System.out.println(e.getMessage());
		}
	}

	
	public void printData(Containable<Integer> vect) {
		for(Integer i : vect)
			System.out.print(i + " ");
		
		System.out.println();
	}
	
	// ***** Identification of each Object *****//
	
	/**
	 * {@link org.liortamir.ariel.javalab.logic.BusinessLogic#BusinessLogic()} searches for the location<br>
	 * of the {@link ExitHandler} via {@link java.util.List#indexOf(Object)}.
	 * This override gives generic support to locate a menu handler  
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof AbstractHandler))
			return false;
		AbstractHandler other = (AbstractHandler)obj;
		
		return hashCode() == other.hashCode();
	}

	@Override
	public int hashCode() {
		return printMenu().hashCode();
	}
	
	

	
}
