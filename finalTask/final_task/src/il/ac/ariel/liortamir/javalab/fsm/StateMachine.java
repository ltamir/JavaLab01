package il.ac.ariel.liortamir.javalab.fsm;

import java.util.HashMap;
import java.util.Map;

import il.ac.ariel.liortamir.javalab.exception.InvalidStateException;
import il.ac.ariel.liortamir.javalab.fsm.handler.AbstractStateHandler;
import il.ac.ariel.liortamir.javalab.fsm.handler.CommitHandler;
import il.ac.ariel.liortamir.javalab.fsm.handler.CreditHandler;
import il.ac.ariel.liortamir.javalab.fsm.handler.RefundHandler;
import il.ac.ariel.liortamir.javalab.fsm.handler.ReserveHandler;
import il.ac.ariel.liortamir.javalab.fsm.handler.UnReserveHandler;

/**
 * This class implements the State and Singletone design patterns.<br>
 * An array of {@link State} maps the State ordinal and and {@link Event} ordinal to a new State.<br>
 * A HashMap maps relevant states to their {@link il.ac.ariel.liortamir.javalab.fsm.handler.AbstractStateHandler}<br>
 * implementation.
 * 
 * @author liort
 *
 */
public class StateMachine {
	
	private static final StateMachine instance = new StateMachine();
	
	public static StateMachine getInstance() {
		return StateMachine.instance;
	}

	private State[][] transitionMap;
	private Map<State, AbstractStateHandler> eventMap = new HashMap<>();
	
	
	// ***** data init ***** //
	
	private StateMachine() {
		initEventMap();
		initTransitionMap();
	}
	
	private void initEventMap() {

		eventMap.put(State.RESERVED, new ReserveHandler());
		eventMap.put(State.COMMITED, new CommitHandler());
		eventMap.put(State.REFUND, new RefundHandler());
		eventMap.put(State.NEW, new UnReserveHandler());
		eventMap.put(State.CREDITED, new CreditHandler());
	}
	
	private void initTransitionMap() {
		transitionMap = new State[State.size()][Event.values().length];
		
		transitionMap[State.NEW.code()][Event.RESERVE.code()] = State.RESERVED;
		transitionMap[State.RESERVED.code()][Event.COMMIT.code()] = State.COMMITED;
		transitionMap[State.RESERVED.code()][Event.UNRESERVE.code()] = State.NEW;
		transitionMap[State.COMMITED.code()][Event.REFUND.code()] = State.REFUND;
		
		transitionMap[State.NEW.code()][Event.CREDIT.code()] = State.CREDITED;	//
	}
	
	
	// ***** exposed methods ***** //
	
	/**
	 * Returns the next state according to the given state and event
	 * @param state
	 * @param eventId
	 * @return State
	 */
	public State getNextState(State state, int eventId) throws InvalidStateException{
		State nextState = null;
		try {
			nextState = transitionMap[state.ordinal()][eventId];
		}catch(IndexOutOfBoundsException e) {
			throw new InvalidStateException("Invalid EventId " + eventId + " for state " + state);
		}
		return nextState;
	}
	
	/**
	 * Returns the appropriate handler for given state(current) and event.<br>
	 * This method does not change the state! Use {@link #getNextState(State, int)} to retrieve the next state.
	 * 
	 * @param state
	 * @param eventId
	 * @return AbstractStateHandler
	 * @throws InvalidStateException
	 */
	public AbstractStateHandler getHandler(State state, int eventId) throws InvalidStateException{
		AbstractStateHandler handler = null;
		State nextState = getNextState(state, eventId);
		handler = eventMap.get(nextState);
		
		if(handler == null)
			throw new InvalidStateException("Invalid EventId " + eventId + " for state " + state);
		
		return handler;
	}
}
