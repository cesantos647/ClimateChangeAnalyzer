/**
 * 
 */
package climatechange;

import java.util.ArrayList;
import java.io.*;

/**
 * @author Christian Santos
 *
 */
public class WeatherIO implements IWeatherIO {
	static String HEAD = "Temperature,Year,Month,Country,Country_Code";
	static String HEAD2 = "Temperature Delta, Year Delta, Month, Country, Country_Code";

	@Override
	public ArrayList<ITemperature> readDataFromFile(String fileName){
		
		ArrayList<ITemperature> data = new ArrayList<ITemperature>();
		
		File dataFile = new File("./data/" + fileName);
		try {
			//If the file is not found, a FileNotFoundException will be thrown
			FileReader dataFileReader = new FileReader(dataFile);
			BufferedReader dataBufferedReader = new BufferedReader(dataFileReader);
			
			//This is the header of the file. It doesn't need to be read
			dataBufferedReader.readLine();

			//Iterates through the whole file. With each line, a ITemperature object is created
			String line = dataBufferedReader.readLine();
			
			//Checks for the end of file null statement
			while(line != null) {
				
				//Parses the line with a delimiter
				String[] arr = line.split(", ");
				
				//Creates all the variables needed to create ITemperature object, then
				//creates the ITemperature object and adds it to the ArrayList.
				double temperature = Double.parseDouble(arr[0]);
				int year = Integer.parseInt(arr[1]);
				String month = arr[2];
				String country = arr[3];
				String countryCode = arr[4];
				ITemperature temp = new Temperature(country, countryCode, month, year, temperature);
				data.add(temp);
				
				//Moves on to the next line
				line = dataBufferedReader.readLine();
				
			}
			
			//Closes all file readers
			dataFileReader.close();
			dataBufferedReader.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}

	@Override
	//Writes the subject header at the top of the file.
	public void writeSubjectHeaderInFile(String filename, String subject){
		File writeFile = new File("./data/" + filename);
		
		FileWriter writeFileWriter = null;
		BufferedWriter writeBufferedWriter = null;
		
		//If the file already exists, append to file.
		if(writeFile.exists()) {
			try {
				writeFileWriter = new FileWriter(writeFile, true);
				writeBufferedWriter = new BufferedWriter(writeFileWriter);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//If not, create a new file with this filename.
		else {
			try {
				writeFile.createNewFile();
				writeFileWriter = new FileWriter(writeFile);
				writeBufferedWriter = new BufferedWriter(writeFileWriter);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			//Add subject header to file.
			writeBufferedWriter.write(subject);
			writeBufferedWriter.newLine();
			
			//Close the writer
			writeBufferedWriter.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void writeDataToFile(String filename, String topic, ArrayList<ITemperature> theWeatherList){
		//This should also not be too bad. Just create a comma separated line with all the information
		//that is needed, then write it into the file.
		//Also use "topic" to create your header
		//head should be "Temperature,Year,Month,Country,Country_Code"
		
		FileWriter newFileWriter;
		BufferedWriter newBufferedWriter = null;
		
		//Open the file for writing
		File newFile = new File("./data/" + filename);
		if(newFile.exists()) {
			try {
				newFileWriter = new FileWriter(newFile, true);
				newBufferedWriter = new BufferedWriter(newFileWriter);
			} catch (IOException e) {
				e.printStackTrace();
				//throw new IOException("There was trouble creating the BufferedWriter");
			}
		} else {
			//throw new IOException("There was no file with that filename");
		}
		
		try {
			//Add the header to the file
			newBufferedWriter.write(topic);
			newBufferedWriter.newLine();
		
			//Add all the temperatures from theWeatherList to the file.
			for(ITemperature temp: theWeatherList) {
				newBufferedWriter.write(temp.toString());
				newBufferedWriter.newLine();
			}
			
			//Close the BufferedWriter
			newBufferedWriter.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}
	

}
