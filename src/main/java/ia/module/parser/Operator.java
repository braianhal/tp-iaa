package ia.module.parser;

import java.util.ArrayList;
import java.util.List;

public class Operator {
    public static final int N = 0;
    public static final int N_PLUS_N = 1;
    public static final int N_MINUS_N = 2;
    public static final int N_BY_N = 3;
    public static final int N_BY_X = 4;
    public static final int N_DIVIDED_N = 5;
    public static final int N_RAISED_TO_MINUS_N = 6;
    public static final int ROOT_OF_N = 7;
    public static final int X = 8;
    public static final int PLUS_OR_MINUS_TERM_WITH_X = 9;
    public static final int BY_TERM_WITH_X = 10;
    public static final int TERM_WITH_X_DIVIDED_N = 11;
    public static final int TERM_WITH_X_BY_TERM_WITH_X = 12;
    public static final int DIVIDED_TERM_WITH_X = 13;
    public static final int TERM_WITH_X_RAISED_TO_MINUS_1 = 14;
    public static final int TERM_WITH_X_RAISED_TO_MINUS_N = 15;
    public static final int ROOT_OF_TERM_WITH_X = 16;
    public static final int TRIGONOMETRIC = 17;
    public static final int RAISED_TO_TERM_WITH_X = 18;
    public static final int LOGARITHM = 19;
    public static final int DERIVATIVE = 20;
    public static final int INTEGRAL = 21;

    private Integer operator;
    private Integer degree;

    public Integer getOperator() {
        return operator;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public static final List<Integer> equivalentTokens(){
        List<Integer> tokens = new ArrayList<>();
        tokens.add(BY_TERM_WITH_X);
        tokens.add(TERM_WITH_X_DIVIDED_N);
        return tokens;
    }

    public Operator(Integer operator, Integer degree){
        this.operator = operator;
        this.degree = degree;
    }

    public static Operator newToken(Integer operator, Integer degree){
        return new Operator(operator, degree);
    }

    @Override
    public String toString() {
        return "Token: " + this.tokenName() + " - Grado: " + this.degree;
    }

    public boolean equals(Operator operator1) {
        return this.operator.equals(operator1.getOperator());
    }

    public String tokenName(){
        switch (this.operator){
            case 0:
                return "N";
            case 1:
                return "N_PLUS_N";
            case 2:
                return "N_MINUS_N";
            case 3:
                return "N_BY_N";
            case 4:
                return "N_BY_X";
            case 5:
                return "N_DIVIDED_N";
            case 6:
                return "N_RAISED_TO_MINUS_N";
            case 7:
                return "ROOT_OF_N";
            case 8:
                return "X";
            case 9:
                return "PLUS_OR_MINUS_TERM_WITH_X";
            case 10:
                return "BY_TERM_WITH_X";
            case 11:
                return "TERM_WITH_X_DIVIDED_N";
            case 12:
                return "TERM_WITH_X_BY_TERM_WITH_X";
            case 13:
                return "DIVIDED_TERM_WITH_X";
            case 14:
                return "TERM_WITH_X_RAISED_TO_MINUS_1";
            case 15:
                return "TERM_WITH_X_RAISED_TO_MINUS_N";
            case 16:
                return "ROOT_OF_TERM_WITH_X";
            case 17:
                return "TRIGONOMETRIC";
            case 18:
                return "RAISED_TO_TERM_WITH_X";
            case 19:
                return "LOGARITHM";
            case 20:
                return "DERIVATIVE";
            default:
                return "INTEGRAL";
        }
    }
}
