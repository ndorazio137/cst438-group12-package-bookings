package cst438.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import cst438.domain.CarInfo;
import cst438.domain.FlightInfo;
import cst438.domain.HotelInfo;
import cst438.domain.Package;
import cst438.domain.Reservation;
import cst438.domain.ReservationInfo;
import cst438.domain.ReservationRepository;
import cst438.domain.TripInfo;
import cst438.domain.User;
import cst438.domain.UserRepository;

@Service
public class PackageService {
	@Autowired
	private CarService carService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private FlightService flightService;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
   private UserRepository userRepository;
	
	public PackageService( ) { }

   public List<Package> getPackageList(TripInfo tripInfo) {
		
      System.out.println("getPackageList(...): Trip Info: ");
      System.out.println(tripInfo);
      
      String username = tripInfo.getUsername();
      
		String startingCity = tripInfo.getStartingCity();
		String startingState = tripInfo.getStartingState();
		String destinationCity = tripInfo.getDestinationCity();
		String destinationState = tripInfo.getDestinationState();
		Date departureDate = tripInfo.getDepartureDate();
		Date arrivalDate = tripInfo.getArrivalDate();
		int passengers = tripInfo.getNumPassengers();
		
		List<Package> packageList = new ArrayList<Package>();
		
		List<CarInfo> availableCarList = 
		   carService.getAvailableCars(destinationCity, departureDate, arrivalDate);
		System.out.println("availableCarList: " + availableCarList);
		
      List<HotelInfo> availableHotelList =
         hotelService.getAvailableHotels(destinationCity, arrivalDate,
            destinationState); System.out.println("availableHotelList: " +
               availableHotelList);
       
		
		List<FlightInfo> availableFlightList = 
		      flightService.getAvailableFlights(startingCity, destinationCity, departureDate, passengers);
		System.out.println("availableFlightList: " + availableFlightList);
		
		if (isNullCarList(availableCarList) 
		      || isNullHotelList(availableHotelList) 
		      || isNullFlightList(availableFlightList)) {
		   System.out.println("PackageService: one or more of the availability"
		         + "lists came back null");
		   return null;
		}
		
		for (CarInfo car: availableCarList) {
         System.out.println(car.getId());
      }
		
      
      for (HotelInfo hotel: availableHotelList) {
         System.out.println(hotel.getId()); 
      }
       
		
		for (FlightInfo flight: availableFlightList) {
         System.out.println(flight.getId());
      }
		
		int shortestListSize = availableCarList.size(); 
      if (availableHotelList.size() < shortestListSize) shortestListSize =
         availableHotelList.size();
      if (availableFlightList.size() < shortestListSize) 
         shortestListSize = availableFlightList.size();
      
      System.out.println("Shortest list out of the three: " + shortestListSize);
      
      for (int i = 0; i < shortestListSize; i++) { 
         Package currentPackage = new Package( availableCarList.get(i), 
                                               availableHotelList.get(i),
                                               availableFlightList.get(i)
         ); 
         packageList.add(currentPackage); 
      }
      System.out.println("Package List: ");
      System.out.println(packageList);
		return packageList;
	}
	
   private boolean isNullCarList(List<CarInfo> list) {
      return (list == null);
   }
   
   private boolean isNullHotelList(List<HotelInfo> list) { 
      return (list == null); 
   }
   
   private boolean isNullFlightList(List<FlightInfo> list) {
      return (list == null);
   }
   
   public List<Reservation> getReservationsByUser(int userId) {
      List<Reservation> reservationList = reservationRepository.findByUserId(userId);
      return reservationList;
   }
   
   // Uses the data in reservationInfo to get the rest of the data
   // needed to actually book all three services, then books them.
   public String bookPackage(ReservationInfo reservationInfo) {
      
//      CarInfo car = pckage.getCar();
//      FlightInfo flight = pckage.getFlight();
//      HotelInfo hotel = pckage.getHotel();
      // Fetch the user from the DB using the username from tripInfo 
      User user = userRepository.findByUsername(reservationInfo.getEmail()).get(0);
      System.out.println("USER: " + user);
      SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH);
      
      long carId = reservationInfo.getCarId();
      System.out.println("CarID: " + carId);
      String dateStart = dateFormatter.format(reservationInfo.getDateStart());
      System.out.println("DateStart: " + dateStart);
      String dateEnd = dateFormatter.format(reservationInfo.getDateEnd());
      System.out.println("DateEnd: " + dateEnd);
      String email = user.getUsername();
      System.out.println("Email: " + email);
      String password = user.getPassword();
      System.out.println("Password: " + password);
      String firstName = user.getFirstName();
      System.out.println("FirstName: " + firstName);
      String lastName = user.getLastName();
      System.out.println("LastName: " + lastName);
      long flightId = reservationInfo.getFlightId();
      System.out.println("FlightId: " + flightId);
      int passengers = reservationInfo.getPassengers();
      System.out.println("Passengers: " + passengers);

      String site = "PACKAGE";
      String date = reservationInfo.getHotelDate();
      System.out.println("HotelDate: " + date);
      int hotelId = reservationInfo.getHotelId();
      System.out.println("HotelId: " + hotelId);
      // Attempt to book car
      JsonNode carResponse = carService.bookCar(email, carId, dateStart, dateEnd);
      System.out.println(carResponse);
      // If car fails, return.
      if (carResponse == null) { 
         System.out.println("Car Reservation failed.");
         return "Car reservation failed.";
      }
      // Car was booked successfully, so get the reservation ID
      long carReservationId = carResponse.get("id").asLong();
      System.out.println("carReservationId: " + carReservationId);
      // Attempt to book the flight
      JsonNode flightResponse = flightService.bookFlight(email, password, site, firstName, lastName, flightId, passengers);
      System.out.println(flightResponse);
      // If flight booking fails, cancel car booking and return.
      if (flightResponse == null) { 
         System.out.println("Flight Reservation failed.");
         carService.cancelReservation(carReservationId, email);
         return "Flight reservation failed.";
      }
      // Flight was booked successfully, so get the reservation ID
      long flightReservationId = flightResponse.get("id").asLong();
      System.out.println(flightReservationId);
      // Attempt to book hotel
      JsonNode hotelResponse = hotelService.bookHotel(date, hotelId);
      // If hotel booking fails, cancel car & flight, then return.
      if (hotelResponse == null) { 
         System.out.println("hotel Reservation failed.");
         carService.cancelReservation(carReservationId, email);
         flightService.deleteReservation(flightReservationId, email, password, site);
         return "Hotel reservation failed.";
      }
      // Hotel was booked successfully, so get the reservation ID
      long hotelReservationId = hotelResponse.get("id").asLong();
      
      // TODO: finish creating reservation object and save in DB
      long userId = user.getUserId();
      Reservation reservation = new Reservation(userId, carReservationId,
            hotelReservationId, flightReservationId);
      
      reservationRepository.save(reservation);
      
      return "Package booking successful.";
   }
}
