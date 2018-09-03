package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.AccountHelper;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.exception.InvalidStateException;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

public class CommitHandler extends AbstractStateHandler {


	@Override
	public int hashCode() {
		return Event.COMMIT.code();
	}
	
	@Override
	public EventData commit(EventData req, Account account, Charge charge) throws InvalidStateException {
		EventData res = createResponse(req);
		State currentState;
		double chargeAmount;
		if(account == null) {
			setNack(res, "invalid " + API.ACCOUNT);
			return res;
		}
		
		currentState = charge.getState();
		
		if(currentState != State.RESERVED) {
			throw new InvalidStateException("Cannot commit on state " + currentState);
		}
		
		chargeAmount = req.getAsDouble(API.AMOUNT);
		if(account.getReservedBalance() < chargeAmount) {
			setNack(res, "not enough reserved ballance " + account.getReservedBalance() 
			+ " for charge " + chargeAmount);
			return res;
		}
	
		AccountHelper.commit(account, chargeAmount);		
		setAck(res);
		
		return res;
	}
	
}
