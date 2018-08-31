package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.AccountHelper;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.fsm.StateMachine;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

public class RefundHandler extends AbstractStateHandler {

	public RefundHandler(StateMachine stateMachine) {
		super(stateMachine);
	}
	
	@Override
	public int hashCode() {
		return Event.REFUND.code();
	}

	
	@Override
	public EventData refund(EventData req, Account account, Charge charge) {
		EventData res = createResponse(req);
		consumeImpl(req, res, account, charge);
		return res;
	}
	
	@Override
	protected void consumeImpl(EventData req, EventData res, Account account, Charge charge) {
		double refundAmount;
		State currentState;
		if(account == null) {
			setNack(res, "invalid " + API.ACCOUNT);
			return;
		}
		
		currentState = charge.getState();
		
		if(currentState != State.COMMITED) {
			setNack(res, "Invalid state: " + currentState + " -> " + State.REFUND);
			return;
		}
			
		refundAmount = req.getAsDouble(API.AMOUNT);

		AccountHelper.refund(account, refundAmount);
		charge.setState(State.REFUND);
		setAck(res);
	}

}
