package il.ac.ariel.liortamir.javalab.exception;

/**
 * This exception is thrown in case a request contains event which does not match<br>
 * the associated Charge state.<br>
 * It is the default exception implemented in {@link il.ac.ariel.liortamir.javalab.fsm.handler.AbstractStateHandler}<br>
 *  logical methods.
 * @author liort
 *
 */
public class InvalidStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1930643737110859745L;

	public InvalidStateException(String description) {
		super(description);
	}
}
