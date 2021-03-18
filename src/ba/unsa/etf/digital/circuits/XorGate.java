package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class XorGate extends LogicCircuit {
    @Override
    public void operation(ArrayList<Boolean> inputs) {
        boolean op = false;
        int x = 2;
        int cnt = 0;
        if(inputs.size() == 1) x = 1;
        for(int i = inputs.size()-x; i < inputs.size(); i++) {
            if (inputs.get(i)) cnt++;
        }

        if(cnt%2 == 1) op = true;
        ArrayList<Boolean> p = new ArrayList<Boolean>();
        p.add(op);
        setOutputs(p);
    }

    public XorGate(String name, int numberOfInputs, int numberOfOutputs) {
        super(name, numberOfInputs, numberOfOutputs);
    }

    @Override
    public String toString() {
        return "Xor" + getNumberOfInputs() + " [Standard]";
    }
}
