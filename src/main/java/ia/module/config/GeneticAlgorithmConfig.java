package ia.module.config;

import io.jenetics.prog.ProgramChromosome;

import static ia.module.config.ExpressionsConfig.*;

public class GeneticAlgorithmConfig {

    public static final int INITIAL_POPULATION_SIZE = 50;

    public static final int MAX_ITERATIONS = 10;

    public static final ProgramChromosome<Double> CHROMOSOME = ProgramChromosome.of(
            TREE_MAX_DEPTH,
            OPERATIONS,
            TERMINALS
    );

}
