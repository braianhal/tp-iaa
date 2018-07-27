package ia.module;

import ia.module.parser.Parser;
import junit.framework.TestCase;

public class SimplificationTests extends TestCase{

    private static Parser parser = new Parser();

    public void testSimplification() throws Exception {
        assertEquals(3.0, parser.parse("3").simplify().getValue());
        assertEquals(7.0, parser.parse("3+4").simplify().getValue());
        assertEquals(0.0, parser.parse("4-4").simplify().getValue());
        assertEquals(-2.0, parser.parse("3+4-5-4").simplify().getValue());
        assertEquals(3.0, parser.parse("3+4-5-4+1+4").simplify().getValue());
        assertEquals(1.0, parser.parse("3/3").simplify().getValue());
        assertEquals(3.0, parser.parse("(3+6)/3").simplify().getValue());
        assertEquals(1.0, parser.parse("(3+4)^0").simplify().getValue());
        assertEquals(1.0, parser.parse("(3+4)^(3-3)").simplify().getValue());
        assertEquals(0.0, parser.parse("x-x").simplify().getValue());
        assertEquals(3.0, parser.parse("x+3-x").simplify().getValue());
        assertEquals(1.0, parser.parse("x/x").simplify().getValue());
        assertEquals(1.0, parser.parse("(x+1)/(x+1)").simplify().getValue());
        assertEquals(1.0, parser.parse("(x^2+2x+1)/(x^2+2x+1)").simplify().getValue());
        assertEquals(1.0, parser.parse("((x^2+2x+1)*(x^2+2x+1))^0").simplify().getValue());
        assertEquals(1.0, parser.parse("x^2/x^2").simplify().getValue());
    }
}
