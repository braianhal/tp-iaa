package ia.module.fitness;

import ia.module.parser.ExpressionsWithArgumentStructures;
import ia.module.parser.Parser;
import ia.module.parser.tree.ExpressionNode;
import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.op.Op;

public class ProceduralSimilarExpressionCalculator extends SimilarExpressionCalculator {

    private String originalExpression;

    @Override
    public Double similarityWith(TreeNode<Op<Double>> otherExpression) {
        return this.similarityWith(new Parser().getAsInfix(otherExpression));
    }

    public Double similarityWith(String candidateExpression) {
        Parser parser = new Parser();
        try {
            ExpressionNode originalExpressionTree = parser.parse(this.originalExpression);
            ExpressionNode candidateExpressionTree = parser.parse(candidateExpression);

            Double levelSimilarity = this.getLevelSimilarityBetween(originalExpressionTree, candidateExpressionTree);
            Double structureSimilarity = this.getStructureSimilarityBetween(originalExpressionTree, candidateExpressionTree);

            return levelSimilarity * structureSimilarity;
        }catch (Exception e){
            System.out.println("Expresión inválida. Original: " + this.originalExpression + " Candidata: " + candidateExpression);
            return 0.0;
        }
    }

    public Double getStructureSimilarityBetween(ExpressionNode originalExpressionTree, ExpressionNode candidateExpressionTree){
        ExpressionsWithArgumentStructures originalExpressionsStructures = originalExpressionTree.getStructureOf(new ExpressionsWithArgumentStructures());
        ExpressionsWithArgumentStructures candidateExpressionsStructures = candidateExpressionTree.getStructureOf(new ExpressionsWithArgumentStructures());

        Double structureExistenceSimilarity = this.getStructureExistenceSimilarity(originalExpressionsStructures, candidateExpressionsStructures);
        Double structuresCountSimilarity = this.getStructuresCountSimilarity(originalExpressionsStructures, candidateExpressionsStructures);

        return structureExistenceSimilarity * structuresCountSimilarity;
    }

    /**
     * Returns the ratio corresponding to the amount of structures that are included into the original expression (pattern) but doesn't belong to the candidate expression.
     * If the pattern has more than 1 occurrences, then count the total of occurrences to create the ratio. When all the structures of the pattern are into the candidate
     * (also occurrences), then the ratio will be 1 (100% of equivalence)
     *
     * Example: pattern = ln(x) + ln(x), candidate = sin(x). In this case, ln(x) is two times into the original expression, and zero times into the candidate expression.
     * Therefore, the ratio is created in this way: 1 / (1+2) --> 1/3.
     *
     * Example 2: pattern = ln(x), candidate = ln(x) + sin(x). In this case, ln(x) is one time into pattern and candidate.
     * Therefore, the ratio will be: 1 / (1+0) --> 1
     */
    private Double getStructureExistenceSimilarity(ExpressionsWithArgumentStructures originalExpressionsStructures, ExpressionsWithArgumentStructures candidateExpressionsStructures){
        return this.toRatio(originalExpressionsStructures.getCountOfStructuresNotIncludedInto(candidateExpressionsStructures));
    }

    /**
     * Returns the ratio corresponding to the diff of occurrences of each structure between candidate and pattern expressions. We iterate the candidate expressions, to find
     * the diff of occurrences of each structure.
     *
     * If a structure is into the candidate, but isn't into the pattern, then the diff will be the occurrences into the candidate.
     *
     * If a structure is into the pattern and candidate, then the diff will be the abs of the occurrences into the pattern minus the occurrences into the candidate,
     * only if the structure is more times into the candidate than the pattern. In other case, won't be diff, so the ratio will be 1.
     *
     * Example: pattern = sin(x), candidate = ln(x). In this case, ln(x) is into the candidate one time, but isn't into the pattern.
     * Therefore, the ratio will be: 1 / (1+1) --> 1/2
     *
     * Example 2: pattern = sin(x)+cos(x), candidate = sin(x)+tan(x)+tan(x). In this case, we have 3 occurrences of trigonometric expressions into the candidate,
     * and 2 occurrences of trigonometric expressions into the pattern. Therefore, the ratio will be: 1 / (1 + 3-2) --> 1/2
     *
     * Example 3: pattern = cos(x)+cos(x)+cos(x), candidate = cos(x). In this case, cos(x) is 1 time into the candidate, but 3 into the pattern, therefore the diff will be 0,
     * and ratio will be 1 (100% of equivalence).
     */
    private Double getStructuresCountSimilarity(ExpressionsWithArgumentStructures originalExpressionsStructures, ExpressionsWithArgumentStructures candidateExpressionsStructures){
        return this.toRatio(candidateExpressionsStructures.getCountOfStructuresWithCountOfOccurrencesOutOfBoundOf(originalExpressionsStructures));
    }

    private Double toRatio(Long value){
        return 1 / (1 + value.doubleValue());
    }

    public Double getLevelSimilarityBetween(ExpressionNode originalExpressionTree, ExpressionNode candidateExpressionTree){
        Integer originalExpressionTreeLevel = originalExpressionTree.getLevel();
        Integer candidateExpressionTreeLevel = candidateExpressionTree.getLevel();

        Integer difference = Math.abs(originalExpressionTreeLevel - candidateExpressionTreeLevel);
        return (10 - difference.doubleValue()) / 10;
    }

    public ProceduralSimilarExpressionCalculator(TreeNode<Op<Double>> originalExpression) {
        super(originalExpression);
    }

    public ProceduralSimilarExpressionCalculator(String originalExpression) {
        super(null);
        this.originalExpression = originalExpression;
    }

    public ProceduralSimilarExpressionCalculator() {
        super(null);
    }
}
