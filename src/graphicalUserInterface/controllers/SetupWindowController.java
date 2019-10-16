/**
 * SetupWindowController
 * @author Sam Malpass
 * @version 0.0.3
 * @since 0.0.3
 */
package graphicalUserInterface.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupWindowController implements Initializable {
    @FXML
    private TextField jarField;
    @FXML
    private ComboBox comboClass;
    @FXML
    private TextField dataField;
    @FXML
    private TextField outputName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public String getJar() {
        return jarField.getText();
    }

    public String getData() {
        return dataField.getText();
    }

    public String getClassName() {
        return comboClass.getValue().toString();
    }

    public String getOutput() {
        return outputName.getText();
    }

    @FXML
    private String browse() {
       return null;
    }
}
