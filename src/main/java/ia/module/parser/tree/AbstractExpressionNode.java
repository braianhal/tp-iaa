package ia.module.parser.tree;

import ia.module.parser.ExpressionsWithArgumentStructures;
import ia.module.parser.Operator;

import java.util.Arrays;
import java.util.List;

import static ia.module.config.NeuralNetworkConfig.INPUTS;

public abstract class AbstractExpressionNode {

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

    public abstract ExpressionNode normalize();

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        return expressionsWithArgumentStructures;
    }

    public Integer getDegree(){
        return 0;
    }

    public Boolean contains(Integer operator){
        return false;
    }

    public abstract List<Operator> getListOfTokens();

    public double[] extractFeaturesForExpression(){
        double[] features = new double[INPUTS];
        Integer index;
        for(Operator operator : this.getListOfTokens()){
            index = operator.getOperator() > Operator.BY_TERM_WITH_X ? operator.getOperator() - 1 : operator.getOperator();
            features[index]++;
        }

        return this.normalizeFeatures(features);
    }

    private double[] normalizeFeatures(double[] features){
        return Arrays.stream(features).map(feature -> feature == 0
                ? 0
                : (feature == 1 ? 0.1 : (1 - Math.abs(Math.log(1 - 1/feature)))) / 2).toArray();
    }
}
