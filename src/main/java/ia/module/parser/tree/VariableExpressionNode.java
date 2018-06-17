package ia.module.parser.tree;

import com.sun.tools.corba.se.idl.constExpr.EvaluationException;
import ia.module.parser.ExpressionsWithArgumentStructures;
import ia.module.parser.Operator;

import java.util.ArrayList;
import java.util.List;

public class VariableExpressionNode extends AbstractExpressionNode implements ExpressionNode{

    private String name;
    private double value;
    private boolean valueSet;

    public VariableExpressionNode(String name) {
        this.name = name;
        valueSet = false;
    }

    public int getType() {
        return ExpressionNode.VARIABLE_NODE;
    }

    public void setValue(double value) {
        this.value = value;
        this.valueSet = true;
    }

    public double getValue() throws EvaluationException{
        if (valueSet){
            return value;
        }

        throw new EvaluationException("Variable '" + name + "' was not initialized.");
    }

    public Integer getLevel(){
        return 3;
    }

    public Boolean hasVariable(){
        return true;
    }

    public ExpressionsWithArgumentStructures getStructureOf(ExpressionsWithArgumentStructures expressionsWithArgumentStructures){
        return expressionsWithArgumentStructures;
    }

    public Integer getToken() {
        return Operator.X;
    }

    public Boolean isVariable(){
        return true;
    }

    public Boolean isLineal() {
        return true;
    }

    public ExpressionNode normalize(){
        return this;
    }

    public Integer getDegree(){
        return 1;
    }

    public List<Operator> getListOfTokens(){
        List<Operator> tokens = new ArrayList<>();
        tokens.add(Operator.newToken(this.getToken(), this.getDegree()));
        return tokens;
    }
}
