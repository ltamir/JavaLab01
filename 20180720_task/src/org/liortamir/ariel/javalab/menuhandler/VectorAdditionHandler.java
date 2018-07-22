package org.liortamir.ariel.javalab.menuhandler;


import org.liortamir.ariel.javalab.exception.VectorException;
import org.liortamir.ariel.javalab.logic.VectorBuilder;
import org.liortamir.ariel.javalab.persistency.ReadFile;
import org.liortamir.ariel.javalab.persistency.Vector;

/**
 * implementation of vectors addition
 * @author liort
 *
 */
public class VectorAdditionHandler extends AbstractMenuHandler {

	public VectorAdditionHandler(String fileName) {
		super(fileName);
	}

	@Override
	public String printMenu() {
		return "+";
	}

	@Override
	public void customHandler(VectorBuilder builder, ReadFile file) throws VectorException  {
		Vector v1 = null;
		Vector v2 = null;
				
		v1 = builder.createVector(file.next());
		v2 = builder.createVector(file.next());
		
		if(v1.size() == 0 || v2.size() == 0)
			throw new VectorException("Vector addition requires two vectors with a minimum of two memebers each");
		if(v1.size() != v2.size())
			throw new VectorException("Vectors size do not match");
		
		for(int pos = 0; pos < v1.size(); pos++) {
			Integer a = v1.get(pos);
			Integer b = v2.get(pos);
			v1.set(pos, a+b); 
		}
	
		printVector(v1);
	}

}
