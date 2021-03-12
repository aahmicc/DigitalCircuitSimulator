package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;
import java.util.Objects;

public abstract class LogicCircuit {

    private String name;
    private int numberOfInputs;
    private int numberOfOutputs;
    private ArrayList<Boolean> inputs = new ArrayList<>();
    private ArrayList<Boolean> outputs = new ArrayList<>();

    public abstract void operation(ArrayList<Boolean> inputs);

    public LogicCircuit() {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        name = new String();
    }

    public LogicCircuit(String name, int numberOfInputs, int numberOfOutputs, ArrayList<Boolean> inputs) {
        this.name = name;
        this.numberOfInputs = numberOfInputs;
        this.numberOfOutputs = numberOfOutputs;
        this.inputs = inputs;
        operation(inputs);
    }

    public LogicCircuit(String name, int numberOfInputs, int numberOfOutputs, ArrayList<Boolean> inputs, ArrayList<Boolean> outputs) {
        this.name = name;
        this.numberOfInputs = numberOfInputs;
        this.numberOfOutputs = numberOfOutputs;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public LogicCircuit(String name) {
        this.name = name;
    }

    public LogicCircuit(String name, int numberOfInputs, int numberOfOutputs) {
        this.name = name;
        this.numberOfInputs = numberOfInputs;
        this.numberOfOutputs = numberOfOutputs;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogicCircuit that = (LogicCircuit) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
