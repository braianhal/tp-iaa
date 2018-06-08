package iaa.tp.fitness;

import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.op.Op;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.Kohonen;

import java.util.Random;

import static iaa.tp.config.GeneticAlgorithmConfig.CHROMOSOME;
import static iaa.tp.config.NeuralNetworkConfig.*;

public class NeuralNetworkSimilarExpressionCalculator extends SimilarExpressionCalculator {

    private Kohonen network;
    private double[] originalExpressionOutput;

    public NeuralNetworkSimilarExpressionCalculator(TreeNode<Op<Double>> original) {
        super(original);
        trainNetwork();
        originalExpressionOutput = calculateOutput(original);
    }

    @Override
    public Double similarityWith(TreeNode<Op<Double>> otherExpression) {
        double[] otherExpressionOutput = calculateOutput(otherExpression);
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
            double[] features = extractFeaturesForExpression(exampleExpression);
            ds.addRow(features);
        }
        return ds;
    }

    // TODO calculate actual features
    private double[] extractFeaturesForExpression(TreeNode<Op<Double>> expression) {
        double[] features = new double[INPUTS];
        for (int i = 0; i < INPUTS; i++) {
            features[i] = new Random().nextDouble();
        }
        return features;
    }

    private double[] calculateOutput(TreeNode<Op<Double>> expression) {
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
