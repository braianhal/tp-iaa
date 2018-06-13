package ia.module.parser;

import java.util.ArrayList;
import java.util.List;

public class ExpressionsWithArgumentStructures {

    private List<ExpressionWithArgumentStructure> expressionsWithArgument;

    public ExpressionsWithArgumentStructures() {
        this.expressionsWithArgument = new ArrayList<>();
    }

    public ExpressionsWithArgumentStructures addExpressionWithArgument(ExpressionWithArgumentStructure expressionWithArgumentStructure){
        this.expressionsWithArgument.add(expressionWithArgumentStructure);
        return this;
    }
}
