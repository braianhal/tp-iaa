package iaa.tp.parser.tree;

import iaa.tp.parser.ExpressionsWithArgumentStructures;

public class ConstantExpressionNode implements ExpressionNode{

    private double value;

    public ConstantExpressionNode(double value) {
        this.value = value;
    }

    public ConstantExpressionNode(String value) {
        this.value = Double.valueOf(value);
    }

    public double getValue() {
        return value;
    }

    public int getType() {
        return ExpressionNode.CONSTANT_NODE;
    }

    public Integer getLevel(){
        return 0;
    }

    public Boolean hasVariable() {
        return false;
    }

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        // TODO: fixear
        return null;
    }
}
