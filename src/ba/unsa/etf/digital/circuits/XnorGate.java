package ba.unsa.etf.digital.circuits;

import java.util.ArrayList;

public class XnorGate extends LogicCircuit {
    @Override
    public void operation(ArrayList<Boolean> inputs) {
        boolean op = true;
        int x = 2;
        int cnt = 0;
        if(inputs.size() == 1) x = 1;
        for(int i = inputs.size()-x; i < inputs.size(); i++) {
            if (inputs.get(i)) cnt++;
        }

        if(cnt%2 == 1) op = false;
        ArrayList<Boolean> p = new ArrayList<Boolean>();
        p.add(op);
        setOutputs(p);
    }

    public XnorGate(String name, int numberOfInputs, int numberOfOutputs) {
        super(name, numberOfInputs, numberOfOutputs);
    }

    @Override
    public String toString() {
        return "Xnor" + getNumberOfInputs() + " [Standard]";
    }
}
