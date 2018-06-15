package ia.module.parser.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;
import ia.module.parser.ExpressionsWithArgumentStructures;
import ia.module.parser.Operator;

public class MultiplicationExpressionNode extends SequenceExpressionNode{

    public MultiplicationExpressionNode(ExpressionNode a, boolean positive) {
        super(a, positive);
    }

    public int getType() {
        return MULTIPLICATION_NODE;
    }

    public double getValue() throws EvaluationException{
        double prod = 1.0;
        for (Term t : terms) {
            if (t.positive){
                prod *= t.expression.getValue();
            }
            else{
                prod /= t.expression.getValue();
            }
        }
        return prod;
    }

    public Integer getLevel(){
        return this.getLevelFromBases(1, 4);
    }

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        // TODO: fixear
        return null;
    }

    public Integer getToken() {
        if(!this.hasVariable()){
            if(this.allPositives()){
                return Operator.N_BY_N;
            }

            return Operator.N_DIVIDED_N;
        }

        if(this.onlyOneTermIsVariable()){
            return Operator.N_BY_X;
        }

        if(this.onlyOneTermHasVariableAsFactor()){
            return Operator.BY_TERM_WITH_X;
        }

        if(this.onlyOneTermHasVariableAsDividend()){
            return Operator.TERM_WITH_X_DIVIDED_N;
        }

        if(this.towOrMoreTermsWithVariableAsFactors()){
            return Operator.TERM_WITH_X_BY_TERM_WITH_X;
        }

        if(this.anyTermWithVariableAsQuotient()){
            return Operator.DIVIDED_TERM_WITH_X;
        }

        return Operator.N;
    }

    private Boolean onlyOneTermIsVariable(){
        return this.terms.stream().filter(Term::hasVariable).count() == 1 &&
                this.terms.stream().filter(Term::isVariable).count() == 1;
    }

    private Long countOfTermsWithVariableAsFactors(){
        return this.terms.stream().filter(term -> term.hasVariable() && term.positive).count();
    }

    private Boolean onlyOneTermHasVariableAsFactor() {
        return this.countOfTermsWithVariableAsFactors() == 1;
    }

    private Boolean onlyOneTermHasVariableAsDividend(){
        return this.onlyOneTermHasVariableAsFactor() &&
                this.terms.stream().filter(term -> term.isNumber() && !term.positive).count() == 1;
    }

    public Boolean isQuadraticX() {
        return (this.terms.stream().filter(Term::hasVariable).count() == 1 && this.terms.stream().filter(Term::isQuadraticX).count() == 1) ||
                (this.terms.stream().filter(Term::hasVariable).count() == 2 && this.terms.stream().filter(Term::isVariable).count() == 2);
    }

    private Boolean towOrMoreTermsWithVariableAsFactors(){
        return this.countOfTermsWithVariableAsFactors() >= 2;
    }

    private Boolean anyTermWithVariableAsQuotient(){
        return this.terms.stream().filter(term -> term.hasVariable() && !term.positive).count() >= 1;
    }

    public Boolean isLineal() {
        return this.terms.stream().filter(Term::hasVariable).count() <= 1 &&
                this.terms.stream().filter(term -> term.hasVariable() && term.isLineal()).count() == 1;
    }

    public Boolean isZero(){
        return this.terms.stream().anyMatch(Term::isZero);
    }
}
