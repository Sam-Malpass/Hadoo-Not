/**
 * Console
 * @author James_D (https://stackoverflow.com/questions/33494052/javafx-redirect-console-output-to-textarea-that-is-created-in-scenebuilder?rq=1)
 * @version 0.0.7
 * @since 0.0.7
 */
package graphicalUserInterface;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.io.OutputStream;

public class Console extends OutputStream {
    /**
     * console holds the TextArea to be output to
     */
    private TextArea console;

    /**
     * Constructor with arguments
     * <p>
     *     Sets the console to the passed TextArea
     * </p>
     * @param console is the TextArea to use
     */
    public Console(TextArea console) {
        this.console = console;
    }

    /**
     * Function appendText()
     * <p>
     *     Adds the string the to console
     * </p>
     * @param valueOf is the String to append
     */
    public void appendText(String valueOf) {
        Platform.runLater(() -> console.appendText(valueOf));
    }

    /**
     * Function write()
     * <p>
     *     Appends the text if the text is an int
     * </p>
     * @param b is the int to be appended
     * @throws IOException
     */
    public void write(int b) throws IOException {
        appendText(String.valueOf((char)b));
    }
}
