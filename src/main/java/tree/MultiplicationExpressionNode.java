package tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;

public class MultiplicationExpressionNode extends SequenceExpressionNode{

    public MultiplicationExpressionNode(ExpressionNode a,
                                        boolean positive) {
        super(a, positive);
    }

    public int getType() {
        return ExpressionNode.MULTIPLICATION_NODE;
    }

    public double getValue() throws EvaluationException{
        double prod = 1.0;
        for (Term t : terms) {
            if (t.positive){
                prod *= t.expression.getValue();
            }
            else{
                prod /= t.expression.getValue();
            }
        }
        return prod;
    }
}
