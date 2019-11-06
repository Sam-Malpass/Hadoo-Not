/**
 * SetupChainController
 * @author Sam Malpass
 * @version 0.0.9
 * @since 0.0.8
 */
package graphicalUserInterface.controllers;

import fileHandler.FileHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SetupChainController implements Initializable {

    /**
     * jarField is the TextField for JAR path
     */
    @FXML
    private TextField jarField;

    /**
     * jarPath holds the file path to the JAR as a String
     */
    private static String jarPath;

    /**
     * dataField is a TextField to enter the file path for the data file
     */
    @FXML
    private TextField dataField;

    /**
     * dataPath holds the file path to the data file as a String
     */
    private static String dataPath;

    /**
     * outputField is a TextField to hold the name of the output file
     */
    @FXML
    private TextField outputField;

    /**
     * outputName holds the name of the output file as a String
     */
    private static String outputName;

    /**
     * listView holds a list of all the jobs to run in the chain
     */
    @FXML
    private ListView listView;

    /**
     * comboBox holds a ComboBox for .class files in the chosen JAR file
     */
    @FXML
    private ComboBox comboBox;

    /**
     * classNames holds a list of all the jobs to be put in the chain
     */
    private static ArrayList<String> classNames;

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
        classNames = new ArrayList<>();
    }

    /**
     * Function cancel()
     * <p>
     *     Cancels the setup and closes the dialog
     * </p>
     */
    @FXML
    private void cancel() {
        classNames = null;
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
        int tmp =0;
        if(!jarField.getText().isEmpty() && jarField.getText().endsWith(".jar")) {
            jarPath = jarField.getText();
            tmp++;
        }
        else {
            jarPath = null;
        }
        if(!dataField.getText().isEmpty()) {
            dataPath = dataField.getText();
            tmp++;
        }
        else {
            dataPath = null;
        }
        if(!outputField.getText().isEmpty()) {
            outputName = outputField.getText();
        }
        else {
            outputName = null;
        }
        if(listView.getItems().size() > 0) {
            for(Object o : listView.getItems()) {
                classNames.add(o.toString().replace(".class", ""));
            }
            tmp++;
        }
        else {
            classNames = null;
            System.err.println("[ERROR] Field(s) filled incorrectly");
        }
        if(tmp == 3) {
            Stage tmpS = (Stage) jarField.getScene().getWindow();
            tmpS.close();
        }
    }

    /**
     * Function addJob()
     * <p>
     *     Adds a selected Job from the comboBox to the list of jobs to be run
     * </p>
     */
    @FXML
    private void addJob() {
        String tmp = comboBox.getValue().toString();
        if(!tmp.isEmpty()) {
            listView.getItems().add(tmp);
        }
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
        comboBox.setItems(FXCollections.observableList(fh.listClasses(jarField.getText())));
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
     * Function getJarPath()
     * <p>
     *     Returns the jarPath
     * </p>
     * @return jarPath
     */
    public static String getJarPath() {
        return jarPath;
    }

    /**
     * Function getDataPath()
     * <p>
     *     Returns the dataPath
     * </p>
     * @return dataPath
     */
    public static String getDataPath() {
        return dataPath;
    }

    /**
     * Function getClassNames()
     * <p>
     *     Return classNames
     * </p>
     * @return classNames
     */
    public static ArrayList<String> getClassNames() {
        return classNames;
    }

    /**
     * Function getOutputName()
     * <p>
     *     Return the outputName
     * </p>
     * @return outputName
     */
    public static String getOutputName() {
        return outputName;
    }
}
