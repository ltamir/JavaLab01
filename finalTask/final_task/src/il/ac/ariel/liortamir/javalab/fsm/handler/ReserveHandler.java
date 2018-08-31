package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.AccountHelper;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.fsm.StateMachine;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

public class ReserveHandler extends AbstractStateHandler {

	public ReserveHandler(StateMachine stateMachine) {
		super(stateMachine);
	}

	@Override
	public int hashCode() {
		return Event.RESERVE.code();
	}
	
	@Override
	public EventData reserve(EventData req, Account account, Charge charge) {
		EventData res = createResponse(req);
		consumeImpl(req, res, account, charge);
		return res;
	}

	@Override
	protected void consumeImpl(EventData req, EventData res, Account account, Charge charge) {
		double chargeAmount;
		if(account == null) { //TODO throw exception Y Y Y
			setNack(res, "invalid " + API.ACCOUNT);
			return;
		}
		
		chargeAmount = req.getAsDouble(API.AMOUNT);
		if(account.getBalance() < chargeAmount) {
			setNack(res, "not enough ballance");
			return;
		}
		
		charge = new Charge(		// The initial state is State.RESERVED in Charge class
				req.getAsInt(API.REQUEST_ID),
				req.getAsDateTime(API.DATETIME),
				chargeAmount,
				account);
		State currentState = charge.getState();
		AccountHelper.reserve(account, chargeAmount);
		account.addCharge(charge);
		charge.setState(stateMachine.getNextState(currentState, req.getAsInt(API.EVENT_ID)));
		
		setAck(res);
		
	}

}
