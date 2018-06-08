package iaa.tp.config;

import iaa.tp.extension.ExtraMathOp;
import io.jenetics.prog.op.EphemeralConst;
import io.jenetics.prog.op.MathOp;
import io.jenetics.prog.op.Op;
import io.jenetics.prog.op.Var;
import io.jenetics.util.ISeq;
import io.jenetics.util.RandomRegistry;

public class ExpressionsConfig {

    // Max amount of nodes in a branch
    public static final int TREE_MAX_DEPTH = 3;

    // Define operations
    public static final ISeq<Op<Double>> OPERATIONS = ISeq.of(
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
    public static final Op<Double> VAR_X = Var.of("x", 0);
    public static final Op<Double> ANY_NUMBER = EphemeralConst.of(() -> (double) RandomRegistry.getRandom().nextInt(10)); // N between 0 and 10 exclusive
    public static final ISeq<Op<Double>> TERMINALS = ISeq.of(VAR_X, ANY_NUMBER);

}
