package com.waataja.dataanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataAnalysis {
	
	public static final String DATA_FILE = "res/president_general_polls_2016.csv";

	public static void main(String[] args) {
		CSVData data = new CSVData(DATA_FILE);
		data.printData();
	}

}
