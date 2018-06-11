package iaa.tp.parser.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;
import iaa.tp.parser.ExpressionsWithArgumentStructures;
import iaa.tp.parser.Operator;

public class ExponentiationExpressionNode extends AbstractExpressionNode implements ExpressionNode{

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

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        return expressionsWithArgumentStructures;
    }

    public Integer getToken() {
        Boolean baseHasVariable = base.hasVariable();
        Boolean baseIsNumber = base.isNumber();
        Boolean baseIsVariable = base.isVariable();

        Boolean exponentIsVariable = exponent.isVariable();
        Boolean exponentHasVariable = exponent.hasVariable();
        Boolean exponentIsNumber = exponent.isNumber();
        Boolean exponentIsMinusOne = exponent.isMinusOne();
        Boolean exponentIsMinusN = exponent.isMinusN();
        Boolean exponentIsFractionalNumber = exponent.isFractionalNumber();

        // 3^4
        if(baseIsNumber && exponentIsNumber){
            return Operator.N_BY_N;
        }

        // 3^(-4)
        if(baseIsNumber && exponentIsMinusN){
            return Operator.N_RAISED_TO_MINUS_N;
        }

        if(baseIsNumber && exponentIsFractionalNumber){
            // TODO: pending
        }

        //TODO: pending

        return null;
    }

}
