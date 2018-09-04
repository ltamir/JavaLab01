package il.ac.ariel.liortamir.javalab.model;

import java.time.LocalDateTime;

import il.ac.ariel.liortamir.javalab.fsm.State;

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
