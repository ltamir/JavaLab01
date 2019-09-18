package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.AccountHelper;
import il.ac.ariel.liortamir.javalab.bl.ChargeFactory;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.exception.InvalidEntityException;
import il.ac.ariel.liortamir.javalab.exception.InvalidStateException;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

/**
 * Implementation of a Credit operation.
 * The a new charge of Credit type is added and the amount is added to the account's balance
 * @author liort
 *
 */
public class CreditHandler extends AbstractStateHandler {

	@Override
	public int hashCode() {
		return Event.CREDIT.code();
	}

	@Override
	public EventData credit(EventData req, Account account, Charge charge) throws InvalidStateException, InvalidEntityException {
		EventData res = createResponse(req);

		double chargeAmount;
		if(account == null) { 
			throw new InvalidEntityException("invalid account " + API.ACCOUNT);
		}
		
		if(charge != null && charge.getState() != State.NEW)
			throw new InvalidStateException("Cannot credit on state " + charge.getState());
		
		chargeAmount = req.getAsDouble(API.AMOUNT);
		charge = ChargeFactory.getCharge(API.CREDIT,	//The initial state is State.NEW in Charge class
				req.getAsInt(API.REQUEST_ID),
				req.getAsDateTime(API.DATETIME),
				chargeAmount,
				account);
		
		AccountHelper.credit(account, chargeAmount);
		
		account.addCharge(charge);
		setAck(res);
		
		return res;
	}
	
	

}
