package com.waataja.dataanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataAnalysis {
	
	public static final String DATA_FILE = "res/president_general_polls_2016.csv";

	public static void main(String[] args) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(DATA_FILE));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				for (String element : CSVData.splitCSVLine(line)) {
					System.out.print(element + " ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
