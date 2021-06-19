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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cst438.domain.HotelInfo;

@Service
public class HotelService {
   
   private static final Logger log =
         LoggerFactory.getLogger(HotelService.class);
   private RestTemplate restTemplate;
   private String hotelUrl;
   private String authToken;
   private String userId;
   
   public HotelService( 
         @Value("${hotel.url}") final String hotelUrl,
         @Value("${hotel.key}") final String authToken,
         @Value("${hotel.userId}") final String userId) {
      this.restTemplate = new RestTemplate();
      this.hotelUrl = hotelUrl;
      this.authToken = authToken;
      this.userId = userId;
   }
   
   public List<HotelInfo> getAvailableHotels(String city, Date date, String state) {
      
      System.out.println("HotelService.getAvailableHotels(...): Getting available hotels...");
      
      String searchUrl = hotelUrl + "/search";
      
      System.out.println("This is whats passed to HotelService:");
      System.out.println("City: " + city);
      System.out.println("Date: " + date);
      System.out.println("State: " + state);
      
      restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      
      JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
      
      ObjectNode searchJsonObject = new ObjectNode(jsonNodeFactory);
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
      String dateString = formatter.format(date);
      searchJsonObject.put("date", dateString);
      searchJsonObject.put("city", city);
      searchJsonObject.put("state", state);
      ObjectMapper objectMapper = new ObjectMapper();
      HttpEntity<String> request = 
            new HttpEntity<String>(searchJsonObject.toString(), headers);
      
      
      JsonNode json;
      try {
         ResponseEntity<String> response = restTemplate.
            postForEntity(searchUrl, request, String.class);
         json = objectMapper.readTree(response.getBody());
         log.info("Status code from hotel server:" +
            response.getStatusCodeValue());
         System.out.println("Response: ");
         System.out.println(response);
      } catch (JsonMappingException e) {
         e.printStackTrace();
         return null;
      } catch (JsonProcessingException e) {
         e.printStackTrace();
         return null;
      }
      
      List<HotelInfo> hotelList = new ArrayList<HotelInfo>();
      for (JsonNode item : json)
      { 
         int id = item.get("hotelId").asInt();
         String hotelName = item.get("hotelName").toString();
         String hotelAddress = item.get("hotelAddress").toString();
         int hotelStars = item.get("hotelStars").asInt();
         String hotelCity = item.get("hotelCity").toString();
         String hotelState = item.get("hotelState").toString();
         int hotelPricePerDay = item.get("hotelPricePerDay").asInt();
         String availableDate = item.get("availableDate").toString();
         int roomsAvailable = item.get("roomsAvailable").asInt();
         HotelInfo hotelInfo = new HotelInfo(
               id, 
               hotelName, 
               hotelAddress, 
               hotelStars, 
               hotelCity, 
               hotelState, 
               hotelPricePerDay, 
               availableDate, 
               roomsAvailable
         );
         hotelList.add(hotelInfo);
         System.out.println("new hotelName added: name= " + hotelName);
         System.out.println("new HotelInfo added: " + hotelInfo);
         
      }
      return hotelList;
   }
   
   public JsonNode bookHotel(String date, long hotelId) {
      System.out.println("HotelService.bookHotel(...): booking hotel...");
      
      String postReservationUrl = hotelUrl + "/reservation";
      
      System.out.println("This is whats passed to HotelService:");
      System.out.println("Date: " + date);
      System.out.println("hotelId: " + hotelId);
      System.out.println("authToken: " + authToken);
      System.out.println("userId: " + userId);
      
      restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
      ObjectNode reservationJsonObject = new ObjectNode(jsonNodeFactory);
      reservationJsonObject.put("date", date);
      reservationJsonObject.put("hotelId", hotelId);
      reservationJsonObject.put("authToken", authToken);
      reservationJsonObject.put("userId", userId);
      ObjectMapper objectMapper = new ObjectMapper();
      HttpEntity<String> request = 
            new HttpEntity<String>(reservationJsonObject.toString(), headers);
      
      JsonNode json;
      
      try {
         ResponseEntity<String> response = restTemplate.
            postForEntity(postReservationUrl, request, String.class);
         json = objectMapper.readTree(response.getBody());
         System.out.println(json);
         return json;
      } catch (HttpServerErrorException.InternalServerError e) {
         System.out.println("Hotel: 500: Internal Server Error");
         try {
            json = objectMapper.readTree("{ \"msg\":" + "\"Hotel: 500: Internal Server Error\" }" );
         } catch (JsonMappingException e1) {
            // TODO Auto-generated catch block
            System.out.println("Hotel: JsonMappingException e1");
            e1.printStackTrace();
         } catch (JsonProcessingException e1) {
            // TODO Auto-generated catch block
            System.out.println("Hotel: JsonProcessingException e1");
            e1.printStackTrace();
         }
      } catch (JsonMappingException e) {
         System.out.println("Hotel: JsonMappingException e");
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (JsonProcessingException e) {
         System.out.println("Hotel: JsonProcessingException e");
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }
   
   public JsonNode cancelReservation(long reservationId) {
      System.out.println("HotelService.cancelReservation(...): canceling reservation...");
      
      String deleteReservationUrl = hotelUrl + "reservation/cancel";
      
      System.out.println("This is whats passed to HotelService:");
      System.out.println("ReservationId: " + reservationId);
      System.out.println("authToken: " + authToken);
      System.out.println("userId: " + userId);
      
      restTemplate = new RestTemplate();
      
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
      ObjectNode reservationJsonObject = new ObjectNode(jsonNodeFactory);
      reservationJsonObject.put("userId", userId);
      reservationJsonObject.put("reservationId", reservationId);
      reservationJsonObject.put("authToken", authToken);
      ObjectMapper objectMapper = new ObjectMapper();
      HttpEntity<String> entity = new HttpEntity<String>(reservationJsonObject.toString(), headers);
      
      JsonNode json;
      
      try {
         ResponseEntity<String> response = restTemplate.exchange(deleteReservationUrl, HttpMethod.DELETE, entity, String.class);
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

