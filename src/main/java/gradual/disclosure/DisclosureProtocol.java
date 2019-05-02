package gradual.disclosure;

import qilin.protocols.ProtocolParty;

import java.io.IOException;

/**
 * A two-party protocol in which Alice gradually discloses to Bob her secret bit.
 */
public interface DisclosureProtocol {

    public interface Alice extends ProtocolParty {

        /**
         * Initialize the protocol-specific parameters,
         * @param secret Alice's secret bit
         * @param p the probability sequence
         * @param precision the precision of the probability sequence
         *                   if precision=k then probabilities are approximated
         *                  up to 1/k precision.
         */
        public void setProtocolParams(boolean secret, float[] p, int precision);

        /**
         * Run the next round of the protocol.
         * After this round is complete, Bob should receive the next bit in the
         * sequence. Alice shouldn't learn any additional information about
         * the bit Bob received.
         *
         * @throws IOException
         */
        public void nextRound() throws IOException;
    }

    public interface Bob extends ProtocolParty {
        /**
         * Initialize the protocol-specific parameters,
         * @param precision the precision of the probability sequence
         *                   if precision=k then probabilities are approximated
         *                  up to 1/k precision.
         */
        public void setProtocolParams(int precision);

        /**
         * Run the next round of the protocol.
         * @return the next bit in the sequence.
         * @throws IOException
         */
        public boolean nextRound() throws IOException;
    }
}
