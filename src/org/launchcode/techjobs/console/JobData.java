package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        /*for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);

            if (aValue.contains(value)) {
                jobs.add(row);
            }
        }*/

        //rewritten as case insensitive
        for (HashMap<String, String> row: allJobs) {

            String aValue = row.get(column);

            String aValueCapitalized = aValue.toUpperCase();

            if (aValueCapitalized.contains(value.toUpperCase())) {
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }
    public static ArrayList<HashMap<String, String>> findByValue(String value) {
        //searches for string within each of the columns
        //should not contain duplicate jobs
        //if a new column is added, the code should automatically search the new column as well
        //don't write a code that calls findByColumnAndValue once for each column
        //utilize loops and collection methods
        //read and understand findByColumnAndValue, as your code will look similar in some ways

        // load data, if not already loaded
        loadData();

        //create empty array to collect search results
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
        //Case Sensitive Search Function
        /*
        for (HashMap<String, String> row : allJobs) {
            if (row.containsValue(value)) {
                jobs.add(row);
            }
        }*/

        //Case Insensitive Search Function

        /*for (HashMap<String, String> row: allJobs) {
            HashMap <String, String> rowCapitalized = new HashMap<>();
            for (Map.Entry<String, String> job: row.entrySet()) {
                String jobCapitalized = job.getValue().toUpperCase();
                rowCapitalized.put(job.getKey(), jobCapitalized);
            }
            if (rowCapitalized.containsValue(value.toUpperCase())) {
                jobs.add(row);
            }


        }*/

        //loop through array list of jobs
        for (HashMap<String, String> row: allJobs) {
            //use inner for loop to loop through all the properties of a job
            for(Map.Entry<String, String> jobField: row.entrySet()) {
                if (jobField.getValue().toUpperCase().contains(value.toUpperCase())) {
                    jobs.add(row);
                }
            }
        }
        //return
        return jobs;
    }

}
