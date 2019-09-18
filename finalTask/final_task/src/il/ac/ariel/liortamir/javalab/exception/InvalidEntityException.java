package il.ac.ariel.liortamir.javalab.exception;

/**
 * This exception is thrown in case the Entity in a request<br>
 * cannot be found or an invalid Charge type is passed to {@link il.ac.ariel.liortamir.javalab.bl.ChargeFactory}.
 * @author liort
 *
 */
public class InvalidEntityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2914202567580358949L;

	public InvalidEntityException(String description) {
		super(description);
	}
}
