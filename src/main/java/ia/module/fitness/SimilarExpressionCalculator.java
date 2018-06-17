package ia.module.fitness;

public abstract class SimilarExpressionCalculator {

    protected String originalExpression;

    public abstract Double similarityWith(String otherExpression);

    SimilarExpressionCalculator(String expression) {
        this.originalExpression = expression;
    }

}
