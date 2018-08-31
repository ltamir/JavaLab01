package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.AccountHelper;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.fsm.StateMachine;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

public class CommitHandler extends AbstractStateHandler {

	public CommitHandler(StateMachine stateMachine) {
		super(stateMachine);
	}
	
	@Override
	public int hashCode() {
		return Event.COMMIT.code();
	}
	
	@Override
	public EventData commit(EventData req, Account account, Charge charge) {
		EventData res = createResponse(req);
		consumeImpl(req, res, account, charge);
		return res;
	}
	
	@Override
	protected void consumeImpl(EventData req, EventData res, Account account, Charge charge) {
		
		State currentState;
		double chargeAmount;
		if(account == null) {
			setNack(res, "invalid " + API.ACCOUNT);
			return;
		}
		
		currentState = charge.getState();
		
		if(currentState != State.RESERVED) {
			setNack(res, "Invalid state: " + currentState + " -> " + State.COMMITED);
			return;
		}
		
		chargeAmount = req.getAsDouble(API.AMOUNT);
		if(account.getReservedBalance() < chargeAmount) {
			setNack(res, "not enough reserved ballance " + account.getReservedBalance() 
			+ " for charge " + chargeAmount);
			return;
		}
	
		AccountHelper.commit(account, chargeAmount);
		charge.setState(stateMachine.getNextState(currentState, req.getAsInt(API.EVENT_ID)));
		
		setAck(res);
		
	}
}
