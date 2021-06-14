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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cst438.domain.TripInfo;
import cst438.domain.User;
import cst438.domain.UserRepository;
import cst438.domain.Package;
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


   @GetMapping("/") // localhost:8080/
   public String getIndex( Model model ) {
      User user = new User();
      model.addAttribute("user", user);
      return "index";
   }
   
   // Authentication
   @PostMapping("/")
   public String signIn( @Valid User user, BindingResult result,
      Model model ) throws ParseException {
      if (result.hasErrors()) {
         return "index";
      }
      TripInfo tripInfo = new TripInfo();
      model.addAttribute("tripInfo", tripInfo);
      return "trip_info_form";
   }

   @GetMapping("/packages") // localhost:8080/packages
   public String getPackageForm( Model model ) {
      TripInfo tripInfo = new TripInfo();
      model.addAttribute("tripInfo", tripInfo);
      return "trip_info_form";
   }
   
   // Package Form submission
   @PostMapping("/packages")
   public String getPackageInfo( @Valid TripInfo tripInfo, 
         BindingResult result, Model model ) {
      if (result.hasErrors()) {
         return "trip_info_form";
      }

      /*
       * List<Package> packageList = packageService.getPackageList(tripInfo);
       * 
       * if (packageList == null) return "packages_error";
       * 
       * model.addAttribute("packageList", packageList);
       */
      List<Package> packageList = new ArrayList<Package>();
      
      int i;
      for (i = 0; i < 10; i++) {
         Package pkge = new Package(new CarInfo(1, "Car"), new HotelInfo(2, "Hotel"), new FlightInfo(3, "Flight"));
         packageList.add(pkge);
      }
      model.addAttribute("packageList", packageList);
      return "packages_show";
   }
   
   // Testing Hotel API endpoint /search
//   @PostMapping("/packages")
//   public String getHotelList(@Valid TripInfo tripInfo, BindingResult result,
//      Model model ) throws ParseException {
//      
//      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//      String cityName = "Los Angeles";
//      String state = "CA";
//      String dateInString = "1-Jul-2021";
//      Date startDate = formatter.parse(dateInString);
//      List<HotelInfo> hotelList = hotelService.getAvailableHotels(cityName, startDate, state);
//      System.out.println(hotelList);
//      model.addAttribute("hotelList", hotelList);
//      return "testHotels";
//   }
   
   // Testing API endpoint
   @GetMapping("/packages/flights/getAvailableFlights") // localhost:8080/packages
   public String getAvailableFlights( Model model ) {
      return "availableFlights";
   }
      
   // Testing API endpoint
   @PostMapping("/packages/flights/getAvailableFlights")
   public String postAvailableFlight( Model model ) throws ParseException {
      
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
      String fromCity = "Los Angeles, CA";
      String toCity = "New York, NY";
      String date = "7-Jul-2021";
      int passengers = 1;
      
      List<FlightInfo> flightList = flightService.getAvailableFlights(fromCity, toCity, date, passengers);
      System.out.println(flightList);
      model.addAttribute("flightList", flightList);
      return "testFlights";
   }
   
   @GetMapping("/packages/flights/bookFlight") // localhost:8080/packages
   public String getBookFlight( Model model ) {
      return "availableFlights";
   }
   
   // Testing API endpoint
   @PostMapping("/packages/flights/bookFlight")
   public String postBookFlight( Model model ) throws ParseException {
      
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
      String email = "ndorazio@csumb.edu";
      String password = "Nicholasdorazio1*";
      String site = "PACKAGE";
      String firstName = "Nick";
      String lastName = "Dorazio";
      long flightId = 1;
      int passengers = 1;
      List<FlightInfo> flightList = flightService.bookFlight(email, password, site, 
         firstName, lastName, flightId, passengers);
      System.out.println(flightList);
      model.addAttribute("flightList", flightList);
      return "testFlights";
   }
   
   @GetMapping("/packages/cars/getAvailableCars") // localhost:8080/packages
   public String getAvailableCars( Model model ) {
      return "availableCars";
   }
   
   // Testing API endpoint
   @PostMapping("/packages/cars/getAvailableCars")
   public String postAvailableCars( Model model ) throws ParseException {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
      String cityName = "Los Angeles";
      String dateInString = "1-Jul-2021";
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
   @PostMapping("/packages/testHotelService")
   public String getHotelList(@Valid TripInfo tripInfo, BindingResult result,
      Model model ) throws ParseException, JsonMappingException, JsonProcessingException {
      
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
      String city = "Sacramento";
      String dateString = "1-Jul-2021";
      String state = "CA";
      Date date = formatter.parse(dateString);
//      String date = formatter.format(dateDate);
//      String date = "2021-06-01";
      List<HotelInfo> hotelList = hotelService.getAvailableHotels(city, date, state);
      System.out.println("Hotel list: ");
      System.out.println(hotelList);
      model.addAttribute("hotelList", hotelList);
      return "testHotels";
   }
}
