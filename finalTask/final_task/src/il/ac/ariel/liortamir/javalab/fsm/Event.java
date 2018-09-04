package il.ac.ariel.liortamir.javalab.fsm;

public enum Event {
	RESERVE {			// Event Id 0
		@Override
		public int code() {
			return this.ordinal();
		}
	},
	COMMIT {			// Event Id 1
		@Override
		public int code() {
			return this.ordinal();
		}
	},
	REFUND {			// Event Id 2
		@Override
		public int code() {
			return this.ordinal();
		}
	},
	UNRESERVE {			// Event Id 3
		@Override
		public int code() {
			return this.ordinal();
		}
	},
	CREDIT {			// Event Id 4
		@Override
		public int code() {
			return this.ordinal();
		}
	};

	public abstract int code();
}
