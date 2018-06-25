package ia.module.extension;

import org.neuroph.core.Neuron;

public class FixedNeuron extends Neuron {

    @Override
    public void calculate() {
        if (inputConnections.size() != 0) {
            this.totalInput = inputFunction.getOutput(inputConnections);
        }
        output = transferFunction.getOutput(totalInput);
    }

}
