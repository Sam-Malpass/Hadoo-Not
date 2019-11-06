/**
 * SetupWindowController
 * @author Sam Malpass
 * @version 0.0.9
 * @since 0.0.3
 */
package graphicalUserInterface.controllers;

import fileHandler.FileHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupJobController implements Initializable {

    /**
     * jarField is the TextField for JAR path
     */
    @FXML
    private TextField jarField;

    /**
     * jar holds the file path to the JAR as a String
     */
    private static String jar;

    /**
     * comboClass holds a ComboBox for .class files in the chosen JAR file
     */
    @FXML
    private ComboBox comboClass;

    /**
     * className holds the name of the .class file to use as a String
     */
    private static String className;

    /**
     * dataField is a TextField to enter the file path for the data file
     */
    @FXML
    private TextField dataField;

    /**
     * dataFile holds the file path to the data file as a String
     */
    private static String dataFile;

    /**
     * outputName is a TextField to hold the name of the output file
     */
    @FXML
    private TextField outputName;

    /**
     * output holds the name of the output file as a String
     */
    private static String output;

    /**
     * setup tells us whether the setup process is complete
     */
    private static boolean setup = false;

    /**
     * Function initialize()
     * <p>
     *     Sets the Strings to null
     * </p>
     * @param url is the URL
     * @param resourceBundle is the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jar = null;
        className = null;
        dataFile = null;
        output = null;
        return;
    }

    /**
     * Function getJar()
     * <p>
     *     Returns the jar
     * </p>
     * @return the path to the JAR
     */
    public static String getJar() {
        return jar;
    }

    /**
     * Function getData()
     * <p>
     *     Return the dataFile
     * </p>
     * @return the path to the data
     */
    public static String getData() {
        return dataFile;
    }

    /**
     * Function getClassName()
     * <p>
     *     Return the className
     * </p>
     * @return the name of the class
     */
    public static String getClassName() {
        return className;
    }

    /**
     * Function getOutput()
     * <p>
     *     Return the output
     * </p>
     * @return the name of the output
     */
    public static String getOutput() {
        return output;
    }

    /**
     * Function getSetup()
     * <p>
     *     Return the setup
     * </p>
     * @return the value of setup
     */
    public static boolean getSetup() {
        return setup;
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
        comboClass.setItems(FXCollections.observableList(fh.listClasses(jarField.getText())));
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

    /**
     * Function cancel()
     * <p>
     *     Cancels the setup and closes the dialog
     * </p>
     */
    @FXML
    private void cancel() {
        setup = false;
        Stage tmp = (Stage) jarField.getScene().getWindow();
        tmp.close();
    }

    /**
     * Function complete()
     * <p>
     *     Checks the fields' validity and either sets up the job parameters or throws an error
     * </p>
     */
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
