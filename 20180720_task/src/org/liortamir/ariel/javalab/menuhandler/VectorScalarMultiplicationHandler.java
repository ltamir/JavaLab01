package org.liortamir.ariel.javalab.menuhandler;



import org.liortamir.ariel.javalab.exception.VectorException;
import org.liortamir.ariel.javalab.logic.VectorBuilder;
import org.liortamir.ariel.javalab.persistency.MutableInteger;
import org.liortamir.ariel.javalab.persistency.ReadFile;
import org.liortamir.ariel.javalab.persistency.Vector;

/**
 * Implementation for scalar multiplication of a vector
 * @author liort
 *
 */
public class VectorScalarMultiplicationHandler extends AbstractMenuHandler {

	public VectorScalarMultiplicationHandler(String fileName) {
		super(fileName);
	}

	@Override
	public String printMenu() {
		return "*";
	}

	@Override
	public void customHandler(VectorBuilder builder, ReadFile file) throws VectorException {
		Vector v1 = null;
		MutableInteger scalar = null;
			
		v1 = builder.createVector(file.next());
		scalar = builder.createScalar(file.next());
				
		if(v1.size() == 0)
			throw new VectorException("Vectors size do not match");
				
		for(MutableInteger elem : v1)
			elem.setValue(elem.getValue()* scalar.getValue());
		
		printVector(v1);
	}

}
