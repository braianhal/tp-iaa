package ia.module;

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

import java.time.Duration;

import static ia.module.config.GeneticAlgorithmConfig.*;

public class Main {

    static final String EXPRESSION = "4+sqrt(25)/(3*2-1)";

    // Define the fitness function
    // static final SimilarExpressionCalculator SIMILAR_EXPRESSION_CALCULATOR = new NeuralNetworkSimilarExpressionCalculator(EXPRESSION);
    static final SimilarExpressionCalculator SIMILAR_EXPRESSION_CALCULATOR = new ProceduralSimilarExpressionCalculator(EXPRESSION);

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
                        new Mutator<>(MUTATION_PROB),
                        new SingleNodeCrossover<>())
                .populationSize(INITIAL_POPULATION_SIZE)
                .maximizing()
                .build();

        Phenotype<ProgramGene<Double>, Double> bestExpression = engine.stream()
                .limit(Limits.byFitnessThreshold(EXPECTED_FITNESS))
                .limit(Limits.byExecutionTime(Duration.ofSeconds(30)))
                .limit(MAX_ITERATIONS)
                .peek(Main::showGeneration)
                .collect(EvolutionResult.toBestPhenotype());

        System.out.println(new Parser().getAsInfix(TreeNode.ofTree(bestExpression.getGenotype().getGene())));
        System.out.println(bestExpression.getFitness());
    }

    public static void showGeneration(EvolutionResult<ProgramGene<Double>, Double> generation) {
        System.out.println(
                "Generation: " + generation.getGeneration() + "; " +
                "Best fitness: " + generation.getBestFitness() + "; " +
                "Best genotype: " + new Parser().getAsInfix(TreeNode.ofTree(generation.getBestPhenotype().getGenotype().getGene())));
    }

}
