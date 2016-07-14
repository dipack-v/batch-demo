package com.example;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.example.wsdl.GetCitiesByCountryResponse;

public class PlaceItemProcessor implements ItemProcessor<GetCitiesByCountryResponse, CityCountry> {

    private static final Logger log = LoggerFactory.getLogger(PlaceItemProcessor.class);
    
    private Unmarshaller unmarshaller;

    public void setUnmarshaller(Jaxb2Marshaller jaxb2Marshaller) {
		this.unmarshaller = (Unmarshaller) jaxb2Marshaller;
	}


	@Override
    public CityCountry process(final GetCitiesByCountryResponse response) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>Processing<<<<<<<<<<<<<<<<<<<<<<<<<<");
    	CityCountry result = (CityCountry)unmarshaller.unmarshal(new StreamSource(new StringReader(response.getGetCitiesByCountryResult())));

        return result;
    }



}