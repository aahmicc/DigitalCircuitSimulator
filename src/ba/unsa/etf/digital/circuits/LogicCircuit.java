package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class LogicCircuit {

    private String name;
    private int numberOfInputs;
    private int numberOfOutputs;
    private ArrayList<Boolean> inputs;
    private ArrayList<Boolean> outputs;

    public LogicCircuit() {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        name = new String();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    public int getNumberOfOutputs() {
        return numberOfOutputs;
    }

    public void setNumberOfOutputs(int numberOfOutputs) {
        this.numberOfOutputs = numberOfOutputs;
    }

    public ArrayList<Boolean> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<Boolean> inputs) {
        this.inputs = inputs;
    }

    public ArrayList<Boolean> getOutputs() {
        return outputs;
    }

    public void setOutputs(ArrayList<Boolean> outputs) {
        this.outputs = outputs;
    }
}
