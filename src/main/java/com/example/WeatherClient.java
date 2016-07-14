package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.wsdl.GetCitiesByCountry;
import com.example.wsdl.GetCitiesByCountryResponse;

public class WeatherClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(WeatherClient.class);

	public GetCitiesByCountryResponse getCityForecastByZip(String countryName) {

		GetCitiesByCountry request = new GetCitiesByCountry();
		request.setCountryName(countryName);

		log.info("Requesting cities for " + countryName);

		GetCitiesByCountryResponse response = (GetCitiesByCountryResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						"http://www.webservicex.net/globalweather.asmx",
						request,
						new SoapActionCallback("http://www.webserviceX.NET/GetCitiesByCountry"));

		return response;
	}
	
	
	

	/*public void printResponse(GetCityForecastByZIPResponse response) {

		ForecastReturn forecastReturn = response.getGetCityForecastByZIPResult();

		if (forecastReturn.isSuccess()) {
			log.info("Forecast for " + forecastReturn.getCity() + ", " + forecastReturn.getState());

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for (Forecast forecast : forecastReturn.getForecastResult().getForecast()) {

				Temp temperature = forecast.getTemperatures();

				log.info(String.format("%s %s %s°-%s°", format.format(forecast.getDate().toGregorianCalendar().getTime()),
						forecast.getDesciption(), temperature.getMorningLow(), temperature.getDaytimeHigh()));
				log.info("");
			}
		} else {
			log.info("No forecast received");
		}
	}*/

}
