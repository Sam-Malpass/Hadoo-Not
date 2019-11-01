/**
 * FileOutput
 * @author Sam Malpass
 * @version 0.0.8
 * @since 0.0.0
 */
package fileHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileOutput {

    /**
     * Constructor with no arguments
     * <p>
     *     Sets up the object
     * </p>
     */
    public FileOutput() {}

    /**
     * Function writeOutput()
     * <p>
     *     Creates a file using the name and data
     * </p>
     * @param fileName is the name for the file
     * @param output is the data to write to the file
     */
    public void writeOutput(String fileName, String output) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("Output/" + fileName));
            writer.append(output);
            writer.flush();
            writer.close();
        }
        catch(Exception e) {
            System.err.println("[ERROR] Could not be written");
        }
    }
}
