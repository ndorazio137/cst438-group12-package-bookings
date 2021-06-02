package cst438.service;

//********* Static imports ***************
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
//******** Non-Static imports ***********
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
   public void testNullPackage() throws Exception {
      // Create an empty response array
      
      
   }
   
}
