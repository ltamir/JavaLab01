package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.exception.InvalidEntityException;
import il.ac.ariel.liortamir.javalab.exception.InvalidStateException;
import il.ac.ariel.liortamir.javalab.infra.AccountStorage;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

/**
 * Parent class for all event handlers.<br>
 * This class contains the logical methods for all events. The default implementation returns Nack.
 * @author liort
 *
 */
public abstract class AbstractStateHandler {

	protected AccountStorage db = AccountStorage.getInstance();
	
	public AbstractStateHandler() {}
	
	public int getCode() { return hashCode(); }
	
	@Override
	public abstract int hashCode();
	
	@Deprecated
	public EventData consume(EventData req, Account account, Charge charge) {
		EventData res = createResponse(req);
		return res;
	}

	/**
	 * Create a new {@link il.ac.ariel.liortamir.javalab.bl.EventData} depicting response<br>
	 * with the request identifiers in it.
	 * @param req
	 * @return EventData
	 */
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
	
	
	// **** default implementation for logical methods ***** //
	
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
	
	public EventData credit(EventData req, Account account, Charge charge) throws InvalidStateException, InvalidEntityException{
		EventData res = createResponse(req);
		setNack(res, "Invalid credit event for state: " + charge.getState());
		return res;
	}
	
	public EventData unreserve(EventData req, Account account, Charge charge) throws InvalidStateException, InvalidEntityException{
		EventData res = createResponse(req);
		setNack(res, "Invalid unreserve event for state: " + charge.getState());
		return res;
	}
	// TODO implement relevant events in relevant children
}
