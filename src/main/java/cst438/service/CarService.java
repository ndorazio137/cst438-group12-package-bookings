package cst438.service;

import java.util.Date;
import java.util.List;

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

@Service
public class CarService 
{
      private static final Logger log =
            LoggerFactory.getLogger(CarService.class);
      private RestTemplate restTemplate;
      private String carUrl;
      
      public CarService( 
            @Value("${car.url}") final String carUrl) {
         this.restTemplate = new RestTemplate();
         this.carUrl = carUrl;
      }
      
      public List<Object> getAvailableCars(String cityName, Date startDate, Date endDate) {
         ResponseEntity<JsonNode> response =
               restTemplate.getForEntity(
                     carUrl + "/" + cityName + "/" + startDate + "/" + endDate, 
                     JsonNode.class);
         JsonNode json = response.getBody();
         log.info("Status code from car server:" +
               response.getStatusCodeValue());
         //TODO: finish when known api structure exists
         return null;
      }
      
      public Object getReservationById(String reservationId) {
         ResponseEntity<JsonNode> response =
               restTemplate.getForEntity(
                     carUrl + "/reservations/details/" + reservationId, 
                     JsonNode.class);
         JsonNode json = response.getBody();
         log.info("Status code from car server:" +
               response.getStatusCodeValue());
         //TODO: finish when known api structure exists
         return null;
      }
      
      public Object postReservation(String email, String carId, String dateStart, String dateEnd) throws JsonMappingException, JsonProcessingException {
         
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
         
         log.info("Status code from car server:" +
               response.getStatusCodeValue());
         //TODO: finish when known api structure exists
         return null;
      }
}
