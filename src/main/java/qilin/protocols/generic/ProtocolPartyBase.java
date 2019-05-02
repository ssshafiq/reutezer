package qilin.protocols.generic;

import qilin.comm.Channel;
import qilin.comm.SendableInput;
import qilin.comm.SendableOutput;
import qilin.protocols.ProtocolParty;

import java.io.IOException;
import java.util.Random;

/**
 * A default implementation of {@link qilin.protocols.ProtocolParty}.
 * @author talm
 *
 */
public class ProtocolPartyBase implements ProtocolParty {
	/**
	 * A communication channel to the peer.
	 */
	protected Channel toPeer;
	
	/**
	 * This is just a copy of {@link #toPeer} for convenience.
	 */
	protected SendableOutput out;
	
	/**
	 * This is just a copy of {@link #toPeer} for convenience.
	 */
	protected SendableInput in;
	
	
	/**
	 * Randomness generator.
	 */
	protected Random rand;

	/**
	 * Set the relevant class members.
	 */
	@Override
	public void setParameters(Channel toPeer, Random rand) {
		this.toPeer = toPeer;
		this.in = toPeer;
		this.out = toPeer;
		this.rand = rand;
	}

	/**
	 * Default initialization does nothing.
	 */
	@Override
	public void init() throws IOException {
		// Default init does nothing.
	}
}
