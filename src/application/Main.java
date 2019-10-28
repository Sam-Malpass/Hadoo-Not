/**
 * Main
 * @author Sam Malpass
 * @version 0.0.6
 * @since 0.0.0
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    /**
     * Function start()
     * <p>
     *     Starts the application
     * </p>
     * @param stage is the window to use
     * @throws Exception if there is a failure
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = FXMLLoader.load(getClass().getResource("../graphicalUserInterface/FXML/MainScreen.fxml"));
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
