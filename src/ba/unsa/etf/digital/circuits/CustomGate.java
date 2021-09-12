package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class CustomGate extends LogicCircuit {
    @Override
    public void operation(ArrayList<Boolean> inputs) {

    }

    public CustomGate(String name, int numberOfInputs, int numberOfOutputs) {
        super(name, numberOfInputs, numberOfOutputs);
    }

    @Override
    public String toString() {
        return "Custom Gate " + getNumberOfInputs();
    }
}
