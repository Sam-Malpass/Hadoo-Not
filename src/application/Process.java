/**
 * Process
 * @author Sam Malpass
 * @version 0.0.1
 * @since 0.0.1
 */
package application;

import fileHandler.FileHandler;
import mapReduce.Job;
import mapReduce.Node;
import mapReduce.Tuple;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Process {

    /**
     * blockSize holds the number of data entries to be sent to a single mapper node
     */
    private int blockSize;

    /**
     * jobName is the name of the class file to use as the Job
     */
    private String jobName;

    /**
     * mapperNodes holds a list of all the Nodes that map
     */
    private ArrayList<Node> mapperNodes = new ArrayList<>();

    /**
     * reducerNodes holds a list of all the Nodes that reduce
     */
    private ArrayList<Node> reducerNodes = new ArrayList<>();

    /**
     * shuffledOutput holds a sorted list of all map Node outputs
     */
    private ArrayList<Tuple> shuffledOutput = new ArrayList<>();

    /**
     * finalOutput holds the results from the reduce Nodes
     */
    private ArrayList<Tuple> finalOutput = new ArrayList<>();

    /**
     * fileHandler gives access to file reading/writing functions
     */
    private FileHandler fileHandler;

    /**
     * Constructor with arguments
     * <p>
     *     Sets up the object using the passed arguments
     * </p>
     * @param size is the number to use for blockSize
     * @param jobName is the name of the class file to load
     */
    public Process(int size, String jobName) {
        this.blockSize = size;
        this.jobName = jobName;
        this.fileHandler = new FileHandler();
    }

    /**
     * Function split()
     * <p>
     *     Splits the list of input data entries into chunks of blockSize
     * </p>
     * @param input is the data to be split
     * @return the chunks
     */
    private ArrayList<ArrayList<Object>> split(ArrayList<Object> input) {
        ArrayList<ArrayList<Object>> chunks = new ArrayList<>();
        for(int i = 0; i < input.size(); i += blockSize) {
            ArrayList<Object> chunk = new ArrayList<>(input.subList(i, Math.min(input.size(), i + blockSize)));
            chunks.add(chunk);
        }
        return chunks;
    }

    /**
     * Function shuffle()
     * <p>
     *     Collects all the map outputs across all mapperNodes and then sorts them all by key
     * </p>
     */
    private void shuffle() {
        for(Node n : mapperNodes) {
            ArrayList<Tuple> mapperOutput = n.getMapperOutput();
            shuffledOutput.addAll(mapperOutput);
        }
        Collections.sort(shuffledOutput, new Comparator<Tuple>() {
            @Override
            public int compare(Tuple o1, Tuple o2) {
                return o1.getKey().toString().compareToIgnoreCase(o2.getKey().toString());
            }
        });
    }

    /**
     * Function readData()
     * <p>
     *     Returns the result of the fileHandler when asked to read a given file
     * </p>
     * @param filePath is the file to read
     * @return the lines of the file as an ArrayList
     */
    private ArrayList<String> readData(String filePath) {
        return fileHandler.read(filePath);
    }

    public void start(String inputPath, String outputPath) {
        /* SETUP JOB */
        try {
            Class cls = Class.forName(jobName);
            ClassLoader cLoader = cls.getClassLoader();
            Class cls2 = Class.forName(jobName, true, cLoader);
            Constructor<Job> constructor = cls2.getConstructor();
            Job task = constructor.newInstance();
            Node.setup(task);
        }
        catch (Exception e) {
            System.err.println("[ERROR] Could not instantiate object for: " + jobName);
            return;
        }
        /* READ IN */

        /* PREPROCESS */

        /* SPLIT */

        /* MAP */

        /* SHUFFLE/SORT */

        /* REDUCE */

        /* OUTPUT */
    }
}

