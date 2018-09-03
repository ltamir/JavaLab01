package il.ac.ariel.liortamir.javalab.fsm;

import java.util.HashMap;
import java.util.Map;

import il.ac.ariel.liortamir.javalab.exception.InvalidStateException;
import il.ac.ariel.liortamir.javalab.fsm.handler.AbstractStateHandler;
import il.ac.ariel.liortamir.javalab.fsm.handler.CommitHandler;
import il.ac.ariel.liortamir.javalab.fsm.handler.RefundHandler;
import il.ac.ariel.liortamir.javalab.fsm.handler.ReserveHandler;

public class StateMachine {
	
	private static final StateMachine instance = new StateMachine();
	
	public static StateMachine getInstance() {
		return StateMachine.instance;
	}

	private State[][] transitionMap;
	private Map<State, AbstractStateHandler> eventMap = new HashMap<>();
	
	private StateMachine() {
		initEventMap();
		initTransitionMap();
		
	}
	
	private void initEventMap() {

		eventMap.put(State.RESERVED, new ReserveHandler());
		eventMap.put(State.COMMITED, new CommitHandler());
		eventMap.put(State.REFUND, new RefundHandler());
	}
	
	private void initTransitionMap() {
		transitionMap = new State[State.size()][Event.values().length];
		
		transitionMap[State.NEW.code()][Event.RESERVE.code()] = State.RESERVED;
		transitionMap[State.RESERVED.code()][Event.COMMIT.code()] = State.COMMITED;
		transitionMap[State.RESERVED.code()][Event.UNRESERVE.code()] = State.NEW;
		transitionMap[State.COMMITED.code()][Event.REFUND.code()] = State.REFUND;
	}
	
	public State getNextState(State state, int eventId){
		State nextState = transitionMap[state.ordinal()][eventId];
		return nextState;
	}
	
	
	public AbstractStateHandler getHandler(State state, int eventId) throws InvalidStateException{
		AbstractStateHandler handler = null;
		State nextState = transitionMap[state.ordinal()][eventId];
		handler = eventMap.get(nextState);
		
		if(handler == null)
			throw new InvalidStateException("Invalid EventId " + eventId + " for state " + state);
		
		return handler;
	}
}
