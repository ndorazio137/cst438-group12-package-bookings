package cst438.service;

import java.text.ParseException;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
   public List<CarInfo> getAvailableCars(String cityName, Date startDate, Date endDate) {
      System.out.println("CarService.getAvailableCars(...): Getting available cars...");
      ResponseEntity<JsonNode> response =
            restTemplate.getForEntity(
                  carUrl + "/carsByCity/" + cityName + "/" + startDate + "/" + endDate, 
                  JsonNode.class);
      JsonNode json = response.getBody();
      log.info("Status code from car server:" +
            response.getStatusCodeValue());
      ArrayList<CarInfo> carList = new ArrayList<CarInfo>();
      for (JsonNode car : json)
      { 
          String carModel = car.get("model").textValue();
          int carId = car.get("id").asInt();
          CarInfo carInfo = new CarInfo(carId, carModel);
          carList.add(carInfo);
          System.out.println("new carModel added: model=" + carModel);
          System.out.println("new CarInfo added: " + carInfo);
      }
      System.out.println("carList added: " + carList);
      
      return carList;
   }
   
   /**
    * Gets the details for a specific car available for rental.
    * 
    * @param id An int representing the id of a specific car.
    * @return Info about a specific car on the form of a CarInfo object. 
    */
   public CarInfo getCarDetails(int id) {
      System.out.println("CarService.getCarDetails(...): Getting available car details...");
      ResponseEntity<JsonNode> response =
            restTemplate.getForEntity(
                  carUrl + "/carsByCity/details/" + id, 
                  JsonNode.class);
      JsonNode json = response.getBody();
      log.info("Status code from car server:" +
            response.getStatusCodeValue());
      int carId = json.get("id").asInt();
      String carModel = json.get("model").textValue();
      CarInfo carDetails = new CarInfo(carId, carModel);
      System.out.println(carDetails.toString());
      return carDetails;
   }
   
   /**
    * Gets the reservation details of a specific car available for rental.
    * 
    * @param int reservationId The id the car will be reserved under when requested.
    * @return 
    * @throws ParseException
    */
      public Object getReservationById(int reservationId) throws ParseException {
         ResponseEntity<JsonNode> response =
               restTemplate.getForEntity(
                     carUrl + "/reservations/details/" + reservationId, 
                     JsonNode.class);
         JsonNode json = response.getBody();
         log.info("Status code from car server:" +
               response.getStatusCodeValue());
         int id = json.get("id").asInt();
         String email = json.get("email").textValue();
         int carId = json.get("car_id").asInt();
         String startDateString = json.get("date_start").textValue();
         String endDateString = json.get("date_end").textValue();
            
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
         Date startDate = formatter.parse(startDateString);
         Date endDate = formatter.parse(endDateString);
         //TODO: find out if we need to get the reservation. Finish then.
         return null;
      }
   
   /**
    * Requests a car to be reserved. 
    * 
    * @param email A String that represents the users email that is booking the reservation.
    * @param carId A String that represents the id of the car to be reserved.
    * @param dateStart A String that represents the date to pick up the reserved car. Format "yyyy-mm-dd".
    * @param dateEnd A String that represents the date to return the reserved car. Format "yyyy-mm-dd".
    * @return A reservation id in the form of an int.
    * @throws JsonMappingException
    * @throws JsonProcessingException
    */
   public JsonNode bookCar(String email, String carId, String dateStart, String dateEnd) {
      
      System.out.println("carService.bookCar(...): booking car...");
      
      String postReservationUrl = carUrl + "/reserve";
      
      restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
      ObjectNode reservationJsonObject = new ObjectNode(jsonNodeFactory);
      reservationJsonObject.put("email", email);
      reservationJsonObject.put("car_id", carId);
      reservationJsonObject.put("date_start", dateStart);
      reservationJsonObject.put("date_end", dateEnd);
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
   
   public JsonNode cancelReservation(String reservationId, String username) {
      
      System.out.println("carService.cancelReservation(...): cancelling car...");
      
      String postReservationUrl = carUrl + "/cancel";
      
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
