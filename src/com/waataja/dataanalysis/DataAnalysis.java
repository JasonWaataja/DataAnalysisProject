package com.waataja.dataanalysis;

import java.util.ArrayList;
import java.util.Collections;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;

public class DataAnalysis {
	
	public static final String DATA_FILE = "res/president_general_polls_2016.csv";

	//samplesize, rawpoll_clinton, rawpoll_trump
	public static void main(String[] args) {
		CSVData data = new CSVData(DATA_FILE);
		//data.printDataForRows(0, 100);
		ArrayList<Double> sampleSize = toDoubleArray(data.getColumnDataWithoutEmpty("samplesize"));
		ArrayList<Double> rawClinton = toDoubleArray(data.getColumnDataWithoutEmpty("rawpoll_clinton"));
		ArrayList<Double> rawTrump = toDoubleArray(data.getColumnDataWithoutEmpty("rawpoll_trump"));
		
		System.out.println("name\t\taverage\tstandard deviation\tmin\tfirst quartile\tmedian\tthird quartile\tmax");
		printData("samplesize", sampleSize);
		printData("rawpoll_clinton", rawClinton);
		printData("rawpoll_trump", rawTrump);
	}
	
	public static ArrayList<Double> toDoubleArray(ArrayList<String> strList) {
		ArrayList<Double> doubleList = new ArrayList<Double>();
		for (String str : strList)
			doubleList.add(Double.valueOf(str));
		return doubleList;
	}
	
	public static void printData(String name, ArrayList<Double> list) {
		double sum = 0;
		for (double num : list)
			sum += num;
		double average = Math.rint(sum / list.size() * 100) / 100;
		double devSum = 0;
		for (double num : list)
			devSum += Math.pow(num - average, 2);
		double stdDev = Math.rint(Math.sqrt(devSum / list.size()) * 100) / 100;
		
		Collections.sort(list);
		double min = list.get(0);
		double max = list.get(list.size() - 1);
		
		double frstQuart = list.get((int) Math.round(list.size() * 0.25));
		double thrdQuart = list.get((int) Math.round(list.size() * 0.75));
		double median = list.get((int) Math.round(list.size() * 0.5));
		
		System.out.println(name + "\t" + average + "\t" + stdDev + "\t\t\t" + min + "\t" + frstQuart + "\t\t" + median + "\t" + thrdQuart + "\t\t" + max);
	}

}
