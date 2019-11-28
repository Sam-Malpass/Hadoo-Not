/**
 * FileInput
 * @author Sam Malpass
 * @version 0.1.0
 * @since 0.0.0
 */
package fileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

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
    public ArrayList<Object> readFile(String filePath) {
        ArrayList<Object> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        }
        catch (Exception e) {
            System.err.println("[ERROR] File not found");
        }
        return lines;
    }

    /**
     * Function readHead()
     * <p>
     *     Opens and reads the top 20 lines of a file
     * </p>
     * @param filePath is the file to open
     * @return the top 20 lines
     */
    public ArrayList<String> readHead(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            int i = 0;
            while ((line = reader.readLine()) != null && i < 20) {
                lines.add(line);
                i++;
            }
            reader.close();
        }
        catch (Exception e) {
            System.err.println("[ERROR] File not found");
        }
        return lines;
    }
}
