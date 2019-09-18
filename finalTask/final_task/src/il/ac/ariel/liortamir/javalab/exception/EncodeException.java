package il.ac.ariel.liortamir.javalab.exception;

/**
 * This exception is thrown in the encoding process<br>
 * done by {@link il.ac.ariel.liortamir.javalab.api.Codec}
 * @author liort
 *
 */
public class EncodeException extends DataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9109887877392384173L;
	
	public EncodeException(String description) {
		super(description);
	}

}
