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
import org.springframework.boot.test.mock.mockito.MockBean;
import cst438.domain.TripInfo;

@WebMvcTest(PackageServiceTest.class)
public class PackageServiceTest {
   //*********MOCKBEANS*************
   @MockBean
   private CarService carService;
   @MockBean
   private HotelService hotelService;
   @MockBean
   private FlightService flightService;

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
}
