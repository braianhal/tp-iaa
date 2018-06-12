package iaa.tp.parser.tree;

import iaa.tp.parser.ExpressionsWithArgumentStructures;
import iaa.tp.parser.Operator;

public class ConstantExpressionNode extends AbstractExpressionNode implements ExpressionNode{

    private Double value;

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

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        return expressionsWithArgumentStructures;
    }

    public Integer getToken() {
        return Operator.N;
    }

    public Boolean isNumber(){
        return true;
    }

    public Boolean isOne(){
        return value == 1;
    }

    public Boolean isN(){
        return value >= 2;
    }

    public Boolean isFractionalNumber(){
        return (value - (value.intValue())) > 0;
    }

    public Boolean isPositiveNumber(){
        return value > 0;
    }

    public Boolean isEven(){
        return value % 2 == 0;
    }
}
