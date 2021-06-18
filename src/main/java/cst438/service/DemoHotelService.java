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
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cst438.domain.CarInfo;
import cst438.domain.HotelInfo;

@Service
public class DemoHotelService {
   
   private static final Logger log =
         LoggerFactory.getLogger(DemoHotelService.class);
   private RestTemplate restTemplate;
   private String hotelUrl;
   private String authToken;
   private int userId;
   
   public DemoHotelService( 
         @Value("${hotel.url}") final String hotelUrl,
         @Value("${hotel.key}") final String authToken,
         @Value("${hotel.userId}") final String userId) {
      this.restTemplate = new RestTemplate();
      this.hotelUrl = hotelUrl;
      this.authToken = authToken;
   }
   
   public List<HotelInfo> getAvailableHotels(String city, Date date, String state) {
      
      System.out.println("DemoHotelService.getAvailableHotels(...): Getting available hotels...");
      
System.out.println("DemoCarService.getAvailableCars(...): Getting available cars...");
      
      ArrayList<HotelInfo> hotelList = new ArrayList<HotelInfo>();
      hotelList.add(new HotelInfo(28, "Hampton", "7360 North Manor St.",
            5, city, state, 600, date.toString(), 80));
      hotelList.add(new HotelInfo(28, "Weston", "9540 North Cobblestone St.",
            5, city, state, 290, date.toString(), 80));
      hotelList.add(new HotelInfo(28, "Residence Inn", "8095 Primrose Drive",
            5, city, state, 110, date.toString(), 80));
      return hotelList;
   }
   
   public JsonNode bookHotel(String date, int hotelId) {
      System.out.println("HotelService.bookHotel(...): booking hotel...");

      ObjectMapper objectMapper = new ObjectMapper();
     
      JsonNode json = null;
      
      String responseBody;
      
      if (hotelId == 28 || hotelId == 29 ) {
         responseBody = "{\n"
               + "    \"id\": 5,\n"
               + "    \"startDate\": \"2021-06-01\",\n"
               + "    \"endDate\": \"2021-06-02\",\n"
               + "    \"hotel\": {\n"
               + "        \"idhotel\": "+ hotelId + ",\n"
               + "        \"name\": \"Weston\",\n"
               + "        \"address\": \"12456 some address\",\n"
               + "        \"stars\": 3,\n"
               + "        \"city\": \"New York\",\n"
               + "        \"state\": \"NY\",\n"
               + "        \"price_per_day\": 100\n"
               + "    },\n"
               + "    \"users\": {\n"
               + "        \"idusers\": 1,\n"
               + "        \"firstName\": \"kyle\",\n"
               + "        \"lastName\": \"oakes\",\n"
               + "        \"password\": \"packageDeals\"\n"
               + "    },\n"
               + "    \"start_date\": \""+ date + "\",\n"
               + "    \"end_date\": \""+ date + "\"\n"
               + "}";
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
}
