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

import cst438.domain.FlightInfo;
import cst438.domain.HotelInfo;

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
   
   public List<HotelInfo> getAvailableHotels(String city, Date date, String state) {
      System.out.println("HotelService.getAvailableHotels(...): Getting available hotels...");
      ResponseEntity<JsonNode> response =
            restTemplate.getForEntity(
                  hotelUrl + "/search" + "?city=" + city + "&date=" + date + "&state=" + state, 
                  JsonNode.class);
         JsonNode json = response.getBody();
         System.out.println("Status code from hotel server: " + 
               response.getStatusCodeValue());
         log.info("Status code from hotel server:" +
               response.getStatusCodeValue());
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
         System.out.println("hotelList added: " + hotelList);
         return hotelList;
   }

}
