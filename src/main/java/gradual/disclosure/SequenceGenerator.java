package gradual.disclosure;

import java.util.Random;

/**
 * Generate a gradual disclosure sequence for a secret bit.
 */
public interface SequenceGenerator
{
    /**
     * Set the Random source used to generate sequences.
     */
    public void setParameters(Random rand);

    /**
     * Return a sequence of bits that satisfies the controlled gradual disclosure
     * properties, where p[] gives the corresponding probabilities.
     * The sequence should be the same length as p.
     *
     * {@link #setParameters(java.util.Random)} must be called before the first call
     * to this method.
     *
     * @param p a sequence of probabilities, 1/2 < p[0] < ... < p[p.length-1] = 1
     * @return the sequence of disclosed bits, or null if p is not a valid probability sequence.
     */
    boolean[] generateSequence(boolean secret, float[] p);
}
