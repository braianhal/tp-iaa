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

    private Double getStructureExistenceSimilarity(ExpressionsWithArgumentStructures originalExpressionsStructures, ExpressionsWithArgumentStructures candidateExpressionsStructures){
        return this.toRatio(originalExpressionsStructures.getCountOfStructuresNotIncludedInto(candidateExpressionsStructures));
    }

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
