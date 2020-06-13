package climatechange;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Scanner;


public class ClimateAnalyzer implements IClimateAnalyzer {

	public ArrayList<ITemperature> weatherList = new ArrayList<ITemperature>();
	public HashSet<String> uniqueCountryNames = new HashSet<String>();
	Scanner userInput;
	public final String[] shortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	public ClimateAnalyzer(){
		WeatherIO getWeather = new WeatherIO();
		
		userInput = new Scanner(System.in);
		
		System.out.println("Please input the file name of the data, or simply press enter to use the default 'world_temp_2000-2016.csv' file");
		String fileName = userInput.nextLine();
		do {
			//Default case
			if(fileName.equalsIgnoreCase("")) {
				weatherList = getWeather.readDataFromFile("world_temp_2000-2016.csv");
			}
			else {
				//If the file exists, use the data from that file
				File newFile = new File("./data/" + fileName);
				if(newFile.exists()) {
					weatherList = getWeather.readDataFromFile(fileName);
				}
				else {
					//If the file does not exist, ask user to try a new input
					System.out.println("Looks like the filename you gave could not be found. Please try again");
					fileName = userInput.nextLine();
				}
				
			}
		//Keep going until there are elements in weatherList to use
		} while(weatherList.size() == 0);
		
		//Creates a list of unique country names from data file.
		for(ITemperature temp : weatherList) {
			//HashSets do not contain duplicate values, so we can keep adding country names
			//Placed in all lowercase to check country names later on.
			uniqueCountryNames.add(temp.getCountry().toLowerCase());
		}
	}

	@Override
	public ITemperature getLowestTempByMonth(String country, int month) {
		ArrayList<ITemperature> monthWeather = new ArrayList<ITemperature>();
		
		//Get shortened month name
		String monthString = shortMonths[month-1];
		
		//Add all ITemperature objects with the same month and country name to monthWeather
		for(ITemperature temp : weatherList) {
			if(temp.getCountry().equalsIgnoreCase(country) && temp.getMonth().equalsIgnoreCase(monthString)) {
				monthWeather.add(temp);
			}
		}
		
		//Format monthWeather from lowest to highest
		Collections.sort(monthWeather);
		
		//First element has the lowest temperature
		ITemperature lowestWeather = monthWeather.get(0);
		return lowestWeather;
	}

	@Override
	public ITemperature getHighestTempByMonth(String country, int month) {
		ArrayList<ITemperature> monthWeather = new ArrayList<ITemperature>();
		
		//Get shortened month name
		String monthString = shortMonths[month-1];
		
		//Add all ITemperature objects with the same month and country name as given
		for(ITemperature temp : weatherList) {
			if(temp.getCountry().equalsIgnoreCase(country) && temp.getMonth().equalsIgnoreCase(monthString)) {
				monthWeather.add(temp);
			}
		}
		
		//Format monthWeather from lowest to highest
		Collections.sort(monthWeather);
		//Last element has the highest temperature
		ITemperature highestWeather = monthWeather.get(monthWeather.size()-1);
		return highestWeather;
	}

	@Override
	public ITemperature getLowestTempByYear(String country, int year) {
		ArrayList<ITemperature> yearWeather = new ArrayList<ITemperature>();
		
		//Add all ITemperature objects with the same year and country name as given 
		for(ITemperature temp : weatherList) {
			if(temp.getCountry().equalsIgnoreCase(country) && temp.getYear() == year) {
				yearWeather.add(temp);
			}
		}
		
		//Format yearWeather from lowest to highest
		Collections.sort(yearWeather);
		//First element has the lowest temperature
		ITemperature lowestWeather = yearWeather.get(0);
		return lowestWeather;
		
	}

	@Override
	public ITemperature getHighestTempByYear(String country, int year) {
		ArrayList<ITemperature> yearWeather = new ArrayList<ITemperature>();
		
		//Add all ITemperature objects with the same year and country name as given 
		for(ITemperature temp : weatherList) {
			if(temp.getCountry().equalsIgnoreCase(country) && temp.getYear() == year) {
				yearWeather.add(temp);
			}
		}
		
		//Format yearWeather from lowest to highest
		Collections.sort(yearWeather);
		//Last element has the highest temperature
		ITemperature highestWeather = yearWeather.get(yearWeather.size()-1);
		return highestWeather;
	}

