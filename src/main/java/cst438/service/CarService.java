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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cst438.domain.CarInfo;

@Service
public class CarService {
   private static final Logger log =
         LoggerFactory.getLogger(CarService.class);
   private RestTemplate restTemplate;
   private String carUrl;
   
   /**
    * CarService Constructor. Sets the Car Rental Service url to be used for the CarService API.
    * 
    * @param carUrl The base url of the Car Rental Service.
    */
   public CarService( 
         @Value("${car.url}") final String carUrl) {
      this.restTemplate = new RestTemplate();
      this.carUrl = carUrl;
   }
   
   /**
    * Gets a list of available cars to be rented.
    * 
    * @param cityName A String representing the destination city where the car will be available for pickup.
    * @param startDate A String representing the date the car will be picked up on. Format "yyyy-mm-dd".
    * @param endDate A String the date the car will be returned. Format "yyyy-mm-dd".
    * @return An ArrayList of CarInfo objects.
    */
   public List<CarInfo> getAvailableCars(String cityName, Date dateStart, Date dateEnd) {
      System.out.println("CarService.getAvailableCars(...): Getting available cars...");
      System.out.println("This is whats passed to CarService:");
      SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
      String dateStartString = dateFormatter.format(dateStart);
      String dateEndString = dateFormatter.format(dateEnd);
      System.out.println("departureDate :" + dateStartString);
      System.out.println("arrivalDate :" + dateEndString);
      System.out.println("destinationCity: " + cityName);
      
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode json = null; 
      
      try {
         ResponseEntity<JsonNode> response =
               restTemplate.getForEntity(
                     carUrl + "/carsByCity/" + cityName + "/" + dateStartString + "/" + dateEndString, 
                     JsonNode.class);
         json = response.getBody();
         log.info("Status code from car server:" +
               response.getStatusCodeValue());
      } catch (HttpServerErrorException.InternalServerError e) {
         System.out.println("Car: 500: Internal Server Error");
         try {
            json = objectMapper.readTree("{ \"msg\":" + "\"Car: 500: Internal Server Error\" }" );
         } catch (JsonMappingException e1) {
            // TODO Auto-generated catch block
            System.out.println("Car: JsonMappingException e");
            e1.printStackTrace();
         } catch (JsonProcessingException e1) {
            // TODO Auto-generated catch block
            System.out.println("Car: JsonProcessingException e");
            e1.printStackTrace();
         }
      }
      
      
      
      ArrayList<CarInfo> carList = new ArrayList<CarInfo>();
      for (JsonNode car : json)
      { 
          System.out.println("Car:");
          System.out.println(car);
          int carId = car.get("id").asInt();
          System.out.println("Car:" + carId);
          String model = car.get("model").textValue();
          String make = car.get("make").textValue();
          int year = car.get("year").asInt();
          String trany = car.get("trany").textValue();
          double rentalPrice = car.get("rentalPrice").asInt();
          String state = car.get("state").textValue();
          String city = car.get("city").textValue();
          
          CarInfo carInfo = new CarInfo(carId, model, make, year, trany, 
                rentalPrice, state, city);
          carList.add(carInfo);
//          System.out.println("new carModel added: model=" + carModel);
//          System.out.println("new CarInfo added: " + carInfo);
      }
      System.out.println("carList added: " + carList);
      
      return carList;
   }
   
   /**
    * Requests a car to be reserved. 
    * 
    * @param email A String that represents the users email that is booking the reservation.
    * @param carId A String that represents the id of the car to be reserved.
    * @param dateStart A String that represents the date to pick up the reserved car. Format "yyyy/mm/dd".
    * @param dateEnd A String that represents the date to return the reserved car. Format "yyyy/mm/dd".
    * @return A reservation id in the form of an int.
    * @throws JsonMappingException
    * @throws JsonProcessingException
    */
   public JsonNode bookCar(String email, long carId, Date dateStart, Date dateEnd) {
      
      System.out.println("carService.bookCar(...): booking car...");
      
      String postReservationUrl = carUrl + "/reserve";
      
      System.out.println("This is whats passed to CarService:");
      System.out.println("CarID: " + carId);
      System.out.println("Email: " + email);
      
      
      SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
      String dateStartString = dateFormatter.format(dateStart);
      String dateEndString = dateFormatter.format(dateEnd);
      System.out.println("departureDate: " + dateStartString);
      System.out.println("arrivalDate: " + dateEndString);
      
      restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
      ObjectNode reservationJsonObject = new ObjectNode(jsonNodeFactory);
      reservationJsonObject.put("email", email);
      reservationJsonObject.put("car_id", carId);
      reservationJsonObject.put("date_start", dateStartString);
      reservationJsonObject.put("date_end", dateEndString);
      ObjectMapper objectMapper = new ObjectMapper();
      HttpEntity<String> request = 
            new HttpEntity<String>(reservationJsonObject.toString(), headers);
      
      JsonNode json = null;
      
      try {
         ResponseEntity<String> response = restTemplate.
            postForEntity(postReservationUrl, request, String.class);
         json = objectMapper.readTree(response.getBody());
         System.out.println(json);
      } catch (HttpClientErrorException.NotFound e) {
         System.out.println("Car: 404: NOT FOUND ERROR");
         json = null;
      } catch (JsonMappingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } 
      
      return json;
   }
   
   public JsonNode cancelReservation(long reservationId, String username) {
      
      System.out.println("carService.cancelReservation(...): cancelling car...");
      
      String postReservationUrl = carUrl + "/cancel";
      
      System.out.println("This is whats passed to CarService:");
      System.out.println("ReservationId: " + reservationId);
      System.out.println("Username: " + username);
      
      
      restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
      ObjectNode reservationJsonObject = new ObjectNode(jsonNodeFactory);
      reservationJsonObject.put("id", reservationId);
      reservationJsonObject.put("username", username);
      ObjectMapper objectMapper = new ObjectMapper();
      HttpEntity<String> request = 
         new HttpEntity<String>(reservationJsonObject.toString(), headers);
  
      JsonNode json = null;
      
      try {
         ResponseEntity<String> response = restTemplate.
            postForEntity(postReservationUrl, request, String.class);
         json = objectMapper.readTree(response.getBody());
         System.out.println(json);
      } catch (HttpClientErrorException.NotFound e) {
         json = null;
      } catch (JsonMappingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } 
      
      return json;
   }
}

