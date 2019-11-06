/**
 * MainScreenController
 * @author Sam Malpass
 * @version 0.0.8
 * @since 0.0.3
 */
package graphicalUserInterface.controllers;

import application.Chain;
import application.Process;
import graphicalUserInterface.Console;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
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
    private static ArrayList<String> jobParameters = new ArrayList<>();

    private static Chain chain = null;

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
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/SetupJob.fxml"));
            tmp = new Scene(root, 640, 360);
        }
        catch(Exception e) {
            System.err.println("[ERROR] Issue opening SetupJob.fxml");
        }
        setupStage.setScene(tmp);
        setupStage.setResizable(false);
        setupStage.setTitle("Setup Job...");
        setupStage.showAndWait();
        setup = SetupJobController.getSetup();
        if(setup) {
            jobParameters = new ArrayList<>();
            jobParameters.add(SetupJobController.getJar());
            jobParameters.add(SetupJobController.getClassName());
            jobParameters.add(SetupJobController.getData());
            jobParameters.add(SetupJobController.getOutput());
            System.out.println("[SYSTEM] Job parameters setup");
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
        if(setup && jobParameters.size() > 0) {
            Thread processThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    long startTime = System.nanoTime();
                    Process p = new Process(jobParameters.get(0), jobParameters.get(1).replace(".class", ""));
                    p.setup(jobParameters.get(2), jobParameters.get(3));
                    p.start(0);
                    jobParameters = new ArrayList<>();
                    long endTime = System.nanoTime();
                    System.out.println("[SYSTEM] Job execution completed in " + (endTime - startTime) / 1000000 + "ms");
                }
            });
            System.out.println("[SYSTEM] Beginning Job...");
            processThread.start();
        }
        else if(setup && !(chain == null)) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    long startTime = System.nanoTime();
                    chain.execute();
                    long endTime = System.nanoTime();
                    System.out.println("[SYSTEM] Job execution completed in " + (endTime - startTime) / 1000000 + "ms");
                }
            });
            System.out.println("[SYSTEM] Beginning Chain...");
            thread.start();
        }
        else if(setup && jobParameters.size() == 0) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    long startTime = System.nanoTime();
                    Process p = new Process(EZSetupController.getSetup());
                    String filePath = EZSetupController.getData();
                    p.setup(filePath, "TEST.txt");
                    p.start(0);
                    long endTime = System.nanoTime();
                    System.out.println("[SYSTEM] Job execution completed in " + (endTime - startTime) / 1000000 + "ms");
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
        ArrayList<String> jobNames = new ArrayList<>();
        jobNames.add("testChain1");
        jobNames.add("testChain2");
        Chain chain = new Chain("C:/Users/Sam/IdeaProjects/Cloud-Computing-Tasks/out/artifacts/Cloud_Computing_Tasks_jar/Cloud-Computing-Tasks.jar", jobNames);
        chain.setupChain("C:/Users/Sam/Desktop/CS3AC18_Coursework/Data/AComp_Passenger_data(2).csv", "TESTCHAIN.txt");
        chain.execute();
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

    /**
     * Function ezSetup()
     * <p>
     *     Opens the EZSetup window
     * </p>
     */
    @FXML
    private void ezSetup() {
        Scene tmp = null;
        Stage setupStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/EZSetup.fxml"));
            tmp = new Scene(root, 640, 360);
        }
        catch(Exception e) {
            System.err.println("[ERROR] Issue opening EZSetup.fxml");
        }
        setupStage.setScene(tmp);
        setupStage.setResizable(false);
        setupStage.setTitle("Setup Job...");
        setupStage.showAndWait();
        if(EZSetupController.getSetup() != null) {
            setup = true;
            System.out.print("[SYSTEM] Job Parameters Setup");
        }
    }

    @FXML
    private void setupChain() {
        Scene tmp = null;
        Stage setupStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/SetupChain.fxml"));
            tmp = new Scene(root, 640, 360);
        }
        catch(Exception e) {
            System.err.println("[ERROR] Issue opening SetupChain.fxml");
        }
        setupStage.setScene(tmp);
        setupStage.setResizable(false);
        setupStage.setTitle("Setup Chain...");
        setupStage.showAndWait();
        if(!(SetupChainController.getJarPath() == null) && !(SetupChainController.getDataPath() == null) && !(SetupChainController.getClassNames() == null)) {
            chain = new Chain(SetupChainController.getJarPath(), SetupChainController.getClassNames());
            chain.setupChain(SetupChainController.getDataPath(), "TESTOut.txt");
            setup = true;
        }
    }
}
