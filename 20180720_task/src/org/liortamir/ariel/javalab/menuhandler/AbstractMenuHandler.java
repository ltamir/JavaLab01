package org.liortamir.ariel.javalab.menuhandler;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.liortamir.ariel.javalab.exception.VectorException;
import org.liortamir.ariel.javalab.logic.VectorBuilder;
import org.liortamir.ariel.javalab.persistency.MutableInteger;
import org.liortamir.ariel.javalab.persistency.ReadFile;
import org.liortamir.ariel.javalab.persistency.Vector;

/**
 * Abstract class for all handlers<br>
 * Supplies {@link ReadFile} {@link VectorBuilder} for {@link #customHandler(VectorBuilder, ReadFile)}<br>
 * and {@link #printVector(Vector)}
 * 
 * @author liort
 *
 */
public abstract class AbstractMenuHandler {

	protected String fileName;
	
	
	public AbstractMenuHandler(String fileName) {
		this.fileName = fileName;
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
	public abstract void customHandler(VectorBuilder builder, ReadFile file) throws VectorException;
	
	
	public void handle() {
		
		VectorBuilder builder = new VectorBuilder();
		
		try {
			ReadFile file = new ReadFile(fileName);
			file.prepareFile();
			
			customHandler(builder, file);
			
		}catch (FileNotFoundException e) {
			System.out.println("Error opening the file");
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}catch(VectorException e) {
			System.out.println(e.getMessage());
		}
	}

	
	public void printVector(Vector vect) {
		for(MutableInteger i : vect)
			System.out.print(i + " ");
		
		System.out.println();
	}
	
	// ***** Identification of each Object *****//
	
	/**
	 * {@link org.liortamir.ariel.javalab.logic.BusinessLogic#BusinessLogic()} searches for the location<br>
	 * of the {@link ExitMenuHandler} via {@link java.util.List#indexOf(Object)}.
	 * This override gives generic support to locate a menu handler  
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof AbstractMenuHandler))
			return false;
		AbstractMenuHandler other = (AbstractMenuHandler)obj;
		
		return hashCode() == other.hashCode();
	}

	@Override
	public int hashCode() {
		return printMenu().hashCode();
	}
	
	

	
}
