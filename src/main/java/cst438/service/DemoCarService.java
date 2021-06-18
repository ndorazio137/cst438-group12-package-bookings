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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;
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
public class DemoCarService {
   private static final Logger log =
         LoggerFactory.getLogger(DemoCarService.class);
   private RestTemplate restTemplate;
   private String carUrl;
   
   /**
    * CarService Constructor. Sets the Car Rental Service url to be used for the CarService API.
    * 
    * @param carUrl The base url of the Car Rental Service.
    */
   public DemoCarService( 
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
      System.out.println("DemoCarService.getAvailableCars(...): Getting available cars...");
      
      ArrayList<CarInfo> carList = new ArrayList<CarInfo>();
      carList.add(new CarInfo(5, "Cruze Premier", "Chevrolet", 2018, "Automatic", 557.0, "CA", "Los Angeles"));
      carList.add(new CarInfo(7, "Impala", "Chevrolet", 2018, "Automatic", 677.0, "CA", "Los Angeles"));
      carList.add(new CarInfo(19, "Charger AWD", "Dodge", 2018, "Automatic", 921.0, "CA", "Los Angeles"));
      
      return carList;
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
   public JsonNode bookCar(String email, int carId, String dateStart, String dateEnd) {
      
      System.out.println("DemoCarService.bookCar(...): booking car...");
      
      ObjectMapper objectMapper = new ObjectMapper();
     
      JsonNode json = null;
      
      String responseBody;
      
      if (carId == 5 || carId == 7 ) {
         responseBody = "{\n"
            + "        \"id\": 27,\n"
            + "        \"email\": \""+ email +"\",\n"
            + "        \"car_id\": " + carId + ",\n"
            + "        \"date_start\": \""+ dateStart +"\",\n"
            + "        \"date_end\": \""+ dateEnd +"\"\n"
            + "    }";
      } else {
         responseBody = "{\"status\": 404}";
      }
      
      try {
         json = objectMapper.readTree(responseBody);
         System.out.println(json);
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
      
      System.out.println("DemoCarService.cancelReservation(...): cancelling car...");
      
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode json = null;
      
      String responseBody = "{status: 200}";
      
      try {
         json = objectMapper.readTree(responseBody);
         System.out.println(json);
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
