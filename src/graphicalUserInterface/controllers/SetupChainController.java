package graphicalUserInterface.controllers;

import fileHandler.FileHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SetupChainController implements Initializable {

    @FXML
    private TextField jarField;
    private String jarPath;

    @FXML
    private TextField dataField;
    private String dataPath;

    private ArrayList<String> classNames;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classNames = new ArrayList<>();
    }

    


    /**
     * Function browseJAR()
     * <p>
     *     Opens a FileChooser window and sets the value of the jarField to the selected file path
     * </p>
     */
    @FXML
    private void browseJar() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JAR...");
        jarField.setText(fileChooser.showOpenDialog(stage).toString());
        FileHandler fh = new FileHandler();
    }

    /**
     * Function browseData()
     * <p>
     *     Opens a FileChooser and sets the dataField to the chosen file path
     * </p>
     */
    @FXML
    private void browseData() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Data...");
        dataField.setText(fileChooser.showOpenDialog(stage).toString());
    }
}
