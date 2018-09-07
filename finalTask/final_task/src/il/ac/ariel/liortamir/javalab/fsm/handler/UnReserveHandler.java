package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.AccountHelper;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.exception.InvalidEntityException;
import il.ac.ariel.liortamir.javalab.exception.InvalidStateException;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

/**
 * Implementation of unreserve on Debit charge.<br>
 * The amount is added to the Account's balance and removed from the Account's reserve balance.
 * @author liort
 *
 */
public class UnReserveHandler extends AbstractStateHandler {

	
	@Override
	public int hashCode() {
		return Event.UNRESERVE.code();
	}

	@Override
	public EventData unreserve(EventData req, Account account, Charge charge) throws InvalidStateException, InvalidEntityException {
		EventData res = createResponse(req);
		double unreserveAmount;
		State currentState;
		
		try {
			currentState = charge.getState();
			
			if(currentState != State.RESERVED) {
				throw new InvalidStateException("Cannot unreserve on state " + currentState);
			}
				
			unreserveAmount = req.getAsDouble(API.AMOUNT);

			if(charge.getAmount() != unreserveAmount) {
				setNack(res, "invalid " + API.ACCOUNT);
				return res;
			}
				
		}catch(NullPointerException e) {
			throw new InvalidEntityException("");
		}

			
		AccountHelper.unreserve(account, unreserveAmount);
		setAck(res);
		
		return res;
	}
	

}
