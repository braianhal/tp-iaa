package ia.module.parser.tree;

import ia.module.parser.ExpressionsWithArgumentStructures;

public class AbstractExpressionNode {

    public Boolean isNumber(){
        return false;
    }

    public Boolean isVariable(){
        return false;
    }

    public Boolean hasVariable(){
        return false;
    }

    public Boolean isMinusOne(){
        return false;
    }

    public Boolean isMinusN(){
        return false;
    }

    public Boolean isOne(){
        return false;
    }

    public Boolean isTwo(){
        return false;
    }

    public Boolean isN(){
        return false;
    }

    public Boolean isFractionalNumber(){
        return false;
    }

    public Boolean isPositiveNumber(){
        return false;
    }

    public Boolean isEven(){
        return false;
    }

    public Boolean isZero(){
        return false;
    }

    public Boolean isQuadraticX(){
        return false;
    }

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        return expressionsWithArgumentStructures;
    }

    public Integer getDegree(){
        return 0;
    }

    public Boolean contains(Integer operator){
        return false;
    }
}
