package cst438.service;

//********* Static imports ***************
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
//******** Non-Static imports ************
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import cst438.domain.TripInfo;
import cst438.domain.User;
import cst438.domain.CarInfo;
import cst438.domain.FlightInfo;
import cst438.domain.HotelInfo;
import cst438.domain.Package;
import cst438.domain.ReservationRepository;

@SpringBootTest
public class PackageServiceTest {
   //*********MOCKBEANS*************
   @MockBean
   private CarService carService;
   @MockBean
   private HotelService hotelService;
   @MockBean
   private FlightService flightService;
   @MockBean
   private ReservationRepository reservationRepository;
   @Autowired
   private PackageService packageService;
   
   //*********TEST CASES***********
   
   // Test the case where all services are down or otherwise return null
   @Test public void testAllServiceResponsesNull() throws Exception { 
      // Create TripInfo Object 
      String startingCity = "Los Angeles"; 
      String startingState = "CA";
      String destinationCity = "New York"; 
      String destinationState = "NY"; 
      Date departureDate = new Date(2021, 07, 01); 
      Date arrivalDate = new Date(2021, 07, 07);
      int numPassengers = 1;
      User user = new User("koakes@csumb.edu", "password1", "Kyle", "Oakes");
      TripInfo tripInfo = new TripInfo(startingCity, startingState,
            destinationCity, destinationState,
            departureDate, arrivalDate, 
            numPassengers, user.getUsername());
     
      // Mock null responses from the services
      given(carService.getAvailableCars(destinationCity, departureDate,
         arrivalDate)).willReturn(null);
      given(hotelService.getAvailableHotels(destinationCity,
         arrivalDate, destinationState)).willReturn(null);
      given(flightService.getAvailableFlights(startingCity, destinationCity,
         departureDate, numPassengers)).willReturn(null);
     
      // Check for null response package list
      assertThat(packageService.getPackageList(tripInfo)).isEqualTo(null); 
   }
     
   // Test the case where there are no cars, hotels, or flights available
   @Test public void testNoAvailability() throws Exception {
      // Create TripInfo Object 
      String startingCity = "Los Angeles"; 
      String startingState = "CA";
      String destinationCity = "New York"; 
      String destinationState = "NY"; 
      Date departureDate = new Date(2021, 07, 01); 
      Date arrivalDate = new Date(2021, 07, 07);
      int numPassengers = 1;
      User user = new User("koakes@csumb.edu", "password1", "Kyle", "Oakes");
      TripInfo tripInfo = new TripInfo(startingCity, startingState,
            destinationCity, destinationState,
            departureDate, arrivalDate, 
            numPassengers, user.getUsername());
  
      // the list returned by the service will be an empty list
      ArrayList<CarInfo> carList = new ArrayList<CarInfo>();
      given(carService.getAvailableCars(startingCity, departureDate,
         arrivalDate)).willReturn(carList);
  
      // the list returned by the service will be an empty list
      ArrayList<HotelInfo> hotelList = new ArrayList<HotelInfo>();
      given(hotelService.getAvailableHotels(startingCity,
         departureDate, destinationState)).willReturn(hotelList);
  
      // the list returned by the service will be an empty list
      ArrayList<FlightInfo> flightList = new ArrayList<FlightInfo>();
      given(flightService.getAvailableFlights(startingCity, destinationCity,
         departureDate, numPassengers)).willReturn(flightList);
  
      // Have the package service use our trip info to generate a list of packages 
      List<Package> actualPackageList = 
         packageService.getPackageList(tripInfo);
  
      // Generate the expected output, an empty list 
      List<Package> expectedPackageList = new ArrayList<Package>();
  
      // Check if actual result matches expected result
      assertThat(expectedPackageList).isEqualTo(actualPackageList); 
   }
     
