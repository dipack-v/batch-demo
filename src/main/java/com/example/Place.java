package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Table")
@XmlAccessorType (XmlAccessType.FIELD)
@Entity
public class Place {
	@Id
	@GeneratedValue
    private Long id;
	@XmlElement(name="Country")  
	private String country;
	@XmlElement(name="City") 
	private String city;
	@ManyToOne
	private CityCountry cityCountry;
	public Place() {
		super();
	}
	
	
	public Place(String country, String city) {
		super();
		this.id = id;
		this.country = country;
		this.city = city;
	}


	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	


	@Override
	public String toString() {
		return "Place [country=" + country + ", city=" + city + "]";
	}
	
	

}
