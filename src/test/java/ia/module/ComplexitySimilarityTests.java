package ia.module;

import ia.module.fitness.ProceduralSimilarExpressionCalculator;
import ia.module.parser.Parser;
import junit.framework.TestCase;

public class ComplexitySimilarityTests extends TestCase{

    private static Parser parser = new Parser();
    private static ProceduralSimilarExpressionCalculator calculator = new ProceduralSimilarExpressionCalculator();

    public void testComplexitySimilarity() throws Exception {
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("ln(ln(x))"), parser.parse("ln(x)")));
        assertEquals(4215.0/4305.0, calculator.getComplexitySimilarity(parser.parse("ln(ln(x))"), parser.parse("3ln(x)")));
        assertEquals(1686.0/1776.0, calculator.getComplexitySimilarity(parser.parse("x+cos(x)"), parser.parse("3cos(x)+x")));
        assertEquals(90.0/236.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("x+2+3")));
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("2x+3"), parser.parse("1+2x+x+4+x")));
        assertEquals(90.0/95.0, calculator.getComplexitySimilarity(parser.parse("2x+3"), parser.parse("3(x+1)")));
        assertEquals(90.0/236.0, calculator.getComplexitySimilarity(parser.parse("2x+3"), parser.parse("x^2+1")));
        assertEquals(90.0/236.0, calculator.getComplexitySimilarity(parser.parse("2x+3"), parser.parse("(x+1)(x+2)")));
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("x(2+x*3)")));
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("3x+x^2")));
        assertEquals(90.0/236.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("x+1")));
        assertEquals(1.0, calculator.getComplexitySimilarity(parser.parse("x^2+1"), parser.parse("(x+1)^2")));
        assertEquals(90.0/4418.0, calculator.getComplexitySimilarity(parser.parse("x^3-1"), parser.parse("1+log(x)")));
        assertEquals(90.0/1224.0, calculator.getComplexitySimilarity(parser.parse("x^3-1"), parser.parse("sqrt(x+2)")));
        assertEquals(236.0/237.0, calculator.getComplexitySimilarity(parser.parse("x^3-1"), parser.parse("-x^2+1")));
        assertEquals(181.0/1834.0, calculator.getComplexitySimilarity(parser.parse("x^3-1"), parser.parse("sin(x^3)")));
    }
}
