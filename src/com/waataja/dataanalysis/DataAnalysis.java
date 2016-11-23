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
		
		ArrayList<Double> sampleSize = toDoubleArray(data.getColumnDataWithoutEmpty("samplesize"));
		ArrayList<Double> pollWeight = toDoubleArray(data.getColumnDataWithoutEmpty("poll_wt"));
		ArrayList<Double> rawClinton = toDoubleArray(data.getColumnDataWithoutEmpty("rawpoll_clinton"));
		ArrayList<Double> adjClinton = toDoubleArray(data.getColumnDataWithoutEmpty("adjpoll_clinton"));
		ArrayList<Double> rawTrump = toDoubleArray(data.getColumnDataWithoutEmpty("rawpoll_trump"));
		ArrayList<Double> adjTrump = toDoubleArray(data.getColumnDataWithoutEmpty("adjpoll_trump"));
		ArrayList<Double> rawJohnson = toDoubleArray(data.getColumnDataWithoutEmpty("rawpoll_johnson"));
		ArrayList<Double> adjJohnson = toDoubleArray(data.getColumnDataWithoutEmpty("adjpoll_johnson"));
		
		System.out.println(String.format("%-16s %-16s %-16s %-16s %-16s %-16s %-16s %-16s", 
				"name", "average", "std deviation", "min", "first quartile", "median", "third quartile", "max"));
		printData("samplesize", sampleSize);
		printData("poll_wt", pollWeight);
		printData("rawpoll_clinton", rawClinton);
		printData("adjpoll_clinton", adjClinton);
		printData("rawpoll_trump", rawTrump);
		printData("adjpoll_trump", adjTrump);
		printData("rawpoll_johnson", rawJohnson);
		printData("adjpoll_johnson", adjJohnson);
		
		System.out.println();
		
		ArrayList<String> pollsters = data.getColumnDataWithoutEmpty("pollster");
		System.out.println("5 most used pollsters");
		printModes(pollsters, 5);
		System.out.println();
		ArrayList<String> states = data.getColumnDataWithEmpty("state");
		System.out.println("10 most common states in polls");
		printModes(states, 10);
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
		double average = sum / list.size();
		double devSum = 0;
		for (double num : list)
			devSum += Math.pow(num - average, 2);
		double stdDev = Math.sqrt(devSum / list.size());
		
		Collections.sort(list);
		double min = list.get(0);
		double max = list.get(list.size() - 1);
		
		double frstQuart = list.get((int) Math.round(list.size() * 0.25));
		double thrdQuart = list.get((int) Math.round(list.size() * 0.75));
		double median = list.get((int) Math.round(list.size() * 0.5));
		String out = String.format("%-16s %-16f %-16f %-16f %-16f %-16f %-16f %-16f", 
				name, average, stdDev, min, frstQuart, median, thrdQuart, max);
		System.out.println(out);
	}
	
	public static void printModes(ArrayList<String> list, int num) {
		ArrayList<FrequencyPair> pairs = new ArrayList<FrequencyPair>();
		for (String name : list) {
			boolean found = false;
			for (FrequencyPair pair : pairs) {
				if (name.equals(pair.name)) {
					pair.amount++;
					found = true;
					break;
				}
			}
			if (!found)
				pairs.add(new FrequencyPair(name, 1));
		}
		Collections.sort(pairs);
		Collections.reverse(pairs);
		System.out.print(pairs.get(0).name);
		for (int i = 1; i < num; i++) {
			System.out.print(", " + pairs.get(i).name);
		}
		System.out.println();
	}

}

class FrequencyPair implements Comparable<FrequencyPair> {
	public String name;
	public int amount;
	
	public FrequencyPair(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}
	
	public int compareTo(FrequencyPair other) {
		int value = Integer.compare(amount, other.amount);
		if (value != 0)
			return value;
		return name.compareToIgnoreCase(other.name);
	}
}
