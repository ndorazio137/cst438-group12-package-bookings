package cst438.service;

//********* Static imports ***************
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
//******** Non-Static imports ***********
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import cst438.domain.TripInfo;

import cst438.domain.Package;
import cst438.domain.TripInfo;

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
   @Test
   public void testAllServiceResponsesNull() throws Exception {
      // Create an empty response array
      String startingCity = "startingCity";
      String destinationCity = "DestinationCity";
      Date randomDepartureDate = new Date(1995, 11, 17);
      Date randomArrivalDate = new Date(1995, 11, 17);
      
      given(carService.getAvailableCars(destinationCity, randomDepartureDate)).willReturn(null);
      given(hotelService.getAvailableHotels(destinationCity, randomDepartureDate)).willReturn(null);
      given(flightService.getAvailableFlights(startingCity, destinationCity, randomDepartureDate)).willReturn(null);

      PackageService packageService = new PackageService(carService, hotelService, flightService);
      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, randomDepartureDate, randomArrivalDate);
      
      assertThat(packageService.getPackageList(tripInfo)).isEqualTo(null);
   }
   
   // All three return valid
   @Test
   public void testAllGoodResponses() throws Exception {
      
      String startingCity = "Chicago";
      String destinationCity = "Miami";
      Date departureDate = new Date("2020-06-03");
      Date arrivalDate = new Date("2020-06-04");
      
      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, departureDate, arrivalDate);
      
      ArrayList<Object> carList = new ArrayList<Object>();
      carList.add("Ford Fusion");
      carList.add("Honda CR-V");
      carList.add("Toyota Camry");
      given(carService.getAvailableCars(startingCity,departureDate)).willReturn(carList);
      
      ArrayList<Object> hotelList = new ArrayList<Object>();
      hotelList.add("Sheraton");
      hotelList.add("Motel 6");
      hotelList.add("Best Western");
      hotelList.add("Hilton");
      given(hotelService.getAvailableHotels(startingCity,departureDate)).willReturn(hotelList);
      
      ArrayList<Object> flightList = new ArrayList<Object>();
      flightList.add("Allegiant");
      flightList.add("Delta");
      given(flightService.getAvailableFlights(startingCity,destinationCity,departureDate)).willReturn(flightList);
      
      // Have the package service use our trip info to generate a list of packages
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
      
      // Generate the expected output
      List<Package> expectedPackageList = new ArrayList<Package>();
      expectedPackageList.add(new Package(carList.get(0), hotelList.get(0), flightList.get(0)));
      expectedPackageList.add(new Package(carList.get(1), hotelList.get(1), flightList.get(1)));
      
      // Check if actual result matches expected result
      // assertEquals(expected, actual);
      assertThat(expectedPackageList).isEqualTo(actualPackageList);
   }
   
   // Test when some services return good responses and some bad.
   // In this case, we'll see what happens if Car and Hotel Services are good but FlightService returns null
   @Test
   public void testOneServiceResponseNull() throws Exception {
      
      String startingCity = "Chicago";
      String destinationCity = "Miami";
      Date departureDate = new Date("2020-06-03");
      Date arrivalDate = new Date("2020-06-04");
      
      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, departureDate, arrivalDate);
      
      // the service will return a list of size > 0
      ArrayList<Object> carList = new ArrayList<Object>();
      carList.add("Ford Fusion");
      carList.add("Honda CR-V");
      carList.add("Toyota Camry");
      given(carService.getAvailableCars(startingCity,departureDate)).willReturn(carList);
      
      // the service will return a list of size > 0
      ArrayList<Object> hotelList = new ArrayList<Object>();
      hotelList.add("Sheraton");
      hotelList.add("Motel 6");
      hotelList.add("Best Western");
      hotelList.add("Hilton");
      given(hotelService.getAvailableHotels(startingCity,departureDate)).willReturn(hotelList);
      
      // the service will return null, rather than a list
      ArrayList<Object> flightList = new ArrayList<Object>();
      flightList.add("Allegiant");
      flightList.add("Delta");
      // Return null for flight service
      given(flightService.getAvailableFlights(startingCity,destinationCity,departureDate)).willReturn(null);
      
      // Have the package service use our trip info to generate a list of packages
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
      
      // Generate the expected output, an empty list
      List<Package> expectedPackageList = new ArrayList<Package>();
      
      // Check if actual result matches expected result
      // assertEquals(expected, actual);
      assertThat(expectedPackageList).isEqualTo(actualPackageList);
   }
   
   // Test the case where there are no cars, hotels, or flights available
   @Test
   public void testNoAvailability() throws Exception {
      
      // Define some trip info
      String startingCity = "Chicago";
      String destinationCity = "Miami";
      Date departureDate = new Date("2020-06-03");
      Date arrivalDate = new Date("2020-06-04");
      // Set up the helper object to store our trip info
      TripInfo tripInfo = new TripInfo(startingCity, destinationCity, departureDate, arrivalDate);
      
      // the list returned by the service will be an empty list
      ArrayList<Object> carList = new ArrayList<Object>();
      given(carService.getAvailableCars(startingCity,departureDate)).willReturn(carList);
      
      // the list returned by the service will be an empty list
      ArrayList<Object> hotelList = new ArrayList<Object>();
      given(hotelService.getAvailableHotels(startingCity,departureDate)).willReturn(hotelList);
      
      // the list returned by the service will be an empty list
      ArrayList<Object> flightList = new ArrayList<Object>();
      given(flightService.getAvailableFlights(startingCity,destinationCity,departureDate)).willReturn(flightList);
      
      // Have the package service use our trip info to generate a list of packages
      List<Package> actualPackageList = packageService.getPackageList(tripInfo);
      
      // Generate the expected output, an empty list
      List<Package> expectedPackageList = new ArrayList<Package>();
      
      // Check if actual result matches expected result
      // assertEquals(expected, actual);
      assertThat(expectedPackageList).isEqualTo(actualPackageList);
   }
}
