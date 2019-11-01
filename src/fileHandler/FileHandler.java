/**
 * FileHandler
 * @author Sam Malpass
 * @version 0.0.8
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
     * jarLoader holds the JarLoader object
     */
    private JarLoader jarLoader;

    /**
     * Constructor with no arguments
     * <p>
     *     Sets up the object
     * </p>
     */
    public FileHandler() {
        this.input = new FileInput();
        this.output = new FileOutput();
        this.jarLoader = new JarLoader();
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

    /**
     * Function loadJob()
     * <p>
     *     Gets the job to be run
     * </p>
     * @param jarPath is the JAR to load from
     * @param className is the job within the JAR
     * @return the created object
     */
    public Object loadJob(String jarPath, String className) {
        return jarLoader.createObject(jarPath, className);
    }

    /**
     * Function listClasses()
     * <p>
     *     Lists all the classes that are in a JAR and not included in the Hadoo-Not library
     * </p>
     * @param jarName is the path to the JAR
     * @return the list of classes
     */
    public ArrayList<String> listClasses(String jarName) {
        return this.jarLoader.getClassNames(jarName);
    }

    /**
     * Function readHead()
     * <p>
     *     Reads and returns the top 20 lines of a data file
     * </p>
     * @param filePath is the file path of the data
     * @return the top 20 data entries
     */
    public ArrayList<String> readHead(String filePath) {
        return input.readHead(filePath);
    }
}
