package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityCSVProcessor {

	List<CityRecord> AllRecords = new ArrayList<CityRecord>();

	Map<String, List<CityRecord>> CityDictionary = new HashMap<String, List<CityRecord>>();
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
		CityDictionary.clear();

		for (var record : AllRecords){

			// If city is not in dictionary
			if (!CityDictionary.containsKey(record.City)){

				CityDictionary.put(record.City, new ArrayList<CityRecord>());
			}

			var cityList = CityDictionary.get(record.City);
			cityList.add(record);

		}

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
	}
}
