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

        assertEquals(N_BY_N, parser.parse("3^4").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("3^(4+2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("3^(4*2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("3^(4/2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(3+2)^(4)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(1/2)^(4)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(1/2 + 1.5)^(4)").getToken().intValue());

        assertEquals(N_BY_N, parser.parse("4dx(2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*dx(2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(dx(2))^2").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(dx(2x))^2").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(dx(1+2x))^2").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(dx((1+2x)+3+x))^2").getToken().intValue());

        assertEquals(N_BY_N, parser.parse("4int(0)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4int(0+0)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4int(0-0)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4int(0*0)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4int(0^2)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*int(0+0)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*int(0-0)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*int(0*0)").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("4*int(0^2)").getToken().intValue());

        assertEquals(N_BY_N, parser.parse("(int(0))^2").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(int(0(1+2)))^2").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(int(0*(1+2)))^2").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(int(0*(1+x)))^2").getToken().intValue());
        assertEquals(N_BY_N, parser.parse("(int((1+x)*0))^2").getToken().intValue());
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
        assertEquals(N_BY_X, parser.parse("x*sin(90)*cos(90)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*(tan(90)^3)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*ln(2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*log(2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*log2b(2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*(log2b(2))^3").getToken().intValue());

        assertEquals(N_BY_X, parser.parse("x*dx(2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*dx(2x)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("2*dx(3x^2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("2*dx(3x*x)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(2^3)*dx(3x*x)").getToken().intValue());

        assertEquals(N_BY_X, parser.parse("2*int(2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("2*int(2^3)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("2*int(2+3)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("2*int(2+3^4)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("2*int(2*3)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("2*int(2/3)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("2*int(2+3*4/3)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(2^3)*int(2+3*4/3)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(2*3)*int(2+3*4/3)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(2+3*4/3)*int(2+3*4/3)").getToken().intValue());
    }

    public void testTokenNumberDivideNumber() throws Exception {
        assertEquals(N_DIVIDED_N, parser.parse("0/2").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("(1+2)/(3+4)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("2/(sqrt(5))").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("2/(sqrt(5))").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("(2*3)/(sqrt(5))").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("(2*3+sqrt(4)*ln(2))/2").getToken().intValue());

        assertEquals(N_DIVIDED_N, parser.parse("4/sin(90)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/cos(90)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/tan(90)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/ln(2)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/log(2)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/log2b(2)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("log2b(2)/4").getToken().intValue());

        assertEquals(N_DIVIDED_N, parser.parse("2/(3^4)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("3/(3^(4+2))").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/(3^(4*2))").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("2/(3^(4/2))").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("5/((3+2)^(4))").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("1/((1/2)^(4))").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("2/((1/2 + 1.5)^(4))").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("((1/2 + 1.5)^(4))/2").getToken().intValue());

        assertEquals(N_DIVIDED_N, parser.parse("4/dx(2)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/dx(2)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("2/((dx(2))^2)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("1/((dx(2x))^2)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("2/((dx(1+2x))^2)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("3/((dx((1+2x)+3+x))^2)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("((dx((1+2x)+3+x))^2)/3").getToken().intValue());

        assertEquals(N_DIVIDED_N, parser.parse("4/int(0)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/int(0+0)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/int(0-0)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/int(0*0)").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("4/int(0^2)").getToken().intValue());

        assertEquals(N_DIVIDED_N, parser.parse("3/(int(0))^2").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("2/(int(0(1+2)))^2").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("1/(int(0*(1+2)))^2").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("2/(int(0*(1+x)))^2").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("3/(int((1+x)*0))^2").getToken().intValue());
        assertEquals(N_DIVIDED_N, parser.parse("(int((1+x)*0))^2/4").getToken().intValue());
    }

    public void testTokenNumberRaisedMinusN() throws Exception {
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("1^-2").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("(1+2)^-3").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("(1*2)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("(1/2)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("(1-2)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("(3^2)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("4^(5-6)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("4^(-2-3)").getToken().intValue());

        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("sin(3)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("cos(3)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("tan(3)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("sin(3+2)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("cos(3+2)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("tan(3+2)^(-3)").getToken().intValue());

        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("log(3)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("log(3+2)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("ln(3)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("ln(3+2)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("log2b(3)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("log2b(3+2)^(-3)").getToken().intValue());

        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("dx(4)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("dx(4+2)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("dx(4x)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("dx(4x+2)^(-3)").getToken().intValue());

        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("int(0)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("int(3-3)^(-3)").getToken().intValue());
        assertEquals(N_RAISED_TO_MINUS_N, parser.parse("int(0x)^(-3)").getToken().intValue());
    }
}
