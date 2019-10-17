/**
 * SetupWindowController
 * @author Sam Malpass
 * @version 0.0.3
 * @since 0.0.3
 */
package graphicalUserInterface.controllers;

import fileHandler.FileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SetupWindowController implements Initializable {
    @FXML
    private TextField jarField;
    private static String jar;
    @FXML
    private ComboBox comboClass;
    private static String className;
    @FXML
    private TextField dataField;
    private static String dataFile;
    @FXML
    private TextField outputName;
    private static String output;

    private static boolean setup = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jar = null;
        className = null;
        dataFile = null;
        output = null;
        return;
    }

    public static String getJar() {
        return jar;
    }

    public static String getData() {
        return dataFile;
    }

    public static String getClassName() {
        return className;
    }

    public static String getOutput() {
        return output;
    }

    public static boolean getSetup() {
        return setup;
    }

    @FXML
    private void browseJar() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JAR...");
        jarField.setText(fileChooser.showOpenDialog(stage).toString());
        FileHandler fh = new FileHandler();
        comboClass.setItems(FXCollections.observableList(fh.listClasses(jarField.getText())));
    }

    @FXML
    private void browseData() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Data...");
        dataField.setText(fileChooser.showOpenDialog(stage).toString());
    }

    @FXML
    private void cancel() {
        setup = false;
        Stage tmp = (Stage) jarField.getScene().getWindow();
        tmp.close();
    }

    @FXML
    private void complete() {
        if(jarField.getText().endsWith(".jar")) {
            jar = jarField.getText();
        }
        if(!comboClass.getValue().toString().equals("Select Class...") || !comboClass.getValue().toString().equals(null) || !comboClass.getValue().toString().isEmpty()) {
            className = comboClass.getValue().toString();
        }
        if(!dataField.getText().isEmpty()) {
            dataFile = dataField.getText();
        }
        if(!outputName.getText().isEmpty()) {
            output = outputName.getText();
        }
        if(jar != null && className != null && dataFile != null && output != null) {
            setup = true;
            Stage tmp = (Stage) comboClass.getScene().getWindow();
            tmp.close();
        }
        else {
            System.err.println("[ERROR] One or more fields filled incorrectly");
        }
    }
}
