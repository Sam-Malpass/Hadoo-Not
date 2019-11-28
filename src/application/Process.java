/**
 * Process
 * @author Sam Malpass
 * @version 0.1.0
 * @since 0.0.1
 */
package application;

import application.nodes.CombinerNode;
import application.nodes.MapNode;
import application.nodes.ReduceNode;
import application.nodes.Node;
import fileHandler.FileHandler;
import fileHandler.JarLoader;
import mapReduce.EZJob;
import mapReduce.Job;
import mapReduce.Tuple;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Process {

    private InterfaceOutput interfaceOutput = new InterfaceOutput();

    /**
     * blockSize holds the number of data entries to be sent to a single mapper node
     */
    private int blockSize;

    /**
     * jobName is the name of the class file to use as the Job
     */
    private String jobName;

    /**
     * jarName is the name of the JAR file to load from
     */
    private String jarName;

    /**
     * mapperNodes holds a list of all the Nodes that map
     */
    private ArrayList<MapNode> mapperNodes = new ArrayList<>();

    /**
     * reducerNodes holds a list of all the Nodes that reduce
     */
    private ArrayList<ReduceNode> reducerNodes = new ArrayList<>();

    /**
     *combinerNodes holds a list of all the Nodes that combine
     */
    private ArrayList<CombinerNode> combinerNodes = new ArrayList<>();

    /**
     * shuffledOutput holds a sorted list of all map Node outputs
     */
    private ArrayList<Tuple> shuffledOutput = new ArrayList<>();

    /**
     * partitionedOutput holds the partitioned output of the sorted mapper outputs
     */
    private ArrayList<ArrayList<Tuple>> partitionedOutput = new ArrayList<>();

    /**
     * combinerOutput holds the output of the combiner(s)
     */
    private ArrayList<Tuple> combinerOutput = new ArrayList<>();

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
    private Job task;

    /**
     * inputPath holds the file path of a data file
     */
    private String inputPath;

    /**
     * outputPath holds the file path to write to
     */
    private String outputPath;

    /**
     * input holds a list of objects to operate on
     */
    private ArrayList<Object> input;

    /**
     * Constructor with arguments
     * <p>
     *     Sets up the object using the passed arguments
     * </p>
     * @param jarName is the location of the JAR file
     * @param jobName is the name of the class file to load
     */
    public Process(String jarName, String jobName) {

        //this.blockSize = ;
        this.jobName = jobName;
        this.jarName = jarName;
        this.fileHandler = new FileHandler();
        /* SETUP JOB */
        try {
            JarLoader jarLoader = new JarLoader();
            task = (Job) jarLoader.createObject(jarName, jobName);
        }
        catch(Exception e) {
            System.err.println("[ERROR] Failed to load job");
        }
    }

    /**
     * Constructor with arguments
     * <p>
     *     Allows for the setup and execution of an EZJob
     * </p>
     * @param job is the EZJob to use
     */
    public Process(EZJob job) {
        task = job;
        this.fileHandler = new FileHandler();
        Node.setup(task);
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
     * Function shuffleSort()
     * <p>
     *     Collects all the map outputs across all mapperNodes and then sorts them all by key
     * </p>
     */
    private void shuffleSort() {
        for(Node n : mapperNodes) {
             ArrayList<Tuple> mapperOutput = (ArrayList<Tuple>) n.getOutput();
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
     * Function partition()
     * <p>
     *     Takes the sorted output and creates separate partitions of data for every key in the keySet
     * </p>
     * @param keySet is the list of keys
     */
    private void partition(ArrayList<Object> keySet) {
        int startingIndex = 0;
        for(Object k : keySet) {
            ArrayList<Tuple> partition = new ArrayList<>();
            for(int i = startingIndex; i <shuffledOutput.size(); i++) {
                if(shuffledOutput.get(i).getKey().toString().equals(k.toString())) {
                    partition.add(shuffledOutput.get(i));
                }
                else {
                    partitionedOutput.add(partition);
                    startingIndex = i;
                    break;
                }
            }
        }
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
    private ArrayList<Object> readData(String filePath) {
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
     * Function setup()
     * <p>
     *     Sets the input/output paths for the process
     * </p>
     * @param inputPath is the file path of the input data
     * @param outputPath is the file path of the output data
     */
    public void setup(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
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
     * @param chain determines the position in the chain, 0 for singular job
     */
    public void start(int chain) {
        Node.setup(task);
        /* READ IN */
        if(chain <= 1) {
            input = readData(inputPath);
        }
        //Determine block size
        int cores = Runtime.getRuntime().availableProcessors();
        int numThreads = (cores * 2) - 1;
        this.blockSize = input.size()/numThreads;
        interfaceOutput.drawStartNode();
        /* PREPROCESS */
        ArrayList<ArrayList<Object>> dataChunks = new ArrayList<>();

        System.out.println("[PREPROCESSOR] Beginning preprocessing...");
        ArrayList<Object> data = task.preprocess(input);
        System.out.println("[PREPROCESSOR] Preprocessing complete!\n");

        /* SPLIT */
        dataChunks = split(data);


        /* MAP */
        interfaceOutput.drawMapperNodes(dataChunks.size());
        System.out.println("[MAPPER] Beginning mapping...");
        for(ArrayList<Object> block : dataChunks) {
            MapNode mapperNode = new MapNode( "MapperNode" + dataChunks.indexOf(block));
            mapperNode.setInput(block);
            mapperNode.start();
            mapperNodes.add(mapperNode);
        }
        //Join all executing threads
        float percentageCalc = 1;
        for(Node n : mapperNodes) {
            try {
                n.getThread().join();
                System.out.println("[MAPPER] Map at " + percentageCalc/mapperNodes.size()*100 + "% Complete");
                percentageCalc++;
            }
            catch (Exception e) {
                System.err.println("[ERROR] Failed to join mapper thread with ID: " + n.getThreadID());
            }
        }
        System.out.println("[MAPPER] Mapping completed!\n");
        /* SHUFFLE/SORT */
        interfaceOutput.drawSorterNode();
        shuffleSort();
        ArrayList<Object> keySet = generateKeySet();
        partition(keySet);

        /*COMBINER*/
        System.out.println("[COMBINER] Beginning Combining...");
        percentageCalc = 0;
        float numCombiners = mapperNodes.size();
        float partitionsPerCombiner = (float) partitionedOutput.size() / numCombiners;
        int partitionCounter = 0;
        interfaceOutput.drawCombinerNodes((int)numCombiners);
        for (int i = 0; i < numCombiners; i++) {
            ArrayList<ArrayList<Tuple>> combinerInput = new ArrayList<>();
            for(int j = 0; j < partitionsPerCombiner; j++) {
                if(partitionCounter == partitionedOutput.size()) {
                    break;
                }
                combinerInput.add(partitionedOutput.get(partitionCounter));
                partitionCounter++;
            }
            CombinerNode combinerNode = new CombinerNode("CombinerNode" + i);
            combinerNode.start(combinerInput);
            combinerNodes.add(combinerNode);
        }
        for(Node n : combinerNodes) {
            try {
                n.getThread().join();
                combinerOutput.addAll((ArrayList<Tuple>)n.getOutput());
                System.out.println("[COMBINER] Combine at: " + percentageCalc/combinerNodes.size()*100 + "% Complete");
                percentageCalc++;
            }
            catch (Exception e) {
                System.err.println("[COMBINER] Failed to join reducer thread with ID: " + n.getThreadID());
            }
        }
        System.out.println("[COMBINER] Combining Complete!\n");

        /* REDUCE */
        System.out.println("[REDUCER] Beginning reducing...");
        percentageCalc = 0;
        float numReducers = mapperNodes.size();
        float tuplesPerReducer = (float) combinerOutput.size() / numReducers;
        int tupleCounter = 0;
        interfaceOutput.drawReducerNodes((int) numReducers);
        for(int i = 0; i < numReducers; i++) {
            ArrayList<Tuple> reducerInput = new ArrayList<>();
            for(int j = 0; j < tuplesPerReducer; j++) {
                if(tupleCounter == combinerOutput.size()) {
                    break;
                }
                reducerInput.add(combinerOutput.get(tupleCounter));
                tupleCounter++;
            }
            ReduceNode reducerNode = new ReduceNode("ReducerNode" + i);
            reducerNode.start(reducerInput);
            reducerNodes.add(reducerNode);
        }
        //Join all executing threads
        for(Node n : reducerNodes) {
            try {
                n.getThread().join();
                System.out.println("[REDUCER] Reduce at: " + percentageCalc/reducerNodes.size()*100 + "% Complete");
                percentageCalc++;
            }
            catch (Exception e) {
                System.err.println("[ERROR] Failed to join reducer thread with ID: " + n.getThreadID());
            }
        }
        //Get all results
        for(Node n : reducerNodes) {
            finalOutput.addAll((ArrayList<Tuple>) n.getOutput());
        }
        System.out.println("[REDUCER] Reducing complete!\n");

        interfaceOutput.drawOutputNode();
        /* OUTPUT */
        System.out.println("[SYSTEM] Writing output...");
        writeData(outputPath, task.format(finalOutput));
    }

    /**
     * Function setInput()
     * <p>
     *     Set the input to the passed value
     * </p>
     * @param input is the data to use
     */
    public void setInput(ArrayList<Object> input) {
        this.input = input;
    }

    /**
     * Function getOutput()
     * <p>
     *     Return the output of the process
     * </p>
     * @return output
     */
    public Object getOutput() {
        return finalOutput;
    }
}

