package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class Output extends LogicCircuit{
    @Override
    public void operation(ArrayList<Boolean> inputs) {
        getOutputs().set(0,getInputs().get(0));
    }

    public Output(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Output [Standard]";
    }
}
