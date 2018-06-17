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
        assertEquals(N_BY_X, parser.parse("3(x+2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(3+5)*(x+2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("3(3+x+x+4x+2)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("3(x+2)/4").getToken().intValue());

        assertEquals(N_BY_X, parser.parse("(2*x)*(sqrt(5))").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("x*sin(90)*cos(90)").getToken().intValue());
        assertEquals(N_BY_X, parser.parse("(2*3)*int(2+3*4/3)").getToken().intValue());
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

    public void testTokenNumberRaisedMinusNumber() throws Exception {
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

    public void testTokenRootOfNumber() throws Exception {
        assertEquals(ROOT_OF_N, parser.parse("sqrt(4)").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(4+3)").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(4-3)").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(4*3)").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(4/3)").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(4^3)").getToken().intValue());

        assertEquals(ROOT_OF_N, parser.parse("sqrt(sin(90))").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(cos(90))").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(tan(90))").getToken().intValue());

        assertEquals(ROOT_OF_N, parser.parse("sqrt(log(2))").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(ln(2))").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(log2b(2))").getToken().intValue());

        assertEquals(ROOT_OF_N, parser.parse("sqrt(dx(4))").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(dx(4x))").getToken().intValue());

        assertEquals(ROOT_OF_N, parser.parse("sqrt(int(0))").getToken().intValue());
        assertEquals(ROOT_OF_N, parser.parse("sqrt(int(0x))").getToken().intValue());
    }

    public void testTokenVariable() throws Exception {
        assertEquals(X, parser.parse("x").getToken().intValue());
        assertEquals(X, parser.parse("2x").getToken().intValue());
        assertEquals(X, parser.parse("2*x").getToken().intValue());
        assertEquals(X, parser.parse("(1+2)*x").getToken().intValue());
        assertEquals(X, parser.parse("x(sqrt(4))").getToken().intValue());
        assertEquals(X, parser.parse("x*(sqrt(2))").getToken().intValue());

        assertEquals(X, parser.parse("(2*3+sqrt(4)*ln(2))*x").getToken().intValue());
        assertEquals(X, parser.parse("(2*3+sqrt(4)*ln(2))x").getToken().intValue());

        assertEquals(X, parser.parse("x*sin(90)").getToken().intValue());
        assertEquals(X, parser.parse("x*cos(90)").getToken().intValue());
        assertEquals(X, parser.parse("x*tan(90)").getToken().intValue());
        assertEquals(X, parser.parse("x*(tan(90)^3)").getToken().intValue());
        assertEquals(X, parser.parse("x*ln(2)").getToken().intValue());
        assertEquals(X, parser.parse("x*log(2)").getToken().intValue());
        assertEquals(X, parser.parse("x*log2b(2)").getToken().intValue());
        assertEquals(X, parser.parse("x*(log2b(2))^3").getToken().intValue());

        assertEquals(X, parser.parse("x*dx(2)").getToken().intValue());
        assertEquals(X, parser.parse("x*dx(2x)").getToken().intValue());
        assertEquals(X, parser.parse("2*dx(3x^2)").getToken().intValue());
        assertEquals(X, parser.parse("2*dx(3x*x)").getToken().intValue());
        assertEquals(X, parser.parse("(2^3)*dx(3x*x)").getToken().intValue());

        assertEquals(X, parser.parse("2*int(2)").getToken().intValue());
        assertEquals(X, parser.parse("2*int(2^3)").getToken().intValue());
        assertEquals(X, parser.parse("2*int(2+3)").getToken().intValue());
        assertEquals(X, parser.parse("2*int(2+3^4)").getToken().intValue());
        assertEquals(X, parser.parse("2*int(2*3)").getToken().intValue());
        assertEquals(X, parser.parse("2*int(2/3)").getToken().intValue());
        assertEquals(X, parser.parse("2*int(2+3*4/3)").getToken().intValue());
        assertEquals(X, parser.parse("(2^3)*int(2+3*4/3)").getToken().intValue());
        assertEquals(X, parser.parse("(2+3*4/3)*int(2+3*4/3)").getToken().intValue());
    }

    public void testTokenPlusMinusVariable() throws Exception {
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("x+1").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("2+x+1").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("2+3x+1").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("2+3x+1+2x").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("(2+3)x+3").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("(2+3)x+3x").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("(2+3)x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("(2+3^3)x+(2*3x)").getToken().intValue());

        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("sin(90)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("cos(90)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("tan(90)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("sin(x)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("cos(x)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("tan(x)*x+(2*3x)").getToken().intValue());

        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("sin(x^2)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("cos(x^2)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("tan(x^2)*x+(2*3x)").getToken().intValue());

        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("sin(x*x + x^3)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("cos(x*x + x^3)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("tan(x*x + x^3)*x+(2*3x)").getToken().intValue());

        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("log(2)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("ln(2)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("log2b(2)*x+(2*3x)").getToken().intValue());

        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("log(2x)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("ln(2x)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("log2b(2x)*x+(2*3x)").getToken().intValue());

        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("log(3+2x)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("ln(3+2x)*x+(2*3x)").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("log2b(3+2x)*x+(2*3x)").getToken().intValue());

        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("dx(2)-3x").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("dx(x^2)-3").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("dx(3+x^2)-3").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("dx(3+x^(4-2))-3").getToken().intValue());

        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("int(2)-3").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("int(2x)-3").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("int(2x+3)-3").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("int(2x^2+3)-3").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("int(2x^3+3)-3").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("2+int(2x^3+3)-3x+2x+4").getToken().intValue());
        assertEquals(PLUS_OR_MINUS_TERM_WITH_X, parser.parse("2+(int(2x^3+3))^4-3x^3+2x+4").getToken().intValue());
    }

    public void testTokenByTermWithVariable() throws Exception {
        assertEquals(BY_TERM_WITH_X, parser.parse("2(x^2+1)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("(2+3)*(x^2+1)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("(2-3)*(x^2+1)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("(2*3)*(x^2+1)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("(2^3)*(x^2+1)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("(2^3)*(x^2+1)^2").getToken().intValue());

        assertEquals(BY_TERM_WITH_X, parser.parse("(x^2+1)*log(5)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("(x^2+1)*log2b(5)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("(x^2+1)*ln(5)").getToken().intValue());

        assertEquals(BY_TERM_WITH_X, parser.parse("(x^2+1)*dx(5)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("(x^2+1)*dx(x)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("(x^2+1)*dx(x+1)").getToken().intValue());

        assertEquals(BY_TERM_WITH_X, parser.parse("3(int(2)^2+4)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("3(int(2x)+4)").getToken().intValue());
        assertEquals(BY_TERM_WITH_X, parser.parse("3(int(2x+3)+4)").getToken().intValue());
    }

    public void testTokenTermWithVariableDividedNumber() throws Exception {
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+1)/2").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+1)/(2+3)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+1)/(2-3)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+1)/(2*3)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+1)*2/3").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("2/3(x^2+1)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+1)*(2/3)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+1)*3(2/3)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+1)/(2^3)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x+1)^2/(2^3)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+1)/(2^3)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/(2^3)").getToken().intValue());

        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/sin(2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/cos(2+2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/tan(2-2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/sin(2*2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/cos(2/2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/tan(2^2)").getToken().intValue());

        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log(2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log(2+2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log(2-2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log(2*2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log(2/2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log(2^2)").getToken().intValue());

        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log2b(2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log2b(2+2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log2b(2-2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log2b(2*2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log2b(2/2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/log2b(2^2)").getToken().intValue());

        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/ln(2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)*1/ln(2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/ln(2+2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/ln(2-2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/ln(2*2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/ln(2/2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/ln(2^2)").getToken().intValue());

        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/dx(4)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/dx(2x)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/dx(2x+3)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(x^2+2x+1)/dx(2x+3)^2").getToken().intValue());

        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(int(3)^2+3)*2/3").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(int(3x)+3x)*2/3").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(int(3x^2+x+2)+3x)*2/3").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(int(3x^2+x+2)+3x)/3").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("1/3int(3x^2+x+2)").getToken().intValue());
        assertEquals(TERM_WITH_X_DIVIDED_N, parser.parse("(1/3)*int(3x^2+x+2)").getToken().intValue());
    }

    public void testTokenTermWithVariableByTermWithVariable() throws Exception {
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*x").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x+1)*x").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*(x+1)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x-1)*(x+1)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x+1)^2").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x+1)^3").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x-1)*(x+1)^2").getToken().intValue());

        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*sin(x)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x^2*sin(x)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*sin(x+1)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*sin(x^2+x+1)").getToken().intValue());

        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*cos(x)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x^2*cos(x)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*cos(x+1)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*cos(x^2+x+1)").getToken().intValue());

        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*tan(x)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x^2*tan(x)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*tan(x+1)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("x*tan(x^2+x+1)").getToken().intValue());

        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x^2+1)*log(5x)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x^2+1)*log2b(5x)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x^2+1)*ln(5x)").getToken().intValue());

        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x^2+1)*dx(5x^2)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x^2+1)*dx(x^2)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("(x^2+1)*dx((x+1)^2)").getToken().intValue());

        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("3x*(int(2)+4)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("3x*(int(2x)+4)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("3x*(int(2x+3)+4)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("3x*(int(2x^2-3)+4)").getToken().intValue());

        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("3(x+1)*(int(2)+4)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("3(x+1)*(int(2x)+4)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("3(x+1)*(int(2x+3)+4)").getToken().intValue());
        assertEquals(TERM_WITH_X_BY_TERM_WITH_X, parser.parse("3(x+1)*(int(2x^2-3)+4)").getToken().intValue());
    }

    public void testTokenDividedTermWithVariable() throws Exception {
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("2/(x+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/(x+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(2+1)/x").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(x+1)/x").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(x-1)/(x+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("2/(x+1)^2").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("4/(x+1)^3").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(x-1)/(x+1)^2").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/sin(x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(sin(x)+3)/x").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(sin(x)+3)/(sin(x)+3)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("sin(x)/x").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x^2/sin(x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/sin(x+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/sin(x^2+x+1)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/cos(x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/(cos(x)+3)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(cos(x)+3)/x").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(cos(x)+3)/(cos(x)+3)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("cos(x)/x").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x^2/cos(x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/cos(x+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/cos(x^2+x+1)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/tan(x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/(tan(x)+3)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(tan(x)+3)/x").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(tan(x)+3)/(tan(x)+3)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("tan(x)/x").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x^2/tan(x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/tan(x+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("x/tan(x^2+x+1)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(x^2+1)/log(5x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(x^2+1)/log2b(5x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(x^2+1)/ln(5x)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("log(5x)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("log(5x+2)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(log(5x+2)+3)/(x^2+1)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("log2b(5x)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("log2b(5x+2)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(log2b(5x+2)+3)/(x^2+1)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("ln(5x)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("ln(5x+2)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(ln(5x+2)+3)/(x^2+1)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(x^2+1)/dx(5x^2)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(x^2+1)/dx(x^2)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(x^2+1)/dx((x+1)^2)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("dx(5x^2)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("dx(x^2)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("dx((x+1)^2)/(x^2+1)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("dx(5)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("dx(5+3)/(x^2+1)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("dx(sin(90))/(x^2+1)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("3x/(int(2)+4)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("3x/(int(2x)+4)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("3x/(int(2x+3)+4)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("3x/(int(2x^2-3)+4)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(int(2)+4)/(3x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(int(2x)+4)/(3x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(int(2x+3)+4)/(3x)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(int(2x^2-3)+4)/(3x)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("3(x+1)/(int(2)+4)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("3(x+1)/(int(2x)+4)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("3(x+1)/(int(2x+3)+4)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("3(x+1)/(int(2x^2-3)+4)").getToken().intValue());

        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("3/(int(2)+4)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("(2+1)/(int(2x)+4)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("sin(90)/(int(2x+3)+4)").getToken().intValue());
        assertEquals(DIVIDED_TERM_WITH_X, parser.parse("dx(2x+1)/(int(2x^2-3)+4)").getToken().intValue());
    }

    public void testTokenTermWithVariableRaisedToMinusOne() throws Exception {
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("x^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("x^(3-4)").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("(x+1)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("(x*x+3)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("(1/x)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("(x^2+1)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("(x^2+x)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("(x^2+x+3)^-1").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("sin(x)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("cos(x)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("tan(x)^-1").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("sin(x+1)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("cos(x+1)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("tan(x+1)^-1").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("log(x)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("log2b(x)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("ln(x)^-1").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("log(x+1)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("log2b(x+1)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("ln(x+1)^-1").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("dx(x^2)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("dx(x^3)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("dx(x^3+2x^2+4x+3)^-1").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("int(3)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("int(x)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("int(x+1)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("int(x^2+1)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("int(x^3+1)^-1").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_1, parser.parse("int(ln(x)+sin(x))^-1").getToken().intValue());
    }

    public void testTokenTermWithVariableRaisedToMinusNumber() throws Exception {
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("x^-2").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("x^(2-4)").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("(x+1)^-2").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("(x*x+3)^-2").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("(1/x)^-2").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("(x^2+1)^-2").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("(x^2+x)^-2").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("(x^2+x+3)^-2").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("sin(x)^-2").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("cos(x)^-3").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("tan(x)^-3").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("sin(x+1)^-3").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("cos(x+1)^-3").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("tan(x+1)^-3").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("log(x)^-4").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("log2b(x)^-4").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("ln(x)^-4").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("log(x+1)^-4").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("log2b(x+1)^-5").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("ln(x+1)^-5").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("dx(x^2)^-5").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("dx(x^3)^-6").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("dx(x^3+2x^2+4x+3)^-6").getToken().intValue());

        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("int(3)^-6").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("int(x)^-6").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("int(x+1)^-6").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("int(x^2+1)^-6").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("int(x^3+1)^-2").getToken().intValue());
        assertEquals(TERM_WITH_X_RAISED_TO_MINUS_N, parser.parse("int(ln(x)+sin(x))^-2").getToken().intValue());
    }

    public void testTokenRootOfTermWithVariable() throws Exception {
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(4x)").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(4x+3)").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(4-3x)").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(4*3x)").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(4/(3x))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(4^x)").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(sqrt(x))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(sqrt(x+1))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(sqrt(x^2))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(sqrt(x*x))").getToken().intValue());

        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(sin(x))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(cos(90)+x)").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(x*tan(90))").getToken().intValue());

        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(x*log(2))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(ln(2x))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(log2b(2)+x+3)").getToken().intValue());

        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(dx(4x^2))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(dx(x^3))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(dx(sin(x)))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(dx(ln(x)+2))").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(dx(x)^x)").getToken().intValue());

        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(int(2)+3)").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(int(2)+3x)").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(int(2)^4+3x)").getToken().intValue());
        assertEquals(ROOT_OF_TERM_WITH_X, parser.parse("sqrt(int(3)+dx(x^3))").getToken().intValue());
    }

    public void testTokenTrigonometric() throws Exception {
        assertEquals(TRIGONOMETRIC, parser.parse("sin(4x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(4x+3)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(4-3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(4*3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(4/(3x))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(4^x)").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("sin(sin(x))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(cos(90)+x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(x*tan(90))").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("sin(x*log(2))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(ln(2x))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(log2b(2)+x+3)").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("sin(dx(4x^2))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(dx(x^3))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(dx(sin(x)))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(dx(ln(x)+2))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(dx(x)^x)").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("sin(int(2)+3)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(int(2)+3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(int(2)^4+3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("sin(int(3)+dx(x^3))").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("cos(4x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(4x+3)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(4-3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(4*3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(4/(3x))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(4^x)").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("cos(sin(x))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(cos(90)+x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(x*tan(90))").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("cos(x*log(2))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(ln(2x))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(log2b(2)+x+3)").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("cos(dx(4x^2))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(dx(x^3))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(dx(sin(x)))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(dx(ln(x)+2))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(dx(x)^x)").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("cos(int(2)+3)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(int(2)+3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(int(2)^4+3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("cos(int(3)+dx(x^3))").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("tan(sin(x))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(tan(90)+x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(x*tan(90))").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("tan(x*log(2))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(ln(2x))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(log2b(2)+x+3)").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("tan(dx(4x^2))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(dx(x^3))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(dx(sin(x)))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(dx(ln(x)+2))").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(dx(x)^x)").getToken().intValue());

        assertEquals(TRIGONOMETRIC, parser.parse("tan(int(2)+3)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(int(2)+3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(int(2)^4+3x)").getToken().intValue());
        assertEquals(TRIGONOMETRIC, parser.parse("tan(int(3)+dx(x^3))").getToken().intValue());
    }

    public void testTokenRaisedToTermWithVariable() throws Exception {
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("2^x").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(2-4)^x").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(-2)^(x+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(2+x)^(x*x+3)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("2^(1/x)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(3x)^(x^2+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("4^(x^2+x)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("5^(x^2+x+3)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(5/x)^(x^2+x+3)").getToken().intValue());

        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("2^sin(x)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("2^cos(x)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("2^tan(x)").getToken().intValue());

        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("3^sin(x+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(3ln(x))^sin(x+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(3log(x))^cos(x+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(3log2b(x))^tan(x+1)").getToken().intValue());

        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("4^log(x)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("4^log2b(x)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("4^ln(x)").getToken().intValue());

        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(4+x)^log(x+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(x*x*x)^log2b(x+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("5^ln(x+1)").getToken().intValue());

        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("5^dx(x^2)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("5^dx(x^3)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(5+x)^dx(x^3)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("5^dx(x^3+2x^2+4x+3)").getToken().intValue());

        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("6^int(3)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(6x)^int(3)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("6^int(x)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("(6^x)^int(x)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("6^int(x+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("6^int(x^2+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("6^int(x^3+1)").getToken().intValue());
        assertEquals(RAISED_TO_TERM_WITH_X, parser.parse("6^int(ln(x)+sin(x))").getToken().intValue());
    }

    public void testTokenLogarithm() throws Exception {
        assertEquals(LOGARITHM, parser.parse("log(4x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(4x+3)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(4-3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(4*3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(4/(3x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(4^x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(sqrt(x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(log(x+1))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(sqrt(x^2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(log(x*x))").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("log(sin(x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(cos(90)+x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(x*tan(90))").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("log(x*log(2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(ln(2x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(log2b(2)+x+3)").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("log(dx(4x^2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(dx(x^3))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(dx(sin(x)))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(dx(ln(x)+2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(dx(x)^x)").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("log(int(2)+3)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(int(2)+3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(int(2)^4+3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log(int(3)+dx(x^3))").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("log2b(4x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(4x+3)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(4-3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(4*3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(4/(3x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(4^x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(log2b(x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(sqrt(x+1))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(log2b(x^2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(ln(x*x))").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("log2b(sin(x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(cos(90)+x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(x*tan(90))").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("log2b(x*log(2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(ln(2x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(log2b(2)+x+3)").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("log2b(dx(4x^2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(dx(x^3))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(dx(sin(x)))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(dx(ln(x)+2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(dx(x)^x)").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("log2b(int(2)+3)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(int(2)+3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(int(2)^4+3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("log2b(int(3)+dx(x^3))").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("ln(4x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(4x+3)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(4-3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(4*3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(4/(3x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(4^x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(ln(x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(ln(x+1))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(ln(x^2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(ln(x*x))").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("ln(sin(x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(cos(90)+x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(x*tan(90))").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("ln(x*log(2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(ln(2x))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(log2b(2)+x+3)").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("ln(dx(4x^2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(dx(x^3))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(dx(sin(x)))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(dx(ln(x)+2))").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(dx(x)^x)").getToken().intValue());

        assertEquals(LOGARITHM, parser.parse("ln(int(2)+3)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(int(2)+3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(int(2)^4+3x)").getToken().intValue());
        assertEquals(LOGARITHM, parser.parse("ln(int(3)+dx(x^3))").getToken().intValue());
    }

    public void testTokenDerivative() throws Exception {
        assertEquals(DERIVATIVE, parser.parse("dx(4)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4+4)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4-4)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4*4)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4/4)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4^4)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4x)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4x+3)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4-3x)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4*3x)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4/(3x))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(4^x)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(dx(x))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(sqrt(x+1))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(dx(x^2))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(sqrt(x*x))").getToken().intValue());

        assertEquals(DERIVATIVE, parser.parse("dx(sin(x))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(cos(90)+x)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(x*tan(90))").getToken().intValue());

        assertEquals(DERIVATIVE, parser.parse("dx(x*log(2))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(ln(2x))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(log2b(2)+x+3)").getToken().intValue());

        assertEquals(DERIVATIVE, parser.parse("dx(dx(4x^2))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(dx(x^3))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(dx(sin(x)))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(dx(ln(x)+2))").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(dx(x)^x)").getToken().intValue());

        assertEquals(DERIVATIVE, parser.parse("dx(int(2)+3)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(int(2)+3x)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(int(2)^4+3x)").getToken().intValue());
        assertEquals(DERIVATIVE, parser.parse("dx(int(3)+dx(x^3))").getToken().intValue());
    }

    public void testTokenIntegral() throws Exception {
        assertEquals(INTEGRAL, parser.parse("int(0)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4+4)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4-4)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4*4)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4/4)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4^4)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4x)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4x+3)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4-3x)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4*3x)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4/(3x))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(4^x)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(int(x))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(sqrt(x+1))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(int(x^2))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(sqrt(x*x))").getToken().intValue());

        assertEquals(INTEGRAL, parser.parse("int(sin(x))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(cos(90)+x)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(x*tan(90))").getToken().intValue());

        assertEquals(INTEGRAL, parser.parse("int(x*log(2))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(ln(2x))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(log2b(2)+x+3)").getToken().intValue());

        assertEquals(INTEGRAL, parser.parse("int(dx(4x^2))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(dx(x^3))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(dx(sin(x)))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(dx(ln(x)+2))").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(dx(x)^x)").getToken().intValue());

        assertEquals(INTEGRAL, parser.parse("int(int(2)+3)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(int(2)+3x)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(int(2)^4+3x)").getToken().intValue());
        assertEquals(INTEGRAL, parser.parse("int(int(3)+dx(x^3))").getToken().intValue());
    }
}
