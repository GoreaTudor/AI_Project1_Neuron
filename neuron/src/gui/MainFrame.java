package gui;

import ai.Neuron;
import exceptions.InvalidNumberOfInputsException;
import exceptions.InvalidNumberOfInputsValueException;
import exceptions.InvalidNumberOfWeightsException;
import exceptions.InvalidOptionException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class MainFrame extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JLabel lbl_inputFunction;
    private JLabel lbl_activationFunction;
    private JLabel lbl_thetaValue;
    private JLabel lbl_gValue;
    private JLabel lbl_outputType;
    private JLabel lbl_inputs;
    private JLabel lbl_weights;
    private JLabel lbl_nrOfInputs;
    private JLabel lbl_obs;
    private JLabel lbl_output;
    private JLabel lbl_activation;
    private JLabel lbl_input;
    private JComboBox <String> comboBox_input;
    private JComboBox <String> comboBox_activation;
    private JComboBox <String> comboBox_outputType;
    private JTextField textField_theta;
    private JTextField textField_g;
    private JTextField textField_inputs;
    private JTextField textField_weights;
    private JTextField textField_output;
    private JTextField textField_activation;
    private JTextField textField_input;
    private JSpinner spinner_nrOfInputs;
    private JButton button_calculate;



    private int numberOfInputs;
    private Double[] inputs;
    private Double[] weights;
    private Double outputValue;

    private Neuron neuron;


    public MainFrame() {
        super("Neuron");

        neuron = new Neuron();
        setup();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);
    }


    private void setup() {
        this.comboBox_input.addItem(StringConstants.SUM);
        this.comboBox_input.addItem(StringConstants.PROD);
        this.comboBox_input.addItem(StringConstants.MIN);
        this.comboBox_input.addItem(StringConstants.MAX);

        this.comboBox_activation.addItem(StringConstants.STEP);
        this.comboBox_activation.addItem(StringConstants.SIGN);
        this.comboBox_activation.addItem(StringConstants.SIGMOID);
        this.comboBox_activation.addItem(StringConstants.TANH);
        this.comboBox_activation.addItem(StringConstants.LINEAR);

        this.comboBox_outputType.addItem(StringConstants.REAL);
        this.comboBox_outputType.addItem(StringConstants.BINARY);

        this.button_calculate.addActionListener(this);

        setupDesign();
        setDefaultValues();
    }

    private void setupDesign() {
        Font font = new Font("Consolas", Font.BOLD, 16);
        Font fontSimple = new Font("Consolas", Font.PLAIN, 12);

        // Labels
        this.lbl_inputFunction.setFont(font);
        this.lbl_activationFunction.setFont(font);
        this.lbl_thetaValue.setFont(font);
        this.lbl_gValue.setFont(font);
        this.lbl_outputType.setFont(font);
        this.lbl_inputs.setFont(font);
        this.lbl_weights.setFont(font);
        this.lbl_nrOfInputs.setFont(font);
        this.lbl_obs.setFont(fontSimple);
        this.lbl_output.setFont(font);
        this.lbl_activation.setFont(font);
        this.lbl_input.setFont(font);

        // TextBoxes
        this.textField_inputs.setFont(font);
        this.textField_weights.setFont(font);
        this.textField_theta.setFont(font);
        this.textField_g.setFont(font);
        this.textField_input.setFont(font);
        this.textField_activation.setFont(font);
        this.textField_output.setFont(font);

        // ComboBoxes
        this.comboBox_input.setFont(font);
        this.comboBox_activation.setFont(font);
        this.comboBox_outputType.setFont(font);

        // Spinner
        this.spinner_nrOfInputs.setFont(font);

        // Button
        this.button_calculate.setFont(font);
    }

    private void setDefaultValues() {
        this.spinner_nrOfInputs.setValue(2);
        this.textField_inputs.setText("1 2");
        this.textField_weights.setText("0.5 1");

        this.textField_theta.setText("0.0");
        this.textField_g.setText("1.0");

        this.textField_output.setText("0.0");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("\nButton pressed");

        try{
            prepareInputsAndWeights();
            neuron.setWeights(weights);
            prepareFunctions();
            outputValue = neuron.getOutputValue(inputs);
            this.textField_input.setText(neuron.getGlobalValue().toString());
            this.textField_activation.setText(neuron.getActivationValue().toString());
            this.textField_output.setText(outputValue.toString());

        } catch (InvalidNumberOfInputsValueException exception) { // invalid nr in the spinner
            JOptionPane.showMessageDialog(this, "Invalid number in the spinner");

        } catch (InvalidNumberOfInputsException exception) { // not enough inputs
            JOptionPane.showMessageDialog(this, "Not enough inputs");

        } catch (InvalidNumberOfWeightsException exception) { // not enough outputs
            JOptionPane.showMessageDialog(this, "Not enough weights");

        } catch (NumberFormatException exception) { // not given a number
            JOptionPane.showMessageDialog(this, "not given a number");

        } catch (InvalidOptionException exception) { // idk really ...
            JOptionPane.showMessageDialog(this, "If you see this error then Congratulations! No one got this error :))");

        } catch (Exception exception) { // general exception handler for app to not crash
            JOptionPane.showMessageDialog(this, "No idea what went wrong here...");
            exception.printStackTrace();
        }
    }


    private void prepareInputsAndWeights() {
        // Nr of inputs
        this.numberOfInputs = (Integer) this.spinner_nrOfInputs.getValue();
        if (this.numberOfInputs <= 0) { // cannot be less than or equal to 0
            throw new InvalidNumberOfInputsValueException();
        }

        // Inputs
        StringTokenizer strtokInputs = new StringTokenizer(this.textField_inputs.getText());
        String[] inputsText = new String[strtokInputs.countTokens()];
        int indexInputs = 0;
        while(strtokInputs.hasMoreTokens()) {
            inputsText[indexInputs++] = strtokInputs.nextToken();
        }

        if(inputsText.length < this.numberOfInputs) { // not enough inputs
            throw new InvalidNumberOfInputsException();
        }
        this.inputs = new Double[inputsText.length];
        for(int i = 0; i < inputsText.length; i++) {
            this.inputs[i] = Double.parseDouble(inputsText[i]); // throws NumberFormatException
        }

        // Weights
        StringTokenizer strtokWeights = new StringTokenizer(this.textField_weights.getText());
        String[] weightsText = new String[strtokWeights.countTokens()];
        int indexWeights = 0;
        while (strtokWeights.hasMoreTokens()) {
            weightsText[indexWeights++] = strtokWeights.nextToken();
        }

        if(weightsText.length < this.numberOfInputs) { // not enough weights
            throw new InvalidNumberOfWeightsException();
        }
        this.weights = new Double[weightsText.length];
        for(int i = 0; i < inputsText.length; i++) {
            this.weights[i] = Double.parseDouble(weightsText[i]); // throws NumberFormatException
        }

        System.out.println(" -- Inputs prepared");
    }


    private void prepareFunctions() {
        // Input function
        String inputOption = this.comboBox_input.getSelectedItem().toString(); // Not gonna have NullPointerException
        if(inputOption.equals(StringConstants.SUM)) {
            Neuron.setInputFunction(weightedInputs -> {
                Double sum = 0.0;
                for (Double item : weightedInputs) {
                    sum += item;
                }
                return sum;
            });

        } else if (inputOption.equals(StringConstants.PROD)) {
            Neuron.setInputFunction(weightedInputs -> {
                Double prod = 1.0;
                for (Double item : weightedInputs) {
                    prod *= item;
                }
                return prod;
            });

        } else if (inputOption.equals(StringConstants.MIN)) {
            Neuron.setInputFunction(weightedInputs -> {
                Double min = weightedInputs[0];
                for (Double item : weightedInputs) {
                    if (item < min) {
                        min = item;
                    }
                }
                return min;
            });

        } else if (inputOption.equals(StringConstants.MAX)) {
            Neuron.setInputFunction(weightedInputs -> {
                Double max = weightedInputs[0];
                for (Double item : weightedInputs) {
                    if (item > max) {
                        max = item;
                    }
                }
                return max;
            });

        } else {
            throw new InvalidOptionException(); // not sure if I can get here
        }


        // Activation function
        String activationOption = this.comboBox_activation.getSelectedItem().toString();
        double theta = Double.parseDouble(this.textField_theta.getText()); // get theta
        double g = Double.parseDouble(this.textField_g.getText()); // get g (or a)

        System.out.println("theta = " + theta);
        System.out.println("g = " + g);

        if (activationOption.equals(StringConstants.STEP)) {
            Neuron.setActivationFunction(x -> ((x < theta) ? 0.0 : 1.0));

        } else if (activationOption.equals(StringConstants.SIGN)) {
            Neuron.setActivationFunction(x -> ((x < theta) ? -1.0 : 1.0));

        } else if (activationOption.equals(StringConstants.SIGMOID)) {
            // sigmoid(x) = 1 / (1 + e ^ (-g * (x - theta)))
            Neuron.setActivationFunction(x -> 1.0 / (1 + Math.exp(-g * (x - theta))));

        } else if (activationOption.equals(StringConstants.TANH)) {
            Neuron.setActivationFunction(x -> {
                // tanh(x) = (e^(g*(x-theta) - e^(-g*(x-theta))) / (e^(g*(x-theta) + e^(-g*(x-theta))
                Double x1 = Math.exp(g * (x - theta)); // e^(g*(x-theta))
                Double x2 = Math.exp(-g * (x - theta)); // e^(-g*(x-theta))
                return (x1 - x2) / (x1 + x2);
            });

        } else if (activationOption.equals(StringConstants.LINEAR)) {
            Neuron.setActivationFunction(x -> {
                if (x < -1/g) return -1.0;
                if (x > 1/g) return 1.0;
                return g * x;
            });

        } else {
            throw new InvalidOptionException();
        }


        // Output function
        String outputOption = this.comboBox_outputType.getSelectedItem().toString();
        if(outputOption.equals(StringConstants.REAL)
                || activationOption.equals(StringConstants.STEP)
                || activationOption.equals(StringConstants.SIGN)
        ) {
            Neuron.setOutputFunction(x -> x);

        } else if(outputOption.equals(StringConstants.BINARY)) {
            if (activationOption.equals(StringConstants.SIGMOID)) {
                Neuron.setOutputFunction(x -> ((x < 0.5) ? 0.0 : 1.0));

            } else {
                Neuron.setOutputFunction(x -> ((x < 0.0) ? -1.0 : 1.0));
            }

        } else {
            throw new InvalidOptionException();
        }

        System.out.println(" -- Functions prepared");
    }
}
