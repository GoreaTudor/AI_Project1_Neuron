package ai;

import java.util.Arrays;
import java.util.function.Function;

public class Neuron {
    ///// STATIC /////
    private static Function <Double[], Double> inputFunction;
    private static Function <Double, Double> activationFunction;
    private static Function <Double, Double> outputFunction;

    private static Double theta;
    private static Double g;


    public static void setInputFunction(Function <Double[], Double> inputFunction) {
        Neuron.inputFunction = inputFunction;
    }
    public static void setActivationFunction(Function <Double, Double> activationFunction) {
        Neuron.activationFunction = activationFunction;
    }
    public static void setOutputFunction(Function <Double, Double> outputFunction) {
        Neuron.outputFunction = outputFunction;
    }

    public static Double getTheta() {
        return theta;
    }
    public static void setTheta(Double theta) {
        Neuron.theta = theta;
    }

    public static Double getG() {
        return g;
    }
    public static void setG(Double g) {
        Neuron.g = g;
    }


    ///// OBJECT /////
    private Double[] weights;
    private Double[] weightedInputs;
    private Double globalValue;
    private Double activationValue;
    private Double outputValue;

    /**
     * Creates a new neuron.
     */
    public Neuron() {
        this.globalValue = 0.0;
        this.activationValue = 0.0;
        this.outputValue = 0.0;
    }

    /**
     * Calculates the output value of the neuron, takes the inputs, multiplies with the weights,
     * applies the input function to the weighted inputs, generating the General Input.
     * It then calculates the Activation value by passing the General Input through the activation function,
     * then calculates the final output by passing the Activation Value through the output function.
     * @param inputs represents the inputs
     * @return calculated value
     */
    public Double getOutputValue(Double[] inputs) {
        System.out.println("Inputs : " + Arrays.toString(inputs));
        System.out.println("Weights : " + Arrays.toString(this.weights));

        getWeightedInputs(inputs);
        System.out.println("Weighted inputs : " + Arrays.toString(this.weightedInputs));

        this.globalValue = Neuron.inputFunction.apply(this.weightedInputs);
        System.out.println("Global input value = " + this.globalValue);

        this.activationValue = Neuron.activationFunction.apply(this.globalValue);
        System.out.println("Activation value = " + this.activationValue);

        this.outputValue = Neuron.outputFunction.apply(this.activationValue);
        System.out.println("Output value = " + this.outputValue);

        return this.outputValue;
    }

    private void getWeightedInputs(Double[] inputs) {
        this.weightedInputs = new Double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            this.weightedInputs[i] = inputs[i] * this.weights[i];
        }
    }

    public void setWeights(Double[] weights) {
        this.weights = weights;
    }

    public Double getGlobalValue() {
        return globalValue;
    }
    public Double getActivationValue() {
        return activationValue;
    }
}
