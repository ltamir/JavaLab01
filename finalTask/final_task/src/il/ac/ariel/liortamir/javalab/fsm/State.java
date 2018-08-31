package il.ac.ariel.liortamir.javalab.fsm;

import il.ac.ariel.liortamir.javalab.fsm.handler.AbstractStateHandler;

public enum State {

	ERROR {	// no balance
		@Override
		int code() {
			return this.ordinal();
		}
	},
	ACTIVE {	// has balance
		@Override
		int code() {
			return this.ordinal();
		}
	},
	RESERVED {		// amount of money saved aside
		@Override
		int code() {
			return this.ordinal();
		}
	},
	COMMITED {		// reserved amount withdrawn from balance
		@Override
		int code() {
			return this.ordinal();
		}
	},
	REFUND {		// reserved amount withdrawn from balance
		@Override
		int code() {
			return this.ordinal();
		}
	};
	
	
	public static int size() {
		return State.values().length;
	}
	
	abstract int code();
	
	public static void consume(AbstractStateHandler handler){
		
	}
}
