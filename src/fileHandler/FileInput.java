/**
 * FileInput
 * @author Sam Malpass
 * @version 0.0.5
 * @since 0.0.0
 */
package fileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileInput {

    /**
     * Constructor with no arguments
     * <p>
     *     Sets up the object
     * </p>
     */
    public FileInput() {}

    /**
     * Function readFile()
     * <p>
     *     Reads the lines from a file to an ArrayList, then returns said ArrayList
     * </p>
     * @param filePath is the file to read from
     * @return the list of lines
     */
    public ArrayList<String> readFile(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (Exception e) {
            System.err.println("[ERROR] File not found");
        }
        return lines;
    }
}
