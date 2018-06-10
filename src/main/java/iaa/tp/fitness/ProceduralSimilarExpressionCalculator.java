package iaa.tp.fitness;

import iaa.tp.parser.Parser;
import iaa.tp.tree.ExpressionNode;
import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.op.Op;
import org.apache.commons.lang3.StringUtils;

public class ProceduralSimilarExpressionCalculator extends SimilarExpressionCalculator {

    private String originalExpression;

    @Override
    public Double similarityWith(TreeNode<Op<Double>> otherExpression) {
        return this.similarityWith(new Parser().getAsInfix(otherExpression));
    }

    public Double similarityWith(String candidateExpression) {
        Parser parser = new Parser();
        try {
            ExpressionNode originalExpressionTree = parser.parse(this.cleanFormatOf(this.originalExpression));
            ExpressionNode candidateExpressionTree = parser.parse(this.cleanFormatOf(candidateExpression));

            Double levelDifferenceBetween = this.levelDifferenceBetween(originalExpressionTree, candidateExpressionTree);

            return levelDifferenceBetween;
        }catch (Exception e){
            System.out.println("Expresión inválida. Original: " + this.originalExpression + " Candidata: " + candidateExpression);
            return 0.0;
        }
    }

    private Double levelDifferenceBetween(ExpressionNode originalExpressionTree, ExpressionNode candidateExpressionTree){
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

    private String cleanFormatOf(String expression){
        String expressionCleaned = expression;

        expressionCleaned = this.addMultiplicationSymbols(expressionCleaned);

        return expressionCleaned
                .replaceAll("e", "2.718281828459045235360")
                .replaceAll("pi", "3.14159265358979323846");
    }

    private String addMultiplicationSymbols(String expression){
        for(int i = 0 ; i <= 9 ; i++){
            expression = expression
                    .replaceAll(i + "\\(", i + "*(")
                    .replaceAll(i + "x", i + "*x");
        }
        return expression;
    }
}
