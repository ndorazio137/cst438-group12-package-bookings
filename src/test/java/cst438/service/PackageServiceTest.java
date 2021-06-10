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

import cst438.domain.Package;

@SpringBootTest
public class PackageServiceTest {
   //*********MOCKBEANS*************
   @MockBean
   private CarService carService;
   @MockBean
   private HotelService hotelService;
   @MockBean
   private FlightService flightService;
   @Autowired
   private PackageService packageService;
   
   //*********TEST CASES***********
   
   // Test the case where all services are down or otherwise return null
   @Test
   public void testAllServiceResponsesNull() throws Exception {
      // Create TripInfo Object
      String startingCity = "startingCity";
      String destinationCity = "DestinationCity";
      Date randomDepartureDate = new Date(1995, 11, 17);
      Date randomArrivalDate = new Date(1995, 11, 17);
      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, randomDepartureDate, randomArrivalDate);
      
      // Mock null responses from the services
      given(carService.getAvailableCars(destinationCity, randomDepartureDate, randomArrivalDate)).willReturn(null);
      given(hotelService.getAvailableHotels(destinationCity, randomArrivalDate)).willReturn(null);
      given(flightService.getAvailableFlights(startingCity, destinationCity, randomDepartureDate)).willReturn(null);
      
      // Check for null response package list
      assertThat(packageService.getPackageList(tripInfo)).isEqualTo(null);
   }
   
   // Test the case where there are no cars, hotels, or flights available
   @Test
   public void testNoAvailability() throws Exception {
      
      // Define some trip info
      String startingCity = "Chicago";
      String destinationCity = "Miami";
      Date departureDate = new Date(1995, 11, 17);
      Date arrivalDate = new Date(1995, 11, 17);
      // Set up the helper object to store our trip info

      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, departureDate, arrivalDate);
      
      // the list returned by the service will be an empty list
      ArrayList<Object> carList = new ArrayList<Object>();
      given(carService.getAvailableCars(startingCity, departureDate, arrivalDate)).willReturn(carList);
      
      // the list returned by the service will be an empty list
      ArrayList<Object> hotelList = new ArrayList<Object>();
      given(hotelService.getAvailableHotels(startingCity, departureDate)).willReturn(hotelList);
      
      // the list returned by the service will be an empty list
      ArrayList<Object> flightList = new ArrayList<Object>();
      given(flightService.getAvailableFlights(startingCity, destinationCity, departureDate)).willReturn(flightList);
      
      // Have the package service use our trip info to generate a list of packages
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
      
      // Generate the expected output, an empty list
      List<Package> expectedPackageList = new ArrayList<Package>();
      
      // Check if actual result matches expected result
      assertThat(expectedPackageList).isEqualTo(actualPackageList);
   }
   
   // Test the case where only the car service response is null
   @Test
   public void testCarServiceResponseNull() throws Exception {
      // Create TripInfo Object
      String startingCity = "Chicago";
      String destinationCity = "Miami";
      Date departureDate = new Date(1995, 11, 17);
      Date arrivalDate = new Date(1995, 11, 17);
      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, departureDate, arrivalDate);
      
      // this service will return a null value
      given(carService.getAvailableCars(destinationCity, departureDate, arrivalDate)).willReturn(null);
      
      // this service will return a list of size > 0
      ArrayList<Object> hotelList = new ArrayList<Object>();
      hotelList.add("Sheraton");
      hotelList.add("Motel 6");
      hotelList.add("Best Western");
      hotelList.add("Hilton");
      given(hotelService.getAvailableHotels(destinationCity, arrivalDate)).willReturn(hotelList);
      
      // this service will return a list of size > 0
      ArrayList<Object> flightList = new ArrayList<Object>();
      flightList.add("Allegiant");
      flightList.add("Delta");
      given(flightService.getAvailableFlights(startingCity, destinationCity, departureDate)).willReturn(flightList);
      
      // Have the package service use our trip info to generate a list of packages
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
      
      // Generate the expected output, a null value
      List<Package> expectedPackageList = null;
      
      // Check if actual result matches expected result
      assertThat(actualPackageList).isEqualTo(expectedPackageList);
   }
   
   // Test the case where only the hotel service response is null
   @Test
   public void testHotelServiceResponseNull() throws Exception {
      // Create TripInfo Object
      String startingCity = "Chicago";
      String destinationCity = "Miami";
      Date departureDate = new Date(1995, 11, 17);
      Date arrivalDate = new Date(1995, 11, 17);
      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, departureDate, arrivalDate);
      
      // this service will return a list of size > 0
      ArrayList<Object> carList = new ArrayList<Object>();
      carList.add("Ford Fusion");
      carList.add("Honda CR-V");
      carList.add("Toyota Camry");
      given(carService.getAvailableCars(destinationCity, departureDate, arrivalDate)).willReturn(carList);
      
      // this service will return a null value
      given(hotelService.getAvailableHotels(destinationCity, arrivalDate)).willReturn(null);
      
      // this service will return a list of size > 0
      ArrayList<Object> flightList = new ArrayList<Object>();
      flightList.add("Allegiant");
      flightList.add("Delta");
      given(flightService.getAvailableFlights(startingCity, destinationCity, departureDate)).willReturn(flightList);
      
      // Have the package service use our trip info to generate a list of packages
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
      
      // Generate the expected output, a null value
      List<Package> expectedPackageList = null;
      
      // Check if actual result matches expected result
      assertThat(actualPackageList).isEqualTo(expectedPackageList);
   }
   
   // Test the case where only the flight service response is null
   @Test
   public void testFlightServiceResponseNull() throws Exception {
      // Create TripInfo Object
      String startingCity = "Chicago";
      String destinationCity = "Miami";
      Date departureDate = new Date(1995, 11, 17);
      Date arrivalDate = new Date(1995, 11, 17);
      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, departureDate, arrivalDate);
      
      // this service will return a list of size > 0
      ArrayList<Object> carList = new ArrayList<Object>();
      carList.add("Ford Fusion");
      carList.add("Honda CR-V");
      carList.add("Toyota Camry");
      given(carService.getAvailableCars(destinationCity, departureDate, arrivalDate)).willReturn(carList);
      
      // this service will return a list of size > 0
      ArrayList<Object> hotelList = new ArrayList<Object>();
      hotelList.add("Sheraton");
      hotelList.add("Motel 6");
      hotelList.add("Best Western");
      hotelList.add("Hilton");
      given(hotelService.getAvailableHotels(destinationCity, arrivalDate)).willReturn(hotelList);
      
      // this service will return a null value
      given(flightService.getAvailableFlights(startingCity, destinationCity, departureDate)).willReturn(null);
      
      // Have the package service use our trip info to generate a list of packages
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
      
      // Generate the expected output, a null value
      List<Package> expectedPackageList = null;
      
      // Check if actual result matches expected result
      assertThat(actualPackageList).isEqualTo(expectedPackageList);
   }
   
   // All three return valid
   @Test
   public void testAllGoodResponses() throws Exception {
      // Create TripInfo Object
      String startingCity = "Chicago";
      String destinationCity = "Miami";
      Date departureDate = new Date(1995, 11, 17);
      Date arrivalDate = new Date(1995, 11, 18);

      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, departureDate, arrivalDate);
      
      // this service will return a list of size > 0
      List<Object> carList = new ArrayList<Object>();
      carList.add("Ford Fusion");
      carList.add("Honda CR-V");
      carList.add("Toyota Camry");
      given(carService.getAvailableCars(destinationCity, departureDate, arrivalDate)).willReturn(carList);
      
      // this service will return a list of size > 0
      List<Object> hotelList = new ArrayList<Object>();
      hotelList.add("Sheraton");
      hotelList.add("Motel 6");
      hotelList.add("Best Western");
      hotelList.add("Hilton");
      given(hotelService.getAvailableHotels(destinationCity, arrivalDate)).willReturn(hotelList);
      
      // this service will return a list of size > 0
      List<Object> flightList = new ArrayList<Object>();
      flightList.add("Allegiant");
      flightList.add("Delta");
      given(flightService.getAvailableFlights(startingCity, destinationCity, departureDate)).willReturn(flightList);
      
      // Have the package service use our trip info to generate a list of packages
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
      
      // Generate the expected output
      List<Package> expectedPackageList = new ArrayList<Package>();
      expectedPackageList.add(new Package(carList.get(0), hotelList.get(0), flightList.get(0)));
      expectedPackageList.add(new Package(carList.get(1), hotelList.get(1), flightList.get(1)));
      
      // Check if actual result matches expected result
      assertThat(actualPackageList).isEqualTo(expectedPackageList);
   }
}
