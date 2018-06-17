package ia.module;

import ia.module.fitness.NeuralNetworkSimilarExpressionCalculator;
import ia.module.fitness.SimilarExpressionCalculator;
import ia.module.parser.Parser;
import io.jenetics.Genotype;
import io.jenetics.Mutator;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.ext.SingleNodeCrossover;
import io.jenetics.ext.util.Tree;
import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.ProgramGene;

import static ia.module.config.GeneticAlgorithmConfig.*;

public class Main {

    static final String EXPRESSION = "1+2";

    // Define the fitness function
    static final SimilarExpressionCalculator SIMILAR_EXPRESSION_CALCULATOR = new NeuralNetworkSimilarExpressionCalculator(EXPRESSION);
    //static final SimilarExpressionCalculator SIMILAR_EXPRESSION_CALCULATOR = new ProceduralSimilarExpressionCalculator(EXPRESSION);

    // Define the structure of solutions (max iaa.tp.parser.tree depth, operations and terminals to consider, etc)
    static final Codec<ProgramGene<Double>, ProgramGene<Double>> CODEC = Codec.of(
            Genotype.of(CHROMOSOME),
            Genotype::getGene
    );

    static final Double fitnessFunction(final ProgramGene<Double> expression) {
        String otherExpression = new Parser().getAsInfix(TreeNode.ofTree(expression));
        return SIMILAR_EXPRESSION_CALCULATOR.similarityWith(otherExpression);
    }

    public static void main(final String[] args) {
        Engine<ProgramGene<Double>, Double> engine = Engine.builder(Main::fitnessFunction, CODEC)
                .alterers(
                        new Mutator<>(),
                        new SingleNodeCrossover<>())
                .populationSize(INITIAL_POPULATION_SIZE)
                .build();

        ProgramGene<Double> bestExpression = engine.stream()
                .limit(MAX_ITERATIONS)
                .collect(EvolutionResult.toBestGenotype())
                .getGene();

        System.out.println(Tree.toString(bestExpression));
    }



}