	@Override
	public TreeSet<ITemperature> getTempWithinRange(String country, double rangeLowTemp, double rangeHighTemp) {
		TreeSet<ITemperature> tempRange = new TreeSet<ITemperature>();
		
		//Add all ITemperature objects within the temperature range and within the country given
		for(ITemperature temp : weatherList) {
			if(temp.getCountry().equalsIgnoreCase(country) && temp.getTemperature(false) >= rangeLowTemp && temp.getTemperature(false) <= rangeHighTemp) {
				tempRange.add(temp);
			}
		}
		
		return tempRange;
	}

	@Override
	public ITemperature getLowestTempYearByCountry(String country) {
		ArrayList<ITemperature> countryTemps = new ArrayList<ITemperature>();
		
		//Add all ITemperature objects belonging to given country
		for(ITemperature temp : weatherList) {
			if(temp.getCountry().equalsIgnoreCase(country)) {
				countryTemps.add(temp);
			}
		}
		
		//Format countryTemps from lowest to highest
		Collections.sort(countryTemps);
		
		//Lowest temperature is going to be the first element
		ITemperature lowestTemp = countryTemps.get(0);
		return lowestTemp;
	}

	public ITemperature getHighestTempYearByCountry(String country) {
		ArrayList<ITemperature> countryTemps = new ArrayList<ITemperature>();
		
		//Add all ITemperature objects belonging to given country
		for(ITemperature temp : weatherList) {
			if(temp.getCountry().equalsIgnoreCase(country)) {
				countryTemps.add(temp);
			}
		}
		
		//Format countryTemps from lowest to highest
		Collections.sort(countryTemps);
		
		//Highest temperature is going to be the last index
		ITemperature highestTemp = countryTemps.get(countryTemps.size()-1);
		return highestTemp;
	}
	
	@Override
	public ArrayList<ITemperature> allCountriesGetTop10LowestTemp(int month) {
		//countries is a unique country identifier. This means that a country cannot
		//be added to the topCountriesTemps ArrayList twice.
		ArrayList<String> countries = new ArrayList<String>();
		ArrayList<ITemperature> topCountriesTemps = new ArrayList<ITemperature>();
		ArrayList<ITemperature> monthWeather = new ArrayList<ITemperature>();
		String monthString = shortMonths[month-1];
		
				
		for(ITemperature temp : weatherList) {
			//If the month is correct, add to monthWeather
			if(temp.getMonth().equalsIgnoreCase(monthString)) {
				monthWeather.add(temp);
			}
		}
		//Sort monthWeather from lowest to highest temperature
		Collections.sort(monthWeather);
		//Counter to iterate through the monthWeather ArrayList until all 10 unique countries are found.
		int counter = 0;
		while(topCountriesTemps.size() < 10) {
			//If the country is not yet in the unique country list
			if(!countries.contains(monthWeather.get(counter).getCountry())) {
				//Adds country into unique country list and records the ITemperature object
				countries.add(monthWeather.get(counter).getCountry());
				topCountriesTemps.add(monthWeather.get(counter));
			}
			counter++;
		}
		return topCountriesTemps;
	}

	@Override
	public ArrayList<ITemperature> allCountriesGetTop10HighestTemp(int month) {
		//countries is a unique country identifier. This means that a country cannot
		//be added to the topCountriesTemps ArrayList twice.
		ArrayList<String> countries = new ArrayList<String>();
		ArrayList<ITemperature> topCountriesTemps = new ArrayList<ITemperature>();
		ArrayList<ITemperature> monthWeather = new ArrayList<ITemperature>();
		String monthString = shortMonths[month-1];
		
				
		for(ITemperature temp : weatherList) {
			//If the month is correct, add to monthWeather
			if(temp.getMonth().equalsIgnoreCase(monthString)) {
				monthWeather.add(temp);
			}
		}
		//Sort monthWeather from lowest to highest temperature
		Collections.sort(monthWeather);
		//Counter to iterate through the monthWeather ArrayList in reverse order until all 10 countries are found.
		int counter = monthWeather.size()-1;
		while(topCountriesTemps.size() < 10) {
			//If the country is not yet in the unique country list
			if(!countries.contains(monthWeather.get(counter).getCountry())) {
				//Adds country into unique country list and records the ITemperature object
				countries.add(monthWeather.get(counter).getCountry());
				topCountriesTemps.add(monthWeather.get(counter));
			}
			counter--;
		}
		
		//Format topCountriesTemps from lowest to highest
		Collections.sort(topCountriesTemps);
		
		return topCountriesTemps;
	}

