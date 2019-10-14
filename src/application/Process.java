/**
 * Process
 * @author Sam Malpass
 * @version 0.0.2
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
     * task holds the Job to be used
     */
    private static Job task;

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
     * Function generateKeySet()
     * <p>
     *     Compiles a list of all the unique keys in the shuffledOutput
     * </p>
     * @return the set of unique keys
     */
    private ArrayList<Object> generateKeySet() {
        ArrayList<Object> keySet = new ArrayList<>();
        for(Tuple t : shuffledOutput) {
            if(!keySet.contains(t.getKey())) {
                keySet.add(t.getKey());
            }
        }
        return keySet;
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

    /**
     * Function writeData()
     * <p>
     *     Writes an output string to a given file
     * </p>
     * @param filePath is the file to output to
     * @param output is the data to be written
     */
    private void writeData(String filePath, String output) {
        fileHandler.write(filePath, output);
    }

    /**
     * Function start()
     * <p>
     *     Runs the all the stages of the job
     *     1. Sets up the Job object
     *     2. Reads in the data
     *     3. Runs the Preprocessor
     *     4. Runs the map stage on a set number of nodes
     *     5. Shuffle/Sort the results from the mapper nodes
     *     6. Run the reduce stage on a number of nodes
     *     7. Output the results to a file
     * </p>
     * @param inputPath is the file to read from
     * @param outputPath is the file to output to
     */
    public void start(String inputPath, String outputPath) {
        /* SETUP JOB */
        try {
            Class cls = Class.forName(jobName);
            ClassLoader cLoader = cls.getClassLoader();
            Class cls2 = Class.forName(jobName, true, cLoader);
            Constructor<Job> constructor = cls2.getConstructor();
            Job tmp = constructor.newInstance();
            task = tmp;
            Node.setup(task);
        }
        catch (Exception e) {
            System.err.println("[ERROR] Could not instantiate object for: " + jobName);
            return;
        }

        /* READ IN */
        ArrayList<String> input = readData(inputPath);

        /* PREPROCESS */
        System.out.println("[PREPROCESSOR] Beginning preprocessing...");
        ArrayList<Object> data = task.preprocess(input);
        System.out.println("[PREPROCESSOR] Preprocessing complete!\n");

        /* SPLIT */
        ArrayList<ArrayList<Object>> dataChunks = split(data);

        /* MAP */
        System.out.println("[MAPPER] Beginning mapping...");
        for(ArrayList<Object> block : dataChunks) {
            Node mapperNode = new Node(true, "MapperNode" + dataChunks.indexOf(block));
            mapperNode.setInput(block);
            mapperNode.start();
            System.out.println("[MAPPER] Starting node: " + mapperNode.getThreadID());
            mapperNodes.add(mapperNode);
        }
        System.out.println("[MAPPER] Total mapper nodes: " + mapperNodes.size() + "!");
        //Join all executing threads
        for(Node n : mapperNodes) {
            try {
                n.getThread().join();
                System.out.println("[MAPPER] Joining node: " + n.getThreadID());
            }
            catch (Exception e) {
                System.err.println("[ERROR] Failed to join mapper thread with ID: " + n.getThreadID());
            }
        }
        System.out.println("[MAPPER] Mapping completed!\n");
        /* SHUFFLE/SORT */
        shuffle();
        ArrayList<Object> keySet = generateKeySet();

        /* REDUCE */
        System.out.println("[REDUCER] Beginning reducing...");
        for(Object key : keySet) {
            Node reducerNode = new Node(false, "ReducerNode" + keySet.indexOf(key));
            reducerNode.setMapperOutput(shuffledOutput);
            reducerNode.setKey(key);
            System.out.println("[REDUCER] Starting node: " + reducerNode.getThreadID());
            reducerNode.start();
            reducerNodes.add(reducerNode);
        }
        System.out.println("[REDUCER] Total reducer nodes: " + reducerNodes.size() + "!");
        //Join all executing threads
        for(Node n : reducerNodes) {
            try {
                n.getThread().join();
                System.out.println("[REDUCER] Joining node: " + n.getThreadID());
            }
            catch (Exception e) {
                System.err.println("[ERROR] Failed to join reducer thread with ID: " + n.getThreadID());
            }
        }
        //Get all results
        for(Node n : reducerNodes) {
            finalOutput.add(n.getReducerOutput());
        }
        System.out.println("[REDUCER] Reducing complete!\n");

        /* OUTPUT */
        writeData(outputPath, task.format(finalOutput));
    }
}

