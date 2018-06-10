package iaa.tp.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;

public class ExponentiationExpressionNode implements ExpressionNode{

    private ExpressionNode base;
    private ExpressionNode exponent;

    public ExponentiationExpressionNode(ExpressionNode base, ExpressionNode exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    public int getType() {
        return ExpressionNode.EXPONENTIATION_NODE;
    }

    public double getValue() throws EvaluationException{
        return Math.pow(base.getValue(), exponent.getValue());
    }

    public Boolean hasVariable() {
        return base.hasVariable() || exponent.hasVariable();
    }

    public Integer getLevel(){
        Boolean baseHasVariable = base.hasVariable();
        Boolean exponentHasVariable = exponent.hasVariable();

        // Base and exponent are numbers
        if(!baseHasVariable && !exponentHasVariable){
            return 2;
        }

        // Base has variable, but exponent is number
        if(baseHasVariable && !exponentHasVariable){
            return 5;
        }

        return 7;
    }
}
