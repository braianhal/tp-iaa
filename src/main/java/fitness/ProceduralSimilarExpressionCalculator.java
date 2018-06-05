package fitness;

import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.op.Op;

public class ProceduralSimilarExpressionCalculator extends SimilarExpressionCalculator {

    @Override
    public Double similarityWith(TreeNode<Op<Double>> otherExpression) {
        return 0d;
    }

    public ProceduralSimilarExpressionCalculator(TreeNode<Op<Double>> original) {
        super(original);
    }

    // The similarity check function
    public static Double percentageOfsimilarityOf(String expressionPattern, String expressionOfChromosome/*TreeNode<Op<Double>> otherExpression*/) {

      /*  // Instancio a un nodo nuestro:
        // Node nuestroNodo = new Node();
        //Para iterar los hijos de un TreeNode:
        List<Node> hijos;
        for (int i = 0; i <= otherExpression.childCount(); i++) {
            hijos.add(nuestroHijo(otherExpression.getChild(i)))
            // Cada hijo del nodo es: otherExpression.getChild(i)
            // Aca instancio a un Node nuestro, que tenga una lista de nodos (hijos)
            // nuestroHijo(otherExpression.getChild(i))
            // nuestroNodo.agregarHijo(nuestroHijo)

            // Invocar recursivamente a la función, para cada uno de los hijos generados. Es decir:
            // nuestroNodo
        }
        new Node(hijos)<



        return (double) Math.abs(originalExpression.size() - otherExpression.size()); // TODO change for fitness function real code*/
      return 0.0;
    }

}
