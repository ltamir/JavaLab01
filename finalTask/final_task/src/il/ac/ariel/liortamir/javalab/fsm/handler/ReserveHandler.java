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
 * Implementation of reserve on Debit charge.<br>
 * A new charge of Debit type is created.<br>
 * The amount is removed from the Account's balance and is added to the Account's Reserved balance
 * @author liort
 *
 */
public class ReserveHandler extends AbstractStateHandler {

	@Override
	public int hashCode() {
		return Event.RESERVE.code();
	}
	
	@Override
	public EventData reserve(EventData req, Account account, Charge charge) throws InvalidStateException, InvalidEntityException {
		EventData res = createResponse(req);
		double chargeAmount;
		if(account == null) { 
			throw new InvalidEntityException("invalid account " + API.ACCOUNT);
		}
		
		chargeAmount = req.getAsDouble(API.AMOUNT);
		if(account.getBalance() < chargeAmount) {
			setNack(res, "not enough ballance");
			return res;
		}
		
		if(charge != null && charge.getState() != State.NEW)
			throw new InvalidStateException("Cannot reserve on state " + charge.getState());
		
		charge = ChargeFactory.getCharge(API.DEBIT,	//The initial state is State.NEW in Charge class
				req.getAsInt(API.REQUEST_ID),
				req.getAsDateTime(API.DATETIME),
				chargeAmount,
				account);


		AccountHelper.reserve(account, chargeAmount);
		account.addCharge(charge);

		setAck(res);
		
		return res;
	}



}
