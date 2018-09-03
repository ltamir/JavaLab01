package il.ac.ariel.liortamir.javalab.exception;

/**
 * This exception is thrown in case the data malformed (null or not comma separated)
 * @author liort
 *
 */
public class DataException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 916442444908907412L;

	public DataException(String description) {
		super(description);
	}
	

	
}
