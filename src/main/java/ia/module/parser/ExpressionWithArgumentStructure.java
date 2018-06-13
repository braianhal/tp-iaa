package ia.module.parser;

public class ExpressionWithArgumentStructure {

    private Integer functionToken;
    private Integer functionArgumentToken;
    private Integer countOfOccurrences;

    public ExpressionWithArgumentStructure(Integer functionToken, Integer functionArgumentToken) {
        this.functionToken = functionToken;
        this.functionArgumentToken = functionArgumentToken;
        this.countOfOccurrences = 1;
    }

    public Integer getFunctionToken() {
        return functionToken;
    }

    public void setFunctionToken(Integer functionToken) {
        this.functionToken = functionToken;
    }

    public Integer getFunctionArgumentToken() {
        return functionArgumentToken;
    }

    public void setFunctionArgumentToken(Integer functionArgumentToken) {
        this.functionArgumentToken = functionArgumentToken;
    }

    public Integer getCountOfOccurrences() {
        return countOfOccurrences;
    }

    public void incrementCountOfOcurrencies(){
        this.countOfOccurrences += 1;
    }
}
