package uni.fmi.masters.entity;

import java.io.Serializable;

public class StatusBean implements Serializable{
	
	private double temp;
	private String city;
	private String description;
	
	public StatusBean(double temp, String city, String description) {
		super();
		this.temp = temp;
		this.city = city;
		this.description = description;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
