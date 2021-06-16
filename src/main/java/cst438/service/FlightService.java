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
import cst438.domain.FlightInfo;

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
   
   public List<FlightInfo> getAvailableFlights(String fromCity, String toCity, Date date, int passengers) {
      System.out.println("FlightService.getAvailableFlights(...): Getting available flights...");
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
      String dateString = formatter.format(date);
      ResponseEntity<JsonNode> response =
            restTemplate.getForEntity(
                  flightUrl + "/searchflights" + "?date=" + dateString + "&departureCity=" + fromCity + "&arrivalCity=" + toCity + "&passengers=" + passengers, 
                  JsonNode.class);
      System.out.println(response);
      JsonNode json = response.getBody();
      log.info("Status code from flight server:" +
         response.getStatusCodeValue());
      System.out.println(json);
      List<FlightInfo> flightList = new ArrayList<FlightInfo>();
      JsonNode flights = json.get("flights");
      FlightInfo flightInfo;
      
      if (response.getStatusCodeValue() == 500) {
         System.out.println("Flights returned empty array list");
         return flightList;
      }
      
      for (JsonNode item : flights)
      { 
          Long id = item.get("id").asLong();
          String flightNumber = item.get("flightNumber").toString();
          String departureCity = item.get("departureCity").toString();
          String arrivalCity = item.get("arrivalCity").toString();
          String departureDate = item.get("departureDate").toString();
          String arrivalDate = item.get("arrivalDate").toString();
          String duration = item.get("duration").toString();
          int distance = item.get("distance").asInt();
          int cost = item.get("cost").asInt();
          int totalSeats = item.get("totalSeats").asInt();
          int reservedSeats = item.get("reservedSeats").asInt();
          int remainingSeats = item.get("remainingSeats").asInt();
          flightInfo = new FlightInfo(
                id,
                flightNumber, 
                departureCity, 
                arrivalCity, 
                departureDate,
                arrivalDate, 
                duration, 
                distance, 
                cost, 
                totalSeats,
                reservedSeats,
                remainingSeats
          );
          flightList.add(flightInfo);
          System.out.println("new FlightInfo added: " + flightInfo);
          
      }
      System.out.println("flightList added: " + flightList);
      return flightList;
   }

   public JsonNode bookFlight(String email, String password, String site, 
         String firstName, String lastName, long flightId, int passengers) {
      System.out.println("FlightService.bookFlight(...): booking flights...");
      
      String postReservationUrl = flightUrl + "/book";
      
      restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
      ObjectNode reservationJsonObject = new ObjectNode(jsonNodeFactory);
      reservationJsonObject.put("email", email);
      reservationJsonObject.put("password", password);
      reservationJsonObject.put("site", site);
      reservationJsonObject.put("firstName", firstName);
      reservationJsonObject.put("lastName", lastName);
      reservationJsonObject.put("flightId", flightId);
      reservationJsonObject.put("passengers", passengers);
      ObjectMapper objectMapper = new ObjectMapper();
      HttpEntity<String> request = 
            new HttpEntity<String>(reservationJsonObject.toString(), headers);
      ResponseEntity<String> response = restTemplate.
            postForEntity(postReservationUrl, request, String.class);
      
      JsonNode json;
      
      try {
         json = objectMapper.readTree(response.getBody());
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
      System.out.println("FlightService.deleteReservation(...): deleting reservation...");
      String deleteReservationUrl = flightUrl + "/cancel/" + id;
      restTemplate = new RestTemplate();
      
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
      ObjectNode reservationJsonObject = new ObjectNode(jsonNodeFactory);
      reservationJsonObject.put("id", id);
      reservationJsonObject.put("email", email);
      reservationJsonObject.put("password", password);
      reservationJsonObject.put("site", site);
      ObjectMapper objectMapper = new ObjectMapper();
      HttpEntity<String> entity = new HttpEntity<String>(reservationJsonObject.toString(), headers);
      ResponseEntity<String> response = restTemplate.exchange(deleteReservationUrl, HttpMethod.DELETE, entity, String.class);

      JsonNode json;
      
      try {
         json = objectMapper.readTree(response.getBody());
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
