/**
 * FileHandler
 * @author Sam Malpass
 * @version 0.0.3
 * @since 0.0.0
 */
package fileHandler;

import java.util.ArrayList;

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

    /**
     * Function read()
     * <p>
     *     Reads and returns a given file's data as an ArrayList of Strings
     * </p>
     * @param fileName is the name of the file to read
     * @return an ArrayList of String which contain the file's contents
     */
    public ArrayList<String> read(String fileName) {
        return this.input.readFile(fileName);
    }

    /**
     * Function write()
     * <p>
     *     Writes a String to a given file
     * </p>
     * @param fileName is the name of the file to write too
     * @param output is the String to be written to the file
     */
    public void write(String fileName, String output) {
        this.output.writeOutput(fileName, output);
    }
}
