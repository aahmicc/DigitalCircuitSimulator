package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class NotGate extends LogicCircuit {

    @Override
    public void operation(ArrayList<Boolean> inputs) {
        boolean op = !inputs.get(0);
        ArrayList<Boolean> p = new ArrayList<Boolean>();
        p.add(op);
        setOutputs(p);
    }

    public NotGate(String name, int numberOfInputs, int numberOfOutputs, ArrayList<Boolean> inputs) {
        super(name, numberOfInputs, numberOfOutputs, inputs);
    }

    public NotGate(String name, int numberOfInputs, int numberOfOutputs) {
        super(name, numberOfInputs, numberOfOutputs);
    }

    @Override
    public String toString() {
        return "Not1 [Standard]";
    }
}
