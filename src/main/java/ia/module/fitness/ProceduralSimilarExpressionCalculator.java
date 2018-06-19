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
        ExpressionNode originalExpressionTreeNormalized = originalExpressionTree.normalize();
        ExpressionNode candidateExpressionTreeNormalized = candidateExpressionTree.normalize();

        List<Operator> originalExpressionTokens = this.removeDuplicates(originalExpressionTreeNormalized.getListOfTokens());
        List<Operator> candidateExpressionTokens = this.removeDuplicates(candidateExpressionTreeNormalized.getListOfTokens());

        List<Operator> intersection = this.getIntersection(originalExpressionTokens, candidateExpressionTokens);
        List<Operator> union = this.getUnion(originalExpressionTokens, candidateExpressionTokens);

        return getWeight(intersection).doubleValue() / getWeight(union).doubleValue();
    }

    private Long getWeight(List<Operator> operators){
        return operators.stream()
                .map(operator -> operator.getFibonacciWeight(operator))
                .reduce(0L, (total, nextFibonacciValue) -> total + nextFibonacciValue);
    }

    private List<Operator> getIntersection(List<Operator> originalExpressionTokens, List<Operator> candidateExpressionTokens){
        List<Operator> intersection = originalExpressionTokens.stream()
                .filter(token -> candidateExpressionTokens.stream().anyMatch(token1 -> token1.equals(token)))
                .collect(Collectors.toList());

        return this.chooseTermWithVariableByTermWithVariableDegree(candidateExpressionTokens, intersection, false);
    }

    private List<Operator> getUnion(List<Operator> originalExpressionTokens, List<Operator> candidateExpressionTokens){
        List<Operator> union = new ArrayList<>();
        union.addAll(originalExpressionTokens);
        union.addAll(candidateExpressionTokens);
        return this.removeDuplicates(union);
    }

    private List<Operator> removeDuplicates(List<Operator> operators){
        List<Operator> operatorsWithoutDuplicates = new ArrayList<>();

        for(Operator operator : operators){
            if(operatorsWithoutDuplicates.stream().noneMatch(operator1 -> operator1.equals(operator))){
                operatorsWithoutDuplicates.add(new Operator(operator.getOperator(), operator.getDegree()));
            }
        }

        return this.chooseTermWithVariableByTermWithVariableDegree(operators, operatorsWithoutDuplicates, true);
    }

    private List<Operator> chooseTermWithVariableByTermWithVariableDegree(List<Operator> reference, List<Operator> result, Boolean chooseMax){
        Integer maxDegree = reference.stream()
                .filter(operator -> operator.getOperator().equals(Operator.TERM_WITH_X_BY_TERM_WITH_X))
                .map(Operator::getDegree)
                .reduce(0, Math::max);

        Integer degreeToUse;

        if(chooseMax){
            degreeToUse = maxDegree;
        }else{
            degreeToUse = reference.stream()
                    .filter(operator -> operator.getOperator().equals(Operator.TERM_WITH_X_BY_TERM_WITH_X))
                    .map(Operator::getDegree)
                    .reduce(maxDegree, Math::min);
        }

        return result.stream()
                .map(operator ->
                        operator.getOperator().equals(Operator.TERM_WITH_X_BY_TERM_WITH_X) ?
                        new Operator(operator.getOperator(), degreeToUse) :
                        operator)
                .collect(Collectors.toList());
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
