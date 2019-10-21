package graphicalUserInterface.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mapReduce.EZJob;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EZSetupController implements Initializable {
    @FXML
    private TextField dataField;
    private String data;
    @FXML
    private TableView tableView;
    @FXML
    private TextField keyField;
    private int keyIndex;
    @FXML
    private TextField valueField;
    private ArrayList<Integer> values;
    @FXML
    private ComboBox selectorBox;
    private String job;

    private EZJob setup = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> jobs = new ArrayList<>();
        jobs.add("Count");
        jobs.add("Total");
        jobs.add("List");
        selectorBox.setItems(FXCollections.observableList(jobs));
        setup = null;
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
        setup = null;
        Stage tmp = (Stage) dataField.getScene().getWindow();
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
        int mode = 0;
        if(dataField.getText().endsWith(".csv")) {
            data = dataField.getText();
        }
        if(!selectorBox.getValue().toString().equals("Select Job Type...") || !selectorBox.getValue().toString().equals(null) || !selectorBox.getValue().toString().isEmpty()) {
            job = selectorBox.getValue().toString();
            if(job.equals("Count")) {
                mode = 0;
            }
            else if(job.equals("Total")) {
                mode = 1;
            }
            else {
                mode = 2;
            }
        }
        if(!keyField.getText().isEmpty()) {
            keyIndex = Integer.parseInt(keyField.getText());
        }
        if(!valueField.getText().isEmpty()) {
            values = new ArrayList<>();
            String s = valueField.getText();
            ArrayList<String> row = new ArrayList<>(Arrays.asList(s.split(",")));
            for(String tmp : row) {
                values.add(Integer.parseInt(tmp));
            }
        }
        /*
        if(!outputName.getText().isEmpty()) {
            output = outputName.getText();
        }
        */
        if(data != null && job != null && !(keyIndex < 0) && values != null) {
            setup = new EZJob();
            setup.ezSetup(keyIndex, values, mode);
            Stage tmp = (Stage) selectorBox.getScene().getWindow();
            tmp.close();
        }
        else {
            System.err.println("[ERROR] One or more fields filled incorrectly");
        }
    }

    public EZJob getSetup() {
        return setup;
    }
}
