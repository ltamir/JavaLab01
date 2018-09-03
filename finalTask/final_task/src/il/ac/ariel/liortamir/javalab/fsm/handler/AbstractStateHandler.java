package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.exception.InvalidEntityException;
import il.ac.ariel.liortamir.javalab.exception.InvalidStateException;
import il.ac.ariel.liortamir.javalab.infra.AccountStorage;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

public abstract class AbstractStateHandler {

	protected AccountStorage db = AccountStorage.getInstance();
	
	public AbstractStateHandler() {}
	
	public int getCode() { return hashCode(); }
	
	@Override
	public abstract int hashCode();
	
	public EventData consume(EventData req, Account account, Charge charge) {
		EventData res = createResponse(req);
		return res;
	}

	
	protected EventData createResponse(EventData req) {
		EventData res = new EventData();
		
		res.set(API.REQUEST_ID, req.getAsString(API.REQUEST_ID));
		res.set(API.EVENT_ID, req.getAsString(API.EVENT_ID));
		return res;
	}
	
	public void setAck(EventData res) {
		res.set(API.REQUEST_STATUS, API.OK);
	}
	
	public void setNack(EventData res, String error) {
		res.set(API.REQUEST_STATUS, API.NOT_OK);
		res.set(API.ERROR, error);
	}
	
	public EventData reserve(EventData req, Account account, Charge charge) throws InvalidStateException, InvalidEntityException{
		EventData res = createResponse(req);
		setNack(res, "Invalid reserve event for state: " + charge.getState());
		return res;
	}
	
	public EventData commit(EventData req, Account account, Charge charge) throws InvalidStateException{
		EventData res = createResponse(req);
		setNack(res, "Invalid commit event for state: " + charge.getState());
		return res;
	}
	
	public EventData refund(EventData req, Account account, Charge charge) throws InvalidStateException{
		EventData res = createResponse(req);
		setNack(res, "Invalid refund event for state: " + charge.getState());
		return res;
	}
	
	public EventData unreserve(EventData req, Account account, Charge charge) throws InvalidStateException{
		EventData res = createResponse(req);
		setNack(res, "Invalid unreserve event for state: " + charge.getState());
		return res;
	}
	// TODO implement relevant events in relevant children
}
