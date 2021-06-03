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

@Service
public class HotelService {
   
   private static final Logger log =
         LoggerFactory.getLogger(CarService.class);
   private RestTemplate restTemplate;
   private String hotelUrl;
   
   public HotelService( 
         @Value("${hotel.url}") final String hotelUrl) {
      this.restTemplate = new RestTemplate();
      this.hotelUrl = hotelUrl;
   }
   
   public List<Object> getAvailableHotels(String city, Date date) {
      ResponseEntity<JsonNode> response =
            restTemplate.getForEntity(
                  hotelUrl + "?fromCity=" + city + "&date=" + date, 
                  JsonNode.class);
         JsonNode json = response.getBody();
         log.info("Status code from hotel server:" +
               response.getStatusCodeValue());
         //TODO: finish when known api structure exists
         return null;
   }

}
