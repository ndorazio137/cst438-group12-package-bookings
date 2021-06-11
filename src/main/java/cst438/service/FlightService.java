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
public class FlightService {

   private static final Logger log =
         LoggerFactory.getLogger(CarService.class);
   private RestTemplate restTemplate;
   private String flightUrl;
   
   public FlightService( 
         @Value("${flight.url}") final String flightUrl) {
      this.restTemplate = new RestTemplate();
      this.flightUrl = flightUrl;
   }
   
   public List<Object> getAvailableFlights(String fromCity, String toCity, Date date) {
      System.out.println("FlightService.getAvailableFlights(...): Getting available flights...");
      ResponseEntity<JsonNode> response =
            restTemplate.getForEntity(
                  flightUrl + "?fromCity=" + fromCity + "&toCity=" + toCity 
                     + "&arrivalDate=" + date, 
                  JsonNode.class);
      JsonNode json = response.getBody();
      System.out.println("Status code from flight server: " + 
            response.getStatusCodeValue());
      log.info("Status code from flight server:" +
            response.getStatusCodeValue());
      //TODO: finish when known api structure exists
      return null;
   }

}
