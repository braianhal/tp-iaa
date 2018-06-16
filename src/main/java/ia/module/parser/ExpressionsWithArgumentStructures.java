package ia.module.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpressionsWithArgumentStructures {

    private List<ExpressionWithArgumentStructure> expressionsWithArgument;

    public ExpressionsWithArgumentStructures() {
        this.expressionsWithArgument = new ArrayList<>();
    }

    public ExpressionsWithArgumentStructures addExpressionWithArguments(Integer dadToken, Integer childToken){

        List<ExpressionWithArgumentStructure> expressions = this.expressionsWithArgument.stream().filter(expressionWithArgumentStructure -> dadToken.equals(expressionWithArgumentStructure.getDadToken()) && childToken.equals(expressionWithArgumentStructure.getChildToken())).collect(Collectors.toList());

        if(expressions.isEmpty()){
            this.expressionsWithArgument.add(new ExpressionWithArgumentStructure(dadToken, childToken));
        }else{
            expressions.get(0).incrementCountOfOccurrences();
        }

        return this;
    }

    public Long getCountOfStructuresNotIncludedInto(ExpressionsWithArgumentStructures structures){
        return this.expressionsWithArgument.stream().filter(structure -> !structures.has(structure)).count();
    }

    public Long getCountOfStructuresWithCountOfOccurrencesOutOfBoundOf(ExpressionsWithArgumentStructures structures){
        return this.expressionsWithArgument.stream().filter(structure -> structure.hasOccurrencesOutOfBoundsOf(structures)).count();
    }

    public Boolean has(ExpressionWithArgumentStructure expressionWithArgumentStructure){
        return this.expressionsWithArgument.stream().anyMatch(structure ->
                (structure.getDadToken().equals(expressionWithArgumentStructure.getDadToken()) &&
                        structure.getChildToken().equals(expressionWithArgumentStructure.getChildToken())) ||
                        haveEquivalentTokens(structure, expressionWithArgumentStructure));
    }

    public ExpressionWithArgumentStructure find(ExpressionWithArgumentStructure expressionWithArgumentStructure){
        List<ExpressionWithArgumentStructure> structures = this.expressionsWithArgument.stream().filter(structure ->
                (structure.getDadToken().equals(expressionWithArgumentStructure.getDadToken()) &&
                        structure.getChildToken().equals(expressionWithArgumentStructure.getChildToken())) ||
                        haveEquivalentTokens(structure, expressionWithArgumentStructure)).collect(Collectors.toList());

        if(structures.isEmpty()){
            return null;
        }

        return structures.get(0);
    }

    private Boolean haveEquivalentTokens(ExpressionWithArgumentStructure structure1, ExpressionWithArgumentStructure structure2){
        return this.hasEquivalentToken(structure1) && this.hasEquivalentToken(structure2);
    }

    private Boolean hasEquivalentToken(ExpressionWithArgumentStructure structure){
        return Operator.equivalentTokens().contains(structure.getDadToken()) && Operator.equivalentTokens().contains(structure.getChildToken());
    }
}
