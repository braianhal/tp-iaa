package ia.module;

import ia.module.fitness.ProceduralSimilarExpressionCalculator;
import ia.module.parser.Parser;
import junit.framework.TestCase;

public class SyntacticSimilarityTests extends TestCase{

    private static Parser parser = new Parser();
    private static ProceduralSimilarExpressionCalculator calculator = new ProceduralSimilarExpressionCalculator();

    public void testComplexitySimilarity() throws Exception {
        assertEquals(0.0, calculator.getSyntacticSimilarity(parser.parse("3"), parser.parse("10")));
        assertEquals(0.0, calculator.getSyntacticSimilarity(parser.parse("3+4"), parser.parse("1+2")));
        assertEquals(0.0, calculator.getSyntacticSimilarity(parser.parse("x^3+x^2"), parser.parse("x^2+x^3")));
        assertEquals(0.0, calculator.getSyntacticSimilarity(parser.parse("x*x+3"), parser.parse("2+x*x")));
        assertEquals(0.0, calculator.getSyntacticSimilarity(parser.parse("2x"), parser.parse("4*x")));

        assertEquals(1.0, calculator.getSyntacticSimilarity(parser.parse("1+2"), parser.parse("3+4+5")));
        assertEquals(1.0, calculator.getSyntacticSimilarity(parser.parse("1+2"), parser.parse("1-2")));
        assertEquals(1.0, calculator.getSyntacticSimilarity(parser.parse("2x+4"), parser.parse("4*x")));
        assertEquals(1.0, calculator.getSyntacticSimilarity(parser.parse("x^2"), parser.parse("x^3")));
        assertEquals(1.0, calculator.getSyntacticSimilarity(parser.parse("2x+3x"), parser.parse("2x")));
        assertEquals(1.0, calculator.getSyntacticSimilarity(parser.parse("2x+3x"), parser.parse("2x+3")));
    }
}
