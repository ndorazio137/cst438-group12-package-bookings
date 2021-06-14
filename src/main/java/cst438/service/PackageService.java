package cst438.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438.domain.CarInfo;
import cst438.domain.FlightInfo;
import cst438.domain.HotelInfo;
import cst438.domain.Package;
import cst438.domain.Reservation;
import cst438.domain.ReservationRepository;
import cst438.domain.TripInfo;

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
		      hotelService.getAvailableHotels(destinationCity, arrivalDate, destinationState);
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
      if (availableHotelList.size() < shortestListSize) 
         shortestListSize = availableHotelList.size(); 
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
}
