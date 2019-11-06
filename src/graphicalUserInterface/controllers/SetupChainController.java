package graphicalUserInterface.controllers;

import fileHandler.FileHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
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
    private static String jarPath;

    @FXML
    private TextField dataField;
    private static String dataPath;

    @FXML
    private ListView listView;

    @FXML
    private ComboBox comboBox;

    private static ArrayList<String> classNames;

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

    public static String getJarPath() {
        return jarPath;
    }

    public static String getDataPath() {
        return dataPath;
    }

    public static ArrayList<String> getClassNames() {
        return classNames;
    }
}
