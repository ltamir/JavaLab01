package il.ac.ariel.liortamir.javalab.fsm;

import java.util.HashMap;
import java.util.Map;

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

		eventMap.put(State.RESERVED, new ReserveHandler(this));
		eventMap.put(State.COMMITED, new CommitHandler(this));
		eventMap.put(State.REFUND, new RefundHandler(this));
	}
	
	private void initTransitionMap() {
		transitionMap = new State[State.size()][Event.values().length];
		
		transitionMap[State.ACTIVE.code()][Event.RESERVE.code()] = State.RESERVED;
		transitionMap[State.RESERVED.code()][Event.COMMIT.code()] = State.COMMITED;
		transitionMap[State.RESERVED.code()][Event.UNRESERVE.code()] = State.ACTIVE;
		transitionMap[State.COMMITED.code()][Event.REFUND.code()] = State.REFUND;
	}
	
	public State getNextState(State state, int eventId){
		State nextState = transitionMap[state.ordinal()][eventId];
		return nextState;
	}
	
	
	public AbstractStateHandler getHandler(State state, int eventId) {
		AbstractStateHandler handler = null;
		State nextState = transitionMap[state.ordinal()][eventId];
		handler = eventMap.get(nextState);
		return handler;
	}
}
