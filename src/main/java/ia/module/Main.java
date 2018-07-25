package ia.module;

import ia.module.genetic.algorithm.GeneticAlgorithm;

public class Main {

    public static void main(final String[] args) {
        // Fitness
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(args[1]);
        String mostSimilarExpression = "";

        if(args[0].equals("tradicional")){
            mostSimilarExpression = geneticAlgorithm.getExpressionMostSimilar();
        }else if(args[0].equals("inteligente")){
            mostSimilarExpression = geneticAlgorithm.getExpressionMostSimilar();
        }

        System.out.println("Best fitness: " + geneticAlgorithm.getSimilarExpressionCalculator().similarityWith(mostSimilarExpression) + "; " + "Best genotype: " + mostSimilarExpression);
    }
}
