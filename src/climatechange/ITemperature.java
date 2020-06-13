package climatechange;

public interface ITemperature extends Comparable<ITemperature> {
	
	public String getCountry(); // get the name of the country
	
	public String getCountry3LetterCode(); // get the 3-letter code of the country
	
	public String getMonth(); // get the month
	
	public int getYear(); // get the year
	
	public double getTemperature(boolean getFahrenheit); // get temperature; input parameter of false = return celsius value

}
