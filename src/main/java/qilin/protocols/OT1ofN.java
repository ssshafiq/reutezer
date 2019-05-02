package qilin.protocols;

import java.io.IOException;

/**
 * Interface for 1-out-of-N string Oblivious Transfer (OT).
 * A 1-out-of-N string OT is a two-party protocol. One party
 * is the {@link Sender} while the other is the {@link Chooser}.
 *
 */
public interface OT1ofN {
	/**
	 * Represents the Sender in a 1-out-of-N string OT.
	 *
	 */
	public interface Sender<T> extends ProtocolParty {
		/**
		 * Execute an OT transaction as the Sender. The Chooser will be
		 * able to learn one (and only one) of the inputs, while the
		 * sender learns nothing about the Chooser's choice.
		 *
		 * @param inputs
		 * @throws java.io.IOException
		 * @see Chooser#receive(int)
		 */
		public void send(T[] inputs) throws IOException;
	}

	/**
	 * Represents the Chooser in a 1-out-of-N string OT
	 *
	 */
	public interface Chooser<T> extends ProtocolParty {
		/**
		 * Execute an OT transaction as the Chooser. The Chooser
		 * selects which of the two Sender inputs to learn. The sender
		 * will not learn anything about the Chooser's input.
		 *
		 * @param idx Can is an integer between 0 and N-1 (where N is the length of the
         *            input vector to {@link Sender#send(Object[])}
		 * @throws java.io.IOException
		 * @see Sender#send(Object[])
		 */
		public T receive(int idx) throws IOException;
	}
}
