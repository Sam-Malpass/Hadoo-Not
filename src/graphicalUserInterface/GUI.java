/**
 * GUI
 * @author Sam Malpass
 * @version 0.0.3
 * @since 0.0.3
 */
package graphicalUserInterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI  {

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
     * Constructor with arguments
     * <p>
     *     Sets ip the object
     * </p>
     * @param stage is the Stage to use
     */
    public GUI(Stage stage) {
        this.mainStage = stage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/MainScreen.fxml"));
            mainScene = new Scene(root, windowWidth, windowHeight);
        }
        catch (Exception e) {
            System.out.println("[ERROR] Could not access MainScreen.fxml");
        }
        setup();
    }

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
}