	@Override
	public ArrayList<ITemperature> allCountriesGetTop10LowestTemp() {
		//countries is a unique country identifier. This means that a country cannot
		//be added to the topCountriesTemps ArrayList twice.
		ArrayList<String> countries = new ArrayList<String>();
		ArrayList<ITemperature> topCountriesTemps = new ArrayList<ITemperature>();
		//Because we are looking at all data available , we can use our original ArrayList
		ArrayList<ITemperature> weatherListCopy = weatherList;
		
		//Format weatherListCopy from lowest to highest
		Collections.sort(weatherListCopy);
		
		//Start from the beginning, as the countries with the lowest temperatures will be at the beginning.
		int counter = 0;
		while(topCountriesTemps.size() < 10) {
			//If the country is not yet in the unique country list
			if(!countries.contains(weatherListCopy.get(counter).getCountry())) {
				//Adds country into unique country list and records the ITemperature object
				countries.add(weatherListCopy.get(counter).getCountry());
				topCountriesTemps.add(weatherListCopy.get(counter));
			}
			counter++;
		}
		return topCountriesTemps;
		
	}

	@Override
	public ArrayList<ITemperature> allCountriesGetTop10HighestTemp() {
		//countries is a unique country identifier. This means that a country cannot
		//be added to the topCountriesTemps ArrayList twice.
		ArrayList<String> countries = new ArrayList<String>();
		ArrayList<ITemperature> topCountriesTemps = new ArrayList<ITemperature>();
		//Because we are looking at all data available , we can use our original ArrayList
		ArrayList<ITemperature> weatherListCopy = weatherList;
		
		//Format weatherListCopy from lowest to highest
		Collections.sort(weatherListCopy);
		
		//Start in reverse order, as the countries with the highest temperatures will be at the end.
		int counter = weatherListCopy.size()-1;
		while(topCountriesTemps.size() < 10) {
			//If the country is not yet in the unique country list
			if(!countries.contains(weatherListCopy.get(counter).getCountry())) {
				//Adds country into unique country list and records the ITemperature object
				countries.add(weatherListCopy.get(counter).getCountry());
				topCountriesTemps.add(weatherListCopy.get(counter));
			}
			counter--;
		}
		
		//Format topCountriesTemps from lowest to highest
		Collections.sort(topCountriesTemps);
		
		return topCountriesTemps;
	}

	@Override
	public ArrayList<ITemperature> allCountriesGetAllDataWithinTempRange(double lowRangeTemp, double highRangeTemp) {
		ArrayList<ITemperature> countryWeather = new ArrayList<ITemperature>();
		
		//Add all ITemperature objects have a temperature within the temperature range given.
		for(ITemperature temp : weatherList) {
			if(temp.getTemperature(false) >= lowRangeTemp && temp.getTemperature(false) <= highRangeTemp)
				countryWeather.add(temp);
		}
		//Sort countryWeather from lowest temperature to highest
		Collections.sort(countryWeather);
		return countryWeather;
	}

