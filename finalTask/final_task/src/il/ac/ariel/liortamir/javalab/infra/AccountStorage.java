package il.ac.ariel.liortamir.javalab.infra;

import java.util.HashMap;
import java.util.Map;

import il.ac.ariel.liortamir.javalab.model.Account;

/**
 * Singletone representing a storage for all data
 * @author liort
 *
 */
public class AccountStorage {

	private static final AccountStorage instance = new AccountStorage();
	private Map<Integer, Account> accountMap = new HashMap<>();
	
	
	/**
	 * Private constructor
	 */
	private AccountStorage() {};
	
	/**
	 * Instance getter for the Singletone
	 * @return
	 */
	public static AccountStorage getInstance() {
		return AccountStorage.instance;
	}
	
	
	// ***** account getter and setter ***** //
	
	public Account get(int id) {
		return accountMap.get(id);
	}
	
	public void add(Account account) {
		accountMap.put(account.getId(), account);
	}
}
