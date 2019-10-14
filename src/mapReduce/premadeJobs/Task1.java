/**
 * Task1
 * @author Sam Malpass
 * @version 0.0.2
 * @since 0.0.2
 */
package mapReduce.premadeJobs;

import mapReduce.Job;
import mapReduce.Tuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Task1 extends Job {
    @Override
    public ArrayList<Object> preprocess(ArrayList<String> input) {
        ArrayList<Object> dataEntries = new ArrayList<>();
        for(String line : input) {
            ArrayList<Object> data = new ArrayList<>();
            boolean errorFlag = false;
            String error = "";
            String[] row = line.split(",");
            ArrayList<String> rowList = new ArrayList(Arrays.asList(row));

            if(rowList.get(0).matches("[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z][A-Z][0-9]")) {
                data.add(rowList.get(0));
            }
            else {
                errorFlag = true;
                error = error + "Passenger ID\n";
            }

            if(rowList.get(1).matches("[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z]")) {
                data.add(rowList.get(1));
            }
            else {
                errorFlag = true;
                error = error + "Flight ID\n";
            }

            if(rowList.get(2).matches("[A-Z][A-Z][A-Z]")) {
                data.add(rowList.get(2));
            }
            else {
                errorFlag = true;
                error = error + "Departure Code\n";
            }

            if(rowList.get(3).matches("[A-Z][A-Z][A-Z]")) {
                data.add(rowList.get(3));
            }
            else {
                errorFlag = true;
                error = error + "Arrival Code\n";
            }

            int length = (int) (Math.log10(Long.parseLong(rowList.get(4)))+1);
            if(length == 10) {
                Date depTime = new Date(Long.valueOf(rowList.get(4)) * 1000);
                data.add(depTime);
            }
            else {
                errorFlag = true;
                error = error + "Departure Time\n";
            }

            length = (int) (Math.log10(Integer.parseInt(rowList.get(5)))+1);
            if(length >= 1 && length <= 4) {
                data.add(Integer.parseInt(rowList.get(5)));
            }
            else {
                errorFlag = true;
                error = error + "Flight Length\n";
            }

            if(!errorFlag) {
                dataEntries.add(data);
            }
            else {
                System.out.println("[WARNING] Data entry number " + input.indexOf(line) + " has following errors: ");
                System.out.print(error);
                System.out.println("[PREPROCESSOR] Removed erroneous data entry!");
            }
        }
        return dataEntries;
    }

    @Override
    public ArrayList<Tuple> map(ArrayList<Object> input) {
        ArrayList<Tuple> mapOutput = new ArrayList<>();
        for(Object o : input) {
            ArrayList<Object> data = (ArrayList) o;
            Tuple tmp = new Tuple(data.get(2), data.get(1));
            mapOutput.add(tmp);
        }
        return mapOutput;
    }

    @Override
    public Tuple reduce(Object key, ArrayList<Tuple> input) {
        ArrayList<Object> total = new ArrayList<>();
        for(Tuple t : input) {
            if(t.getKey().equals(key)) {
                total.add(t.getValue());
            }
        }
        return new Tuple(key, total.size());
    }

    @Override
    public String format(ArrayList<Tuple> input) {
        String build = "";
        for(Tuple t : input) {
            build = build + "Airport: " + t.getKey() + "\nTotal Flights: " + t.getValue() + "\n\n";
        }
        return build;
    }
}
