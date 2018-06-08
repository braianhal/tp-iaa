package iaa.tp.parser;

import iaa.tp.tree.*;
import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.op.Op;
import sun.tools.jstat.ParserException;

import java.text.ParseException;
import java.util.LinkedList;

public class Parser {

    LinkedList<Token> tokens;
    Token lookahead;

    public ExpressionNode parse(TreeNode<Op<Double>> expression) throws ParserException, ParseException{
        return this.parse(this.getAsInfix(expression));
    }

    private String getAsInfix(TreeNode<Op<Double>> expression){
        try{
            return Double.valueOf(expression.toString()).toString();
        }catch (Exception e){
            String currentExpression = expression.getValue().name();
            switch (currentExpression){
                case "ADD":
                    return this.sumOrMinusAsInfix(expression, "+");
                case "SUB":
                    return this.sumOrMinusAsInfix(expression, "-");
                case "MUL":
                    return this.mulOrDivOperation(expression, "*");
                case "DIV":
                    return this.mulOrDivOperation(expression, "/");
                case "POW":
                    return this.powOperation(expression);
                case "SIN":
                    return this.trigonometricOperation(expression, "sin");
                case "COS":
                    return this.trigonometricOperation(expression, "cos");
                case "TAN":
                    return this.trigonometricOperation(expression, "tan");
                default:
                    return currentExpression;
            }
        }
    }

    private String sumOrMinusAsInfix(TreeNode<Op<Double>> expression, String operator){
        String sum = "";
        Integer childCount = expression.childCount();
        for(int i = 0; i < childCount ; i++){
            sum += this.getAsInfix(expression.getChild(i)) + operator;
        }
        return sum.substring(0, sum.length() - 1);
    }

    private String mulOrDivOperation(TreeNode<Op<Double>> expression, String operator){
        String result = "";
        Integer childCount = expression.childCount();
        for(int i = 0; i < childCount ; i++){
            result += "(" + this.getAsInfix(expression.getChild(i)) + ")" + operator;
        }
        return result.substring(0, result.length() - 1);
    }

    private String powOperation(TreeNode<Op<Double>> expression){
        String result = "";
        Integer childCount = expression.childCount();
        for(int i = 0; i < childCount ; i++){
            result += "(" + this.getAsInfix(expression.getChild(i)) + ")" + "^";
        }
        return result.substring(0, result.length() - 1);
    }

    private String trigonometricOperation(TreeNode<Op<Double>> expression, String operator){
        String result = "";
        Integer childCount = expression.childCount();
        for(int i = 0; i < childCount ; i++){
            result += operator + "(" + this.getAsInfix(expression.getChild(i)) + ")";
        }
        return result;
    }

    public ExpressionNode parse(String expression) throws ParserException, ParseException{
        Tokenizer tokenizer = Tokenizer.getExpressionTokenizer();
        tokenizer.tokenize(expression);
        LinkedList<Token> tokens = tokenizer.getTokens();
        return this.parse(tokens);
    }

    public ExpressionNode parse(LinkedList<Token> tokens) throws ParseException{
        this.tokens = (LinkedList<Token>)tokens.clone();
        lookahead = this.tokens.getFirst();

        ExpressionNode expr = expression();

        if (lookahead.token != Token.EPSILON){
            throw new ParseException("Unexpected symbol " + lookahead + " found", 0);
        }

        return expr;
    }

    private void nextToken() {
        tokens.pop();
        // at the end of input we return an epsilon token
        if (tokens.isEmpty()){
            lookahead = new Token(Token.EPSILON, "", -1);
        }
        else{
            lookahead = tokens.getFirst();
        }
    }

    private ExpressionNode expression() throws ParseException{
        // expression -> signed_term sum_op
        ExpressionNode expr = signedTerm();
        return sumOp(expr);
    }

    private ExpressionNode sumOp(ExpressionNode expr) throws ParseException{
        // sum_op -> PLUSMINUS term sum_op
        if (lookahead.token == Token.PLUSMINUS) {
            AdditionExpressionNode sum;
            if (expr.getType() == ExpressionNode.ADDITION_NODE){
                sum = (AdditionExpressionNode)expr;
            } else{
                sum = new AdditionExpressionNode(expr, true);
            }

            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode t = term();
            sum.add(t, positive);

            return sumOp(sum);
        }

        // sum_op -> EPSILON
        return expr;
    }

    private ExpressionNode signedTerm() throws ParseException{
        // signed_term -> PLUSMINUS term
        if (lookahead.token == Token.PLUSMINUS) {
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode t = term();
            if (positive){
                return t;
            } else{
                return new AdditionExpressionNode(t, false);
            }
        }

        // signed_term -> term
        return term();
    }

    private ExpressionNode term() throws ParseException{
        // term -> factor term_op
        ExpressionNode f = factor();
        return termOp(f);
    }

    private ExpressionNode termOp(ExpressionNode expression) throws ParseException{
        // term_op -> MULTDIV factor term_op
        if (lookahead.token == Token.MULTDIV) {
            MultiplicationExpressionNode prod;

            if (expression.getType() == ExpressionNode.MULTIPLICATION_NODE){
                prod = (MultiplicationExpressionNode)expression;
            }
            else{
                prod = new MultiplicationExpressionNode(expression, true);
            }

            boolean positive = lookahead.sequence.equals("*");
            nextToken();
            ExpressionNode f = signedFactor();
            prod.add(f, positive);

            return termOp(prod);
        }

        // term_op -> EPSILON
        return expression;
    }

    private ExpressionNode signedFactor() throws ParseException{
        // signed_factor -> PLUSMINUS factor
        if (lookahead.token == Token.PLUSMINUS) {
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode t = factor();
            if (positive){
                return t;
            }

            return new AdditionExpressionNode(t, false);
        }

        // signed_factor -> factor
        return factor();
    }

    private ExpressionNode factor() throws ParseException{
        // factor -> argument factor_op
        ExpressionNode a = argument();
        return factorOp(a);
    }

    private ExpressionNode factorOp(ExpressionNode expression) throws ParseException{
        // factor_op -> RAISED factor
        if (lookahead.token == Token.RAISED) {
            nextToken();
            ExpressionNode exponent = signedFactor();

            return new ExponentiationExpressionNode(expression, exponent);
        }

        // factor_op -> EPSILON
        return expression;
    }

    private ExpressionNode argument() throws ParseException{
        // argument -> FUNCTION argument
        if (lookahead.token == Token.FUNCTION) {
            int function = FunctionExpressionNode.stringToFunction(lookahead.sequence);
            nextToken();
            ExpressionNode expr = argument();
            return new FunctionExpressionNode(function, expr);
        } else if (lookahead.token == Token.OPEN_BRACKET){ // argument -> OPEN_BRACKET sum CLOSE_BRACKET
            nextToken();
            ExpressionNode expr = expression();
            if (lookahead.token != Token.CLOSE_BRACKET){
                throw new ParseException("Closing brackets expected: " + lookahead, 0);
            }
            nextToken();
            return expr;
        }

        // argument -> value
        return value();
    }

    private ExpressionNode value() throws ParseException{
        // argument -> NUMBER
        if (lookahead.token == Token.NUMBER) {
            ExpressionNode expr = new ConstantExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }

        // argument -> VARIABLE
        if (lookahead.token == Token.VARIABLE) {
            ExpressionNode expr = new VariableExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }

        if (lookahead.token == Token.EPSILON){
            throw new ParseException("Unexpected end of input", 0);
        } else{
            throw new ParseException("Unexpected symbol " + lookahead + " found", 0);
        }
    }
}