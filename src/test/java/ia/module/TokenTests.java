package ia.module;

import ia.module.parser.Parser;
import junit.framework.TestCase;

import static ia.module.parser.Operator.*;

public class TokenTests extends TestCase{

    private static Parser parser = new Parser();

    public void testTokenNumber() throws Exception {
        assertEquals(N, parser.parse("0").getToken().intValue());
        assertEquals(N, parser.parse("12").getToken().intValue());
    }

    public void testTokenNumberPlusNumber() throws Exception {
        assertEquals(N_PLUS_N, parser.parse("1+0").getToken().intValue());
        assertEquals(N_PLUS_N, parser.parse("1+2+3+4").getToken().intValue());
        assertEquals(N_PLUS_N, parser.parse("(2*3)+(sqrt(5))").getToken().intValue());
        assertEquals(N_PLUS_N, parser.parse("(2*3+sqrt(4)*ln(2))+1").getToken().intValue());
    }

    public void testTokenNumberMinusNumber() throws Exception {
        assertEquals(N_MINUS_N, parser.parse("1-0").getToken().intValue());
        assertEquals(N_MINUS_N, parser.parse("1+2-3+4").getToken().intValue());
        assertEquals(N_MINUS_N, parser.parse("1+2+3-4").getToken().intValue());
        assertEquals(N_MINUS_N, parser.parse("(2*3)-(sqrt(5))").getToken().intValue());
        assertEquals(N_MINUS_N, parser.parse("(2*3+sqrt(4)*ln(2))-1").getToken().intValue());
        assertEquals(N_MINUS_N, parser.parse("(2*3-sqrt(4)*ln(2))+1").getToken().intValue());
    }

    public void testTokenNumberByNumber() throws Exception {
        assertEquals(N_BY_N, parser.parse("1*0").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(1+2)*(3+4)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("2(sqrt(5))").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("2*(sqrt(5))").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(2*3)*(sqrt(5))").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(2*3+sqrt(4)*ln(2))*2").getToken().intValue());

        assertEquals(N_BY_N, parser.parse("4sin(90)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4cos(90)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4tan(90)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4ln(2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4log(2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4log2b(2)").getToken().intValue());

        assertEquals(N_BY_N, parser.parse("4*sin(90)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*cos(90)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*tan(90)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*ln(2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*log(2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*log2b(2)").getToken().intValue());

        assertEquals(N_BY_N, parser.parse("4dx(2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4int(2)").getToken().intValue());

        assertEquals(N_BY_N, parser.parse("4*dx(2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*int(2)").getToken().intValue());
    }

    public void testTokenNumberByVariable() throws Exception {
        assertEquals(N_BY_X, parser.parse("2*x").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(1+2)*x").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x(sqrt(4))").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*(sqrt(2))").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(2*x)*(sqrt(5))").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(2*3+sqrt(4)*ln(2))*x").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(2*3+sqrt(4)*ln(2))x").getToken().intValue());

        assertEquals(N_BY_X, parser.parse("x*sin(90)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*cos(90)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*tan(90)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*ln(2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*log(2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*log2b(2)").getToken().intValue());

        assertEquals(N_BY_X, parser.parse("x*dx(2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*int(2)").getToken().intValue());
    }
}
