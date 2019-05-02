package concrete;

import generic.SequenceGeneratorTest;
import gradual.disclosure.SequenceGenerator;
import gradual.disclosure.concrete.CleveSequenceGenerator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CleveSequenceGeneratorTest extends SequenceGeneratorTest {
    protected SequenceGenerator getGenerator() {
        return new CleveSequenceGenerator();
    }
}