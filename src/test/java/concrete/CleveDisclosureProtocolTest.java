package concrete;

import generic.DisclosureProtocolTest;
import gradual.disclosure.DisclosureProtocol;
import gradual.disclosure.SequenceGenerator;
import gradual.disclosure.concrete.CleveDisclosureProtocol;
import gradual.disclosure.concrete.CleveSequenceGenerator;
import org.junit.Before;
import org.junit.Test;
import qilin.comm.Channel;
import qilin.comm.LocalChannelFactory;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class CleveDisclosureProtocolTest  extends DisclosureProtocolTest {
    @Override
    protected DisclosureProtocol.Alice getAlice() {
        return new CleveDisclosureProtocol.Alice();
    }

    @Override
    protected DisclosureProtocol.Bob getBob() {
        return new CleveDisclosureProtocol.Bob();
    }
}