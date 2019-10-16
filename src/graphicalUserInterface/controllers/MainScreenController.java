/**
 * MainScreenController
 * @author Sam Malpass
 * @version 0.0.3
 * @since 0.0.3
 */
package graphicalUserInterface.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    private TextArea console;

    private boolean setup = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        console.setEditable(false);
    }

    @FXML
    private void setupJob() {
        setup = true;
    }

    @FXML
    private void run() {

    }

    @FXML
    private void about() {

    }

    @FXML
    private void help() {

    }

    @FXML
    private void openOutput() {

    }

    public TextArea getConsole() {
        return console;
    }

    public void writeConsole(String text) {
        console.appendText(text);
    }

    public void clearConsole() {
        console.clear();
    }

}
