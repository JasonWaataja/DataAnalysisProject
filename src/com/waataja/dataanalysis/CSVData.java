package com.waataja.dataanalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class CSVData {

	private String[] columnTitles;
	private ArrayList<String[]> rows;

	public CSVData() {
		columnTitles = null;
		rows = new ArrayList<String[]>();
	}

	public CSVData(String filename) {
		this();
		loadFromFile(filename);
	}

	/* This doesn't parse it correctly, but works for this project. */
	public static String[] splitCSVLine(String line) {
		ArrayList<String> elements = new ArrayList<String>();
		elements.addAll(Arrays.asList(line.split(",")));

		String[] asArray = new String[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).startsWith("\"") && elements.get(i).endsWith("\""))
				asArray[i] = elements.get(i).substring(1, elements.get(i).length() - 1);
			else
				asArray[i] = new String(elements.get(i));
		}

		return asArray;
	}

	public int getColumnIndex(String columnTitle) {
		int columnIndex = -1;
		for (int i = 0; i < columnTitles.length; i++) {
			if (columnTitles[i].equals(columnTitle)) {
				columnIndex = i;
				break;
			}
		}

		if (columnIndex == -1)
			throw new IllegalArgumentException("Column title not found");

		return columnIndex;
	}

	public String getEntry(int row, String columnTitle) {
		return rows.get(row)[getColumnIndex(columnTitle)];
	}

	public void setEntry(int row, String columnTitle, String value) {
		rows.get(row)[getColumnIndex(columnTitle)] = value;
	}

	/**
	 * Gets the entries in a given column, including entries that are "".
	 * 
	 * @param columnTitle
	 * @return
	 */
	public ArrayList<String> getColumnDataWithEmpty(String columnTitle) {
		ArrayList<String> entries = new ArrayList<String>();
		int columnIndex = getColumnIndex(columnTitle);
		for (String[] row : rows) {
			entries.add(row[columnIndex]);
		}

		return entries;
	}

	/**
	 * Gets the entries in a given column, exculding entries hat are "".
	 * 
	 * @param columnTitle
	 * @return
	 */
	public ArrayList<String> getColumnDataWithoutEmpty(String columnTitle) {
		ArrayList<String> entries = new ArrayList<String>();
		int columnIndex = getColumnIndex(columnTitle);
		for (String[] row : rows) {
			if (!row[columnIndex].equals("")) {
				entries.add(row[columnIndex]);
			}
		}

		return entries;
	}

	/**
	 * Gets the data in the given columns, including entries that would contain
	 * "".
	 * 
	 * @param columnTitles
	 * @return
	 */
	public ArrayList<String[]> getColumnsWithEmpty(String... columnTitles) {
		ArrayList<String[]> columns = new ArrayList<String[]>();

		int[] columnIndices = new int[columnTitles.length];
		for (int i = 0; i < columnTitles.length; i++) {
			columnIndices[i] = getColumnIndex(columnTitles[i]);
		}

		for (String[] row : rows) {
			String[] entry = new String[columnTitles.length];
			for (int i = 0; i < columnIndices.length; i++) {
				entry[i] = row[i];
			}
			columns.add(entry);
		}

		return columns;
	}

	/**
	 * Gets the data in the given columns, not including entries that would contain
	 * "".
	 * 
	 * @param columnTitles
	 * @return
	 */
	public ArrayList<String[]> getColumnsWithoutEmpty(String... columnTitles) {
		ArrayList<String[]> columns = new ArrayList<String[]>();
		int[] columnIndices = new int[columnTitles.length];

		for (int i = 0; i < columnTitles.length; i++)
			columnIndices[i] = getColumnIndex(columnTitles[i]);

		for (String[] row : rows) {
			boolean nonEmpty = true;
			String[] entry = new String[columnTitles.length];
			for (int i = 0; i < columnTitles.length; i++) {
				if (!row[i].equals("")) {
					entry[i] = row[i];
				} else {
					nonEmpty = false;
					break;
				}
			}
			if (nonEmpty)
				columns.add(entry);
		}

		return columns;
	}

	public void loadFromFile(String filename) {

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(filename));

			String line = reader.readLine();

			columnTitles = splitCSVLine(line);

			while ((line = reader.readLine()) != null)
				rows.add(splitCSVLine(line));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String[] getColumnTitles() {
		return columnTitles;
	}

	public void setColumnTitles(String[] columnTitles) {
		this.columnTitles = columnTitles;
	}

	public ArrayList<String[]> getRows() {
		return rows;
	}

	public void setRows(ArrayList<String[]> rows) {
		this.rows = rows;
	}

	public int getColumnCount() {
		return columnTitles.length;
	}
	
	public int getRowCount() {
		return rows.size();
	}

	public void printData() {
		/* Get the maximum column length for each column. */
		int[] maxColumnLengths = new int[getColumnCount()];
		for (int i = 0; i < getColumnCount(); i++) {
			maxColumnLengths[i] = columnTitles[i].length();
			for (String[] row : rows) {
				String element = row[i];
				if (element.length() > maxColumnLengths[i])
					maxColumnLengths[i] = element.length();
			}
		}

		for (int i = 0; i < getColumnCount(); i++) {
			System.out.println(columnTitles[i]);
			for (int j = 0; j < maxColumnLengths[i] - columnTitles[i].length() + 1; j++)
				System.out.print(" ");
		}
		
		System.out.println();
		System.out.println();
		
		for (String[] row : rows) {
			for (int i = 0; i < getColumnCount(); i++) {
				System.out.print(row[i]);
				for (int j = 0; j < maxColumnLengths[i] - row[i].length() + 1; j++)
					System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	/*
	 * Does the same thing as printData, except only prints rows lower to upper-1.
	 */
	public void printDataForRows(int lower, int upper) {
		int[] maxColumnLengths = new int[getColumnCount()];
		for (int i = 0; i < getColumnCount(); i++) {
			maxColumnLengths[i] = columnTitles[i].length();
			for (int j = lower; j < upper; j++) {
				String[] row = rows.get(j);
				if (row[i].length() > maxColumnLengths[i])
					maxColumnLengths[i] = row[i].length();
			}
		}
		
		for (int i = 0; i < getColumnCount(); i++) {
			System.out.print(columnTitles[i]);
			
			for (int j = 0; j < maxColumnLengths[i] - columnTitles[i].length() + 1; j++)
				System.out.print(" ");
		}
		
		System.out.println();
		System.out.println();
		
		for (int i = lower; i < upper; i++) {
			String[] row = rows.get(i);
			for (int j = 0; j < getColumnCount(); j++) {
				System.out.print(row[j]);
				for (int k = 0; k < maxColumnLengths[j] - row[j].length() + 1; k++)
					System.out.print(" ");
			}
			System.out.println();
		}
	}
}
