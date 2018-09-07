package il.ac.ariel.liortamir.javalab.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The Account holds general information as id and fullName<br>
 * and balance information: Account balance and reserved amount balance.<br>
 * Each {@link Charge} associated with each operation are saved into a Map with the {@link Charge#getId()} as key.<br>
 * The {@link #hashCode()} for this class uses the account ID.<br>
 *  
 * @author liort
 *
 */
public class Account {

	private int id;
	private String fullName;
	private double balance;
	private double reservedBalance;
	private Map<Integer, Charge> chargeList = new HashMap<>();
	
	public Account() {}
	
	public Account(int id, String fullName, double balance) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.balance = balance;
	}
	
	// ***** getters and setters ***** //
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public double getReservedBalance() {
		return reservedBalance;
	}

	public void setReservedBalance(double reservedBalance) {
		this.reservedBalance = reservedBalance;
	}

	public void addCharge(Charge charge){
		this.chargeList.put(charge.getId(), charge);
	}
	
	public Charge getCharge(int chargeId){
		return this.chargeList.get(chargeId);
	}
	
	public Map<Integer, Charge> getChargeList(){
		return this.chargeList;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Account))
			return false;
		
		return obj.hashCode() == hashCode();
	}
	
	

}
