package ia.module.parser.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;
import ia.module.parser.ExpressionsWithArgumentStructures;

public interface ExpressionNode {

    static final int VARIABLE_NODE = 1;
    static final int CONSTANT_NODE = 2;
    static final int ADDITION_NODE = 3;
    static final int MULTIPLICATION_NODE = 4;
    static final int EXPONENTIATION_NODE = 5;
    static final int FUNCTION_NODE = 6;
    int getType();
    double getValue() throws EvaluationException;
    Integer getLevel();
    Boolean hasVariable();
    ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures);
    Integer getToken();
    Boolean isNumber();
    Boolean isLineal();
    Boolean isQuadraticX();
    Boolean isZero();
    Boolean isVariable();
    Boolean isMinusOne();
    Boolean isMinusN();
    Boolean isOne();
    Boolean isTwo();
    Boolean isFractionalNumber();
    Boolean isPositiveNumber();
    Boolean isEven();
}
