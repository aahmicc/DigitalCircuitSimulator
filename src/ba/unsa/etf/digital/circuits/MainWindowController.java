package ba.unsa.etf.digital.circuits;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public Button newId, openId, saveId, printId, undoId, redoId, componentsId;
    public Button pauseId, zoomOutId, zoomSheetId, playId, stopId, optionsId, zoomInId;
    public Separator separator1,separator2,separator3,separator4,separator5,separator6,separator7,separator8;
    public ChoiceBox<LogicCircuit> recentlyUsedChoice;
    public Pane paneId;

    private boolean connecting = false;
    private LogicCircuit connectingElement = null;
    private double connectingX, connectingY;
    private Map<Button, LogicCircuit> logicCircuitMap = new HashMap<>();

    public LogicCircuit currentlyPickedLC;

    public MainWindowController() {
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
    }

    private ArrayList<LogicCircuit> createStandardGates() {
        ArrayList<LogicCircuit> retValues = new ArrayList<>();

        None n = new None();
        retValues.add(n);

        ConstantGate conGate = new ConstantGate("Constant Input", 0, 1, null, true);
        retValues.add(conGate);

        Output output = new Output("Output");
        retValues.add(output);

        ArrayList<Boolean> notArray = new ArrayList<>();
        notArray.add(false);
        NotGate not = new NotGate("Not1 [Standard]",1,1, notArray);
        retValues.add(not);

        return retValues;
    }

    public void drawAction(MouseEvent actionEvent) {
        if(currentlyPickedLC != null) {
            if(currentlyPickedLC.getClass().equals(NotGate.class)) drawStandardNotGate(actionEvent);
            else if(currentlyPickedLC.getClass().equals(ConstantGate.class)) drawConstantGate(actionEvent);
            else if(currentlyPickedLC.getClass().equals(Output.class)) drawOutputGate(actionEvent);
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

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("standardNotStyle");
            b.getStyleClass().add("standardNotStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("standardNotStyleHover");
            b.getStyleClass().add("standardNotStyle");
        });
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(connecting) {
                    if(connectingElement != logicCircuitMap.get(b)) {
                        logicCircuitMap.get(b).setInputs(connectingElement.getOutputs());
                        connecting = false;
                        logicCircuitMap.get(b).operation(logicCircuitMap.get(b).getInputs());

                        Line line = new Line();
                        line.setStartX(actionEvent.getX()-35);
                        line.setStartY(actionEvent.getY()+1);
                        line.setEndX(connectingX);
                        line.setEndY(connectingY);
                        line.setOpacity(0.75);
                        paneId.getChildren().add(line);
                    }
                } else {
                    connecting = true;
                    connectingElement = notGate;
                    connectingX = actionEvent.getX()+35;
                    connectingY = actionEvent.getY()+1;
                }
            }
        });
        paneId.getChildren().add(b);
    }

    private void drawConstantGate(MouseEvent actionEvent) {
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

        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("constantStyle");
            b.getStyleClass().add("constantStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("constantStyleHover");
            b.getStyleClass().add("constantStyle");
        });
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                connecting = true;
                connectingElement = constantGate;
                connectingX = actionEvent.getX()+12;
                connectingY = actionEvent.getY()+1;
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
        b.setOnMouseEntered(e-> {
            b.getStyleClass().remove("outputStyle");
            b.getStyleClass().add("outputStyleHover");
        });
        b.setOnMouseExited(e-> {
            b.getStyleClass().remove("outputStyleHover");
            b.getStyleClass().add("outputStyle");
        });
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(connecting) {
                    connecting = false;
                    if(connectingElement.getOutputs().get(0)) b.setText("1");
                    else b.setText("0");
                    Line line = new Line();
                    line.setStartX(actionEvent.getX()-12);
                    line.setStartY(actionEvent.getY()+1);
                    line.setEndX(connectingX);
                    line.setEndY(connectingY);
                    line.setOpacity(1);
                    paneId.getChildren().add(line);
                }
            }
        });
        paneId.getChildren().add(b);
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

    public void undoAction(ActionEvent actionEvent) {
    }

    public void redoAction(ActionEvent actionEvent) {
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
