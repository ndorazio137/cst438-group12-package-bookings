package cst438.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import cst438.domain.CarInfo;
import cst438.domain.FlightInfo;
import cst438.domain.HotelInfo;

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
   
   public List<FlightInfo> getAvailableFlights(String fromCity, String toCity, Date date) {
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
      List<FlightInfo> flightList = new ArrayList<FlightInfo>();
      for (JsonNode item : json)
      { 
          long id = item.get("flightId").asInt();
          String flightNumber = item.get("flightNumber").toString();
          String departureCity = item.get("departureCity").toString();
          String arrivalCity = item.get("arrivalCity").toString();
          String departureDate = item.get("departureDate").toString();
          String arrivalDate = item.get("arrivalDate").toString();
          String duration = item.get("duration").toString();
          int distance = item.get("distance").asInt();
          int cost = item.get("cost").asInt();
          int totalSeats = item.get("totalSeats").asInt();
          FlightInfo flightInfo = new FlightInfo(
                id,
                flightNumber, 
                departureCity, 
                arrivalCity, 
                departureDate,
                arrivalDate, 
                duration, 
                distance, 
                cost, 
                totalSeats
          );
          flightList.add(flightInfo);
//          System.out.println("new flightNumber added: " + flightNumber);
          System.out.println("new HotelInfo added: " + flightInfo);
          
      }
      System.out.println("hotelList added: " + flightList);
      return flightList;
   }

}
