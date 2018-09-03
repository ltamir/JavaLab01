package il.ac.ariel.liortamir.javalab.bl;

import il.ac.ariel.liortamir.javalab.model.Account;

/**
 * Helper class to perform logic actions on account.<br>
 * All methods are static.
 * @author liort
 *
 */
public class AccountHelper {

	public static void reserve(Account account, double amount) {
		double reservedBalance = account.getReservedBalance();
		account.setReservedBalance(reservedBalance + amount);
	}
	
	public static void commit(Account account, double amount) {
		double balance = account.getBalance();
		double reservedBalance = account.getReservedBalance();
		
		account.setBalance(balance - amount);
		account.setReservedBalance(reservedBalance - amount);
	}
	
	public static void refund(Account account, double amount) {
		double balance = account.getBalance();
		double reservedBalance = account.getReservedBalance();
		
		account.setBalance(balance + amount);
		account.setReservedBalance(reservedBalance - amount);
	}
	
	public static void unreserve(Account account, double amount) {
		double reservedBalance = account.getReservedBalance();
		account.setReservedBalance(reservedBalance - amount);
	}
	
}
