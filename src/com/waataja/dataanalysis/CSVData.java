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
	
	public ArrayList<String> getColumnDataWithEmpty(String columnTitle) {
		ArrayList<String> entries = new ArrayList<String>();
		int columnIndex = getColumnIndex(columnTitle);
		for (String[] row : rows) {
			entries.add(row[columnIndex]);
		}
		
		return entries;
	}
	
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
}
