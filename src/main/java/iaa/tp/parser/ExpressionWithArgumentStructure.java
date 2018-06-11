package iaa.tp.parser;

public class ExpressionWithArgumentStructure {

    private String functionName;
    private String functionArgumentToken;
    private Integer countOfOccurrences;

    public ExpressionWithArgumentStructure(String functionName, String functionArgumentToken) {
        this.functionName = functionName;
        this.functionArgumentToken = functionArgumentToken;
        this.countOfOccurrences = 1;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionArgumentToken() {
        return functionArgumentToken;
    }

    public void setFunctionArgumentToken(String functionArgumentToken) {
        this.functionArgumentToken = functionArgumentToken;
    }

    public Integer getCountOfOccurrences() {
        return countOfOccurrences;
    }

    public void incrementCountOfOcurrencies(){
        this.countOfOccurrences += 1;
    }
}
