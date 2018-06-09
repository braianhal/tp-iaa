package iaa.tp.fitness;

import iaa.tp.parser.Parser;
import iaa.tp.tree.ExpressionNode;
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

            Double levelDifferenceBetween = this.levelDifferenceBetween(originalExpressionTree, candidateExpressionTree);

            return levelDifferenceBetween;
        }catch (Exception e){
            System.out.println("Expresión inválida: " + candidateExpression);
            return 0.0;
        }
    }

    private Double levelDifferenceBetween(ExpressionNode originalExpressionTree, ExpressionNode candidateExpressionTree){
        Integer originalExpressionTreeLevel = originalExpressionTree.getLevel();
        Integer candidateExpressionTreeLevel = originalExpressionTree.getLevel();

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
}
