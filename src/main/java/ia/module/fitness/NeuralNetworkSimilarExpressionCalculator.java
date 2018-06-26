package ia.module.fitness;

import ia.module.extension.FixedKohonen;
import ia.module.parser.Operator;
import ia.module.parser.Parser;
import ia.module.parser.tree.ExpressionNode;
import org.neuroph.core.data.DataSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static ia.module.config.NeuralNetworkConfig.INPUTS;
import static ia.module.config.NeuralNetworkConfig.OUTPUTS;

public class NeuralNetworkSimilarExpressionCalculator extends SimilarExpressionCalculator {

    private FixedKohonen network;
    public double[] originalExpressionInput;
    public double[] originalExpressionOutput;

    public NeuralNetworkSimilarExpressionCalculator(String original) {
        super(original);
        trainNetwork();
        try {
            ExpressionNode expressionNode = new Parser().parse(original);
            originalExpressionInput = expressionNode.extractFeaturesForExpression();
            originalExpressionOutput = calculateOutput(expressionNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double similarityWith(String otherExpression) {
        double[] otherExpressionInput = new double[INPUTS];
        double[] otherExpressionOutput = new double[OUTPUTS];
        try {
            ExpressionNode expressionNode = new Parser().parse(otherExpression);
            otherExpressionInput = expressionNode.extractFeaturesForExpression();
            otherExpressionOutput = calculateOutput(expressionNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return similarity(otherExpressionInput, otherExpressionOutput);
    }

    private double similarity(double[] candidateInput, double[] candidateOutput) {
        Integer diff = 0;
        for(int i = 0 ; i < OUTPUTS ; i++){
            diff += (int)Math.abs(candidateOutput[i] - originalExpressionOutput[i]);
        }

        if(diff == 0){
            return this.fineAdjustment(candidateInput);
        }

        return 1 - (diff / OUTPUTS);
    }

    private double fineAdjustment(double[] candidateInput){
        Double originalExpressionInputCategory = this.category(originalExpressionInput);
        Double candidateExpressionInputCategory = this.category(candidateInput);
        Double ratio = 1 - Math.abs(originalExpressionInputCategory - candidateExpressionInputCategory) / (originalExpressionInputCategory + candidateExpressionInputCategory);
        if(candidateExpressionInputCategory > originalExpressionInputCategory){
            return 1 - ratio;
        }
        return ratio;
    }

    private void trainNetwork() {
        network = new FixedKohonen(INPUTS, OUTPUTS);
        trainWithExamples();
    }

    private void trainWithExamples(){
        try {
            DataSet ds = new DataSet(INPUTS);
            File file = new File("resources/training/patterns.text");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ExpressionNode expressionNode = new Parser().parse(line);
                ds.addRow(expressionNode.extractFeaturesForExpression());
            }
            network.learn(ds);
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private double[] calculateOutput(ExpressionNode expression) {
        double[] input = expression.extractFeaturesForExpression();
        network.setInput(input);
        network.calculate();
        double[] output = network.getOutput();
        DataSet ds = new DataSet(INPUTS);
        ds.addRow(input);
        network.learn(ds);
        return output;
    }

    private void print(double[] vector, Integer size){
        String line = "(";
        for(int i = 0 ; i < size ; i++){
            line += vector[i];
            if(i + 1 == size){
                line += ")";
            }else
                line += ",";
        }
        System.out.println(line);
    }

    private Double category(double[] input) {
        Double category = 0.0;
        for (int i = 0; i < INPUTS; i++) {
            category += input[i] != 0.0 ? Operator.getFibonacciWeight(i) : 0.0;
        }
        return category;
    }
}
