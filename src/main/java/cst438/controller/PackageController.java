package cst438.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cst438.domain.TripInfo;
import cst438.domain.User;
import cst438.domain.UserRepository;
import cst438.domain.Package;
import cst438.domain.ReservationInfo;
import cst438.service.CarService;
import cst438.service.FlightService;
import cst438.domain.CarInfo;
import cst438.domain.FlightInfo;
import cst438.service.HotelService;
import cst438.domain.HotelInfo;
import cst438.service.PackageService;

@Controller
public class PackageController {
   @Autowired
   private PackageService packageService;
   @Autowired
   private CarService carService;
   @Autowired
   private HotelService hotelService;
   @Autowired
   private FlightService flightService;
   @Autowired 
   private UserRepository userRepository;

   // Login Form
   @GetMapping("/") // localhost:8080/
   public String getIndex( Model model ) {
      User user = new User();
      model.addAttribute("user", user);
      return "index";
   }
   
   // Authentication
   @PostMapping("/")
   public String logIn( @Valid User user, BindingResult result,
      Model model ) throws ParseException {
      if (result.hasErrors()) {
         return "index";
      }
      
      // look up user
      List<User> users = userRepository.findByUsername(user.getUsername());
      // no user found with the username provided
      if (users.size() <= 0) {
         return "signup";
      }
      
      // found user but wrong password
      User userInfo = users.get(0);
      if (!(userInfo.getPassword().equals(user.getPassword()))) {
         return "index";
      }
      
      // Render the trip info form
      TripInfo tripInfo = new TripInfo();
      tripInfo.setUsername(user.getUsername());
      model.addAttribute("tripInfo", tripInfo);
      
      return "trip_info_form";
   }
   
   // render sign up page
   @GetMapping("/signup") // localhost:8080/
   public String registerUser( Model model ) {
      User user = new User();
      model.addAttribute("user", user);
      return "signup";
   }
   
   // Sign up new users by entering in the database
   @PostMapping("/signup")
   public String signUp( @Valid User user, BindingResult result,
      Model model ) throws ParseException {
      if (result.hasErrors()) {
         return "signup";
      }
      
      // if user does not exist, create the user
      List<User> users = userRepository.findByUsername(user.getUsername());
      if (users.size() <= 0) {
         userRepository.save(user);
         TripInfo tripInfo = new TripInfo();
         model.addAttribute("tripInfo", tripInfo);
         return "trip_info_form";
      }
      
      return "signUp";
   }

   // This won't work anymore because the user must login first
   @GetMapping("/packages") // localhost:8080/packages
   public String getPackageForm( Model model ) {
      TripInfo tripInfo = new TripInfo();
      model.addAttribute("tripInfo", tripInfo);
      return "trip_info_form";
   }
   
   // Display a list of packages to choose from
   @PostMapping("/packages")
   public String getPackageInfo( @Valid TripInfo tripInfo, 
         BindingResult result, Model model ) {
      if (result.hasErrors()) {
         return "trip_info_form";
      }
      
      List<Package> packageList = packageService.getPackageList(tripInfo); 
      if (packageList == null) return "packages_error";
      model.addAttribute("packageList", packageList);
      ReservationInfo reservationInfo = new ReservationInfo(
            tripInfo.getUsername(),
            tripInfo.getNumPassengers(),
            tripInfo.getDepartureDate(),
            tripInfo.getArrivalDate()
      );
      model.addAttribute("reservationInfo", reservationInfo);
      return "packages_show";
   }
   
   // Package Form submission
   @PostMapping("/packages/book")
   public String bookPackage( @Valid ReservationInfo reservationInfo, 
         BindingResult result, Model model ) {
      if (result.hasErrors()) {
         return "trip_info_form";
      }
      
      // TODO: fill in the missing info in reservationInfo
      reservationInfo.setCarId(0);
      reservationInfo.setFlightId(0);
      reservationInfo.setHotelId(0);
      reservationInfo.setHotelDate(null);
      
      String bookingResult = packageService.bookPackage(reservationInfo);
      model.addAttribute("bookingResult", bookingResult);
    
      return "package_booked";
   }
   
  
   /***************** HOTELS *************/

   // Testing API endpoint
   @GetMapping("/packages/hotels/getAvailableHotels") // localhost:8080/packages
   public String getAvailableHotels( Model model ) {
      return "availableHotels";
   }
   
   // Testing API endpoint
   @PostMapping("/packages/hotels/getAvailableHotels")
   public String getAvailableHotels(@Valid TripInfo tripInfo, BindingResult result,
      Model model ) throws ParseException, JsonMappingException, JsonProcessingException {
      
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
      String city = "Chicago";
      String dateString = "2021-07-01";
      String state = "IL";
      Date date = formatter.parse(dateString);
      List<HotelInfo> hotelList = hotelService.getAvailableHotels(city, date, state);
      System.out.println("Hotel list: ");
      System.out.println(hotelList);
      model.addAttribute("hotelList", hotelList);
      return "testHotels";
   }
   
   
   /************* FLIGHTS ****************/
   
   // Testing API endpoint
   @GetMapping("/packages/flights/getAvailableFlights") // localhost:8080/packages
   public String getAvailableFlights( Model model ) {
      return "availableFlights";
   }
      
