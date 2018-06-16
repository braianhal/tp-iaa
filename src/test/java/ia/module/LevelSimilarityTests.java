package ia.module;

import ia.module.fitness.ProceduralSimilarExpressionCalculator;
import ia.module.parser.Parser;
import junit.framework.TestCase;

public class LevelSimilarityTests extends TestCase{

    private static Parser parser = new Parser();
    private static ProceduralSimilarExpressionCalculator calculator = new ProceduralSimilarExpressionCalculator();

    public void testSameLevel() throws Exception {
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("1+2"), parser.parse("3+2")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("1+2*3"), parser.parse("3*2*4-2")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("5^4"), parser.parse("3*2*4-2^2")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("1-x+4"), parser.parse("x+x+3")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("1-3x/2+4"), parser.parse("2x+x+3")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("x^2+2x+1"), parser.parse("(x+1)^2")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("sin(x)^2+cos(x)^2"), parser.parse("tan(x^2)")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("3^(x+2)"), parser.parse("(1/5+4)^(sin(x)+x^2)")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("3+ln(1+x^2+sin(x))"), parser.parse("1/(log2b(4x^2+3^4))")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("dx(4)"), parser.parse("2^(dx(4^x))")));
        assertEquals(1.0, calculator.getLevelSimilarityBetween(parser.parse("int(sin(x)+ln(x))"), parser.parse("int(x^2*dx(x^3))")));
    }

    public void testLevelDistance1() throws Exception {
        assertEquals(9.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("1+2"), parser.parse("1+2*3")));
        assertEquals(9.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("3x+3"), parser.parse("3sqrt(x)+2^3")));
        assertEquals(9.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("sin(x)^2"), parser.parse("(3x)^(2x^2)")));
        assertEquals(9.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("ln(x)"), parser.parse("dx(5)")));
        assertEquals(9.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("dx(x^2+ln(x))"), parser.parse("int((x^3)/(6x))")));
    }

    public void testLevelDistance2() throws Exception {
        assertEquals(8.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("1+2"), parser.parse("1+sqrt(4)")));
        assertEquals(8.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("x+x+4+x"), parser.parse("x^2+2x+1")));
        assertEquals(8.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("x^2+2x+1"), parser.parse("3^x")));
        assertEquals(8.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("sin(sin(cos(x)))"), parser.parse("ln(x)")));
        assertEquals(8.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("3^(x^2+x^3)"), parser.parse("dx(4x)")));
        assertEquals(8.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("log(10x+4x+sin(x))"), parser.parse("int(4x)")));
    }

    public void testLevelDistance3() throws Exception {
        assertEquals(7.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("1+2"), parser.parse("1+2+x+3")));
        assertEquals(7.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("sqrt(1+2)"), parser.parse("sqrt(1+2+x+3)")));
        assertEquals(7.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("x^2+(x+1)^2"), parser.parse("ln(3x+2)")));
        assertEquals(7.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("sin(x+1)"), parser.parse("dx(x)")));
        assertEquals(7.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("sin(x)^(4x)"), parser.parse("int(2x*ln(x)^sin(x))")));
    }

    public void testLevelDistance4() throws Exception {
        assertEquals(6.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("3*2+4*5"), parser.parse("x^2")));
        assertEquals(6.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("x+3+x"), parser.parse("3^(x*x)")));
        assertEquals(6.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("2x+3x+1"), parser.parse("log(2)")));
        assertEquals(6.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("sin(x)+tan(x)"), parser.parse("2^(int(x^2))")));
    }

    public void testLevelDistance5() throws Exception {
        assertEquals(5.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("-3"), parser.parse("sqrt(x+3)")));
        assertEquals(5.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("sqrt(4)"), parser.parse("(3-4)^(sin(x))")));
        assertEquals(5.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("3+2x"), parser.parse("(2-5)/(4dx(ln(4x)))")));
        assertEquals(5.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("3sqrt(x^-1)"), parser.parse("2-int(4x^2)")));
    }

    public void testLevelDistance6() throws Exception {
        assertEquals(4.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("-3+4+5"), parser.parse("1+sin(x^2)")));
        assertEquals(4.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("2^3+3*3"), parser.parse("1/(log2b(4))")));
        assertEquals(4.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("x+3*3"), parser.parse("sin(dx(4x))")));
        assertEquals(4.0/10.0, calculator.getLevelSimilarityBetween(parser.parse("(2^3)x+3*3"), parser.parse("tan(int(4x))")));
    }

    public void testLevelDistance7() throws Exception {
        assertEquals(3.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("-3+4+5"), parser.parse("1+sin(2^(x^2))")));
        assertEquals(3.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("-3+4/5"), parser.parse("1+sin(ln(x^2))")));
        assertEquals(3.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("sqrt(-3+4)/5"), parser.parse("1+dx(ln(x^2))")));
        assertEquals(3.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("(-3+4)/5+x"), parser.parse("1+dx(int(x^2))")));
    }

    public void testLevelDistance8() throws Exception {
        assertEquals(2.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("-3+4+5"), parser.parse("1+sin(2^(ln(x^2)))")));
        assertEquals(2.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("-3*4/5"), parser.parse("1+dx(2^(ln(x^2)))")));
        assertEquals(2.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("-3*4^5"), parser.parse("1+int(2^(ln(x^2)))")));
    }

    public void testLevelDistance9() throws Exception {
        assertEquals(1.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("-3+4+5"), parser.parse("1+dx(2^(ln(sin(x^2))))")));
        assertEquals(1.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("-3*4+5"), parser.parse("1+dx(2^(int(sin(x^2))))")));
    }

    public void testLevelDistance10() throws Exception {
        assertEquals(0.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("-3+4+5"), parser.parse("int(2)")));
        assertEquals(0.0 / 10.0, calculator.getLevelSimilarityBetween(parser.parse("-3+4+5"), parser.parse("1+dx(2^(int(sin(x^2))))")));
    }
}
