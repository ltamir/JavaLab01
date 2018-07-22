package org.liortamir.ariel.javalab.menuhandler;



import org.liortamir.ariel.javalab.exception.VectorException;
import org.liortamir.ariel.javalab.logic.VectorBuilder;
import org.liortamir.ariel.javalab.persistency.MutableInteger;
import org.liortamir.ariel.javalab.persistency.ReadFile;
import org.liortamir.ariel.javalab.persistency.Vector;

/**
 * Implementation for Cartesian multiplication of two vectors
 * @author liort
 *
 */
public class VectorCartesianMultiplicationHandler extends AbstractMenuHandler {

	public VectorCartesianMultiplicationHandler(String fileName) {
		super(fileName);
	}

	@Override
	public String printMenu() {
		return "X";
	}

	@Override
	public void customHandler(VectorBuilder builder, ReadFile file) throws VectorException {
		Vector v1 = null;
		Vector v2 = null;
		
		v1 = builder.createVector(file.next());
		v2 = builder.createVector(file.next());
		
		if(v1.size() == 0)
			throw new VectorException("Vectors size do not match");
			
		for(MutableInteger elem1 : v1) {
			Integer elemSum = 0;
			for(MutableInteger elem2 : v2)
				elemSum += elem1.getValue() * elem2.getValue();
			elem1.setValue(elemSum);
		}		
		
		printVector(v1);
	}

}
