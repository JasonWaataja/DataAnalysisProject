package com.waataja.dataanalysis;

import java.io.File;

public class DataAnalysis {

	public static void main(String[] args) {
		File myFile = new File(".");
		System.out.println(myFile.getAbsolutePath());
	}

}
