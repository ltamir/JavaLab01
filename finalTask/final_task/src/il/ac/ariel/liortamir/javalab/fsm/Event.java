package il.ac.ariel.liortamir.javalab.fsm;

public enum Event {
	RESERVE {
		@Override
		public int code() {
			return this.ordinal();
		}
	},
	COMMIT {
		@Override
		public int code() {
			return this.ordinal();
		}
	},
	REFUND {
		@Override
		public int code() {
			return this.ordinal();
		}
	},
	UNRESERVE {
		@Override
		public int code() {
			return this.ordinal();
		}
	},
	CREDIT {
		@Override
		public int code() {
			return this.ordinal();
		}
	};

	public abstract int code();
}
