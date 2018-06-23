package ia.module.fitness;

import ia.module.parser.Operator;
import ia.module.parser.Parser;
import ia.module.parser.tree.ExpressionNode;
import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.op.Op;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.Kohonen;

import java.util.Arrays;
import java.util.List;

import static ia.module.config.GeneticAlgorithmConfig.CHROMOSOME;
import static ia.module.config.NeuralNetworkConfig.*;

public class NeuralNetworkSimilarExpressionCalculator extends SimilarExpressionCalculator {

    private Kohonen network;
    private double[] originalExpressionOutput;

    public NeuralNetworkSimilarExpressionCalculator(String original) {
        super(original);
        trainNetwork();
        try {
            originalExpressionOutput = calculateOutput(new Parser().parse(original));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double similarityWith(String otherExpression) {
        double[] otherExpressionOutput = new double[0];
        try {
            otherExpressionOutput = calculateOutput(new Parser().parse(otherExpression));
        } catch (Exception e) {
            e.printStackTrace();
        }
        double baseSimilarity = baseSimilarity(originalExpressionOutput, otherExpressionOutput);
        double fineSimilarity = fineSimilarity(originalExpressionOutput, otherExpressionOutput, 1 - baseSimilarity);
        return baseSimilarity + fineSimilarity;
    }

    private void trainNetwork() {
        network = new Kohonen(INPUTS, OUTPUTS);
        network.learn(generateTrainingSet());
    }

    private DataSet generateTrainingSet() {
        DataSet ds = new DataSet(INPUTS);
        for (int example = 0; example < TRAINING_EXAMPLES; example++) {
            TreeNode<Op<Double>> exampleExpression = TreeNode.ofTree(CHROMOSOME.newInstance().getGene());
            double[] features = new double[0];
            try {
                features = extractFeaturesForExpression(new Parser().parse(exampleExpression));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ds.addRow(features);
        }
        return ds;
    }

    private double[] extractFeaturesForExpression(ExpressionNode expression) {
        double[] features = new double[INPUTS];
        List<Operator> operators = this.getListOfTokensOfNormalizedExpression(expression);
        Integer index;
        for(Operator operator : operators){
            index = operator.getOperator() > Operator.BY_TERM_WITH_X ? operator.getOperator() - 1 : operator.getOperator();
            features[index]++;
        }

        return this.normalizeFeatures(features);
    }

    private double[] normalizeFeatures(double[] features){
        return Arrays.stream(features).map(feature -> feature == 0
                ? 0
                : (feature == 1 ? 0.1 : (1 - Math.abs(Math.log(1 - 1/feature)))) / 2).toArray();
    }

    private double[] calculateOutput(ExpressionNode expression) {
        double[] input = extractFeaturesForExpression(expression);
        network.setInput(input);
        network.calculate();
        return network.getOutput();
    }

    private double baseSimilarity(double[] output1, double[] output2) {
        int category1 = category(output1);
        if (category1 == category(output2)) {
            return BASE_SIMILARITY_MAIN_CATEOGORY;
        }
        if (output2[category1] <= SIMILAR_CATEGORY_LIMIT) {
            return BASE_SIMILARITY_SECONDARY_CATEGORY;
        }
        return BASE_SIMILARITY_OTHER_CATEGORY;
    }

    private double fineSimilarity(double[] output1, double[] output2, double maxSimilarity) {
        double difference = 0;
        for (int i = 0; i < OUTPUTS; i++) {
            difference += Math.abs(output1[i] - output2[i]);
        }
        return (1 - (difference / (double) OUTPUTS)) * maxSimilarity;
    }

    private int category(double[] output) {
        for (int i = 0; i < OUTPUTS; i++) {
            if (output[i] == 0.0) {
                return i;
            }
        }
        throw new RuntimeException("Unrecognized category"); // Shouldn't happen
    }

}
