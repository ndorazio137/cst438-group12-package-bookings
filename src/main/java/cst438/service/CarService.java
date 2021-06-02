package cst438.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
      
      public List<Object> getAvailableCars(String cityName, Date date) {
         ResponseEntity<JsonNode> response =
               restTemplate.getForEntity(
                     carUrl + "?q=" + cityName + "&date=" + date, 
                     JsonNode.class);
            JsonNode json = response.getBody(); // 2
            log.info("Status code from car server:" +
            response.getStatusCodeValue());
            return null;
      }
}
