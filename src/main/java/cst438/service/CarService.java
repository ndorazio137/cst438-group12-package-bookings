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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cst438.domain.CarInfo;

@Service
public class CarService {
      private static final Logger log =
            LoggerFactory.getLogger(CarService.class);
      private RestTemplate restTemplate;
      private String carUrl;
      
      public CarService( 
            @Value("${car.url}") final String carUrl) {
         this.restTemplate = new RestTemplate();
         this.carUrl = carUrl;
      }
      
      public List<CarInfo> getAvailableCars(String cityName, Date startDate, Date endDate) {
         System.out.println("CarService.getAvailableCars(...): Getting available cars...");
         ResponseEntity<JsonNode> response =
               restTemplate.getForEntity(
                     carUrl + "/carsByCity/" + cityName + "/" + startDate + "/" + endDate, 
                     JsonNode.class);
         JsonNode json = response.getBody();
         log.info("Status code from car server:" +
               response.getStatusCodeValue());
         List<CarInfo> carList = new ArrayList<CarInfo>();
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
      
      public Object getReservationById(String reservationId) throws ParseException {
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
            
         SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
         Date startDate = formatter.parse(startDateString);
         Date endDate = formatter.parse(endDateString);
         //TODO: find out if we need to get the reservation. Finish then.
         return null;
      }
      
      public int postReservation(String email, String carId, String dateStart, String dateEnd) throws JsonMappingException, JsonProcessingException {
         
         String postReservationUrl = carUrl + "/reserve";
         
         restTemplate = new RestTemplate();
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         ObjectNode reservationJsonObject = new ObjectNode(null);
         reservationJsonObject.put("email", email);
         reservationJsonObject.put("car_id", carId);
         reservationJsonObject.put("date_start", dateStart);
         reservationJsonObject.put("date_end", dateEnd);
         ObjectMapper objectMapper = new ObjectMapper();
         HttpEntity<String> request = 
               new HttpEntity<String>(reservationJsonObject.toString(), headers);
         ResponseEntity<String> response = restTemplate.
               postForEntity(postReservationUrl, request, String.class);
         
         JsonNode json = objectMapper.readTree(response.getBody());
         int reservationId = json.get("id").asInt();
         log.info("Status code from car server:" +
               response.getStatusCodeValue());
         return reservationId;
      }
      
}
