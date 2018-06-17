package ia.module.parser.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;
import ia.module.parser.Operator;

public class AdditionExpressionNode extends SequenceExpressionNode{

    public AdditionExpressionNode() {
        super();
    }

    public AdditionExpressionNode(ExpressionNode a, boolean positive) {
        super(a, positive);
    }

    public int getType() {
        return ADDITION_NODE;
    }

    public double getValue() throws EvaluationException{
        double sum = 0.0;
        for (Term t : terms) {
            if (t.positive){
                sum += t.expression.getValue();
            }
            else{
                sum -= t.expression.getValue();
            }
        }
        return sum;
    }

    public Integer getLevel(){
        return this.getLevelFromBases(0, 3);
    }

    public Integer getToken() {
        if(!this.hasVariable()){
            if(this.allPositives()){
                return Operator.N_PLUS_N;
            }

            return Operator.N_MINUS_N;
        }

        return Operator.PLUS_OR_MINUS_TERM_WITH_X;
    }

    public Boolean isLineal() {
        return this.terms.stream().allMatch(Term::isLineal);
    }

    public ExpressionNode normalize(){
        return this;
    }
}
