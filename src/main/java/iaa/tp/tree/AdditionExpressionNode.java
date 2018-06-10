package iaa.tp.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;

public class AdditionExpressionNode extends SequenceExpressionNode{

    public AdditionExpressionNode() {
        super();
    }

    public AdditionExpressionNode(ExpressionNode a, boolean positive) {
        super(a, positive);
    }

    public int getType() {
        return ExpressionNode.ADDITION_NODE;
    }

    public double getValue() throws EvaluationException{
        double sum = 0.0;
        for (Term t : terms) {
            if (t.positive){
                sum += t.expression.getValue();
            }
            else{
                sum -= t.expression.getValue();
            }
        }
        return sum;
    }

    public Integer getLevel(){
        return this.getLevelFromBases(0, 3);
    }
}
