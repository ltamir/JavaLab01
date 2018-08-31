package il.ac.ariel.liortamir.javalab.fsm.handler;

import il.ac.ariel.liortamir.javalab.api.API;
import il.ac.ariel.liortamir.javalab.bl.EventData;
import il.ac.ariel.liortamir.javalab.fsm.Event;
import il.ac.ariel.liortamir.javalab.fsm.State;
import il.ac.ariel.liortamir.javalab.fsm.StateMachine;
import il.ac.ariel.liortamir.javalab.model.Account;
import il.ac.ariel.liortamir.javalab.model.Charge;

/**
 * dummy class for cases where the event is not mapped to any state
 * @author liort
 *
 */
public class DefaultHandler extends AbstractStateHandler {

	public DefaultHandler(StateMachine stateMachine) {
		super(stateMachine);
	}

	@Override
	public int hashCode() {
		return State.ERROR.ordinal();
	}

	@Override
	protected void consumeImpl(EventData req, EventData res, Account account, Charge charge) {
		State currentState = charge.getState();
		int eventId = req.getAsInt(API.EVENT_ID);
		
		res.set(API.REQUEST_STATUS, API.NOT_OK);
		res.set(API.ERROR, "Invalid Action. State: " + currentState + " -> " + Event.values()[eventId]);
		
	}

}
