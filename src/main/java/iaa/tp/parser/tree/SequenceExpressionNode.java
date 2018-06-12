package iaa.tp.parser.tree;

import java.util.LinkedList;

public abstract class SequenceExpressionNode extends AbstractExpressionNode implements ExpressionNode{

    protected LinkedList<Term> terms;

    public SequenceExpressionNode() {
        this.terms = new LinkedList<Term>();
    }

    public SequenceExpressionNode(ExpressionNode a, boolean positive) {
        this.terms = new LinkedList<Term>();
        this.terms.add(new Term(positive, a));
    }

    public void add(ExpressionNode a, boolean positive) {
        this.terms.add(new Term(positive, a));
    }

    public Boolean hasVariable() {
        return terms.stream().anyMatch(Term::hasVariable);
    }

    public Boolean isNumber() {
        return this.terms.stream().allMatch(Term::isNumber);
    }

    public Boolean isPositiveNumber() {
        try{
            return this.isNumber() && this.getValue() >= 0;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean isEven(){
        try{
            return this.isNumber() && this.getValue() % 2 == 0;
        }catch (Exception e){
            return false;
        }
    }

    public Integer maxLevelAgainst(Integer level){
        return terms.stream().map(Term::getLevel).reduce(level, Math::max);
    }

    protected Integer getLevelFromBases(Integer lowerBase, Integer upperBase){
        Boolean existsTermWithVariable = terms.stream().anyMatch(Term::hasVariable);
        Integer base = existsTermWithVariable ? upperBase : lowerBase;
        return this.maxLevelAgainst(base);
    }

    private Boolean is(Integer number){
        if(this.hasVariable()){
            return false;
        }

        try{
            return this.getValue() == number;
        }catch (Exception e){
            return false;
        }
    }

    private Boolean isGreaterThan(Integer number, Boolean positive){
        if(this.hasVariable()){
            return false;
        }

        try{
            Double value = this.getValue();
            return Math.abs(value) >= number && (positive ? value >= 0 : value < 0);
        }catch (Exception e){
            return false;
        }
    }

    public Boolean isMinusOne(){
        return this.is(-1);
    }

    public Boolean isMinusN(){
        return this.isGreaterThan(2, false);
    }

    public Boolean isOne(){
        return this.is(1);
    }

    public Boolean isN(){
        return this.isGreaterThan(2, true);
    }

    public Boolean isFractionalNumber(){
        if(this.hasVariable()){
            return false;
        }

        try{
            Double value = this.getValue();
            return Math.abs((value - value.intValue())) > 0;
        }catch (Exception e){
            return false;
        }
    }

    protected Boolean allPositives(){
        return this.terms.stream().allMatch(term -> term.positive);
    }
}
