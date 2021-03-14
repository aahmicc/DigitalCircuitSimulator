package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class OrGate extends LogicCircuit {
    @Override
    public void operation(ArrayList<Boolean> inputs) {
        boolean op = false;
        for(Boolean i: inputs) if(i) op = true;

        ArrayList<Boolean> p = new ArrayList<Boolean>();
        p.add(op);
        setOutputs(p);
    }

    public OrGate(String name, int numberOfInputs, int numberOfOutputs) {
        super(name, numberOfInputs, numberOfOutputs);
    }

    @Override
    public String toString() {
        return "Or" + getNumberOfInputs() + " [Standard]";
    }
}