   // Test the case where only the car service response is null
   @Test public void testCarServiceResponseNull() throws Exception { 
      // Create TripInfo Object 
      String startingCity = "Los Angeles"; 
      String startingState = "CA";
      String destinationCity = "New York"; 
      String destinationState = "NY"; 
      Date departureDate = new Date(2021, 07, 01); 
      Date arrivalDate = new Date(2021, 07, 07);
      int numPassengers = 1;
      User user = new User("koakes@csumb.edu", "password1", "Kyle", "Oakes");
      TripInfo tripInfo = new TripInfo(startingCity, startingState,
            destinationCity, destinationState,
            departureDate, arrivalDate, 
            numPassengers, user.getUsername());
      
      // this service will return a null value
      given(carService.getAvailableCars(destinationCity, departureDate,
         arrivalDate)).willReturn(null);
     
      // this service will return a list of size > 0 
      ArrayList<HotelInfo> hotelList = 
         new ArrayList<HotelInfo>(); 
      hotelList.add(new HotelInfo(1, "Weston", "12456 some address",
            3, "Sacramento", "CA", 100, "2021-07-01", 4));
      hotelList.add(new HotelInfo(2, "Hampton Inn", "12456 some address",
            2, "Sacramento", "CA", 200, "2021-07-01", 2));
      given(hotelService.getAvailableHotels(destinationCity,
         arrivalDate, destinationState)).willReturn(hotelList);
     
      // this service will return a list of size > 0 
      ArrayList<FlightInfo> flightList = 
         new ArrayList<FlightInfo>(); 
      flightList.add(new FlightInfo(1, "some flight number", "Los Angeles", "Sacramento", "2021-07-01", "2021-07-01", "some duration", 1, 1, 1, 1, 1));
      flightList.add(new FlightInfo(2, "some other flight number", "Los Angeles", "Sacramento", "2021-07-01", "2021-07-01", "some duration", 1, 1, 1, 1, 1));
      given(flightService.getAvailableFlights(startingCity, destinationCity,
         departureDate, numPassengers)).willReturn(flightList);
     
      // Have the package service use our trip info to generate a list of packages 
      List<Package> actualPackageList =
         packageService.getPackageList(tripInfo);
     
      // Generate the expected output, a null value 
      List<Package> expectedPackageList = null;
     
      // Check if actual result matches expected result
      assertThat(actualPackageList).isEqualTo(expectedPackageList); 
   }
     
   // Test the case where only the hotel service response is null
   @Test public void testHotelServiceResponseNull() throws Exception { 
      // Create TripInfo Object 
      String startingCity = "Los Angeles"; 
      String startingState = "CA";
      String destinationCity = "New York"; 
      String destinationState = "NY"; 
      Date departureDate = new Date(2021, 07, 01); 
      Date arrivalDate = new Date(2021, 07, 07);
      int numPassengers = 1;
      User user = new User("koakes@csumb.edu", "password1", "Kyle", "Oakes");
      TripInfo tripInfo = new TripInfo(startingCity, startingState,
            destinationCity, destinationState,
            departureDate, arrivalDate, 
            numPassengers, user.getUsername());
      
      // this service will return a list of size > 0 
      List<CarInfo> carList = new ArrayList<CarInfo>(); 
      
      // params: carId, model, make, year, trany, rentalPrice, state, city
      carList.add(new CarInfo(5, "Cruze Premier", "Chevrolet", 2018, "Automatic", 557.0, "CA", "Los Angeles"));
      carList.add(new CarInfo(7, "Impala", "Chevrolet", 2018, "Automatic", 677.0, "CA", "Los Angeles"));
      carList.add(new CarInfo(19, "Charger AWD", "Dodge", 2018, "Automatic", 921.0, "CA", "Los Angeles"));
      given(carService.getAvailableCars(destinationCity, departureDate,
         arrivalDate)).willReturn(carList);
     
      // this service will return a null value
      given(hotelService.getAvailableHotels(destinationCity,
         arrivalDate, destinationState)).willReturn(null);
     
      // this service will return a list of size > 0 
      ArrayList<FlightInfo> flightList = 
         new ArrayList<FlightInfo>(); 
      flightList.add(new FlightInfo(1, "some flight number", "Los Angeles", "Sacramento", "2021-07-01", "2021-07-01", "some duration", 1, 1, 1, 1, 1));
      flightList.add(new FlightInfo(2, "some other flight number", "Los Angeles", "Sacramento", "2021-07-01", "2021-07-01", "some duration", 1, 1, 1, 1, 1));
      given(flightService.getAvailableFlights(startingCity, destinationCity,
         departureDate, numPassengers)).willReturn(flightList);
     
      // Have the package service use our trip info to generate a list of packages 
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
     
      // Generate the expected output, a null value 
      List<Package> expectedPackageList = null;
     
      // Check if actual result matches expected result
      assertThat(actualPackageList).isEqualTo(expectedPackageList); 
   }
  
