package il.ac.ariel.liortamir.javalab.bl;

import il.ac.ariel.liortamir.javalab.model.Account;

/**
 * A helper class to perform logical actions on {@link il.ac.ariel.liortamir.javalab.model.Account}.<br>
 * All methods are static.
 * @author liort
 *
 */
public class AccountHelper {

	public static void reserve(Account account, double amount) {
		double reservedBalance = account.getReservedBalance();
		double balance = account.getBalance();
		
		account.setReservedBalance(reservedBalance + amount);
		account.setBalance(balance - amount);
	}
	
	public static void commit(Account account, double amount) {
		double reservedBalance = account.getReservedBalance();
		
		account.setReservedBalance(reservedBalance - amount);
	}
	
	public static void refund(Account account, double amount) {
		double balance = account.getBalance();

		account.setBalance(balance + amount);
	}
	
	public static void unreserve(Account account, double amount) {
		double reservedBalance = account.getReservedBalance();
		double balance = account.getBalance();
		
		account.setReservedBalance(reservedBalance - amount);
		account.setBalance(balance + amount);
	}

	public static void credit(Account account, double amount) {
		double balance = account.getBalance();
		account.setBalance(balance + amount);
	}
}
