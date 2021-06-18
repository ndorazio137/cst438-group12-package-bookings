package cst438.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cst438.domain.CarInfo;
import cst438.domain.FlightInfo;

@Service
public class DemoFlightService {

   private static final Logger log =
         LoggerFactory.getLogger(CarService.class);
   private RestTemplate restTemplate;
   private String flightUrl;
   
   public DemoFlightService( 
         @Value("${flight.url}") final String flightUrl) {
      this.restTemplate = new RestTemplate();
      this.flightUrl = flightUrl;
   }
   
   public List<FlightInfo> getAvailableFlights(String fromCity, String toCity, Date date, int passengers) {
System.out.println("DemoFlightService.getAvailableFlights(...): Getting available flights...");
      
      ArrayList<FlightInfo> flightList = new ArrayList<FlightInfo>();
      flightList.add(new FlightInfo(318, "AA 7721", fromCity, toCity, date.toString(), date.toString(), "6h 11m", 2472, 694, 5, 1, 4));
      flightList.add(new FlightInfo(320, "AA 7721", fromCity, toCity, date.toString(), date.toString(), "6h 41m", 2472, 694, 4, 1, 3));
      flightList.add(new FlightInfo(321, "AA 7721", fromCity, toCity, date.toString(), date.toString(), "6h 41m", 2472, 694, 5, 2, 3));
      
      return flightList;
   }

   public JsonNode bookFlight(String email, String password, String site, 
         String firstName, String lastName, long flightId, int passengers) {
      System.out.println("DemoFlightService.bookFlight(...): booking flights...");
      
      ObjectMapper objectMapper = new ObjectMapper();
      
      JsonNode json;
      
      // hardcoded for demo / manual testing
      String responseBody = "{\"id\": 362}";
      
      try {
         json = objectMapper.readTree(responseBody);
         System.out.println(json);
         return json;
      } catch (JsonMappingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }
   
   public JsonNode deleteReservation(String id, String email, String password, String site) {
      System.out.println("DemoFlightService.deleteReservation(...): deleting reservation...");
      ObjectMapper objectMapper = new ObjectMapper();
      
      JsonNode json;
      
      // hardcoded for demo / manual testing
      String responseBody = "{\"status\": 200}";
      
      try {
         json = objectMapper.readTree(responseBody);
         System.out.println(json);
         return json;
      } catch (JsonMappingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
      
   }
}
