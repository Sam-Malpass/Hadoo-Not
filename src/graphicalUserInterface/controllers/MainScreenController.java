/**
 * MainScreenController
 * @author Sam Malpass
 * @version 0.0.6
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

    /**
     * console holds the TextArea for the console
     */
    @FXML
    private TextArea console;

    /**
     * ps holds the PrintStream object
     */
    private PrintStream ps;

    /**
     * setup says whether a job has been setup
     */
    private static boolean setup = false;

    /**
     * jobParameters holds a list of parameter Strings
     */
    private static ArrayList<String> jobParameters;

    /**
     * Function initialize()
     * <p>
     *     Initialize the controller
     * </p>
     * @param url is the file path
     * @param resourceBundle is the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        console.setEditable(false);
        ps = new PrintStream(new Console(console));
        System.setErr(ps);
        System.setOut(ps);
    }

    /**
     * Function setupJob()
     * <p>
     *     Opens the dialog for the setup job and uses the result(s) to gather parameters
     * </p>
     */
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
            System.out.println("[SYSTEM] Job parameters setup up");
        }
    }

    /**
     * Function run()
     * <p>
     *     Checks if there is a job set up and if there is, run it
     * </p>
     */
    @FXML
    private void run() {
        if(setup) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Process p = new Process(10, jobParameters.get(0), jobParameters.get(1).replace(".class", ""));
                    p.start(jobParameters.get(2), jobParameters.get(3));
                }
            });
            System.out.println("[SYSTEM] Beginning Job...");
            thread.start();
        }
        else {
            System.err.println("[ERROR] Job not setup");
        }
    }

    /**
     * Function about()
     * <p>
     *     Creates an alert with the version number and author
     * </p>
     */
    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Hadoo-Not");
        alert.setContentText("Version 0.0.5 by Sam Malpass");
        alert.setTitle("About");
        alert.showAndWait();
    }

    /**
     * Function help()
     * <p>
     *
     * </p>
     */
    @FXML
    private void help() {

    }

    /**
     * Function openOutput()
     * <p>
     *     Opens the output folder in the file explorer
     * </p>
     */
    @FXML
    private void openOutput() {
        try {
            Runtime.getRuntime().exec("explorer.exe /open," + "Output");
        }
        catch (Exception e) {
            System.err.println("[ERROR] Couldn't open the folder");
        }
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
