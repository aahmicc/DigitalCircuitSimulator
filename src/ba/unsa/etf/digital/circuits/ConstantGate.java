package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class ConstantGate extends LogicCircuit {

    private boolean con;

    public ConstantGate(String name, int numberOfInputs, int numberOfOutputs, ArrayList<Boolean> inputs, boolean con) {
        super(name, numberOfInputs, numberOfOutputs, inputs);
        this.con = con;
    }

    public ConstantGate(String name, int numberOfInputs, int numberOfOutputs, boolean con) {
        super(name, numberOfInputs, numberOfOutputs);
        this.con = con;
        operation(getInputs());
    }

    public ConstantGate(String name) {
        super(name);
    }

    @Override
    public void operation(ArrayList<Boolean> inputs) {
        ArrayList<Boolean> p = new ArrayList<Boolean>();
        p.add(con);
        setOutputs(p);
    }

    @Override
    public String toString() {
        if(con) return "Constant Input [High]";
        return "Constant Input [Low]";
    }
}
