package com.aligarhwizard.simpletodo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static List<String> readLines(File file) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String> items = new ArrayList<String>();
		String line = null;
		while((line = reader.readLine()) != null) {
			items.add(line);
		}
		reader.close();
		return items;
	}

	public static void writeLines(File file, List<String> items) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		for (String item: items) {
			writer.write(item + "\n");
		}
		writer.close();
	}
}
