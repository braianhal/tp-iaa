package tree;

import java.util.LinkedList;

public abstract class SequenceExpressionNode implements ExpressionNode{

    protected LinkedList<Term> terms;

    public SequenceExpressionNode() {
        this.terms = new LinkedList<Term>();
    }

    public SequenceExpressionNode(ExpressionNode a, boolean positive) {
        this.terms = new LinkedList<Term>();
        this.terms.add(new Term(positive, a));
    }

    public void add(ExpressionNode a, boolean positive) {
        this.terms.add(new Term(positive, a));
    }

}
