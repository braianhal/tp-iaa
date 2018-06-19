package ia.module;

import ia.module.fitness.ProceduralSimilarExpressionCalculator;
import ia.module.parser.Parser;
import junit.framework.TestCase;

public class ComplexitySimilarityTests extends TestCase{

    private static Parser parser = new Parser();
    private static ProceduralSimilarExpressionCalculator calculator = new ProceduralSimilarExpressionCalculator();

    public void testComplexitySimilarity() throws Exception {
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("ln(ln(x))"), parser.parse("ln(x)")));
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("2x+3"), parser.parse("1+2x+x+4+x")));
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("x(2+x*3)")));
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("3x+x^2")));
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("(x+1)^2")));
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("dx((4*x)/(x+1))"), parser.parse("2.0+dx((x)/(x))")));

        assertEquals(4215.0/4305.0, calculator.getComplexitySimilarity(parser.parse("ln(ln(x))"), parser.parse("3ln(x)")));
        assertEquals(1686.0/1776.0, calculator.getComplexitySimilarity(parser.parse("x+cos(x)"), parser.parse("3cos(x)+x")));
        assertEquals(90.0/236.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("x+2+3")));
        assertEquals(90.0/95.0, calculator.getComplexitySimilarity(parser.parse("2x+3"), parser.parse("3(x+1)")));
        assertEquals(90.0/236.0, calculator.getComplexitySimilarity(parser.parse("2x+3"), parser.parse("x^2+1")));
        assertEquals(90.0/236.0, calculator.getComplexitySimilarity(parser.parse("2x+3"), parser.parse("(x+1)(x+2)")));
        assertEquals(90.0/236.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("x+1")));
        assertEquals(90.0/4418.0, calculator.getComplexitySimilarity(parser.parse("x^3-1"), parser.parse("1+log(x)")));
        assertEquals(90.0/1224.0, calculator.getComplexitySimilarity(parser.parse("x^3-1"), parser.parse("sqrt(x+2)")));
        assertEquals(236.0/237.0, calculator.getComplexitySimilarity(parser.parse("x^3-1"), parser.parse("-x^2+1")));
        assertEquals(181.0/1834.0, calculator.getComplexitySimilarity(parser.parse("x^3-1"), parser.parse("sin(x^3)")));
        assertEquals(4270.0/5957.0, calculator.getComplexitySimilarity(parser.parse("ln(x+16)*12"), parser.parse("sin(ln(x-x))")));
        assertEquals(22.0/36.0, calculator.getComplexitySimilarity(parser.parse("4+sqrt(25)/(3*2-1)"), parser.parse("sqrt(0.0)")));
        assertEquals(20385.0/23062.0, calculator.getComplexitySimilarity(parser.parse("int(dx(cos(x)+3sin(x)^(4x^x+x*x^-4)))/(3-x)"), parser.parse("dx(int(4^(dx(x^x+5))))")));
        assertEquals(20531.0/23062.0, calculator.getComplexitySimilarity(parser.parse("int(dx(cos(x)+3sin(x)^(4x^x+x*x^-4)))/(3-x)"), parser.parse("dx(int(4^(dx(x^x+5))))+2-x+4-3+x^2")));
    }
}
