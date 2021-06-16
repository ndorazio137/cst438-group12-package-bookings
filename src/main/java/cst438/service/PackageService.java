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
import cst438.domain.ReservationRepository;
import cst438.domain.TripInfo;
import cst438.domain.User;

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
            destinationState); System.out.println("availableHotelList: " +
               availableHotelList);
       
		
		List<FlightInfo> availableFlightList = 
		      flightService.getAvailableFlights(startingCity, destinationCity, departureDate, passengers);
		System.out.println("availableFlightList: " + availableFlightList);
		
		if (isNullCarList(availableCarList) 
		     // || isNullHotelList(availableHotelList) 
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
                                               availableFlightList.get(i)); 
         packageList.add(currentPackage); 
      }
      System.out.println("Package List: ");
      System.out.println(packageList);
		return packageList;
	}
	
   private boolean isNullCarList(List<CarInfo> list) {
      return (list == null);
   }
   
   /*
    * private boolean isNullHotelList(List<HotelInfo> list) { return (list ==
    * null); }
    */
   
   private boolean isNullFlightList(List<FlightInfo> list) {
      return (list == null);
   }
   
   public List<Reservation> getReservationsByUser(int userId) {
      List<Reservation> reservationList = reservationRepository.findByUserId(userId);
      return reservationList;
   }
   
   public String bookPackage(Package pckage, User user, TripInfo tripInfo) {
      CarInfo car = pckage.getCar();
      FlightInfo flight = pckage.getFlight();
      HotelInfo hotel = pckage.getHotel();
      
      SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
      
      String carId = String.valueOf(car.getId());
      String dateStart = dateFormatter.format(tripInfo.getDepartureDate());
      String dateEnd = dateFormatter.format(tripInfo.getArrivalDate());
      String email = user.getUsername();
      String password = user.getPassword();
      String firstName = user.getFirstName();
      String lastName = user.getLastName();
      long flightId = flight.getId();
      int passengers = tripInfo.getNumPassengers();
      String site = "PACKAGES";
      String date = hotel.getAvailableDate();
      int hotelId = hotel.getId();
      int userId = 1;
      
      // Attempt to book car
      JsonNode carResponse = carService.bookCar(email, carId, dateStart, dateEnd);
      // If car fails, return.
      if (carResponse == null) { 
         System.out.println("Car Reservation failed.");
         return "Car reservation failed.";
      }
      // Car was booked successfully, so get the reservation ID
      String carReservationId = carResponse.get("id").asText();
      // Attempt to book the flight
      JsonNode flightResponse = flightService.bookFlight(email, password, site, firstName, lastName, flightId, passengers);
      // If flight booking fails, cancel car booking and return.
      if (flightResponse == null) { 
         System.out.println("Flight Reservation failed.");
         carService.cancelReservation(carReservationId, email);
         return "Flight reservation failed.";
      }
      // Flight was booked successfully, so get the reservation ID
      String flightReservationId = flightResponse.get("id").asText();
      // Attempt to book hotel
      JsonNode hotelResponse = hotelService.bookHotel(date, hotelId, userId);
      // If hotel booking fails, cancel car & flight, then return.
      if (hotelResponse == null) { 
         System.out.println("hotel Reservation failed.");
         carService.cancelReservation(carReservationId, email);
         flightService.deleteReservation(flightReservationId, email, password, site);
         return "Hotel reservation failed.";
      }
      // Hotel was booked successfully, so get the reservation ID
      String hotelReservationId = hotelResponse.get("id").asText();
      
      // TODO: finish creating reservation object and save in DB
      Reservation reservation = new Reservation(userId, carReservationId,
            hotelReservationId, flightReservationId);
      
      reservationRepository.save(reservation);
      
      return "Package booking successful.";
   }
}
