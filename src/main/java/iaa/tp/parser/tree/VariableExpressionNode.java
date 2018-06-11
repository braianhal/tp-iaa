package iaa.tp.parser.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;
import iaa.tp.parser.ExpressionsWithArgumentStructures;

public class VariableExpressionNode implements ExpressionNode{

    private String name;
    private double value;
    private boolean valueSet;

    public VariableExpressionNode(String name) {
        this.name = name;
        valueSet = false;
    }

    public int getType() {
        return ExpressionNode.VARIABLE_NODE;
    }

    public void setValue(double value) {
        this.value = value;
        this.valueSet = true;
    }

    public double getValue() throws EvaluationException{
        if (valueSet){
            return value;
        }

        throw new EvaluationException("Variable '" + name + "' was not initialized.");
    }

    public Integer getLevel(){
        return 3;
    }

    public Boolean hasVariable(){
        return true;
    }

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        // TODO: fixear
        return null;
    }
}
