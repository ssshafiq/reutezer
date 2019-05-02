package generic;

import gradual.disclosure.DisclosureProtocol;
import gradual.disclosure.SequenceGenerator;
import gradual.disclosure.concrete.CleveDisclosureProtocol;
import gradual.disclosure.concrete.CleveSequenceGenerator;
import org.junit.Before;
import org.junit.Test;
import qilin.comm.Channel;
import qilin.comm.LocalChannelFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

abstract public class DisclosureProtocolTest extends SequenceGeneratorTest {
    final public static int PRECISION = 10;

    /**
     * The class {@link gradual.disclosure.concrete.CleveSequenceGenerator} should have
     * a method of this name, accepting a float[] and returning a float[] in order to
     * use the higher accuracy approximation for the test.
     */
    final public static String GEN_S_SEQUENCE_METHOD = "generateSsequence";

    /**
     * The class {@link gradual.disclosure.concrete.CleveSequenceGenerator} should have
     * a method of this name, accepting a float[] and returning a float[] in order to
     * use the higher accuracy approximation for the test.
     */
    final public static String GEN_T_SEQUENCE_METHOD = "generateTsequence";

    protected DisclosureProtocol.Alice alice;
    protected DisclosureProtocol.Bob bob;
    Random randA, randB;
    Channel chanA, chanB;

    protected final ExecutorService pool;

    abstract protected DisclosureProtocol.Alice getAlice();
    abstract protected DisclosureProtocol.Bob getBob();

    public DisclosureProtocolTest() {
        pool = Executors.newFixedThreadPool(1);
    }

    class ProtocolSequenceGenerator implements  SequenceGenerator {
        @Override
        public void setParameters(Random rand) {
            // do nothing; parameters should have already been set
        }

        @Override
        public boolean[] generateSequence(final boolean secret, final float[] p) {
            pool.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    alice.setProtocolParams(secret, p, PRECISION);

                    alice.init();
                    for (int i = 0; i < p.length; ++i)
                        alice.nextRound();
                    return null;
                }
            });

            boolean[] retval = new boolean[p.length];
            try {
                bob.setProtocolParams(PRECISION);
                bob.init();
                for (int i = 0; i < retval.length; ++i)
                    retval[i] = bob.nextRound();
                return retval;
            } catch (IOException e) {
                return null;
            }
        }

    }

    /**
     * Should return the S sequence for p, as defined by Cleve.
     * if this and {@link #getTsequence(float[])} both return a non-null
     * value, they are used to improve the test's approximation.
     * (see {@link #getExpectedSequence(float[])})
     */
    protected float[] getSsequence(float[] p) { return null; }

    /**
     * Should return the S sequence for p, as defined by Cleve.
     * if this and {@link #getSsequence(float[])} both return a non-null
     * value, they are used to improve the test's approximation.
     * (see {@link #getExpectedSequence(float[])})
     */
    protected float[] getTsequence(float[] p) { return null; }

    @Override
    protected SequenceGenerator getGenerator() {
        return new ProtocolSequenceGenerator();
    }

    @Before
    public void setUp() throws Exception {
        super.setup();
        randA = new Random();
        randB = new Random();

        Channel[] channels = new LocalChannelFactory().getChannelPair();
        chanA = channels[0];
        chanB = channels[1];

        alice = getAlice();
        bob = getBob();

        alice.setParameters(chanA, randA);
        bob.setParameters(chanB, randB);
    }

    /**
     * Override sequence generator to round to precision.
     * This only improves accuracy if the CleveSequenceGenerator implements
     * the methods {@link #getSsequence(float[])} and {@link #getTsequence(float[])}
     * are both overloaded and return a non-null value.
     */
    @Override
    protected float[] getExpectedSequence(float[] p) {

        float[] seqS = getSsequence(p);
        float[] seqT = getTsequence(p);

        if (seqS == null || seqT == null)
            return p;

        for (int i = 0; i < p.length; ++i) {
            int k = Math.round(PRECISION * seqS[i]);
            seqS[i] =  k / (float) PRECISION;
            k = Math.round(PRECISION * seqT[i]);
            seqT[i] =  k / (float) PRECISION;
        }

        p[0] = seqS[0];
        for (int i = 1; i < p.length; ++i) {
            p[i] = p[i - 1]*seqS[i]+(1-p[i - 1])*(1-seqT[i]);
        }
        return p;

    }
}
