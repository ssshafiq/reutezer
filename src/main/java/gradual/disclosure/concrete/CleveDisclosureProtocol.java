package gradual.disclosure.concrete;

import gradual.disclosure.DisclosureProtocol;
import qilin.comm.Channel;
import qilin.protocols.OT1ofN;
import qilin.protocols.generic.DummyOT1ofN;
import qilin.protocols.generic.ProtocolPartyBase;

import java.io.IOException;
import java.util.Random;

/**
 *
 */
public class CleveDisclosureProtocol {
    public static class Alice extends ProtocolPartyBase implements DisclosureProtocol.Alice {
        @Override
        public void setProtocolParams(boolean secret, float[] p, int precision) {
            // TODO: Implement
        }

        @Override
        public void nextRound() throws IOException {
            // TODO: Implement
        }
    }

    public static class Bob extends ProtocolPartyBase implements DisclosureProtocol.Bob {
        @Override
        public void setProtocolParams(int precision) {
            // TODO: Implement
        }

        @Override
        public boolean nextRound() throws IOException {
            // TODO: Implement
            return false;
        }
    }

}
