package il.ac.ariel.liortamir.javalab.exception;

/**
 * This exception is thrown on a field validation during decode<br>
 * done by {@link il.ac.ariel.liortamir.javalab.api.Codec}
 * @author liort
 *
 */
public class DecodeException extends DataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2982808470104041417L;

	public DecodeException(String description) {
		super(description);
	}
}
