package ia.module;

import ia.module.fitness.ProceduralSimilarExpressionCalculator;
import ia.module.parser.Parser;
import junit.framework.TestCase;

public class StructureSimilarityTests extends TestCase{

    private static Parser parser = new Parser();
    private static ProceduralSimilarExpressionCalculator calculator = new ProceduralSimilarExpressionCalculator();

    public void testSameStructure() throws Exception {
        assertEquals(1.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)"), parser.parse("cos(x)")));
        assertEquals(1.0, calculator.getStructureSimilarityBetween(parser.parse("dx(sin(x))"), parser.parse("dx(cos(x))")));
        assertEquals(1.0, calculator.getStructureSimilarityBetween(parser.parse("cos(sin(x))"), parser.parse("tan(cos(x))")));
        assertEquals(1.0, calculator.getStructureSimilarityBetween(parser.parse("cos(sqrt(x))"), parser.parse("tan(sqrt(x))")));
        assertEquals(1.0, calculator.getStructureSimilarityBetween(parser.parse("ln(cos(sqrt(x)))"), parser.parse("log(tan(sqrt(x)))")));
        assertEquals(1.0, calculator.getStructureSimilarityBetween(parser.parse("int(dx(x))"), parser.parse("3*int(dx(x))")));
        assertEquals(1.0, calculator.getStructureSimilarityBetween(parser.parse("5sqrt(1+x+3)+int(dx(x*cos(x)+3))"), parser.parse("3sqrt(1+x)+int(dx(x*sin(x)+3x))")));
        assertEquals(1.0, calculator.getStructureSimilarityBetween(parser.parse("cos(x-3)-log(x)"), parser.parse("3cos(x+1)/2+ln(x)")));
        assertEquals(1.0, calculator.getStructureSimilarityBetween(parser.parse("cos(x)+cos(x)+cos(x)"), parser.parse("cos(x)")));
    }

    public void testDifferentStructures() throws Exception {
        assertEquals(1.0/3.0 * 1.0/2.0, calculator.getStructureSimilarityBetween(parser.parse("ln(x)+ln(x)"), parser.parse("sin(x)")));
        assertEquals(1.0/2.0, calculator.getStructureSimilarityBetween(parser.parse("ln(x)"), parser.parse("sin(x)+ln(x)")));
        assertEquals(1.0/3.0, calculator.getStructureSimilarityBetween(parser.parse("ln(x)"), parser.parse("sin(x)+ln(x)+ln(x)")));
        assertEquals(1.0/2.0 * 1.0/2.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)"), parser.parse("ln(x)")));
        assertEquals(1.0/3.0 * 1.0/2.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)+cos(x)"), parser.parse("ln(x)")));
        assertEquals(1.0/3.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)+dx(ln(x))"), parser.parse("ln(x)")));
        assertEquals(1.0/2.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)+ln(x)"), parser.parse("ln(x)")));
        assertEquals(1.0/3.0, calculator.getStructureSimilarityBetween(parser.parse("dx(int(ln(x)))+int(x)"), parser.parse("dx(int(x))")));
        assertEquals(1.0/4.0, calculator.getStructureSimilarityBetween(parser.parse("4dx(int(ln(x)+3x+sqrt(5)))+int(x)"), parser.parse("dx(int(x))/5")));
        assertEquals(1.0/4.0, calculator.getStructureSimilarityBetween(parser.parse("cos(cos(cos(cos(x))))"), parser.parse("cos(x)")));
        assertEquals(1.0/5.0 * 1.0/2.0, calculator.getStructureSimilarityBetween(parser.parse("cos(cos(cos(cos(x))))"), parser.parse("ln(x)")));
        assertEquals(1.0/2.0 * 1.0/5.0, calculator.getStructureSimilarityBetween(parser.parse("ln(x)"), parser.parse("cos(cos(cos(cos(x))))")));
    }

    public void testDifferentStructuresCardinality() throws Exception {
        assertEquals(1.0/2.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)"), parser.parse("cos(x)+sin(x)")));
        assertEquals(1.0/3.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)"), parser.parse("cos(x)+sin(x)+tan(x)")));
        assertEquals(1.0/2.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)+ln(x)+ln(x)+ln(x)"), parser.parse("cos(x)+sin(x)+ln(x)")));
        assertEquals(1.0/2.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)+ln(x)+ln(x)+ln(x)"), parser.parse("cos(x)+sin(x)+log(x)+ln(x)+log2b(x)")));
        assertEquals(1.0/3.0, calculator.getStructureSimilarityBetween(parser.parse("sin(x)+ln(x)+ln(x)+ln(x)"), parser.parse("cos(x)+sin(x)+log(x)+ln(x)+log2b(x)+ln(x)")));
        assertEquals(1.0/3.0, calculator.getStructureSimilarityBetween(parser.parse("dx(2)+int(2x)"), parser.parse("dx(3)+dx(5)+dx(x)+int(x/3)")));
    }
}
