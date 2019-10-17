/**
 * MainScreenController
 * @author Sam Malpass
 * @version 0.0.3
 * @since 0.0.3
 */
package graphicalUserInterface.controllers;

import application.Process;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    private TextArea console;
    private PrintStream ps;
    private static boolean setup = false;
    private static ArrayList<String> jobParameters;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        console.setEditable(false);
        ps = new PrintStream(new Console(console));
        System.setErr(ps);
        System.setOut(ps);
    }

    @FXML
    private void setupJob() {
        Scene tmp = null;
        Stage setupStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/SetupWindow.fxml"));
            tmp = new Scene(root, 640, 360);
        }
        catch(Exception e) {
            System.err.println("[ERROR] Issue opening SetupWindow.fxml");
        }
        setupStage.setScene(tmp);
        setupStage.setResizable(false);
        setupStage.setTitle("Setup Job...");
        setupStage.showAndWait();
        setup = SetupWindowController.getSetup();
        if(setup) {
            jobParameters = new ArrayList<>();
            jobParameters.add(SetupWindowController.getJar());
            jobParameters.add(SetupWindowController.getClassName());
            jobParameters.add(SetupWindowController.getData());
            jobParameters.add(SetupWindowController.getOutput());
            writeConsole("[SYSTEM] Job parameters setup up");
        }
    }

    @FXML
    private void run() {
        if(setup) {
            Process p = new Process(10, jobParameters.get(0), jobParameters.get(1).replace(".class", ""));
            p.start(jobParameters.get(2), jobParameters.get(3));
            writeConsole("[SYSTEM] Beginning Job...");
        }
        else {
            System.err.println("[ERROR] Job not setup");
        }
    }

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Hadoo-Not");
        alert.setContentText("Version 0.0.3 by Sam Malpass");
        alert.setTitle("About");
        alert.showAndWait();
    }

    @FXML
    private void help() {

    }

    @FXML
    private void openOutput() {
        try {
            Runtime.getRuntime().exec("explorer.exe /open," + "Output");
        }
        catch (Exception e) {
            System.err.println("[ERROR] Couldn't open the folder");
        }
    }


    public void writeConsole(String text) {
        console.appendText(text + "\n");
    }

    public void clearConsole() {
        console.clear();
    }
    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }

}
