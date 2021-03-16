package ba.unsa.etf.digital.circuits;

import javafx.scene.shape.Line;

public class Triples {
    private LogicCircuit l1;
    private Line line;
    private LogicCircuit l2;

    public Triples(LogicCircuit l1, Line line, LogicCircuit l2) {
        this.l1 = l1;
        this.line = line;
        this.l2 = l2;
    }

    public Triples() {

    }

    public LogicCircuit getFirst() {
        return l1;
    }

    public void setFirst(LogicCircuit l1) {
        this.l1 = l1;
    }

    public Line getSecond() {
        return line;
    }

    public void setSecond(Line line) {
        this.line = line;
    }

    public LogicCircuit getThird() {
        return l2;
    }

    public void setThird(LogicCircuit l2) {
        this.l2 = l2;
    }
}
