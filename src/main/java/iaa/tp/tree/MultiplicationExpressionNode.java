package iaa.tp.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;

public class MultiplicationExpressionNode extends SequenceExpressionNode{

    public MultiplicationExpressionNode(ExpressionNode a, boolean positive) {
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

    public Integer getLevel(){
        // TODO: falta aplicar regla del paso 2 del algoritmo del doc
        return 0;
    }
}
