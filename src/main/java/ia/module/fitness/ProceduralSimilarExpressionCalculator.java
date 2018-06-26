package ia.module.fitness;

import ia.module.parser.ExpressionsWithArgumentStructures;
import ia.module.parser.Operator;
import ia.module.parser.Parser;
import ia.module.parser.tree.ExpressionNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProceduralSimilarExpressionCalculator extends SimilarExpressionCalculator {

    private ExpressionNode originalExpressionTree;
    private Parser parser = new Parser();

    @Override
    public Double similarityWith(String candidateExpression) {
        try{
            ExpressionNode candidateExpressionTree = parser.parse(candidateExpression);

            Double levelSimilarity = this.getLevelSimilarityBetween(originalExpressionTree, candidateExpressionTree);
            Double structureSimilarity = this.getStructureSimilarityBetween(originalExpressionTree, candidateExpressionTree);
            Double complexitySimilarity = this.getComplexitySimilarity(originalExpressionTree, candidateExpressionTree);

            return levelSimilarity * structureSimilarity * complexitySimilarity;
        }catch (Exception e){
            System.out.println();
            return 0.0;
        }
    }

    public Double getComplexitySimilarity(ExpressionNode originalExpressionTree, ExpressionNode candidateExpressionTree){
        List<Operator> originalExpressionTokens = this.getListOfTokensOfNormalizedExpression(originalExpressionTree);
        List<Operator> candidateExpressionTokens = this.getListOfTokensOfNormalizedExpression(candidateExpressionTree);

        List<Operator> intersection = this.getIntersection(originalExpressionTokens, candidateExpressionTokens);
        List<Operator> union = this.getUnion(originalExpressionTokens, candidateExpressionTokens);

        return getWeight(intersection).doubleValue() / getWeight(union).doubleValue();
    }

    private Long getWeight(List<Operator> operators){
        return operators.stream()
                .map(operator -> operator.getFibonacciWeight())
                .reduce(0L, (total, nextFibonacciValue) -> total + nextFibonacciValue);
    }

    private List<Operator> getIntersection(List<Operator> originalExpressionTokens, List<Operator> candidateExpressionTokens){
        return originalExpressionTokens.stream()
                .filter(token -> candidateExpressionTokens.stream().anyMatch(token1 -> token1.equals(token)))
                .map(token -> token.getOperator().equals(Operator.TERM_WITH_X_BY_TERM_WITH_X)
                        ? new Operator(Operator.TERM_WITH_X_BY_TERM_WITH_X, getIntersectionOfTermWithXByTermWithXDegrees(originalExpressionTokens, candidateExpressionTokens))
                        : token)
                .collect(Collectors.toList());
    }

    private Integer getIntersectionOfTermWithXByTermWithXDegrees(List<Operator> originalExpressionTokens, List<Operator> candidateExpressionTokens){
        Integer originalExpressionMaxDegree = this.maxDegreeOf(originalExpressionTokens);
        Integer candidateExpressionMaxDegree = this.maxDegreeOf(candidateExpressionTokens);
        return Math.min(originalExpressionMaxDegree, candidateExpressionMaxDegree);
    }

    private Integer maxDegreeOf(List<Operator> operators){
        return operators.stream()
                .map(token -> token.getOperator().equals(Operator.TERM_WITH_X_BY_TERM_WITH_X) ? token.getDegree() : 0)
                .reduce(0, (max, nextDegree) -> Math.max(max, nextDegree));
    }

    private List<Operator> getUnion(List<Operator> originalExpressionTokens, List<Operator> candidateExpressionTokens){
        List<Operator> union = new ArrayList<>();
        union.addAll(originalExpressionTokens);
        union.addAll(candidateExpressionTokens);
        return this.removeDuplicates(union);
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

    public ProceduralSimilarExpressionCalculator(String originalExpression) {
        super(originalExpression);

        try {
            this.originalExpressionTree = this.parser.parse(originalExpression);
        }catch (Exception e){
            System.out.println("Expresión patrón inválida: " + this.originalExpression + " Exceptión: " + e);
        }
    }

    public ProceduralSimilarExpressionCalculator() {
        super(null);
    }
}