   // Test the case where only the flight service response is null
   @Test public void testFlightServiceResponseNull() throws Exception { 
      // Create TripInfo Object 
      String startingCity = "Los Angeles"; 
      String startingState = "CA";
      String destinationCity = "New York"; 
      String destinationState = "NY"; 
      Date departureDate = new Date(2021, 07, 01); 
      Date arrivalDate = new Date(2021, 07, 07);
      int numPassengers = 1;
      User user = new User("koakes@csumb.edu", "password1", "Kyle", "Oakes");
      TripInfo tripInfo = new TripInfo(startingCity, startingState,
            destinationCity, destinationState,
            departureDate, arrivalDate, 
            numPassengers, user.getUsername());
  
      // this service will return a list of size > 0 
      ArrayList<CarInfo> carList = new ArrayList<CarInfo>(); 
      // params: carId, model, make, year, trany, rentalPrice, state, city
      carList.add(new CarInfo(5, "Cruze Premier", "Chevrolet", 2018, "Automatic", 557.0, "CA", "Los Angeles"));
      carList.add(new CarInfo(7, "Impala", "Chevrolet", 2018, "Automatic", 677.0, "CA", "Los Angeles"));
      carList.add(new CarInfo(19, "Charger AWD", "Dodge", 2018, "Automatic", 921.0, "CA", "Los Angeles"));
      given(carService.getAvailableCars(destinationCity, departureDate,
         arrivalDate)).willReturn(carList);
  
      // this service will return a list of size > 0 
      ArrayList<HotelInfo> hotelList = new ArrayList<HotelInfo>(); 
      hotelList.add(new HotelInfo(1, "Weston", "12456 some address",
            3, "Sacramento", "CA", 100, "2021-06-01", 4));
      hotelList.add(new HotelInfo(2, "Hampton Inn", "12456 some address",
            2, "Sacramento", "CA", 200, "2021-06-01", 2));
      given(hotelService.getAvailableHotels(destinationCity,
         arrivalDate, destinationState)).willReturn(hotelList);
  
      // this service will return a null value
      given(flightService.getAvailableFlights(startingCity, destinationCity,
         departureDate, numPassengers)).willReturn(null);
  
      // Have the package service use our trip info to generate a list of packages 
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
  
      // Generate the expected output, a null value 
      List<Package> expectedPackageList = null;
  
      // Check if actual result matches expected result
      assertThat(actualPackageList).isEqualTo(expectedPackageList); 
   }
  
   // All three return valid
   @Test public void testAllGoodResponses() throws Exception { 
      // Create TripInfo Object 
      String startingCity = "Los Angeles"; 
      String startingState = "CA";
      String destinationCity = "New York"; 
      String destinationState = "NY"; 
      Date departureDate = new Date(2021, 07, 01); 
      Date arrivalDate = new Date(2021, 07, 07);
      int numPassengers = 1;
      User user = new User("koakes@csumb.edu", "password1", "Kyle", "Oakes");
      TripInfo tripInfo = new TripInfo(startingCity, startingState,
            destinationCity, destinationState,
            departureDate, arrivalDate, 
            numPassengers, user.getUsername());
  
      // this service will return a list of size > 0 
      List<CarInfo> carList = new ArrayList<CarInfo>(); 
      // params: carId, model, make, year, trany, rentalPrice, state, city
      carList.add(new CarInfo(5, "Cruze Premier", "Chevrolet", 2018, "Automatic", 557.0, "CA", "Los Angeles"));
      carList.add(new CarInfo(7, "Impala", "Chevrolet", 2018, "Automatic", 677.0, "CA", "Los Angeles"));
      carList.add(new CarInfo(19, "Charger AWD", "Dodge", 2018, "Automatic", 921.0, "CA", "Los Angeles"));
      given(carService.getAvailableCars(destinationCity, departureDate,
         arrivalDate)).willReturn(carList);
  
      // this service will return a list of size > 0 
      List<HotelInfo> hotelList = new ArrayList<HotelInfo>(); 
      hotelList.add(new HotelInfo(1, "Weston", "12456 some address",
            3, "Sacramento", "CA", 100, "2021-06-01", 4));
      hotelList.add(new HotelInfo(2, "Hampton Inn", "12456 some address",
            2, "Sacramento", "CA", 200, "2021-06-01", 2));
      given(hotelService.getAvailableHotels(destinationCity,
         arrivalDate, destinationState)).willReturn(hotelList);
  
      // this service will return a list of size > 0 
      List<FlightInfo> flightList = new ArrayList<FlightInfo>(); 
      flightList.add(new FlightInfo(1, "some flight number", "Los Angeles", "Sacramento", "2021-07-01", "2021-07-01", "some duration", 1, 1, 1, 1, 1));
      flightList.add(new FlightInfo(2, "some other flight number", "Los Angeles", "Sacramento", "2021-07-01", "2021-07-01", "some duration", 1, 1, 1, 1, 1));
      given(flightService.getAvailableFlights(startingCity, destinationCity,
         departureDate, numPassengers)).willReturn(flightList);
  
      // Have the package service use our trip info to generate a list of packages 
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
  
      // Generate the expected output 
      List<Package> expectedPackageList = new ArrayList<Package>(); 
      expectedPackageList.add(
         new Package(carList.get(0), hotelList.get(0), flightList.get(0))); 
      expectedPackageList.add(
         new Package(carList.get(1), hotelList.get(1), flightList.get(1)));
  
      // Check if actual result matches expected result
      assertThat(actualPackageList).isEqualTo(expectedPackageList); 
   }
}
