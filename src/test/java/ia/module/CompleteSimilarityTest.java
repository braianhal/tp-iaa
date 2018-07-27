package ia.module;

import ia.module.fitness.ProceduralSimilarExpressionCalculator;
import ia.module.parser.Parser;
import junit.framework.TestCase;

public class CompleteSimilarityTest extends TestCase{

    private static Parser parser = new Parser();
    private static ProceduralSimilarExpressionCalculator calculator = new ProceduralSimilarExpressionCalculator();

    public void testComplexitySimilarity() throws Exception {
        assertEquals(calculator.getLevelSimilarityBetween(parser.parse("2x+3"), parser.parse("1+2x+x+4+x"))
                        * calculator.getStructureSimilarityBetween(parser.parse("2x+3"), parser.parse("1+2x+x+4+x"))
                        * calculator.getComplexitySimilarity(parser.parse("2x+3"), parser.parse("1+2x+x+4+x"))
                        * calculator.getSyntacticSimilarity(parser.parse("2x+3"), parser.parse("1+2x+x+4+x")),
                calculator.getSimilarity(parser.parse("2x+3"), parser.parse("1+2x+x+4+x")));

        assertEquals(calculator.getLevelSimilarityBetween(parser.parse("ln(ln(x))"), parser.parse("ln(x)"))
                        * calculator.getStructureSimilarityBetween(parser.parse("ln(ln(x))"), parser.parse("ln(x)"))
                        * calculator.getComplexitySimilarity(parser.parse("ln(ln(x))"), parser.parse("ln(x)"))
                        * calculator.getSyntacticSimilarity(parser.parse("ln(ln(x))"), parser.parse("ln(x)")),
                calculator.getSimilarity(parser.parse("ln(ln(x))"), parser.parse("ln(x)")));

        assertEquals(calculator.getLevelSimilarityBetween(parser.parse("int(dx(cos(x)+3sin(x)^(4x^x+x*x^-4)))/(3-x)"), parser.parse("dx(int(4^(dx(x^x+5))))+2-x+4-3+x^2"))
                        * calculator.getStructureSimilarityBetween(parser.parse("int(dx(cos(x)+3sin(x)^(4x^x+x*x^-4)))/(3-x)"), parser.parse("dx(int(4^(dx(x^x+5))))+2-x+4-3+x^2"))
                        * calculator.getComplexitySimilarity(parser.parse("int(dx(cos(x)+3sin(x)^(4x^x+x*x^-4)))/(3-x)"), parser.parse("dx(int(4^(dx(x^x+5))))+2-x+4-3+x^2"))
                        * calculator.getSyntacticSimilarity(parser.parse("int(dx(cos(x)+3sin(x)^(4x^x+x*x^-4)))/(3-x)"), parser.parse("dx(int(4^(dx(x^x+5))))+2-x+4-3+x^2")),
                calculator.getSimilarity(parser.parse("int(dx(cos(x)+3sin(x)^(4x^x+x*x^-4)))/(3-x)"), parser.parse("dx(int(4^(dx(x^x+5))))+2-x+4-3+x^2")));
    }
}
