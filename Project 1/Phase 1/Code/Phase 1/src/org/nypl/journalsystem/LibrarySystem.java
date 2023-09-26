package org.nypl.journalsystem;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

public class LibrarySystem {
	
	public LibrarySystem() {
		//TODO: Initialize system with default journals.
	}
	
	public void load() throws FileNotFoundException, IOException {
		loadAuthors();
		loadArticles();
	}
	
	protected void loadAuthors() throws FileNotFoundException, IOException {

		// Read File
		File fileDirectory = new File("Phase 1/Code/Phase 1/data");
		File file = new File(fileDirectory, "Authors.csv");
		Reader in = new FileReader(file);

		Iterable<CSVRecord> d = CSVFormat.DEFAULT.parse(in);
		//TODO: Load authors from file

	}
	
	protected void loadArticles() throws FileNotFoundException, IOException {

		File fileDirectory = new File("Phase 1/Code/Phase 1/data");
		File file = new File(fileDirectory, "Articles.csv");
		//TODO: Load articles from file and assign them to appropriate journal
	}
	
	
	public void listContents() {
		//TODO: Print all journals with their respective articles and authors to the console.
	}
	
	public static void main(String[] args) throws Exception {

		System.out.print(System.getProperty("user.dir"));

		LibrarySystem librarySystem = new LibrarySystem();

		librarySystem.loadArticles();
		librarySystem.loadAuthors();
	}
}
