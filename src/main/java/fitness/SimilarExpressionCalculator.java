package fitness;

import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.op.Op;

public abstract class SimilarExpressionCalculator {

    protected TreeNode<Op<Double>> originalExpression;

    public abstract Double similarityWith(TreeNode<Op<Double>> otherExpression);

    SimilarExpressionCalculator(TreeNode<Op<Double>> expression) {
        this.originalExpression = expression;
    }

}
