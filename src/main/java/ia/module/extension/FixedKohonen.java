package ia.module.extension;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.input.Difference;
import org.neuroph.core.transfer.Linear;
import org.neuroph.nnet.learning.KohonenLearning;
import org.neuroph.util.*;

public class FixedKohonen extends NeuralNetwork {

    public FixedKohonen(int inputNeuronsCount, int outputNeuronsCount) {
        this.createNetwork(inputNeuronsCount, outputNeuronsCount);
    }

    private void createNetwork(int inputNeuronsCount, int outputNeuronsCount) {
        NeuronProperties inputNeuronProperties = new NeuronProperties(
                FixedNeuron.class
        );
        NeuronProperties outputNeuronProperties = new NeuronProperties(
                FixedNeuron.class,        // neuron type
                Difference.class,   // input function
                Linear.class       // transfer function
        );
        // set network type
        this.setNetworkType(NeuralNetworkType.KOHONEN);

        // createLayer input layer
        Layer inLayer = LayerFactory.createLayer(inputNeuronsCount,
                inputNeuronProperties);
        this.addLayer(inLayer);

        // createLayer map layer
        Layer mapLayer = LayerFactory.createLayer(outputNeuronsCount,
                outputNeuronProperties);
        this.addLayer(mapLayer);

        // createLayer full connectivity between input and output layer
        ConnectionFactory.fullConnect(inLayer, mapLayer);

        // set network input and output cells
        NeuralNetworkFactory.setDefaultIO(this);

        this.setLearningRule(new KohonenLearning());
    }

}
