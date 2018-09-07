package il.ac.ariel.liortamir.javalab.model;

import java.time.LocalDateTime;

import il.ac.ariel.liortamir.javalab.fsm.State;

/**
 * The class holds all the Credit / Debit information in a request<br>
 * as well as the {@link il.ac.ariel.liortamir.javalab.fsm.State}.<br>
 * The class is marked as Abstract so only its sub classes will be instantiated.<br>
 * The {@link #hashCode()} for this class uses the Charge ID.<br>
 * @author liort
 *
 */
public abstract class Charge {

	private int id;
	private LocalDateTime datetime;
	private double amount;
	private Account account;
	private State state = State.NEW;
	
	public Charge(){}
	
	public Charge(int id, LocalDateTime datetime, double amount, Account account) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.amount = amount;
		this.account = account;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	
	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Charge))
			return false;
		
		return obj.hashCode() == hashCode();
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(" ");
		sb.append(datetime);
		sb.append(" ");
		sb.append(amount);
		sb.append(" ");
		sb.append(state);
		
		return sb.toString();
	}
	
}