	@Override
	public ArrayList<ITemperature> allCountriesTop10TempDelta(int month, int year1, int year2) {
		ArrayList<ITemperature> countryData = new ArrayList<ITemperature>();
		ArrayList<ITemperature> tempChanges = new ArrayList<ITemperature>();
		String monthString = shortMonths[month-1];
		
		//Iterate through weatherList to get all ITemperature objects 
		//from the same month and years that were given
		for(ITemperature temp : weatherList) {
			if(temp.getMonth().equalsIgnoreCase(monthString) && (temp.getYear() == year1 || temp.getYear() == year2)) {
				countryData.add(temp);
			}
		}
		//Because the data is now formatted such that the two ITemperature objects from each country is right next to each other,
		//We can find the temperature change between the two.
		for(int i = 0; i < countryData.size(); i += 2) {
			//Get the two ITemperature objects from a country
			ITemperature temp1 = countryData.get(i);
			ITemperature temp2 = countryData.get(i+1);
			
			double changeInTemp = Math.abs(temp2.getTemperature(false) - temp1.getTemperature(false));
			int changeInYear = year2-year1;
			
			//Because temp1 and temp2 are from the same country in the same month, we can use either
			//to fill in the country name, the country 3 letter code, and the month.
			ITemperature tempChange = new Temperature(temp1.getCountry(), temp1.getCountry3LetterCode(), temp1.getMonth(), changeInYear, changeInTemp);
			
			//Add new ITemperature finding the change in temperature to tempChange
			tempChanges.add(tempChange);
		}
		
		//Format the ArrayList from lowest to highest
		Collections.sort(tempChanges);
		
		
		//There is no possibility that there are duplicate countries as there can only be one temperature change for each country
		ArrayList<ITemperature> topCountriesTemps = new ArrayList<ITemperature>();
		//Iterates the last 10 countries in tempChanges to topCountriesTemps
		for(int i = tempChanges.size()-10; i < tempChanges.size();i++) {
			topCountriesTemps.add(tempChanges.get(i));
		}
		
		return topCountriesTemps;
	}
	
	
	@Override
	public void runClimateAnalyzer() {		
		//Scanner userInput = new Scanner(System.in);
		WeatherIO writer = new WeatherIO();
		ArrayList<ITemperature> output = new ArrayList<ITemperature>();
		
		//Task A1
		System.out.println("TASK A1 pt. 1");
		System.out.println("Please input a country to find its lowest temperature for a specific month");
		String taskA1Country = userInput.nextLine();
		
		//Testing to make sure that the country is found in the data set.
		while(!uniqueCountryNames.contains(taskA1Country.toLowerCase())) {
			System.out.println("There doesn't seem to be any country of that name. Please try again");
			taskA1Country = userInput.nextLine();
		}
		
		System.out.println("Please input a month number, with 1 = January and 12 = December");
		//Testing to make sure an integer is inputted
		while(!userInput.hasNextInt()) {
			System.out.println("That's not an int! Please try again");
			userInput.nextLine();
		}
		int taskA1Month = userInput.nextInt();
		userInput.nextLine(); //consuming \n left behind by nextInt()
		
		//Testing to make sure that a proper month number is inputted
		while(taskA1Month < 1 || taskA1Month > 12) {
			System.out.println("It seems that your number does not correspond to a month. Please try again");
			//Testing to make sure an integer is inputted
			while(!userInput.hasNextInt()) {
				System.out.println("That's not an int! Please try again");
				userInput.nextLine();
			}
			taskA1Month = userInput.nextInt();
			userInput.nextLine(); //consuming \n left behind by nextInt()
		}
		
		output.add(this.getLowestTempByMonth(taskA1Country, taskA1Month));
		writer.writeSubjectHeaderInFile("taskA1_climate_info.csv", "Task A1: The lowest temperature in " + shortMonths[taskA1Month-1] + " for " + taskA1Country + " between 2000 and 2016");
		writer.writeDataToFile("taskA1_climate_info.csv", WeatherIO.HEAD, output);
		
		output.clear();
		//----------------------------------------
		System.out.println("TASK A1 pt. 2");
		System.out.println("Please input a country to find its highest temperature for a specific month");
		taskA1Country = userInput.nextLine();
		
		//Testing to make sure that the country is found in the data set.
		while(!uniqueCountryNames.contains(taskA1Country.toLowerCase())) {
			System.out.println("There doesn't seem to be any country of that name. Please try again");
			taskA1Country = userInput.nextLine();
		}
		
		System.out.println("\"Please input a month number, with 1 = January and 12 = December\"");
		//Testing to make sure an integer is inputted
		while(!userInput.hasNextInt()) {
			System.out.println("That's not an int! Please try again");
			userInput.nextLine();
		}
		taskA1Month = userInput.nextInt();
		userInput.nextLine(); //consuming \n left behind by nextInt()
		
		//Testing to make sure that a proper month number is inputted
		while(taskA1Month < 1 || taskA1Month > 12) {
			System.out.println("It seems that your number does not correspond to a month. Please try again");
			//Testing to make sure an integer is inputted
			while(!userInput.hasNextInt()) {
				System.out.println("That's not an int! Please try again");
				userInput.nextLine();
			}
			taskA1Month = userInput.nextInt();
			userInput.nextLine(); //consuming \n left behind by nextInt()
			
			while(!userInput.hasNextInt()) {
				System.out.println("That's not an int! Please try again");
				userInput.nextLine();
			}
			taskA1Month = userInput.nextInt();
			userInput.nextLine(); //consuming \n left behind by nextInt()
		}
		
		output.add(this.getHighestTempByMonth(taskA1Country, taskA1Month));
		
		writer.writeSubjectHeaderInFile("taskA1_climate_info.csv", "Task A1: The highest temperature in " + shortMonths[taskA1Month-1] + " for " + taskA1Country + " between 2000 and 2016");
		writer.writeDataToFile("taskA1_climate_info.csv", WeatherIO.HEAD, output);
		
		output.clear();
		
		
		//Task A2
		System.out.println("TASK A2 pt. 1");
		System.out.println("Please input a country to find its lowest temperature for a specific year in that country");
		String taskA2Country = userInput.nextLine();
		
		//Testing to make sure that the country is found in the data set.
		while(!uniqueCountryNames.contains(taskA2Country.toLowerCase())) {
			System.out.println("There doesn't seem to be any country of that name. Please try again");
			taskA2Country = userInput.nextLine();
		}
		
		System.out.println("Please input a year between 2000 and 2016 to get the lowest temperature for that year in the given country");
		//Testing to make sure an integer is inputted
		while(!userInput.hasNextInt()) {
			System.out.println("That's not an int! Please try again");
			userInput.nextLine();
		}
		int taskA2Year = userInput.nextInt();
		userInput.nextLine(); //consuming \n left behind by nextInt()
		
		//Testing to make sure that a year within the range of the data is selected
		while(taskA2Year < 2000 || taskA2Year > 2016) {
			System.out.println("It seems you have inputted a year not between 2000 and 2016. Please try again");
			
			//Testing to make sure an integer is inputted
			while(!userInput.hasNextInt()) {
				System.out.println("That's not an int! Please try again");
				userInput.nextLine();
			}
			
			taskA2Year = userInput.nextInt();
			userInput.nextLine(); //consuming \n left behind by nextInt()
		}
		
		output.add(this.getLowestTempByYear(taskA2Country, taskA2Year));
		writer.writeSubjectHeaderInFile("taskA2_climate_info.csv", "Task A2: The lowest temperature for the year " + taskA2Year + " in " + taskA2Country);
		writer.writeDataToFile("taskA2_climate_info.csv", WeatherIO.HEAD, output);
		
		output.clear();
		
		//----------------------------------------
		System.out.println("TASK A2 pt. 2");
		System.out.println("Please input a country to find its highest temperature for a specific year in that country");
		 taskA2Country = userInput.nextLine();
		
		//Testing to make sure that the country is found in the data set.
		while(!uniqueCountryNames.contains(taskA2Country.toLowerCase())) {
			System.out.println("There doesn't seem to be any country of that name. Please try again");
			taskA2Country = userInput.nextLine();
		}
		
		System.out.println("Please input a year between 2000 and 2016 to get the lowest temperature for that year in the given country");
		//Testing to make sure an integer is inputted
		while(!userInput.hasNextInt()) {
			System.out.println("That's not an int! Please try again");
			userInput.nextLine();
		}
		 taskA2Year = userInput.nextInt();
		 userInput.nextLine(); //consuming \n left behind by nextInt()
		
		//Testing to make sure that a year within the range of the data is selected
		while(taskA2Year < 2000 || taskA2Year > 2016) {
			System.out.println("It seems you have inputted a year not between 2000 and 2016. Please try again");
			//Testing to make sure an integer is inputted
			while(!userInput.hasNextInt()) {
				System.out.println("That's not an int! Please try again");
				userInput.nextLine();
			}
			taskA2Year = userInput.nextInt();
			userInput.nextLine(); //consuming \n left behind by nextInt()
		}
		
		output.add(this.getHighestTempByYear(taskA2Country, taskA2Year));
		writer.writeSubjectHeaderInFile("taskA2_climate_info.csv", "Task A2: The highest temperature for the year " + taskA2Year + " in " + taskA2Country);
		writer.writeDataToFile("taskA2_climate_info.csv", WeatherIO.HEAD, output);
		
		output.clear();
		
		//Task A3
		//Ask for country, rangeLowTemp, rangeHighTemp
		System.out.println("TASK A3");
		System.out.println("Please input a country that you would like to find temperature range data from");
		String taskA3Country = userInput.nextLine();
		
		//Testing to make sure that the country is found in the data set.
		while(!uniqueCountryNames.contains(taskA3Country.toLowerCase())) {
			System.out.println("There doesn't seem to be any country of that name. Please try again");
			taskA3Country = userInput.nextLine();
		}
		
		System.out.println("Please input your lower range of of the temperature range you would like in celsius");
		//Testing to make sure a double is inputted
		while(!userInput.hasNextDouble()) {
			System.out.println("That's not an double! Please try again");
			userInput.nextLine();
		}
		double taskA3RangeLowTemp = userInput.nextDouble();
		userInput.nextLine(); //consuming \n left behind by nextDouble()
		
		System.out.println("Please input your upper range of the temperature range you would like in celsius");
		//Testing to make sure a double is inputted
		while(!userInput.hasNextDouble()) {
			System.out.println("That's not an double! Please try again");
			userInput.nextLine();
		}
		double taskA3RangeHighTemp = userInput.nextDouble();
		userInput.nextLine(); //consuming \n left behind by nextDouble()
		
		//Testing to make sure the data range is valid
		while(taskA3RangeHighTemp < taskA3RangeLowTemp) {
			System.out.println("It seems that you have inputted an upper range that is smaller than your larger range. Please try again");
			
			System.out.println("Please input your lower range of of the temperature range you would like in celsius");
			//Testing to make sure a double is inputted
			while(!userInput.hasNextDouble()) {
				System.out.println("That's not an double! Please try again");
				userInput.nextLine();
			}
			
			taskA3RangeLowTemp = userInput.nextDouble();
			userInput.nextLine(); //consuming \n left behind by nextDouble()
			
			System.out.println("Please input your upper range of the temperature range you would like in celsius");
			//Testing to make sure a double is inputted
			while(!userInput.hasNextDouble()) {
				System.out.println("That's not an double! Please try again");
				userInput.nextLine();
			}
			
			taskA3RangeHighTemp = userInput.nextDouble();
			userInput.nextLine(); //consuming \n left behind by nextDouble()
		}
		
		output = new ArrayList<ITemperature>(this.getTempWithinRange(taskA3Country, taskA3RangeLowTemp, taskA3RangeHighTemp));
		writer.writeSubjectHeaderInFile("taskA3_climate_info.csv", "Task A3: all data ranging from " + taskA3RangeLowTemp + " to " + taskA3RangeHighTemp + " degrees celsius in " + taskA3Country);
		writer.writeDataToFile("taskA3_climate_info.csv", WeatherIO.HEAD, output);
		
		output.clear();
		
		//Task A4
		//Ask for country
		System.out.println("TASK A4 pt. 1");
		System.out.println("Please input a country in which you would like to get the lowest temperature between 2000 and 2016");
		String taskA4Country = userInput.nextLine();
		
		//Testing to make sure that the country is found in the data set.
		while(!uniqueCountryNames.contains(taskA4Country.toLowerCase())) {
			System.out.println("There doesn't seem to be any country of that name. Please try again");
			taskA4Country = userInput.nextLine();
		}
		
		output.add(this.getLowestTempYearByCountry(taskA4Country));
		writer.writeSubjectHeaderInFile("taskA4_climate_info.csv", "Task A4: lowest temperature in " + taskA4Country + " between 2000 and 2016");
		writer.writeDataToFile("taskA4_climate_info.csv", WeatherIO.HEAD, output);
		output.clear();
		
		//----------------------------
		System.out.println("TASK A4 pt. 2");
		System.out.println("Please input a country in which you would like to get the highest temperature between 2000 and 2016");
		taskA4Country = userInput.nextLine();
		
		//Testing to make sure that the country is found in the data set.
		while(!uniqueCountryNames.contains(taskA4Country.toLowerCase())) {
			System.out.println("There doesn't seem to be any country of that name. Please try again");
			taskA4Country = userInput.nextLine();
		}
		
		output.add(this.getHighestTempYearByCountry(taskA4Country));
		writer.writeSubjectHeaderInFile("taskA4_climate_info.csv", "Task A4: highest temperature in " + taskA4Country + " between 2000 and 2016");
		writer.writeDataToFile("taskA4_climate_info.csv", WeatherIO.HEAD, output);
		
		output.clear();
		
		//Task B1
		System.out.println("TASK B1 pt. 1");
		System.out.println("\"Please input a month number, with 1 = January and 12 = December\"");
		//Testing to make sure an integer is inputted
		while(!userInput.hasNextInt()) {
			System.out.println("That's not an int! Please try again");
			userInput.nextLine();
		}
		int taskB1Month = userInput.nextInt();
		userInput.nextLine(); //consuming \n left behind by nextInt()
		
		//Testing to make sure that a proper month number is inputted
		while(taskB1Month < 1 || taskB1Month > 12) {
			System.out.println("It seems that your number does not correspond to a month. Please try again");
			//Testing to make sure an integer is inputted
			while(!userInput.hasNextInt()) {
				System.out.println("That's not an int! Please try again");
				userInput.nextLine();
			}
			taskB1Month = userInput.nextInt();
			userInput.nextLine(); //consuming \n left behind by nextInt()
		}
		
		output = this.allCountriesGetTop10LowestTemp(taskB1Month);
		writer.writeSubjectHeaderInFile("taskB1_climate_info.csv", "Task B1: Top 10 countries with the lowest temperatures in the month of " + shortMonths[taskB1Month-1]);
		writer.writeDataToFile("taskB1_climate_info.csv", WeatherIO.HEAD, output);
		output.clear();
		
		//-------------------------------------------
		System.out.println("TASK B1 pt. 2");
		System.out.println("\"Please input a month number, with 1 = January and 12 = December");
		//Testing to make sure an integer is inputted
		while(!userInput.hasNextInt()) {
			System.out.println("That's not an int! Please try again");
			userInput.nextLine();
		}
		taskB1Month = userInput.nextInt();
		userInput.nextLine(); //consuming \n left behind by nextInt()
		
		//Testing to make sure that a proper month number is inputted
		while(taskB1Month < 1 || taskB1Month > 12) {
			System.out.println("It seems that your number does not correspond to a month. Please try again");
			//Testing to make sure an integer is inputted
			while(!userInput.hasNextInt()) {
				System.out.println("That's not an int! Please try again");
				userInput.nextLine();
			}
			taskB1Month = userInput.nextInt();
			userInput.nextLine(); //consuming \n left behind by nextInt()
		}
		
		output = this.allCountriesGetTop10HighestTemp(taskB1Month);
		writer.writeSubjectHeaderInFile("taskB1_climate_info.csv", "Task B1: Top 10 countries with the highest temperatures in the month of " + shortMonths[taskB1Month-1] + " between 2000 and 2016");
		writer.writeDataToFile("taskB1_climate_info.csv", WeatherIO.HEAD, output);
		output.clear();
		
		
		//Task B2
		System.out.println("TASK B2 pt. 1");
		System.out.println("No user input needed press enter to keep on going");
		userInput.nextLine();
		output = this.allCountriesGetTop10LowestTemp();
		writer.writeSubjectHeaderInFile("taskB2_climate_info.csv", "Task B2: Top 10 countries with the lowest temperatures overall between 2000 and 2016");
		writer.writeDataToFile("taskB2_climate_info.csv", WeatherIO.HEAD, output);
		
		output.clear();
		
		//-----------------------------------------
		System.out.println("TASK B2 pt. 2");
		System.out.println("No user input needed press enter to keep on going");
		userInput.nextLine();
		output = this.allCountriesGetTop10HighestTemp();
		writer.writeSubjectHeaderInFile("taskB2_climate_info.csv", "Task B2: Top 10 countries with the highest temperatures overall between 2000 and 2016");
		writer.writeDataToFile("taskB2_climate_info.csv", WeatherIO.HEAD, output);
		
		output.clear();
		
		//Task B3
		//Ask for lowRangeTemp, highRangeTemp
		System.out.println("TASK B3");
		System.out.println("Please input your lower range of of the temperature range you would like in celsius");
		//Testing to make sure a double is inputted
		while(!userInput.hasNextDouble()) {
			System.out.println("That's not an double! Please try again");
			userInput.nextLine();
		}
		
		double taskB3LowRangeTemp = userInput.nextDouble();
		userInput.nextLine(); //consuming \n left behind by nextDouble()
		
		System.out.println("Please input your upper range of the temperature range you would like in celsius");
		//Testing to make sure a double is inputted
		while(!userInput.hasNextDouble()) {
			System.out.println("That's not an double! Please try again");
			userInput.nextLine();
		}
		
		double taskB3HighRangeTemp = userInput.nextDouble();
		userInput.nextLine(); //consuming \n left behind by nextDouble()
		
		//Testing to make sure the data range is valid
		while(taskB3HighRangeTemp < taskB3LowRangeTemp) {
			System.out.println("It seems that you have inputted an upper range that is smaller than your larger range. Please try again");
			
			System.out.println("Please input your lower range of of the temperature range you would like in celsius");
			//Testing to make sure a double is inputted
			while(!userInput.hasNextDouble()) {
				System.out.println("That's not an double! Please try again");
				userInput.nextLine();
			}
			
			taskB3LowRangeTemp = userInput.nextDouble();
			userInput.nextLine(); //consuming \n left behind by nextDouble()
			
			System.out.println("Please input your upper range of the temperature range you would like in celsius");
			//Testing to make sure a double is inputted
			while(!userInput.hasNextDouble()) {
				System.out.println("That's not an double! Please try again");
				userInput.nextLine();
			}
			
			taskB3HighRangeTemp = userInput.nextDouble();
			userInput.nextLine(); //consuming \n left behind by nextDouble()
		}
		
		output = this.allCountriesGetAllDataWithinTempRange(taskB3LowRangeTemp, taskB3HighRangeTemp);
		writer.writeSubjectHeaderInFile("taskB3_climate_info.csv", "Task B3: All data within the temperature range of " + taskB3LowRangeTemp + " and " + taskB3HighRangeTemp + " degrees celsius between 2000 and 2016");
		writer.writeDataToFile("taskB3_climate_info.csv", WeatherIO.HEAD, output);

		output.clear();
		
		
		//Task C1
		//Ask for month, year1, and year2
		System.out.println("TASK C1");
		System.out.println("\"Please input a month number, with 1 = January and 12 = December");
		//Testing to make sure an integer is inputted
		while(!userInput.hasNextInt()) {
			System.out.println("That's not an int! Please try again");
			userInput.nextLine();
		}
		
		int taskC1Month = userInput.nextInt();
		userInput.nextLine(); //consuming \n left behind by nextInt()
		
		//Testing to make sure that a proper month number is inputted
		while(taskC1Month < 1 || taskC1Month > 12) {
			System.out.println("It seems that your number does not correspond to a month. Please try again");
			//Testing to make sure an integer is inputted
			while(!userInput.hasNextInt()) {
				System.out.println("That's not an int! Please try again");
				userInput.nextLine();
			}
			
			taskC1Month = userInput.nextInt();
			userInput.nextLine(); //consuming \n left behind by nextInt()
		}
		
		System.out.println("Please pick a year between 2000 and 2016");
		//Testing to make sure an integer is inputted
		while(!userInput.hasNextInt()) {
			System.out.println("That's not an int! Please try again");
			userInput.nextLine();
		}
		
		int taskC1Year1 = userInput.nextInt();
		
		System.out.println("Please pick a second year after the first year but still between 2000 and 2016");
		//Testing to make sure an integer is inputted
		while(!userInput.hasNextInt()) {
			System.out.println("That's not an int! Please try again");
			userInput.nextLine();
		}
		
		int taskC1Year2 = userInput.nextInt();
		
		//Testing to make sure that two valid years are set
		while(taskC1Year1 > taskC1Year2 || taskC1Year1 < 2000 || taskC1Year1 > 2016 || taskC1Year2 < 2000 || taskC1Year2 > 2016) {
			if(taskC1Year1 > taskC1Year2) {
				System.out.println("It looks like your second year is greater than your first year. Please try again");
				System.out.println("Please pick a year between 2000 and 2016");
				//Testing to make sure an integer is inputted
				while(!userInput.hasNextInt()) {
					System.out.println("That's not an int! Please try again");
					userInput.nextLine();
				}
				
				taskC1Year1 = userInput.nextInt();
				userInput.nextLine(); //consuming \n left behind by nextInt()
				System.out.println("Please pick a second year after the first year but still between 2000 and 2016");
				//Testing to make sure an integer is inputted
				while(!userInput.hasNextInt()) {
					System.out.println("That's not an int! Please try again");
					userInput.nextLine();
				}
				
				taskC1Year2 = userInput.nextInt();
				userInput.nextLine(); //consuming \n left behind by nextInt()
			}
			else {
				System.out.println("It looks like one of your years are out of the bounds of the data set. Please try again");
				System.out.println("Please pick a year between 2000 and 2016");
				//Testing to make sure an integer is inputted
				while(!userInput.hasNextInt()) {
					System.out.println("That's not an int! Please try again");
					userInput.nextLine();
				}
				
				taskC1Year1 = userInput.nextInt();
				userInput.nextLine(); //consuming \n left behind by nextInt()
				System.out.println("Please pick a second year after the first year but still between 2000 and 2016");
				//Testing to make sure an integer is inputted
				while(!userInput.hasNextInt()) {
					System.out.println("That's not an int! Please try again");
					userInput.nextLine();
				}
				
				taskC1Year2 = userInput.nextInt();
				userInput.nextLine(); //consuming \n left behind by nextInt()
			}
		}
		output = this.allCountriesTop10TempDelta(taskC1Month, taskC1Year1, taskC1Year2);
		writer.writeSubjectHeaderInFile("taskC1_climate_info.csv", "Task C1: The top 10 countries with the largest difference in temperature between " + shortMonths[taskC1Month-1] + " " + taskC1Year1 + " and " + shortMonths[taskC1Month-1] + " " + taskC1Year2);
		writer.writeDataToFile("taskC1_climate_info.csv", WeatherIO.HEAD2, output); 
		
		userInput.close();
		System.out.println("runClimateAnalyzer has now finished");
	}

	public static void main(String[] args) {
		ClimateAnalyzer analyze = new ClimateAnalyzer();
		analyze.runClimateAnalyzer();
		
	}

}
