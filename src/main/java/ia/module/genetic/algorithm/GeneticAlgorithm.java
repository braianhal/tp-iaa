package ia.module.genetic.algorithm;

import ia.module.fitness.NeuralNetworkSimilarExpressionCalculator;
import ia.module.fitness.ProceduralSimilarExpressionCalculator;
import ia.module.fitness.SimilarExpressionCalculator;
import ia.module.parser.Parser;
import io.jenetics.Genotype;
import io.jenetics.Mutator;
import io.jenetics.Phenotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.ext.SingleNodeCrossover;
import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.ProgramGene;

import static ia.module.config.GeneticAlgorithmConfig.*;

public class GeneticAlgorithm {

    // Define the fitness function
    // static final SimilarExpressionCalculator SIMILAR_EXPRESSION_CALCULATOR = new NeuralNetworkSimilarExpressionCalculator(EXPRESSION);
    private static SimilarExpressionCalculator SIMILAR_EXPRESSION_CALCULATOR;

    // Define the structure of solutions (max tree depth, operations and terminals to consider, etc)
    private static final Codec<ProgramGene<Double>, ProgramGene<Double>> CODEC = Codec.of(
            Genotype.of(CHROMOSOME),
            Genotype::getGene
    );

    public GeneticAlgorithm(String candidate){
        SIMILAR_EXPRESSION_CALCULATOR = new ProceduralSimilarExpressionCalculator(candidate);
    }

    public GeneticAlgorithm(String candidate, Boolean useNeuralNetworkFitness){
        SIMILAR_EXPRESSION_CALCULATOR = useNeuralNetworkFitness ? new NeuralNetworkSimilarExpressionCalculator(candidate) : new ProceduralSimilarExpressionCalculator(candidate);
    }

    private static final Double fitnessFunction(final ProgramGene<Double> expression) {
        String otherExpression = new Parser().getAsInfix(TreeNode.ofTree(expression));
        return SIMILAR_EXPRESSION_CALCULATOR.similarityWith(otherExpression);
    }

    public String getExpressionMostSimilar(){
        Engine<ProgramGene<Double>, Double> engine = Engine.builder(GeneticAlgorithm::fitnessFunction, CODEC)
                .alterers(
                        new Mutator<>(MUTATION_PROB),
                        new SingleNodeCrossover<>())
                .populationSize(INITIAL_POPULATION_SIZE)
                .executor(Runnable::run)
                .maximizing()
                .build();

        Phenotype<ProgramGene<Double>, Double> bestExpression = engine.stream()
                .limit(Limits.bySteadyFitness(MIN_ITERATIONS))
                .peek(GeneticAlgorithm::showGeneration)
                .collect(EvolutionResult.toBestPhenotype());

        TreeNode bestCandidate = TreeNode.ofTree(bestExpression.getGenotype().getGene());
        return new Parser().getAsInfix(bestCandidate);
    }

    public static void showGeneration(EvolutionResult<ProgramGene<Double>, Double> generation) {
        TreeNode bestCandidate = TreeNode.ofTree(generation.getBestPhenotype().getGenotype().getGene());
        String candidateAsInfix = new Parser().getAsInfix(bestCandidate);

        System.out.println(
                "Generation: " + generation.getGeneration() + "; " +
                        "Best fitness: " + SIMILAR_EXPRESSION_CALCULATOR.similarityWith(candidateAsInfix) + "; " +
                        "Best genotype: " + candidateAsInfix);
    }

    public SimilarExpressionCalculator getSimilarExpressionCalculator() {
        return SIMILAR_EXPRESSION_CALCULATOR;
    }
}
