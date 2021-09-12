package ba.unsa.etf.digital.circuits;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class EditBlackBoxController extends Component implements Initializable{

    public Button newId, openId, saveId, printId, undoId, redoId, componentsId;
    public Button pauseId, zoomOutId, zoomSheetId, playId, stopId, optionsId, zoomInId;
    public Separator separator1,separator2,separator3,separator4,separator5,separator6,separator7,separator8;
    public ChoiceBox<LogicCircuit> recentlyUsedChoice;
    public Pane paneId;
    private Rectangle rectangle = new Rectangle();

    private boolean connecting = false;
    private LogicCircuit connectingElement = null;
    private double connectingX, connectingY;

    private HashMap<Button, LogicCircuit> logicCircuitMap = new HashMap<>();
    private ArrayList<Triples> allConns = new ArrayList<>();
    private HashMap<Line, Integer> allLines = new HashMap<>();
    private HashMap<Button, Label> buttonLabelMap = new HashMap<>();

    private ArrayList<Object> allActions = new ArrayList<>();
    private ArrayList<Object> allDeletedActions = new ArrayList<>();

    final ContextMenu contextMenu = new ContextMenu();

    public LogicCircuit currentlyPickedLC;

    public ArrayList<CustomGate> allCustomGates = new ArrayList<>();

    private ContextMenu contextMenuEditBlackBox = new ContextMenu();


    public Button editBlackBoxInput1, editBlackBoxInput2, editBlackBoxOutput;

    public EditBlackBoxController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        recentlyUsedChoice.setItems(FXCollections.observableArrayList(createStandardGates()));
        recentlyUsedChoice.setValue(recentlyUsedChoice.getItems().get(0));

        recentlyUsedChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                currentlyPickedLC = recentlyUsedChoice.getItems().get((Integer) number2);
            }
        });

        setIconStyles();
        setSeparatorStyles();
        setInputOverrideContextMenu();

        drawConstantGateHigh(20, 151);
        drawConstantGateHigh(20, 273);
        drawOutputGate(546,200);

    }

    private None n = new None();
    private ArrayList<LogicCircuit> createStandardGates() {
        ArrayList<LogicCircuit> retValues = new ArrayList<>();

        retValues.add(n);

        ConstantGate conGateH = new ConstantGate("Constant Input [High]", 0, 1, null, true);
        retValues.add(conGateH);

        ConstantGate conGateL = new ConstantGate("Constant Input [Low]", 0, 1, null, false);
        retValues.add(conGateL);

        Output output = new Output("Output");
        //retValues.add(output);

        ArrayList<Boolean> notArray = new ArrayList<>();
        notArray.add(false);
        NotGate not = new NotGate("Not1 [Standard]",1,1, notArray);
        retValues.add(not);

        AndGate and = new AndGate("And2 [Standard]", 2, 1);
        retValues.add(and);

        OrGate or = new OrGate("Or2 [Standard]",2,1);
        retValues.add(or);

        NandGate nand = new NandGate("Nand2 [Standard]",2,1);
        retValues.add(nand);

        NorGate nor = new NorGate("Nor2 [Standard]",2,1);
        retValues.add(nor);

        XorGate xor = new XorGate("Xor2 [Standard]",2,1);
        retValues.add(xor);

        XnorGate xnor = new XnorGate("Xnor2 [Standard]",2,1);
        retValues.add(xnor);

        CustomGate customGate = new CustomGate("Custom Gate",2,1);
        //retValues.add(customGate);

        return retValues;
    }

    public void drawAction(MouseEvent actionEvent) {
        if(currentlyPickedLC != null) {
            if(!connecting) {
                if (currentlyPickedLC.getClass().equals(NotGate.class)) drawStandardNotGate(actionEvent);
                else if (currentlyPickedLC.getClass().equals(ConstantGate.class)) {
                    if (currentlyPickedLC.getName().equals("Constant Input [High]"))
                        drawConstantGateHigh(actionEvent);
                    else if (currentlyPickedLC.getName().equals("Constant Input [Low]"))
                        drawConstantGateLow(actionEvent);
                } else if (currentlyPickedLC.getClass().equals(Output.class)) drawOutputGate(actionEvent);
                else if (currentlyPickedLC.getClass().equals(AndGate.class)) drawStandardAndGate(actionEvent);
                else if (currentlyPickedLC.getClass().equals(OrGate.class)) drawStandardOrGate(actionEvent);
                else if (currentlyPickedLC.getClass().equals(NandGate.class)) drawStandardNandGate(actionEvent);
                else if (currentlyPickedLC.getClass().equals(NorGate.class)) drawStandardNorGate(actionEvent);
                else if (currentlyPickedLC.getClass().equals(XorGate.class)) drawStandardXorGate(actionEvent);
                else if (currentlyPickedLC.getClass().equals(XnorGate.class)) drawStandardXnorGate(actionEvent);
                else if (currentlyPickedLC.getClass().equals(CustomGate.class)) drawCustomGate(actionEvent);
            }
            connecting = false;
            paneId.getChildren().remove(rectangle);
        }
    }

    private void drawStandardNotGate(MouseEvent actionEvent) {
        Button b = new Button();
        b.setLayoutX(actionEvent.getX()-35);
        b.setLayoutY(actionEvent.getY()-18);
        b.setPrefSize(70,37);
        b.getStyleClass().add("standardNotStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(NotGate.class)) cnt++;
        String name = "not" + cnt;
        NotGate notGate = new NotGate(name,1,1);
        logicCircuitMap.put(b, notGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-15);
        l.setLayoutY(actionEvent.getY()+20);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("standardNotStyle");
            b.getStyleClass().add("standardNotStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("standardNotStyleHover");
            b.getStyleClass().add("standardNotStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(connecting) {
                    connecting = false;
                    paneId.getChildren().remove(rectangle);
                    if(connectingElement != logicCircuitMap.get(b)) {
                        Line line = new Line();

                        boolean del = false;
                        Triples p = new Triples();
                        for(Triples t: allConns) {
                            if(t.getFirst().equals(connectingElement)) {
                                del = true;
                                p = t;
                                paneId.getChildren().remove(t.getSecond());
                            }
                        }
                        if(del) allConns.remove(p);

                        del = false;
                        for(Triples t: allConns) {
                            if(t.getThird().equals(logicCircuitMap.get(b))) {
                                paneId.getChildren().remove(t.getSecond());
                                p = t;
                                del = true;
                            }
                        }
                        if(del) allConns.remove(p);

                        logicCircuitMap.get(b).setInputs(connectingElement.getOutputs());
                        logicCircuitMap.get(b).operation(logicCircuitMap.get(b).getInputs());

                        line.setStartX(actionEvent.getX()-35);
                        line.setStartY(actionEvent.getY()+1);
                        line.setEndX(connectingX);
                        line.setEndY(connectingY);
                        line.setOpacity(1);
                        paneId.getChildren().add(line);

                        allConns.add(new Triples(connectingElement, line, logicCircuitMap.get(b)));
                        allLines.put(line, allLines.size());
                        allActions.add(line);
                        updateAll();
                        updateAll();
                    }
                } else {
                    connecting = true;
                    connectingElement = notGate;
                    connectingX = actionEvent.getX()+35;
                    connectingY = actionEvent.getY()+1;
                    drawOutlineForStandardGateNot(b);
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private Button bAnd = new Button();
    private MouseEvent actionEventAnd;

    private void drawStandardAndGate(MouseEvent actionEvent) {
        actionEventAnd = actionEvent;
        Button b = new Button();
        b.setLayoutX(actionEvent.getX()-32);
        b.setLayoutY(actionEvent.getY()-17);
        b.setPrefSize(65,34);
        b.getStyleClass().add("standardAndStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(AndGate.class)) cnt++;
        String name = "and" + cnt;
        AndGate andGate = new AndGate(name,2,1);
        logicCircuitMap.put(b, andGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-15);
        l.setLayoutY(actionEvent.getY()+20);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("standardAndStyle");
            b.getStyleClass().add("standardAndStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("standardAndStyleHover");
            b.getStyleClass().add("standardAndStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                actionEventAnd = actionEvent;
                if(connecting) {
                    paneId.getChildren().remove(rectangle);
                    input2Components(b);
                }
                else {
                    connecting = true;
                    connectingElement = andGate;
                    connectingX = actionEvent.getX()+32;
                    connectingY = actionEvent.getY();
                    drawOutlineForStandardGate(b);
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawStandardNandGate(MouseEvent actionEvent) {
        actionEventAnd = actionEvent;
        Button b = new Button();
        b.setLayoutX(actionEvent.getX()-32);
        b.setLayoutY(actionEvent.getY()-17);
        b.setPrefSize(65,34);
        b.getStyleClass().add("standardNandStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(NandGate.class)) cnt++;
        String name = "nand" + cnt;
        NandGate nandGate = new NandGate(name,2,1);
        logicCircuitMap.put(b, nandGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-15);
        l.setLayoutY(actionEvent.getY()+20);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("standardNandStyle");
            b.getStyleClass().add("standardNandStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("standardNandStyleHover");
            b.getStyleClass().add("standardNandStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                actionEventAnd = actionEvent;
                if(connecting) {
                    paneId.getChildren().remove(rectangle);
                    input2Components(b);
                }
                else {
                    connecting = true;
                    connectingElement = nandGate;
                    connectingX = actionEvent.getX()+32;
                    connectingY = actionEvent.getY();
                    drawOutlineForStandardGate(b);
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawStandardOrGate(MouseEvent actionEvent) {
        actionEventAnd = actionEvent;
        Button b = new Button();
        b.setLayoutX(actionEvent.getX()-32);
        b.setLayoutY(actionEvent.getY()-17);
        b.setPrefSize(65,34);
        b.getStyleClass().add("standardOrStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(OrGate.class)) cnt++;
        String name = "or" + cnt;
        OrGate orGate = new OrGate(name,2,1);
        logicCircuitMap.put(b, orGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-15);
        l.setLayoutY(actionEvent.getY()+20);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("standardOrStyle");
            b.getStyleClass().add("standardOrStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("standardOrStyleHover");
            b.getStyleClass().add("standardOrStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                actionEventAnd = actionEvent;
                if(connecting) {
                    paneId.getChildren().remove(rectangle);
                    input2Components(b);
                }
                else {
                    connecting = true;
                    connectingElement = orGate;
                    connectingX = actionEvent.getX()+32;
                    connectingY = actionEvent.getY();
                    drawOutlineForStandardGate(b);
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawStandardNorGate(MouseEvent actionEvent) {
        actionEventAnd = actionEvent;
        Button b = new Button();
        b.setLayoutX(actionEvent.getX()-32);
        b.setLayoutY(actionEvent.getY()-17);
        b.setPrefSize(65,34);
        b.getStyleClass().add("standardNorStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(NorGate.class)) cnt++;
        String name = "nor" + cnt;
        NorGate norGate = new NorGate(name,2,1);
        logicCircuitMap.put(b, norGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-15);
        l.setLayoutY(actionEvent.getY()+20);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("standardNorStyle");
            b.getStyleClass().add("standardNorStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("standardNorStyleHover");
            b.getStyleClass().add("standardNorStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                actionEventAnd = actionEvent;
                if(connecting) {
                    paneId.getChildren().remove(rectangle);
                    input2Components(b);
                }
                else {
                    connecting = true;
                    connectingElement = norGate;
                    connectingX = actionEvent.getX()+32;
                    connectingY = actionEvent.getY();
                    drawOutlineForStandardGate(b);
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawStandardXorGate(MouseEvent actionEvent) {
        actionEventAnd = actionEvent;
        Button b = new Button();
        b.setLayoutX(actionEvent.getX()-32);
        b.setLayoutY(actionEvent.getY()-17);
        b.setPrefSize(65,34);
        b.getStyleClass().add("standardXorStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(XorGate.class)) cnt++;
        String name = "xor" + cnt;
        XorGate xorGate = new XorGate(name,2,1);
        logicCircuitMap.put(b, xorGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-15);
        l.setLayoutY(actionEvent.getY()+20);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("standardXorStyle");
            b.getStyleClass().add("standardXorStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("standardXorStyleHover");
            b.getStyleClass().add("standardXorStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                actionEventAnd = actionEvent;
                if(connecting) {
                    paneId.getChildren().remove(rectangle);
                    input2Components(b);
                }
                else {
                    connecting = true;
                    connectingElement = xorGate;
                    connectingX = actionEvent.getX()+32;
                    connectingY = actionEvent.getY();
                    drawOutlineForStandardGate(b);
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawStandardXnorGate(MouseEvent actionEvent) {
        actionEventAnd = actionEvent;
        Button b = new Button();
        b.setLayoutX(actionEvent.getX()-32);
        b.setLayoutY(actionEvent.getY()-17);
        b.setPrefSize(65,34);
        b.getStyleClass().add("standardXnorStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(XnorGate.class)) cnt++;
        String name = "xnor" + cnt;
        XnorGate xnorGate = new XnorGate(name,2,1);
        logicCircuitMap.put(b, xnorGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-15);
        l.setLayoutY(actionEvent.getY()+20);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("standardXnorStyle");
            b.getStyleClass().add("standardXnorStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("standardXnorStyleHover");
            b.getStyleClass().add("standardXnorStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                actionEventAnd = actionEvent;
                if(connecting) {
                    paneId.getChildren().remove(rectangle);
                    input2Components(b);
                }
                else {
                    connecting = true;
                    connectingElement = xnorGate;
                    connectingX = actionEvent.getX()+32;
                    connectingY = actionEvent.getY();
                    drawOutlineForStandardGate(b);
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawCustomGate(MouseEvent actionEvent) {
        actionEventAnd = actionEvent;
        Button b = new Button();
        b.setLayoutX(actionEvent.getX()-32);
        b.setLayoutY(actionEvent.getY()-17);
        b.setPrefSize(65,34);
        b.getStyleClass().add("standardXnorStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(XnorGate.class)) cnt++;
        String name = "xnor" + cnt;
        XnorGate xnorGate = new XnorGate(name,2,1);
        logicCircuitMap.put(b, xnorGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-15);
        l.setLayoutY(actionEvent.getY()+20);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("standardXnorStyle");
            b.getStyleClass().add("standardXnorStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("standardXnorStyleHover");
            b.getStyleClass().add("standardXnorStyle");
        });
        b.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY)
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditBlackBox.fxml"));
                EditBlackBoxController ctrl = new EditBlackBoxController();
                loader.setController(ctrl);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stg = new Stage();
                stg.setTitle("Edit " + name);
                stg.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stg.show();

            }
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                actionEventAnd = actionEvent;
                if(connecting) {
                    paneId.getChildren().remove(rectangle);
                    input2Components(b);
                }
                else {
                    connecting = true;
                    connectingElement = xnorGate;
                    connectingX = actionEvent.getX()+32;
                    connectingY = actionEvent.getY();
                    drawOutlineForStandardGate(b);
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void input2Components(Button b) {
        connecting = false;
        if(connectingElement != logicCircuitMap.get(b)) {
            Line line = new Line();

            boolean del = false;
            Triples p = new Triples();
            for(Triples t: allConns) {
                if(t.getFirst().equals(connectingElement)) {
                    del = true;
                    p = t;
                    paneId.getChildren().remove(t.getSecond());
                }
            }
            if(del) allConns.remove(p);

            int numberOfUsedInputs = 0;
            for(Triples t: allConns)
                if(t.getThird().equals(logicCircuitMap.get(b))) numberOfUsedInputs++;

            if(numberOfUsedInputs == logicCircuitMap.get(b).getNumberOfInputs()) {
                bAnd = b;
                contextMenu.show(b, Side.BOTTOM, 0, 0);
            }
            else {
                if(numberOfUsedInputs == 0) {
                    line = drawAndFirstInput();
                    logicCircuitMap.get(b).getInputs().add(connectingElement.getOutputs().get(0));
                }
                else {
                    line = drawAndSecondInput();
                    logicCircuitMap.get(b).getInputs().add(connectingElement.getOutputs().get(0));
                }
                allConns.add(new Triples(connectingElement, line, logicCircuitMap.get(b)));

                logicCircuitMap.get(b).operation(logicCircuitMap.get(b).getInputs());
                allActions.add(line);
                updateAll();
                updateAll();
            }
        }
    }

    private void setInputOverrideContextMenu() {
        MenuItem item1 = new MenuItem("Override Input 1");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Line line = drawAndFirstInput();
                for(Triples t: allConns) {
                    if(Math.abs(line.getStartX()-t.getSecond().getStartX()) < 1.5 && Math.abs(line.getStartY()-t.getSecond().getStartY()) < 1.5) {
                        paneId.getChildren().remove(t.getSecond());
                        allConns.remove(t);
                        break;
                    }
                }
                allConns.add(new Triples(connectingElement, line, logicCircuitMap.get(bAnd)));
                logicCircuitMap.get(bAnd).getInputs().set(0,connectingElement.getOutputs().get(0));
                logicCircuitMap.get(bAnd).operation(logicCircuitMap.get(bAnd).getInputs());

                updateAll();
                updateAll();
            }
        });
        MenuItem item2 = new MenuItem("Override Input 2");
        item2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Line line = drawAndSecondInput();
                for(Triples t: allConns) {
                    if(Math.abs(line.getStartX()-t.getSecond().getStartX()) < 1.5 && Math.abs(line.getStartY()-t.getSecond().getStartY()) < 1.5) {
                        paneId.getChildren().remove(t.getSecond());
                        allConns.remove(t);
                        break;
                    }
                }
                allConns.add(new Triples(connectingElement, line, logicCircuitMap.get(bAnd)));
                logicCircuitMap.get(bAnd).getInputs().set(1,connectingElement.getOutputs().get(0));
                logicCircuitMap.get(bAnd).operation(logicCircuitMap.get(bAnd).getInputs());

                updateAll();
                updateAll();
            }
        });
        contextMenu.getItems().addAll(item1, item2);
    }

    private Line drawAndFirstInput() {
        Line line = new Line();
        line.setStartX(actionEventAnd.getX() - 32);
        line.setStartY(actionEventAnd.getY() - 8);
        line.setEndX(connectingX);
        line.setEndY(connectingY);
        line.setOpacity(1);
        paneId.getChildren().add(line);
        allLines.put(line, allLines.size());
        return line;
    }

    private Line drawAndSecondInput() {
        Line line = new Line();
        line.setStartX(actionEventAnd.getX() - 32);
        line.setStartY(actionEventAnd.getY() + 8);
        line.setEndX(connectingX);
        line.setEndY(connectingY);
        line.setOpacity(1);
        paneId.getChildren().add(line);
        allLines.put(line, allLines.size());
        return line;
    }

    private void drawConstantGateHigh(MouseEvent actionEvent) {
        Button b = new Button("1");
        b.setLayoutX(actionEvent.getX()-12);
        b.setLayoutY(actionEvent.getY()-12);
        b.setPrefSize(26,26);
        b.getStyleClass().add("constantStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(ConstantGate.class)) cnt++;
        String name = "constant" + cnt;
        ConstantGate constantGate = new ConstantGate(name,1,1,true);
        logicCircuitMap.put(b, constantGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-23);
        l.setLayoutY(actionEvent.getY()+12);
        paneId.getChildren().add(l);


        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("constantStyle");
            b.getStyleClass().add("constantStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("constantStyleHover");
            b.getStyleClass().add("constantStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                connecting = true;
                connectingElement = constantGate;
                connectingX = actionEvent.getX()+12;
                connectingY = actionEvent.getY()+1;
                drawOutlineForInput(b);
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawConstantGateHigh(int x, int y) {
        Button b = new Button("Input");
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setPrefSize(80,45);
        b.getStyleClass().add("BBinputStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(ConstantGate.class)) cnt++;
        String name = "constant" + cnt;
        ConstantGate constantGate = new ConstantGate(name,1,1,true);
        logicCircuitMap.put(b, constantGate);

        Label l = new Label();
        l.setText("");
        l.setLayoutX(x);
        l.setLayoutY(y);
        paneId.getChildren().add(l);


        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("BBinputStyle");
            b.getStyleClass().add("BBinputStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("BBinputStyleHover");
            b.getStyleClass().add("BBinputStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                connecting = true;
                connectingElement = constantGate;
                connectingX = x+75;
                connectingY = y+22;
                drawOutlineForBBInput(b);
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawConstantGateLow(MouseEvent actionEvent) {
        Button b = new Button("0");
        b.setLayoutX(actionEvent.getX()-12);
        b.setLayoutY(actionEvent.getY()-12);
        b.setPrefSize(26,26);
        b.getStyleClass().add("constantStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(ConstantGate.class)) cnt++;
        String name = "constant" + cnt;
        ConstantGate constantGate = new ConstantGate(name,1,1,false);
        logicCircuitMap.put(b, constantGate);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-23);
        l.setLayoutY(actionEvent.getY()+12);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("constantStyle");
            b.getStyleClass().add("constantStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("constantStyleHover");
            b.getStyleClass().add("constantStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                connecting = true;
                connectingElement = constantGate;
                connectingX = actionEvent.getX()+12;
                connectingY = actionEvent.getY()+1;
                drawOutlineForInput(b);
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawOutputGate(MouseEvent actionEvent) {
        Button b = new Button();
        b.setLayoutX(actionEvent.getX()-12);
        b.setLayoutY(actionEvent.getY()-12);
        b.setPrefSize(26,26);
        b.getStyleClass().add("outputStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(Output.class)) cnt++;
        String name = "output" + cnt;
        Output output = new Output(name);
        logicCircuitMap.put(b, output);

        Label l = new Label();
        l.setText(name);
        l.setLayoutX(actionEvent.getX()-22);
        l.setLayoutY(actionEvent.getY()+12);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("outputStyle");
            b.getStyleClass().add("outputStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("outputStyleHover");
            b.getStyleClass().add("outputStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(connecting) {
                    connecting = false;
                    paneId.getChildren().remove(rectangle);

                    Triples p = new Triples();
                    Line line = new Line();
                    boolean del = false;

                    for(Triples t: allConns) {
                        if(t.getFirst().equals(connectingElement)) {
                            del = true;
                            p = t;
                            paneId.getChildren().remove(t.getSecond());
                        }
                    }
                    if(del) allConns.remove(p);

                    for(Triples t: allConns) {
                        if(t.getThird().equals(logicCircuitMap.get(b))) {
                            paneId.getChildren().remove(t.getSecond());
                            p = t;
                            del = true;
                        }
                    }
                    if(del) allConns.remove(p);

                    if(connectingElement.getOutputs().get(0)) b.setText("1");
                    else b.setText("0");
                    line.setStartX(actionEvent.getX()-12);
                    line.setStartY(actionEvent.getY()+1);
                    line.setEndX(connectingX);
                    line.setEndY(connectingY);
                    line.setOpacity(1);
                    paneId.getChildren().add(line);

                    allConns.add(new Triples(connectingElement, line, logicCircuitMap.get(b)));
                    allLines.put(line, allLines.size());
                    allActions.add(line);
                    updateAll();
                    updateAll();
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawOutputGate(int x, int y) {
        Button b = new Button("Output");
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setPrefSize(80,45);
        b.getStyleClass().add("BBoutputStyle");

        int cnt = 1;
        for (LogicCircuit l: logicCircuitMap.values())
            if (l.getClass().equals(Output.class)) cnt++;
        String name = "output" + cnt;
        Output output = new Output(name);
        logicCircuitMap.put(b, output);

        Label l = new Label();
        l.setText("");
        l.setLayoutX(x);
        l.setLayoutY(y);
        paneId.getChildren().add(l);

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("BBoutputStyle");
            b.getStyleClass().add("BBoutputStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("BBoutputStyleHover");
            b.getStyleClass().add("BBoutputStyle");
        });
        allActions.add(b);
        buttonLabelMap.put(b,l);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(connecting) {
                    connecting = false;
                    paneId.getChildren().remove(rectangle);

                    Triples p = new Triples();
                    Line line = new Line();
                    boolean del = false;

                    for(Triples t: allConns) {
                        if(t.getFirst().equals(connectingElement)) {
                            del = true;
                            p = t;
                            paneId.getChildren().remove(t.getSecond());
                        }
                    }
                    if(del) allConns.remove(p);

                    for(Triples t: allConns) {
                        if(t.getThird().equals(logicCircuitMap.get(b))) {
                            paneId.getChildren().remove(t.getSecond());
                            p = t;
                            del = true;
                        }
                    }
                    if(del) allConns.remove(p);

                    if(connectingElement.getOutputs().get(0)) b.setText("Output");
                    else b.setText("Output");
                    line.setStartX(x+5);
                    line.setStartY(y+23);
                    line.setEndX(connectingX);
                    line.setEndY(connectingY);
                    line.setOpacity(1);
                    paneId.getChildren().add(line);

                    allConns.add(new Triples(connectingElement, line, logicCircuitMap.get(b)));
                    allLines.put(line, allLines.size());
                    allActions.add(line);
                    updateAll();
                    updateAll();
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void updateAll() {
        for(Triples t: allConns) {
            t.getFirst().operation(t.getFirst().getInputs());
            t.getThird().getInputs().add(t.getFirst().getOutputs().get(t.getFirst().getOutputs().size()-1));
            updateButtons(t.getThird());
        }
    }

    private void updateButtons(LogicCircuit l) {
        for(Button b: logicCircuitMap.keySet()) {
            if(logicCircuitMap.get(b).equals(l)) {
                if(l.getClass().equals(Output.class) && !l.getName().equals("output1")) {
                    System.out.println(l.getName());
                    if(l.getInputs().get(l.getInputs().size()-1)) b.setText("1");
                    else b.setText("0");
                }
            }
        }
    }


    private void drawOutlineForStandardGateNot(Button b) {
        rectangle.setX((int)b.getLayoutX()-10);
        rectangle.setY((int)b.getLayoutY()-22);
        rectangle.setWidth(90);
        rectangle.setHeight(90);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.GREY);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.getStrokeDashArray().addAll(5d,5d);
        paneId.getChildren().add(rectangle);
    }

    private void drawOutlineForBBInput(Button b) {
        rectangle.setX((int)b.getLayoutX()-5);
        rectangle.setY((int)b.getLayoutY()-22);
        rectangle.setWidth(90);
        rectangle.setHeight(90);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.GREY);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.getStrokeDashArray().addAll(5d,5d);
        paneId.getChildren().add(rectangle);
    }
    private void drawOutlineForBBOutput(Button b) {
        rectangle.setX((int)b.getLayoutX()-31);
        rectangle.setY((int)b.getLayoutY()-30);
        rectangle.setWidth(90);
        rectangle.setHeight(90);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.GREY);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.getStrokeDashArray().addAll(5d,5d);
        paneId.getChildren().add(rectangle);
    }

    private void drawOutlineForStandardGate(Button b) {
        rectangle.setX((int)b.getLayoutX()-12);
        rectangle.setY((int)b.getLayoutY()-24);
        rectangle.setWidth(90);
        rectangle.setHeight(90);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.GREY);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.getStrokeDashArray().addAll(5d,5d);
        paneId.getChildren().add(rectangle);
    }

    private void drawOutlineForInput(Button b) {
        rectangle.setX((int)b.getLayoutX()-17);
        rectangle.setY((int)b.getLayoutY()-12);
        rectangle.setWidth(66);
        rectangle.setHeight(66);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.GREY);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.getStrokeDashArray().addAll(5d,5d);
        paneId.getChildren().add(rectangle);
    }

    public void newAction(ActionEvent actionEvent) {
    }

    public void openAction(ActionEvent actionEvent) {
    }

    public void closeAction(ActionEvent actionEvent) {
    }

    public void closeAllAction(ActionEvent actionEvent) {
    }

    public void saveAction(ActionEvent actionEvent) {
    }

    public void saveAsAction(ActionEvent actionEvent) {
    }

    public void saveAllAction(ActionEvent actionEvent) {
    }

    public void printAction(ActionEvent actionEvent) {
    }

    public void exitAction(ActionEvent actionEvent) {
    }

    private HashMap<Button, LogicCircuit> logicCircuitMapDel = new HashMap<>();
    private ArrayList<Triples> allConnsDel = new ArrayList<>();
    private HashMap<Line, Integer> allLinesDel = new HashMap<>();
    private HashMap<Button, Label> buttonLabelMapDel = new HashMap<>();

    public void undoAction(ActionEvent actionEvent) {
        if(allActions.get(allActions.size()-1).getClass().equals(Button.class)) {
            Button b = (Button) allActions.get(allActions.size() - 1);
            paneId.getChildren().remove(b);
            paneId.getChildren().remove(buttonLabelMap.get(b));

            buttonLabelMapDel.put(b, buttonLabelMap.get(b));
            buttonLabelMap.remove(b);

            logicCircuitMapDel.put(b, logicCircuitMap.get(b));
            logicCircuitMap.remove(b);

            ArrayList<Triples> triplesToRemove = new ArrayList<>();
            for(Triples t: allConns) {
                if(t.getFirst().equals(logicCircuitMap.get(b)) || t.getThird().equals(logicCircuitMap.get(b))) {
                    triplesToRemove.add(t);
                    allLines.remove(t.getSecond());
                }
            }
            allConnsDel.addAll(triplesToRemove);
            allConns.removeAll(triplesToRemove);

            allDeletedActions.add(b);
            allActions.remove(b);

            updateAll(); updateAll();
        }
        else if(allActions.get(allActions.size()-1).getClass().equals(Line.class)) {
            Line l = (Line) allActions.get(allActions.size() - 1);
            paneId.getChildren().remove(l);

            allLinesDel.put(l,allLines.get(l));
            allLines.remove(l);

            Triples tripleToRemove = new Triples();
            for(Triples t: allConns) {
                if(t.getSecond().equals(l)) {
                    tripleToRemove = t;
                    break;
                }
            }
            allConnsDel.add(tripleToRemove);
            allConns.remove(tripleToRemove);

            allDeletedActions.add(l);
            allActions.remove(l);

            updateAll(); updateAll();
        }
    }

    public void redoAction(ActionEvent actionEvent) {
        if(allDeletedActions.get(allDeletedActions.size()-1).getClass().equals(Button.class)) {
            Button b = (Button) allDeletedActions.get(allDeletedActions.size() - 1);
            paneId.getChildren().add(b);
            paneId.getChildren().add(buttonLabelMapDel.get(b));

            buttonLabelMap.put(b, buttonLabelMapDel.get(b));
            buttonLabelMapDel.remove(b);

            logicCircuitMap.put(b, logicCircuitMapDel.get(b));
            logicCircuitMapDel.remove(b);

            ArrayList<Triples> triplesToRemove = new ArrayList<>();
            for(Triples t: allConnsDel) {
                if(t.getFirst().equals(logicCircuitMapDel.get(b)) || t.getThird().equals(logicCircuitMapDel.get(b))) {
                    triplesToRemove.add(t);
                    allLines.put(t.getSecond(), allLines.size());
                }
            }
            allConns.addAll(triplesToRemove);
            allConnsDel.removeAll(triplesToRemove);


            allActions.add(b);
            allDeletedActions.remove(b);
            updateAll(); updateAll();
        }
        else if(allDeletedActions.get(allDeletedActions.size()-1).getClass().equals(Line.class)) {
            Line l = (Line) allDeletedActions.get(allDeletedActions.size() - 1);
            paneId.getChildren().add(l);

            allLines.put(l, allLinesDel.get(l));
            allLinesDel.remove(l);

            Triples tripleToRemove = new Triples();
            for(Triples t: allConnsDel) {
                if(t.getSecond().equals(l)) {
                    tripleToRemove = t;
                    break;
                }
            }
            allConns.add(tripleToRemove);
            allConnsDel.remove(tripleToRemove);

            allActions.add(l);
            allDeletedActions.remove(l);
            updateAll(); updateAll();
        }
    }

    public void cutAction(ActionEvent actionEvent) {
    }

    public void copyAction(ActionEvent actionEvent) {
    }

    public void pasteAction(ActionEvent actionEvent) {
    }

    public void deleteAction(ActionEvent actionEvent) {
    }

    public void selectAction(ActionEvent actionEvent) {
    }

    public void propertiesAction(ActionEvent actionEvent) {
    }

    public void fullScreenAction(ActionEvent actionEvent) {
    }

    public void zoomInAction(ActionEvent actionEvent) {
    }

    public void zoomOutAction(ActionEvent actionEvent) {
    }

    public void zoomSheetAction(ActionEvent actionEvent) {
    }

    public void componentAction(ActionEvent actionEvent) {
    }

    public void textAction(ActionEvent actionEvent) {
    }

    public void probeAction(ActionEvent actionEvent) {
    }

    public void globalOptionsAction(ActionEvent actionEvent) {
    }

    public void projectOptionsAction(ActionEvent actionEvent) {
    }

    public void customizeAction(ActionEvent actionEvent) {
    }

    public void newWindowAction(ActionEvent actionEvent) {
    }

    public void closeWindowAction(ActionEvent actionEvent) {
    }

    public void closeAllWindowsAction(ActionEvent actionEvent) {
    }

    public void DCSHelpAction(ActionEvent actionEvent) {
    }

    public void aboutDSCAction(ActionEvent actionEvent) {
    }

    public void creditsAction(ActionEvent actionEvent) {
    }

    public void playAction(ActionEvent actionEvent) {
    }

    public void pauseAction(ActionEvent actionEvent) {
    }

    public void stopAction(ActionEvent actionEvent) {
    }

    private void setIconStyles() {
        newId.getStyleClass().add("newStyle");
        openId.getStyleClass().add("openStyle");
        saveId.getStyleClass().add("saveStyle");
        printId.getStyleClass().add("printStyle");
        undoId.getStyleClass().add("undoStyle");
        redoId.getStyleClass().add("redoStyle");
        componentsId.getStyleClass().add("componentsStyle");
        playId.getStyleClass().add("playStyle");
        pauseId.getStyleClass().add("pauseStyle");
        stopId.getStyleClass().add("stopStyle");
        optionsId.getStyleClass().add("optionsStyle");
        zoomInId.getStyleClass().add("zoomInStyle");
        zoomOutId.getStyleClass().add("zoomOutStyle");
        zoomSheetId.getStyleClass().add("zoomSheetStyle");

        newId.setOnMouseEntered(e-> {
            newId.getStyleClass().remove("newStyle");
            newId.getStyleClass().add("newStyleHover");
        });
        newId.setOnMouseExited(e-> {
            newId.getStyleClass().remove("newStyleHover");
            newId.getStyleClass().add("newStyle");
        });

        openId.setOnMouseEntered(e-> {
            openId.getStyleClass().remove("openStyle");
            openId.getStyleClass().add("openStyleHover");
        });
        openId.setOnMouseExited(e-> {
            openId.getStyleClass().remove("openStyleHover");
            openId.getStyleClass().add("openStyle");
        });

        saveId.setOnMouseEntered(e-> {
            saveId.getStyleClass().remove("saveStyle");
            saveId.getStyleClass().add("saveStyleHover");
        });
        saveId.setOnMouseExited(e-> {
            saveId.getStyleClass().remove("saveStyleHover");
            saveId.getStyleClass().add("saveStyle");
        });

        printId.setOnMouseEntered(e-> {
            printId.getStyleClass().remove("printStyle");
            printId.getStyleClass().add("printStyleHover");
        });
        printId.setOnMouseExited(e-> {
            printId.getStyleClass().remove("printStyleHover");
            printId.getStyleClass().add("printStyle");
        });

        undoId.setOnMouseEntered(e-> {
            undoId.getStyleClass().remove("undoStyle");
            undoId.getStyleClass().add("undoStyleHover");
        });
        undoId.setOnMouseExited(e-> {
            undoId.getStyleClass().remove("undoStyleHover");
            undoId.getStyleClass().add("undoStyle");
        });

        redoId.setOnMouseEntered(e-> {
            redoId.getStyleClass().remove("redoStyle");
            redoId.getStyleClass().add("redoStyleHover");
        });
        redoId.setOnMouseExited(e-> {
            redoId.getStyleClass().remove("redoStyleHover");
            redoId.getStyleClass().add("redoStyle");
        });

        componentsId.setOnMouseEntered(e-> {
            componentsId.getStyleClass().remove("componentsStyle");
            componentsId.getStyleClass().add("componentsStyleHover");
        });
        componentsId.setOnMouseExited(e-> {
            componentsId.getStyleClass().remove("componentsStyleHover");
            componentsId.getStyleClass().add("componentsStyle");
        });

        playId.setOnMouseEntered(e-> {
            playId.getStyleClass().remove("playStyle");
            playId.getStyleClass().add("playStyleHover");
        });
        playId.setOnMouseExited(e-> {
            playId.getStyleClass().remove("playStyleHover");
            playId.getStyleClass().add("playStyle");
        });

        pauseId.setOnMouseEntered(e-> {
            pauseId.getStyleClass().remove("pauseStyle");
            pauseId.getStyleClass().add("pauseStyleHover");
        });
        pauseId.setOnMouseExited(e-> {
            pauseId.getStyleClass().remove("pauseStyleHover");
            pauseId.getStyleClass().add("pauseStyle");
        });

        stopId.setOnMouseEntered(e-> {
            stopId.getStyleClass().remove("stopStyle");
            stopId.getStyleClass().add("stopStyleHover");
        });
        stopId.setOnMouseExited(e-> {
            stopId.getStyleClass().remove("stopStyleHover");
            stopId.getStyleClass().add("stopStyle");
        });

        optionsId.setOnMouseEntered(e-> {
            optionsId.getStyleClass().remove("optionsStyle");
            optionsId.getStyleClass().add("optionsStyleHover");
        });
        optionsId.setOnMouseExited(e-> {
            optionsId.getStyleClass().remove("optionsStyleHover");
            optionsId.getStyleClass().add("optionsStyle");
        });

        zoomInId.setOnMouseEntered(e-> {
            zoomInId.getStyleClass().remove("zoomInStyle");
            zoomInId.getStyleClass().add("zoomInStyleHover");
        });
        zoomInId.setOnMouseExited(e-> {
            zoomInId.getStyleClass().remove("zoomInStyleHover");
            zoomInId.getStyleClass().add("zoomInStyle");
        });

        zoomOutId.setOnMouseEntered(e-> {
            zoomOutId.getStyleClass().remove("zoomOutStyle");
            zoomOutId.getStyleClass().add("zoomOutStyleHover");
        });
        zoomOutId.setOnMouseExited(e-> {
            zoomOutId.getStyleClass().remove("zoomOutStyleHover");
            zoomOutId.getStyleClass().add("zoomOutStyle");
        });

        zoomSheetId.setOnMouseEntered(e-> {
            zoomSheetId.getStyleClass().remove("zoomSheetStyle");
            zoomSheetId.getStyleClass().add("zoomSheetStyleHover");
        });
        zoomSheetId.setOnMouseExited(e-> {
            zoomSheetId.getStyleClass().remove("zoomSheetStyleHover");
            zoomSheetId.getStyleClass().add("zoomSheetStyle");
        });
    }

    private void setSeparatorStyles() {
        separator1.getStyleClass().add("separatorStyle");
        separator2.getStyleClass().add("separatorStyle");
        separator3.getStyleClass().add("separatorStyle");
        separator4.getStyleClass().add("separatorStyle");
        separator5.getStyleClass().add("separatorStyle");
        separator6.getStyleClass().add("separatorStyle");
        separator7.getStyleClass().add("separatorStyle");
        separator8.getStyleClass().add("separatorStyle");

    }
}
