package cst438.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class CarService {

	private static final Logger log =
                LoggerFactory.getLogger(CarService.class);
	private RestTemplate restTemplate;
	private String carUrl;
	private String apiKey;
	
	public CarService(    //1
			@Value("${car.url}") final String weatherUrl, 
			@Value("${car.apikey}") final String apiKey ) {
		this.restTemplate = new RestTemplate();
		this.carUrl = weatherUrl;
		this.apiKey = apiKey; 
	}

	public List<Object> getAvailableCars(String cityName, Date date) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
