/**
 * EZJob
 * @author Sam Malpass
 * @version 0.0.7
 * @since 0.0.7
 */
package mapReduce;

import java.util.ArrayList;
import java.util.Arrays;

public class EZJob extends Job {

    /**
     * keyIndex determines which column will be used as the key
     */
    private int keyIndex;

    /**
     * valueIndices determines which column(s) will be used as the values
     */
    private ArrayList<Integer> valueIndices;

    /**
     * mode holds the job type
     * 0 = count;
     * 1 = total;
     * 2 = list;
     */
    private int mode;

    /**
     * Function ezSetup()
     * <p>
     *     Sets up the EZ job using the passed values
     * </p>
     * @param keyIndex is the index of the key in the data structure
     * @param valueIndices are the indices of the value(s) in the data structure
     * @param mode is the type of job to perform
     */
    public void ezSetup(int keyIndex, ArrayList<Integer> valueIndices, int mode) {
        this.keyIndex = keyIndex;
        this.valueIndices = valueIndices;
        this.mode = mode;
    }

    @Override
    public ArrayList<Object> preprocess(ArrayList<String> input) {
        ArrayList<Object> dataEntries = new ArrayList<>();
        RegGuess brute = new RegGuess();
        ArrayList<String> regexList =  brute.guessRegex(input, ",");
        for(String s : input) {
            ArrayList<Object> data = new ArrayList<>();
            String[] row = s.split(",");
            ArrayList<String> rowList = new ArrayList(Arrays.asList(row));
            boolean bad = false;
            for(int i = 0; i < rowList.size(); i++) {
                if(!rowList.get(i).matches(regexList.get(i))) {
                    bad = true;
                    System.out.println("[PREPROCESSOR] Removed erroneous data entry!");
                }
            }
            if(!bad) {
                data.addAll(rowList);
                for(Object d : dataEntries) {
                    ArrayList<Object> x = (ArrayList) d;
                    if(data.get(keyIndex).equals(x.get(keyIndex))) {
                        for(Integer i : valueIndices) {
                            if(data.get(i).equals(x.get(i))) {
                                bad = true;
                            }
                        }
                    }
                }
                if(!bad) {
                    dataEntries.add(rowList);
                }
            }
        }
        return dataEntries;
    }

    @Override
    public ArrayList<Tuple> map(ArrayList<Object> input) {
        ArrayList<Tuple> mapOutput = new ArrayList<>();
        for(Object o : input) {
            ArrayList<Object> data = (ArrayList) o;
            ArrayList<Object> vals = new ArrayList<>();
            for(Integer i : valueIndices) {
                vals.add(data.get(i));
            }
            Tuple tmp = new Tuple(data.get(keyIndex), vals);
            mapOutput.add(tmp);
        }
        return mapOutput;
    }

    @Override
    public ArrayList<Tuple> reduce(ArrayList<Tuple> input) {
        if(mode == 0) {
            ArrayList<Tuple> output = new ArrayList<>();
            for(Tuple t : input) {
                Object key = t.getKey();
                ArrayList<Object> vals = (ArrayList<Object>) t.getValue();
                Tuple x = new Tuple(key, vals.size());
                output.add(x);
            }
            return output;
        }
        else if(mode == 1) {
            ArrayList<Tuple> output = new ArrayList<>();
            for(Tuple t : input) {
                Object key = t.getKey();
                ArrayList<Object> vals = (ArrayList<Object>) t.getValue();
                int total = 0;
                for(Object o : vals) {
                    total = total + (int) o;
                }
                Tuple x = new Tuple(key, total);
                output.add(x);
            }
            return output;
        }
        else if(mode == 2) {
            return input;
        }
        else {
            System.err.println("[ERROR] Mode for Job set outside known job values");
            return null;
        }
    }

    @Override
    public String format(ArrayList<Tuple> input) {
        String output = "";
        if(mode == 0) {
            for(Tuple t : input) {
                output = output + "Key: " + t.getKey() + " has count of " + t.getValue() + "\n";
            }
        }
        else if(mode == 1) {
            for(Tuple t : input) {
                output = output + "Key: " + t.getKey() + " has total of " + t.getValue() + "\n";
            }
        }
        else if(mode == 2) {
            for(Tuple t : input) {
                output = output + "Key: " + t.getKey() + " has associated values: \n";
                ArrayList<Object> vals = (ArrayList) t.getValue();
                for(Object o : vals) {
                    output = output + o.toString() + "\n";
                }
                output = output + "\n";
            }
        }
        return output;
    }
}
