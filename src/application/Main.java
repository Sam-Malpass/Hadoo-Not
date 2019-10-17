/**
 * Main
 * @author Sam Malpass
 * @version 0.0.3
 * @since 0.0.0
 */
package application;

import graphicalUserInterface.controllers.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{

    /**
     * windowHeight holds the height of the window
     */
    private double windowHeight = 720;

    /**
     * windowWidth holds the width of the window
     */
    private double windowWidth = 1280;

    /**
     * mainStage holds the Stage for the application
     */
    private Stage mainStage;

    /**
     * mainScene holds the Scene for the application
     */
    private Scene mainScene;

    /**
     * Function setup()
     * <p>
     *     Sets up the actual window
     * </p>
     */
    private void setup() {
        this.mainStage.setTitle("Hadoo-Not");
        this.mainStage.setScene(this.mainScene);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("../graphicalUserInterface/FXML/MainScreen.fxml"));
            mainScene = new Scene(root, windowWidth, windowHeight);
        }
        catch (Exception e) {
            System.out.println("[ERROR] Could not access MainScreen.fxml");
        }
        setup();
        this.mainStage.setResizable(false);
        this.mainStage.show();
    }

}
