package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

/**
 * dummy class for cases where the event is not mapped to any state
 * @author liort
 *
 */
public class DefaultHandler extends AbstractStateHandler {


	@Override
	public int hashCode() {
		return State.ERROR.ordinal();
	}
	
	

	@Override
	public EventData consume(EventData req, Account account, Charge charge) {
		EventData res = createResponse(req);
		State currentState = State.NEW;
		
		if(charge != null)
			currentState = charge.getState();
		
		int eventId = req.getAsInt(API.EVENT_ID);
		setNack(res, "Invalid Action. State: " + currentState + " -> " + Event.values()[eventId]);

		return res;
	}
	
	public EventData consume(EventData req, String error) {
		EventData res = createResponse(req);
		
		setNack(res, error);

		return res;
	}

}
