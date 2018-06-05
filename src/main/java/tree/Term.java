package tree;

public class Term {

    public boolean positive;
    public ExpressionNode expression;

    public Term(boolean positive, ExpressionNode expression) {
        super();
        this.positive = positive;
        this.expression = expression;
    }
}
