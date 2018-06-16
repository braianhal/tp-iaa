package ia.module.parser.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;
import ia.module.parser.ExpressionsWithArgumentStructures;
import ia.module.parser.Operator;

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

    public Boolean isNumber() {
        return base.isNumber() && exponent.isNumber();
    }

    public Boolean isPositiveNumber() {
        return base.isPositiveNumber() || exponent.isEven();
    }

    public Boolean isEven(){
        try{
            return this.getValue() % 2 == 0;
        }catch (Exception e){
            return false;
        }
    }

    public Integer getLevel(){
        Boolean baseHasVariable = base.hasVariable();
        Boolean exponentHasVariable = exponent.hasVariable();
        Integer minLevel = 7;

        // Base and exponent are numbers
        if(!baseHasVariable && !exponentHasVariable){
            minLevel = 2;
        }

        // Base has variable, but exponent is number
        if(baseHasVariable && !exponentHasVariable){
            minLevel = 5;
        }

        return Math.max(minLevel, Math.max(base.getLevel(), exponent.getLevel()));
    }

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        return expressionsWithArgumentStructures;
    }

    public Integer getToken() {
        Boolean baseHasVariable = base.hasVariable();
        Boolean baseIsNumber = base.isNumber();

        Boolean exponentHasVariable = exponent.hasVariable();
        Boolean exponentIsPositiveNumber = exponent.isPositiveNumber();
        Boolean exponentIsMinusOne = exponent.isMinusOne();
        Boolean exponentIsMinusN = exponent.isMinusN();
        Boolean exponentIsFractionalNumber = exponent.isFractionalNumber();

        // 3^4
        if(baseIsNumber && exponentIsPositiveNumber && !exponentIsFractionalNumber){
            return Operator.N_BY_N;
        }

        // 3^(-4)
        if(baseIsNumber && (exponentIsMinusN || exponentIsMinusOne)){
            return Operator.N_RAISED_TO_MINUS_N;
        }

        // 3^(1/3)
        if(baseIsNumber && exponentIsFractionalNumber){
            return Operator.ROOT_OF_N;
        }

        // x^2
        if(baseHasVariable && exponentIsPositiveNumber){
            return Operator.TERM_WITH_X_BY_TERM_WITH_X;
        }

        // x^(-1)
        if(baseHasVariable && exponentIsMinusOne){
            return Operator.TERM_WITH_X_RAISED_TO_MINUS_1;
        }

        // x^(-N)
        if(baseHasVariable && exponentIsMinusN){
            return Operator.TERM_WITH_X_RAISED_TO_MINUS_N;
        }

        // x^(1/2)
        if(baseHasVariable && exponentIsFractionalNumber){
            return Operator.ROOT_OF_TERM_WITH_X;
        }

        // 3^x
        if(exponentHasVariable){
            return Operator.RAISED_TO_TERM_WITH_X;
        }

        return Operator.N;
    }

    public Boolean isLineal() {
        return (this.base.isNumber() && this.exponent.isNumber()) ||
                (this.base.isLineal() && this.exponent.isOne());
    }

    public Boolean isQuadraticX() {
        return this.base.isVariable() && this.exponent.isTwo();
    }

    public Boolean isZero() {
        return this.base.isZero() && this.exponent.isPositiveNumber();
    }
}
