package iaa.tp.parser;

import java.util.ArrayList;
import java.util.List;

public class ExpressionsWithArgumentStructures {

    private List<ExpressionWithArgumentStructure> expressionsWithArgument;

    public ExpressionsWithArgumentStructures() {
        this.expressionsWithArgument = new ArrayList<>();
    }

    public void addExpressionWithArgument(ExpressionWithArgumentStructure expressionWithArgumentStructure){
        this.expressionsWithArgument.add(expressionWithArgumentStructure);
    }
}
