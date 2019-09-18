package il.ac.ariel.liortamir.javalab.bl;

import java.time.LocalDateTime;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.exception.InvalidEntityException;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;
import il.ac.ariel.liortamir.javalab.model.Credit;
import il.ac.ariel.liortamir.javalab.model.Debit;

/**
 * This class implements Factory design pattern for charge type creations.<br>
 * Charges can be either Debit for withdraw or Credit for deposit 
 * @author liort
 *
 */
public class ChargeFactory {

	
	/**
	 * Return a child class of charge according to given String from API class
	 * @param type {@link API#CREDIT} or {@link API#DEBIT} 
	 * @param reqID
	 * @param timestamp
	 * @param amount
	 * @param account
	 * @return appropriate sub class of {@link Charge}
	 * @throws InvalidEntityException- in case of incorrect type
	 */
	public static Charge getCharge(String type, int reqID, LocalDateTime timestamp, double amount, Account account) throws InvalidEntityException{
		switch(type) {
		case API.CREDIT:
			return new Credit(reqID, timestamp, amount, account);
		case API.DEBIT:
			return new Debit(reqID, timestamp, amount, account);
			default:
				throw new InvalidEntityException("Invalid charge type " + type);
		}
	}
}
