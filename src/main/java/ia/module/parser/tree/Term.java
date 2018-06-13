package ia.module.parser.tree;

public class Term {

    public boolean positive;
    public ExpressionNode expression;

    public Term(boolean positive, ExpressionNode expression) {
        super();
        this.positive = positive;
        this.expression = expression;
    }

    public Boolean hasVariable(){
        return this.expression.hasVariable();
    }

    public Boolean isVariable(){
        return this.expression.isVariable();
    }

    public Boolean isNumber(){
        return this.expression.isNumber();
    }

    public Integer getLevel(){
        return expression.getLevel();
    }

    public Boolean isMinusOne(){
        return !positive && expression.isOne();
    }

    public Boolean isMinusN(){
        return !positive && expression.isN();
    }
}
