package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class AndGate extends LogicCircuit {
    @Override
    public void operation(ArrayList<Boolean> inputs) {
        boolean op = true;
        for(Boolean i: inputs) if(!i) op = false;

        ArrayList<Boolean> p = new ArrayList<Boolean>();
        p.add(op);
        setOutputs(p);
    }

    public AndGate(String name, int numberOfInputs, int numberOfOutputs) {
        super(name, numberOfInputs, numberOfOutputs);
    }

    @Override
    public String toString() {
        return "And" + getNumberOfInputs() + " [Standard]";
    }
}
