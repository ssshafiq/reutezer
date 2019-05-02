package generic;

import gradual.disclosure.SequenceGenerator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Generic test for Sequence Generator
 */
abstract public class SequenceGeneratorTest {
    final public static int COUNT = 10;
    final public static int SAMPLES = 10000;
    final public static float DELTA = 0.05f; // Allow 5% error
    final public static int MAX_SEQ_LEN = 10;

    // Use fixed randomness for reproducibility
    protected  Random rand = new Random(1);
    protected SequenceGenerator generator;

    protected abstract SequenceGenerator getGenerator();

    /**
     * Allows modification to expected sequence due to limited precision, for example.
     * @param p
     * @return
     */
    protected float[] getExpectedSequence(float[] p) { return p; }

    @Before
    public void setup() {
        generator = getGenerator();
        generator.setParameters(rand);
    }

    /**
     * Generate a random sequence of probabilities starting at 1/2 and going up to 1.
     * @param n
     * @return
     */
    protected float[] generateRandomProbabilitySequence(int n) {
        float[] p = new float[n];

        float sum = 0;

        for (int i = 0; i < n; ++i) {
            float next = rand.nextFloat();
            sum += next;
            p[i] = sum;
        }

        // Normalize to 1/2...1
        for (int i = 0; i < n; ++i) {
            p[i] = 0.5f + p[i]/(2*sum);
        }

        return p;
    }


    /**
     * Generate a histogram of the marginal distributions for each  bit
     * by generating multiple sequences and counting.
     * @param secret
     * @param p
     * @return
     * @throws Exception
     */
    public float[] testMarginals(boolean secret, float[] p) throws Exception {
        float[] histogram = new float[p.length];

        for (int j = 0; j < SAMPLES; ++j) {
            boolean[] seq = generator.generateSequence(secret, p);
            for (int i = 0; i < seq.length; ++i) {
                if (seq[i] == secret)
                    histogram[i] += 1.0/SAMPLES;
            }
        }
        return histogram;
    }


    /**
     * Test the marginal distributions.
     * @throws Exception
     */
    @Test
    public void testSequenceBits() throws Exception {
        for (int i = 0; i < COUNT; ++i) {

            int seqLen = rand.nextInt(MAX_SEQ_LEN - 2) + 2;
            float[] p = generateRandomProbabilitySequence(seqLen);
            boolean secret = rand.nextBoolean();

            float[] hist = testMarginals(secret, p);

            float[] expected = getExpectedSequence(p);

            for (int j = 0; j < hist.length; ++j) {
                assertEquals(
                        String.format("Marginal distribution for bit %d is too far off (seqlen=%d, i=%d)",
                                j, seqLen, i),
                        expected[j], hist[j], DELTA);
            }
        }

    }
}
