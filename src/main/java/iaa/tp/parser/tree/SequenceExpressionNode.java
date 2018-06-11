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

    public Integer maxLevelAgainst(Integer level){
        return terms.stream().map(Term::getLevel).reduce(level, Math::max);
    }

    protected Integer getLevelFromBases(Integer lowerBase, Integer upperBase){
        Boolean existsTermWithVariable = terms.stream().anyMatch(Term::hasVariable);
        Integer base = existsTermWithVariable ? upperBase : lowerBase;
        return this.maxLevelAgainst(base);
    }

    public Boolean isMinusOne(){
        if(this.hasVariable()){
            return false;
        }

        try{
            return this.getValue() == -1;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean isMinusN(){
        if(this.hasVariable()){
            return false;
        }

        try{
            return this.getValue() <= -2;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean isOne(){
        if(this.hasVariable()){
            return false;
        }

        try{
            return this.getValue() == 1;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean isN(){
        if(this.hasVariable()){
            return false;
        }

        try{
            return this.getValue() >= 2;
        }catch (Exception e){
            return false;
        }
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
}
