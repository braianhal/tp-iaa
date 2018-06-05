package fitness;

import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.op.Op;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Kohonen;

import java.util.Arrays;

public class NeuralNetworkSimilarExpressionCalculator extends SimilarExpressionCalculator {

    private Kohonen network;

    private static int INPUTS = 5;
    private static int OUTPUTS = 16;

    public NeuralNetworkSimilarExpressionCalculator(TreeNode<Op<Double>> original) {
        super(original);
        trainNetwork();
    }

    private void trainNetwork() {
        network = new Kohonen(INPUTS, OUTPUTS);
        network.learn(testTrainingSet());
        network.setInput(3, 2, 3, 4, 1); // ln (int cos (x))
        network.calculate();
        System.out.println(Arrays.toString(network.getOutput()));
    }

    @Override
    public Double similarityWith(TreeNode<Op<Double>> otherExpression) {
        return 1d;
    }

    // | Cant terminales | Cant operadores | Nodos de max rama | Max nivel | Min nivel
    private DataSet testTrainingSet() {
        DataSet ds = new DataSet(INPUTS);
        ds.addRow(new DataSetRow(3, 2, 3, 3, 0)); // 2*x + 3
        ds.addRow(new DataSetRow(1, 1, 2, 6, 1)); // cos (x)
        ds.addRow(new DataSetRow(2, 3, 4, 9, 0)); // ln (int 3*x)
        ds.addRow(new DataSetRow(2, 3, 4, 7, 0)); // sen (sen (4^x))
        ds.addRow(new DataSetRow(3, 2, 3, 4, 0)); // 25 * 3 + 58
        ds.addRow(new DataSetRow(4, 4, 5, 8, 0)); // (8x + sen (2x))'
        ds.addRow(new DataSetRow(3, 2, 3, 4, 1)); // x^x + x
        ds.addRow(new DataSetRow(5, 5, 5, 4, 0)); // 4*x^2 + 3*x + 1
        ds.addRow(new DataSetRow(6, 5, 4, 4, 0)); // (3*x+2)*(5 + 2*x)
        return ds;
    }

}
