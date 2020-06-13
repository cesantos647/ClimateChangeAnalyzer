/**
 * 
 */
package climatechange;
import java.util.ArrayList;

/**
 * @author Christian Santos
 *
 */
public class Temperature implements ITemperature, Comparable<ITemperature>{
	
	private String country;
	private String countryCode;
	private String month;
	private int year;
	private double temperature;
	public final String[] shortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	
	public Temperature(String country, String countryCode, String month, int year, double temperature) {
		this.country = country;
		this.countryCode = countryCode;
		this.month = month;
		this.year = year;
		this.temperature = temperature;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public String getCountry3LetterCode() {
		return countryCode;
	}

	@Override
	public String getMonth() {
		return month;
	}

	@Override
	public int getYear() {
		return year;
	}

	@Override
	public double getTemperature(boolean getFahrenheit) {
		
		//If getFahrenheit is true, convert to Fahrenheit
		if(getFahrenheit) {
			//The conversion from celsius to fahrenheit is
			//multiplying by 1.8 (9/5) and adding 32;
			double fahrenheit = (temperature * 1.8) + 32;
			return fahrenheit;
		}
		
		//getFahrenheit is false, return the temperature in celsius
		return temperature;
	}

	//DONE
	@Override
	public int compareTo(ITemperature t) {
		if(this.equals(t)) {
			return 0;
		}
		
		//If there is a tie in the temperature reading, first go by country name, then by year,
		//then go by month
		if(this.getTemperature(false) == t.getTemperature(false)) {
			
			//Because temperature readings are the same, go by country name
			if(this.getCountry().equalsIgnoreCase(t.getCountry())) {
				
				//Because country names are the same, go by year
				if(this.getYear() == this.getYear()) {
					//Because years are the same, go by month
					
					//Get list of shortened months and add it into ArrayList
					ArrayList<String> monthList = new ArrayList<String>();
					for(String month : shortMonths) {
						monthList.add(month);
					}
					
					//If month is earlier, return positive integer. Else return negative integer
					return monthList.indexOf(t.getMonth()) - monthList.indexOf(this.getMonth());
				}
				else {
					//If year is earlier, return positive integer. Else return negative integer
					return t.getYear() - this.getYear();
				}
			}
			else {
				//Compare strings lexicographically. Return positive integer if lexicographically greater, else return negative integer
				return this.getCountry().compareToIgnoreCase(t.getCountry());
			}	
		}
		
		else {

			//If this.getTemperature is greater, return 1, otherwise return -1
			double delta = this.getTemperature(false) - t.getTemperature(false);
			if(delta > 0) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}
	
	
	public boolean equals(Temperature t) {
		if (this == t) {
			return true;
		}
		
		return (this.getCountry().equalsIgnoreCase(t.getCountry())) &&
				(this.getCountry3LetterCode().equalsIgnoreCase(t.getCountry3LetterCode())) &&
				(this.getMonth().equalsIgnoreCase(t.getMonth())) &&
				(this.getTemperature(false) == t.getTemperature(false)) &&
				(this.getYear() == this.getYear());
		
	}

	@Override
	public String toString() {	
		//Format temperature to 2 decimal places
		return String.format("%.2f", this.getTemperature(false)) + "(C) " + 
				String.format("%.2f", this.getTemperature(true)) + "(F)," + 
				year + "," + month + "," + country + "," + countryCode;
	}

	@Override
	//For overriding hashCode, you need to choose a prime, usually 31
	//Read more: https://www.java67.com/2013/04/example-of-overriding-equals-hashcode-compareTo-java-method.html#ixzz6KWnE5QhZ
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + country.hashCode();
		result = prime * result + countryCode.hashCode();
		result = prime * result + month.hashCode();
		result = prime * result + Double.hashCode(temperature);
		result = prime * result + year;
		return result;
	}
	

}
