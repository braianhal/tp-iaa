package ia.module.parser;

public class ExpressionWithArgumentStructure {

    private Integer dadToken;
    private Integer childToken;
    private Integer countOfOccurrences;

    public ExpressionWithArgumentStructure(Integer dadToken, Integer childToken) {
        this.dadToken = dadToken;
        this.childToken = childToken;
        this.countOfOccurrences = 1;
    }

    public Integer getDadToken() {
        return dadToken;
    }

    public Integer getChildToken() {
        return childToken;
    }

    public Integer getCountOfOccurrences() {
        return countOfOccurrences;
    }

    public void incrementCountOfOccurrences(){
        this.countOfOccurrences += 1;
    }

    public Boolean hasOccurrencesOutOfBoundsOf(ExpressionsWithArgumentStructures structures){
        ExpressionWithArgumentStructure expressionWithArgumentStructureFound = structures.find(this);

        if(expressionWithArgumentStructureFound == null){
            return true;
        }

        return this.countOfOccurrences > expressionWithArgumentStructureFound.getCountOfOccurrences();
    }

    public Integer getDiffOfOccurrences(ExpressionsWithArgumentStructures structures){
        ExpressionWithArgumentStructure expressionWithArgumentStructureFound = structures.find(this);

        if(expressionWithArgumentStructureFound == null){
            return this.countOfOccurrences;
        }

        return Math.abs(this.countOfOccurrences - expressionWithArgumentStructureFound.getCountOfOccurrences());
    }
}
