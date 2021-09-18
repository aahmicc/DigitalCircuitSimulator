package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class CustomGate extends LogicCircuit {

    private ArrayList<Boolean> outputsCustom;
    private String name;

    public ArrayList<Boolean> getOutputsCustom() {
        return outputsCustom;
    }

    public void setOutputsCustom(ArrayList<Boolean> outputs) {
        this.outputsCustom = outputs;
    }

    @Override
    public void operation(ArrayList<Boolean> inputs) {
        boolean op = true;

        if(inputs.get(0) == false && inputs.get(1) == false) op = outputsCustom.get(0);
        else if(inputs.get(0) == false && inputs.get(1) == true) op = outputsCustom.get(1);
        else if(inputs.get(0) == true && inputs.get(1) == false) op = outputsCustom.get(2);
        else if(inputs.get(0) == true && inputs.get(1) == true) op = outputsCustom.get(3);

        ArrayList<Boolean> p = new ArrayList<Boolean>();
        p.add(op);
        setOutputs(p);
    }

    public CustomGate(String name, int numberOfInputs, int numberOfOutputs) {
        super(name, numberOfInputs, numberOfOutputs);
        this.name = name;
    }

    @Override
    public String toString() {
        return "[Custom Gate] " + name;
    }
}
