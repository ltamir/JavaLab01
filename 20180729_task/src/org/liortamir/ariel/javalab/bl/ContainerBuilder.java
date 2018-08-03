package org.liortamir.ariel.javalab.bl;

import org.liortamir.ariel.javalab.container.Containable;
import org.liortamir.ariel.javalab.exception.DataFormatException;

public class ContainerBuilder {

	public void build(String line, Containable<Integer> container) throws DataFormatException {

		String[] arr = line.split("[ ,]");
		
		for(String s : arr) {
			try {
				validateInteger(s);
				Double dbl = Double.parseDouble(s);
				container.addElement(dbl.intValue());
				
			}catch(NumberFormatException e) {
				throw new DataFormatException("Value must be decimal");
			}
		}
		
	}
	
	/**
	 * validates the String contains non other than decimal digits and a dot sign
	 * @param line comma delimited or a single representation of an integer 
	 * @throws DataFormatException in case the string contains representation of a real number  
	 */
	private void validateInteger(String line) throws DataFormatException{
		
		if(!line.matches("(\\d+)(\\.*)(\\d*)"))
			throw new DataFormatException("Value must be an integer");
	}
}
