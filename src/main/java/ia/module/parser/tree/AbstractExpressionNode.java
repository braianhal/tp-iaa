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
        for(Operator operator : this.normalize().getListOfTokens()){
            index = operator.getOperator() > Operator.BY_TERM_WITH_X ? operator.getOperator() - 1 : operator.getOperator();
            features = this.incrementFeaturesUntil(features, index);
        }

        return this.normalizeFeatures(features);
    }

    private double[] incrementFeaturesUntil(double[] features, Integer index){
        for(int i = 0 ; i <= index ; i++){
            features[i] += 1;
        }
        return features;
    }

    private double[] normalizeFeatures(double[] features){
        Long maxOrder = this.getMaxOrder(features);
        return Arrays.stream(features).map(feature -> feature / maxOrder).toArray();
    }

    private Long getMaxOrder(double[] features){
        Double max = Arrays.stream(features).reduce(0, Math::max);
        Long quotient = 1L;
        while(max / quotient >= 1){
            quotient *= 10;
        }
        return quotient;
    }
}
