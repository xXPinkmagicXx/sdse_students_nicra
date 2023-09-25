package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class CityCSVProcessor {

	List<CityRecord> AllRecords = new ArrayList<CityRecord>();

	Map<String, List<CityRecord>> RecordsByCity = new HashMap<String, List<CityRecord>>();
	class CityRecord {

		int Id;
		int Year;
		String City;
		int Population;

		public CityRecord(
				int id,
				int year,
				String city,
				int population)
		{
			Id = id;
			Year = year;
			City = city;
			Population = population;
		}


	}

	public void readAndProcess(File file) {
		//Try with resource statement (as of Java 8)
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			//Discard header row
			br.readLine();

			// clear data
			AllRecords.clear();

			String line;
			
			while ((line = br.readLine()) != null) {
				// Parse each line
				String[] rawValues = line.split(",");

				int id = convertToInt(rawValues[0]);
				int year = convertToInt(rawValues[1]);
				String city = convertToString(rawValues[2]);
				int population = convertToInt(rawValues[3]);


				CityRecord data = new CityRecord(id, year, city, population);

				AllRecords.add(data);

				System.out.println("id: " + id + ", year: " + year + ", city: " + city + ", population: " + population);
				
				//TODO: Extend the program to process entries!
			}
		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}
	}

	public void MapData(){

		// CLear
		RecordsByCity.clear();

		for (var record : AllRecords){

			// If city is not in dictionary
			if (!RecordsByCity.containsKey(record.City)){

				RecordsByCity.put(record.City, new ArrayList<CityRecord>());
			}

			var cityList = RecordsByCity.get(record.City);
			cityList.add(record);

		}

	}

	public void  ProcessData(){

		for (Map.Entry<String, List<CityRecord>> entry : RecordsByCity.entrySet()) {
			//...
			var values = entry.getValue();
			String key = entry.getKey();
			// IF data is not there??

			// The total number of entries for that city
			var nEntries = values.size();

			// The minimum year and the maximum year
			int maxYear = GetMaximumYear(values);
			int minYear = GetMinYear(values);

			// The average population
			var averagePopulation = GetAverage(values, nEntries);

			System.out.printf("%s (%s - %s) with average population: %s \n", key, minYear, maxYear, averagePopulation);

		}


	}
	private double GetAverage(List<CityRecord> data, int nEntries){

		var population = 0d;

		for (var entry : data){

			population += entry.Population;

		}

		return population / nEntries;

	}
	private int GetMaximumYear(List<CityRecord> data){
		int maxYear = 0;

		for (var entry : data) {

			if (entry.Year > maxYear){
				maxYear = entry.Year;

			}
		}
		return  maxYear;
	}

	private int GetMinYear(List<CityRecord> data){
		int minYear = 3000;

		for (var entry : data) {

			if (entry.Year < minYear){
				minYear = entry.Year;

			}
		}
		return  minYear;
	}

	private String cleanRawValue(String rawValue) {
		return rawValue.trim();
	}
	
	private int convertToInt(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		return Integer.parseInt(rawValue);
	}
	
	private String convertToString(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		
		if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		
		return rawValue;
	}
	
	public static void main(String[] args) {


		CityCSVProcessor reader = new CityCSVProcessor();

		File dataDirectory = new File("Exercises/Java4/CSVProcessor/data");

		File csvFile = new File(dataDirectory, "Cities.csv");
		
		reader.readAndProcess(csvFile);
		reader.MapData();
		reader.ProcessData();
	}
}
