import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.op.Op;

public class SimilarExpressionCalculator {

    public TreeNode<Op<Double>> originalExpression;

    public SimilarExpressionCalculator(TreeNode<Op<Double>> original) {
        this.originalExpression = original;
    }

    // The similarity check function
    public Double similarityWith(TreeNode<Op<Double>> otherExpression) {
        return (double) Math.abs(originalExpression.size() - otherExpression.size()); // TODO change for fitness function real code
    }

}
