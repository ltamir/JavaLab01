package org.liortamir.ariel.javalab.logic;

import org.liortamir.ariel.javalab.exception.VectorException;
import org.liortamir.ariel.javalab.persistency.MutableInteger;
import org.liortamir.ariel.javalab.persistency.Vector;

/**
 * helper class to build a {@link Vector} and a scalar from String
 * @author liort
 *
 */
public class VectorBuilder {

	/**
	 * Receives a comma delimited String where each value represents an integer<br>
	 * and returns a new Vector holding each value as an integer
	 * @param line comma delimited String of values representing integers
	 * @return Vector
	 * @throws VectorException in case the value is not numeric 
	 */
	public Vector createVector(String line) throws VectorException {
		Vector vector = new Vector();
		String[] arr = line.split(",");
		
		for(String s : arr) {
			try {
				validateInteger(line);
				Integer i = Integer.parseInt(s);
				vector.addItem(i);
				
			}catch(NumberFormatException e) {
				throw new VectorException("Value must be an number of Integer type");
			}
		}
		
		return vector;
	}
	
	
	/**
	 * Receives a String representing an integer and returns a MutableInteger<br>
	 * containing the value as integer
	 * @param line String representing an integer
	 * @return MutableInteger
	 * @throws VectorException in case the value is not numeric 
	 */
	public MutableInteger createScalar(String line) throws VectorException  {
		MutableInteger scalar = new MutableInteger();
		try {
			validateInteger(line);			
			scalar.setValue(Integer.parseInt(line));
			
		}catch(NumberFormatException e) {
			throw new VectorException("Value must be a numeric integer type");
		}
		
		return scalar;
	}
	
	
	/**
	 * validates the String does not represent a real number
	 * @param line comma delimited or a single representation of an integer 
	 * @throws VectorException in case the string contains representation of a real number  
	 */
	private void validateInteger(String line) throws VectorException{
		if(line.contains("."))
			throw new VectorException("Value must be an integer\nand cannot contain rational number");
	}
	
}
