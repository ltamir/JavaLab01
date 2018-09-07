package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.AccountHelper;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.exception.InvalidStateException;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

/**
 * Implementation of refund on Debit charge.<br/>
 * The amount is added to the Account's balance.
 * @author liort
 *
 */
public class RefundHandler extends AbstractStateHandler {

	
	@Override
	public int hashCode() {
		return Event.REFUND.code();
	}

	
	@Override
	public EventData refund(EventData req, Account account, Charge charge) throws InvalidStateException {
		EventData res = createResponse(req);
		
		double refundAmount;
		State currentState;
		if(account == null) {
			setNack(res, "invalid " + API.ACCOUNT);
			return res;
		}
		
		currentState = charge.getState();
		
		if(currentState != State.COMMITED) {
			throw new InvalidStateException("Cannot refund on state " + currentState);
		}
			
		refundAmount = req.getAsDouble(API.AMOUNT);

		AccountHelper.refund(account, refundAmount);
		setAck(res);
		
		return res;
	}
	

}
