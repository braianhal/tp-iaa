package iaa.tp.parser.tree;

import java.util.LinkedList;

public abstract class SequenceExpressionNode implements ExpressionNode{

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
}
