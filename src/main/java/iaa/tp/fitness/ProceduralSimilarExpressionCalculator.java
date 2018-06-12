package iaa.tp.fitness;

import iaa.tp.parser.ExpressionsWithArgumentStructures;
import iaa.tp.parser.Parser;
import iaa.tp.parser.tree.ExpressionNode;
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
            ExpressionNode originalExpressionTree = parser.parse(this.cleanFormatOf(this.originalExpression));
            ExpressionNode candidateExpressionTree = parser.parse(this.cleanFormatOf(candidateExpression));

            Double levelSimilarity = this.getLevelSimilarityBetween(originalExpressionTree, candidateExpressionTree);
            Double structureSimilarity = this.getStructureSimilarityBetween(originalExpressionTree, candidateExpressionTree);

            return levelSimilarity;
        }catch (Exception e){
            System.out.println("Expresión inválida. Original: " + this.originalExpression + " Candidata: " + candidateExpression);
            return 0.0;
        }
    }

    private Double getStructureSimilarityBetween(ExpressionNode originalExpressionTree, ExpressionNode candidateExpressionTree){
        ExpressionsWithArgumentStructures originalExpressionsStructure = originalExpressionTree.getStructureOf(new ExpressionsWithArgumentStructures());
        ExpressionsWithArgumentStructures candidateExpressionsStructure = candidateExpressionTree.getStructureOf(new ExpressionsWithArgumentStructures());

        return 0.0;
    }

    private Double getLevelSimilarityBetween(ExpressionNode originalExpressionTree, ExpressionNode candidateExpressionTree){
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
                .replaceAll("pi", "3.14159265358979323846")
                .replaceAll("\\)x", ")*x");
    }

    private String addMultiplicationSymbols(String expression){
        for(int i = 0 ; i <= 9 ; i++){
            expression = expression
                    .replaceAll(i + "\\(", i + "*(")
                    .replaceAll(i + "sqrt", i + "*sqrt")
                    .replaceAll(i + "sin", i + "*sin")
                    .replaceAll(i + "cos", i + "*cos")
                    .replaceAll(i + "tan", i + "*tan")
                    .replaceAll(i + "ln", i + "*ln")
                    .replaceAll(i + "log2", i + "*log2")
                    .replaceAll(i + "derivative", i + "*derivative")
                    .replaceAll(i + "integral", i + "*integral")
                    .replaceAll(i + "x", i + "*x");
        }
        return expression;
    }
}
