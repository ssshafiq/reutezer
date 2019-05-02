package qilin.protocols.generic;

import qilin.protocols.OT1ofN;

import java.io.IOException;

/**
 * A dummy implementation of OT, that realizes the interface but doesn't actually give any
 * security.
 */
public class DummyOT1ofN {
    public static class Sender<T> extends ProtocolPartyBase  implements OT1ofN.Sender<T> {
        @Override
        public void send(T[] inputs) throws IOException {
            int idx = toPeer.readInt();
            toPeer.writeObject(inputs[idx]);
            toPeer.flush();
        }
    }

    public static class Chooser<T> extends ProtocolPartyBase  implements OT1ofN.Chooser<T> {
        final Class<T> type;

        public Chooser(Class<T> type) {
            this.type = type;
        }

        @Override
        public T receive(int idx) throws IOException {
            toPeer.writeInt(idx);
            toPeer.flush();
            return toPeer.readObject(type);
        }
    }

}
