package ia.module;

import ia.module.parser.Parser;
import junit.framework.TestCase;

import static ia.module.parser.Operator.N;

public class TokenTests extends TestCase{

    private static Parser parser = new Parser();

    public void testTokenNumber() throws Exception {
        assertEquals(N, parser.parse("0").getToken().intValue());
        assertEquals(N, parser.parse("12").getToken().intValue());
    }
}
