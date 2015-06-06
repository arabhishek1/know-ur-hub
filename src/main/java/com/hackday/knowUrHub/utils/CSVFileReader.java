package com.hackday.knowUrHub.utils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class CSVFileReader {
    public static CSVReader readFile(String filePath) throws FileNotFoundException {
        final CSVReader csvReader = new CSVReader(new FileReader(filePath), ',', '\'', 1);
        return csvReader;
    }

    public static CSVWriter writeFile(String filePath) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
        return csvWriter;
    }

}
