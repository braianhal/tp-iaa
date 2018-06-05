package iaa.tp;

import iaa.tp.fitness.NeuralNetworkSimilarExpressionCalculator;
import iaa.tp.fitness.SimilarExpressionCalculator;
import iaa.tp.tree.ExpressionNode;
import io.jenetics.Genotype;
import io.jenetics.engine.Codec;
import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.ProgramChromosome;
import io.jenetics.prog.ProgramGene;
import io.jenetics.prog.op.EphemeralConst;
import io.jenetics.prog.op.MathOp;
import io.jenetics.prog.op.Op;
import io.jenetics.prog.op.Var;
import io.jenetics.util.ISeq;
import io.jenetics.util.RandomRegistry;
import iaa.tp.parser.Parser;

public class Main {

    static final int INITIAL_POPULATION_SIZE = 50;

    static final int MAX_ITERATIONS = 10;

    // Max amount of nodes in a branch
    static final int TREE_MAX_DEPTH = 3;

    // Define operations
    static final ISeq<Op<Double>> OPERATIONS = ISeq.of(
            // Operations given by the engine
            MathOp.ADD,
            MathOp.SUB,
            MathOp.MUL,
            MathOp.DIV,
            MathOp.POW,
            MathOp.COS,
            MathOp.SIN,
            MathOp.TAN,
            ExtraMathOp.INTEGRAL,
            ExtraMathOp.DERIVATIVE
    );

    // Define terminals
    static final Op<Double> VAR_X = Var.of("x", 0);
    static final Op<Double> ANY_NUMBER = EphemeralConst.of(() -> (double) RandomRegistry.getRandom().nextInt(10)); // N between 0 and 10 exclusive
    static final ISeq<Op<Double>> TERMINALS = ISeq.of(VAR_X, ANY_NUMBER);

    // Define the structure of solutions (max iaa.tp.tree depth, operations and terminals to consider, etc)
    static final Codec<ProgramGene<Double>, ProgramGene<Double>> CODEC = Codec.of(
            Genotype.of(ProgramChromosome.of(
                    TREE_MAX_DEPTH,
                    OPERATIONS,
                    TERMINALS
            )),
            Genotype::getGene
    );

    static final String EXPRESSION_PATTERN = "1+2";

    // Define the original expression (ex.: int(N + cos(x))
    // TODO should be received by program parameter
    static final TreeNode<Op<Double>> EXPRESSION =  TreeNode.of((Op<Double>) ExtraMathOp.INTEGRAL)
            .attach(TreeNode.of((Op<Double>)MathOp.ADD)
                    .attach(ANY_NUMBER)
                    .attach(TreeNode.of((Op<Double>) MathOp.COS)
                            .attach(VAR_X)));

    // Define the iaa.tp.fitness function
    static final SimilarExpressionCalculator SIMILAR_EXPRESSION_CALCULATOR = new NeuralNetworkSimilarExpressionCalculator(EXPRESSION);

    static final Double fitnessFunction(final ProgramGene<Double> expression) {
        //return SimilarExpressionCalculator.percentageOfsimilarityOf(EXPRESSION_PATTERN, Parser.treeNodeToString(TreeNode.ofTree(expression)));
        return 0.0;
    }

    public static void main(final String[] args) {
        // Configure the engine with a crossover specific for trees



        /*Engine<ProgramGene<Double>, Double> engine = Engine.builder(iaa.tp.Main::fitnessFunction, CODEC)
                .alterers(
                        new Mutator<>(),
                        new SingleNodeCrossover<>())
                .populationSize(INITIAL_POPULATION_SIZE) // default 50
                .build();

        ProgramGene<Double> bestExpression = engine.stream()
                .limit(MAX_ITERATIONS) // TODO should be change to 90% of expected value or something like that
                .collect(EvolutionResult.toBestGenotype())
                .getGene();

        System.out.println(Tree.toString(bestExpression)); // TODO should be program output
        */


        // Define the original expression (ex.: int(N + cos(x))
        // TODO should be received by program parameter
        // Esto debería estar definido con nuestro tipo
        /*TreeNode<Op<Double>> exp =  TreeNode.of((Op<Double>) iaa.tp.ExtraMathOp.INTEGRAL)
                .attach(TreeNode.of((Op<Double>)MathOp.ADD)
                        .attach(ANY_NUMBER)
                        .attach(TreeNode.of((Op<Double>) MathOp.COS)
                                .attach(VAR_X)));*/

        TreeNode<Op<Double>> exp = TreeNode.of((Op<Double>)MathOp.TAN).attach(TreeNode.of((Op<Double>)MathOp.ADD).attach(ANY_NUMBER).attach(ANY_NUMBER));
        System.out.println(exp.toString());

        Parser parser = new Parser();
        try {
            ExpressionNode expression = parser.parse(exp);
            System.out.println("The value of the expression is " + expression.getValue());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
