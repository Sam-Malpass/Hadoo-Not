/**
 * FileHanlder
 * @author Sam Malpass
 * @version 0.0.0
 * @since 0.0.0
 */
package fileHandler;

public class FileHandler {

    /**
     * input holds the FileInput object
     */
    private FileInput input;

    /**
     * output holds the FileOutput object
     */
    private FileOutput output;

    /**
     * Constructor with no arguments
     * <p>
     *     Sets up the object
     * </p>
     */
    public FileHandler() {
        this.input = new FileInput();
        this.output = new FileOutput();
    }
}
