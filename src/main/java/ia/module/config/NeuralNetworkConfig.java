package ia.module.config;

public class NeuralNetworkConfig {

    public static int INPUTS = 21; // Expression features
    public static int OUTPUTS = 400; // Expression categories

    public static int TRAINING_EXAMPLES = 1000;

    // Similarity minimum value for each case when comparing 2 expressions
    public static double BASE_SIMILARITY_MAIN_CATEOGORY = 0.8;
    public static double BASE_SIMILARITY_SECONDARY_CATEGORY = 0.5;
    public static double BASE_SIMILARITY_OTHER_CATEGORY = 0.0;

    public static double SIMILAR_CATEGORY_LIMIT = 0.01;

}
