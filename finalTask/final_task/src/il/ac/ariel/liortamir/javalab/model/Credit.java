package il.ac.ariel.liortamir.javalab.model;

import java.time.LocalDateTime;

/**
 * Represents a credit operation in an account.
 * @author liort
 *
 */
public class Credit extends Charge {

	public Credit(int reqID, LocalDateTime timestamp, double amount, Account account) {
		super(reqID, timestamp, amount, account);
	}

}
