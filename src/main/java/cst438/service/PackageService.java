package cst438.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log =
      LoggerFactory.getLogger(PackageService.class);
	
	public PackageService( ) { }

   public List<Package> getPackageList(TripInfo tripInfo) {
		
      System.out.println("getPackageList(...): Trip Info: ");
      System.out.println(tripInfo);
      
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
            destinationState); 
      System.out.println("availableHotelList: " + availableHotelList);
       
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
      
      System.out.println("ReservationInfo: " + reservationInfo);
      // Fetch the user from the DB using the username from tripInfo 
      User user = userRepository.findByUsername(reservationInfo.getEmail()).get(0);
      System.out.println("USER: " + user);
      
      
      long carId = reservationInfo.getCarId();
      Date dateStart = reservationInfo.getDateStart();
      Date dateEnd = reservationInfo.getDateEnd();
      String email = user.getUsername();
      String password = user.getPassword();
      String firstName = user.getFirstName();
      String lastName = user.getLastName();
      long flightId = reservationInfo.getFlightId();
      int passengers = reservationInfo.getPassengers();
      String date = reservationInfo.getHotelDate();
      System.out.println("HotelDate: " + date);
      int hotelId = reservationInfo.getHotelId();
      System.out.println("HotelId: " + hotelId);
      
      
      // Attempt to book car
      JsonNode carResponse = carService.bookCar(email, carId, dateStart, dateEnd);
      System.out.println(carResponse);
      
      
      // Attempt to book the flight
      JsonNode flightResponse = flightService.bookFlight(email, password, firstName, lastName, flightId, passengers);
      System.out.println(flightResponse);
      
      // Attempt to book hotel
      JsonNode hotelResponse = hotelService.bookHotel(date, hotelId);
      System.out.println(hotelResponse);
      
      // If any of the services fails to reserve item, cancel all reservations attached to the package.
      if (carResponse == null || carResponse.get("id") == null 
            || flightResponse == null
            || hotelResponse == null) {
         cancelCarReservation(carResponse);
         cancelFlightReservation(flightResponse, user);
         cancelHotelReservation(hotelResponse);
         return "We could not book your package at this time";
      }
      
      // Car was booked successfully, so get the reservation ID
      long carReservationId = carResponse.get("id").asLong();
      System.out.println("carReservationId: " + carReservationId);
      
      // Flight was booked successfully, so get the reservation ID
      long flightReservationId = flightResponse.get("reservation").get("id").asLong();
      System.out.println(flightReservationId);
      
      // Hotel was booked successfully, so get the reservation ID
      long hotelReservationId = hotelResponse.get("idreservations").asLong();
      System.out.println(hotelReservationId);
      
      // TODO: finish creating reservation object and save in DB
      long userId = user.getUserId();
      Reservation reservation = new Reservation(userId, carReservationId,
            hotelReservationId, flightReservationId);
      
      reservationRepository.save(reservation);
      
      return "Package booking successful.";
   }
   
   private void cancelCarReservation(JsonNode carResponse) {
      if ( carResponse != null) {
         long carReservationId = carResponse.get("id").asLong();
         String email = carResponse.get("email").asText();
         carService.cancelReservation(carReservationId, email);
         log.info("Car Reservation Successfully Cancelled");
      }
   }
   
   private void cancelFlightReservation(JsonNode flightResponse, User user) {
      if ( flightResponse != null ) {
         long flightId = flightResponse.get("reservation").get("id").asLong();
         String email = user.getUsername();
         String password = user.getPassword();
         flightService.cancelReservation(flightId, email, password);
         log.info("Flight Reservation Successfully Cancelled");
      } 
   }
   
   private void cancelHotelReservation(JsonNode hotelResponse) {
      if ( hotelResponse != null ) {
         long reservationId = hotelResponse.get("idreservations").asLong();
         hotelService.cancelReservation(reservationId);
         log.info("Hotel Reservation Successfully Cancelled");
      }
   }
}

