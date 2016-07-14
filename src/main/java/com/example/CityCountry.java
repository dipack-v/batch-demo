package com.example;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="NewDataSet")
@XmlAccessorType (XmlAccessType.FIELD)
@Entity
public class CityCountry {
	@Id
	@GeneratedValue
    private Long id;
	
	@XmlElement(name="Table")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cityCountry", orphanRemoval = true, fetch=FetchType.EAGER)
	List<Place> places;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	
}
