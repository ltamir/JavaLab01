package il.ac.ariel.liortamir.javalab.model;

import java.util.HashMap;
import java.util.Map;

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
	
//	// ***** Balance specific ***** //
//	
//	public void reserve(double amount){
//		reservedBalance += amount;
//	}
//	
//	public void commit(double amount){
//		balance -= amount;
//		reservedBalance -= amount;
//	}
//	
//	public void refund(double amount){
//		balance += amount;
//		reservedBalance -= amount;
//	}
}