   // Testing API endpoint
   @PostMapping("/packages/flights/getAvailableFlights")
   public String postAvailableFlight( Model model ) throws ParseException {
      
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
      String fromCity = "Los Angeles";
      String toCity = "New York";
      String dateString = "2021-07-01";
      Date date = formatter.parse(dateString);
      int passengers = 1;
      
      List<FlightInfo> flightList = flightService.getAvailableFlights(fromCity, toCity, date, passengers);
      System.out.println(flightList);
      model.addAttribute("flightList", flightList);
      return "testFlights";
   }
   
   // Testing API endpoint
   @GetMapping("/packages/flights/bookFlight") // localhost:8080/packages
   public String getBookFlight( Model model ) {
      return "bookFlight";
   }
   
   // Testing API endpoint
   @PostMapping("/packages/flights/bookFlight")
   public String postBookFlight( Model model ) throws ParseException {
      
      String email = "koakesndorazio@csumb.edu";
      String password = "KyleOakesNickDorazio1*";
      String site = "PACKAGE";
      String firstName = "NickKyle";
      String lastName = "DorazioOakes";
      long flightId = 271;
      int passengers = 1;
      JsonNode reservationBooking = flightService.bookFlight(email, password, site, 
         firstName, lastName, flightId, passengers);
      System.out.println(reservationBooking);
      int reservationId = reservationBooking.get("reservation").get("id").asInt();
      model.addAttribute("reservationId", reservationId);
      return "testFlightBooking";
   }
   
   // Testing API endpoint
   @GetMapping("/packages/flights/deleteBooking") // localhost:8080/packages
   public String deleteBookedFlight( Model model ) {
      return "deleteBookedFlight";
   }
   
   // Testing API endpoint
   @PostMapping("/packages/flights/deleteBooking")
   public String postDeleteBookedFlight( Model model ) {
      String id = "286";
      String email = "ndorazio@csumb.edu";
      String password = "Nicholasdorazio1*";
      String site = "PACKAGE";
      
      JsonNode deletedBooking = flightService.deleteReservation(id, email, password, site);
      System.out.println(deletedBooking);
      int reservationId = deletedBooking.get("reservation").get("id").asInt();
      String cancellationMessage = deletedBooking.get("message").asText();
      model.addAttribute("reservationId", reservationId);
      model.addAttribute("cancellationMessage", cancellationMessage);
      return "testDeleteBookedFlight";
   }
   
   /************ CARS ******************/
   
   // Testing API endpoint
   @GetMapping("/packages/cars/getAvailableCars") // localhost:8080/packages
   public String getAvailableCars( Model model ) {
      return "availableCars";
   }
   
   // Testing API endpoint
   @PostMapping("/packages/cars/getAvailableCars")
   public String postAvailableCars( Model model ) throws ParseException {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
      String cityName = "Los Angeles";
      String dateInString = "2021-07-01";
      Date startDate = formatter.parse(dateInString);
      Date endDate = formatter.parse(dateInString);
      List<CarInfo> carList = carService.getAvailableCars(cityName, startDate, endDate);
      System.out.println(carList);
      model.addAttribute("carList", carList);
      return "testCars";
   }
   
   // Testing API endpoint
   @GetMapping("/packages/cars/details")
   public String getCarDetails( Model model ) {
      int carId = 383;
      CarInfo carInfo = carService.getCarDetails(carId);
      System.out.println(carInfo.toString());
      List<CarInfo> carList = new ArrayList<CarInfo>();
      carList.add(carInfo);
      
      model.addAttribute("carList", carList);
      return "testCars";
   }
   
   // Testing API endpoint
   @GetMapping("/packages/cars/reserve") // localhost:8080/packages
   public String bookCar( Model model ) {
      return "bookCar";
   }
   
   // Testing API endpoint
   @PostMapping("/packages/cars/reserve")
   public String postReservation( Model model ) {
      String email = "ndorazio@csumb.edu";
      String id = "5";
      String startDate = "2021-07-01";
      String endDate = "2021-07-01";
      JsonNode reservationBooking = carService.bookCar(email, id, startDate, endDate);
      System.out.println(reservationBooking);
      if (reservationBooking != null) {
         int reservationId = reservationBooking.get("id").asInt();
         model.addAttribute("reservationId", reservationId);
      } else {
         String msg = "There was an error booking your car reservation.";
         model.addAttribute("msg", msg);
      }
      return "testCarBooking";
   }
   
   // Testing API endpoint
   @GetMapping("/packages/cars/cancel") // localhost:8080/packages
   public String cancelCar( Model model ) {
      return "cancelCar";
   }
   
   // Testing API endpoint
   @PostMapping("/packages/cars/cancel")
   public String cancelReservation( Model model ) {
      String id = "8";
      String email = "ndorazio@csumb.edu";
      JsonNode reservationBooking = carService.cancelReservation(id, email);
      System.out.println(reservationBooking);
      
      if (reservationBooking != null) {
         int reservationId = reservationBooking.get("id").asInt();
         model.addAttribute("reservationId", reservationId);
      } else {
         String msg = "No reservation exists for this reservation id.";
         model.addAttribute("msg", msg);
      }
      return "testCancelCarReservation";
   }
}
