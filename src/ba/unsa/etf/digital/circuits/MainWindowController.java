package ba.unsa.etf.digital.circuits;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public Button newId;
    public Button openId;
    public Button saveId;
    public Button printId;
    public Button undoId;
    public Button redoId;
    public Button componentsId;
    public Button playId;
    public Button pauseId;
    public Button stopId;
    public Button optionsId;
    public Button zoomInId;
    public Button zoomOutId;
    public Button zoomSheetId;

    public Separator separator1,separator2,separator3,separator4,separator5,separator6,separator7,separator8;

    public MainWindowController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIconStyles();
        setSeparatorStyles();
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
