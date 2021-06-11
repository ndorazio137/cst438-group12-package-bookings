package cst438.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438.domain.CarInfo;
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
		String destinationCity = tripInfo.getDestinationCity();
		Date departureDate = tripInfo.getDepartureDate();
		Date arrivalDate = tripInfo.getArrivalDate();
		
		List<Package> packageList = new ArrayList<Package>();
		
		List<CarInfo> availableCarList = 
		   carService.getAvailableCars(destinationCity, departureDate, arrivalDate);
		
		System.out.println("availableCarList: " + availableCarList);
		
		List<Object> availableHotelList = 
		   hotelService.getAvailableHotels(destinationCity, arrivalDate);
		List<Object> availableFlightList = 
		   flightService.getAvailableFlights(startingCity, destinationCity, departureDate);
		
		if (isNullCarList(availableCarList) || isNull(availableHotelList) 
		      || isNull(availableFlightList)) {
		   return null;
		}
		
		for (CarInfo car: availableCarList) {
         System.out.println(car.getId());
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
      if (list == null) 
         return true;
      
      return false;
   }
   
   private boolean isNull(List<Object> list) {
      if (list == null) 
         return true;
      
      return false;
   }
   
   public List<Reservation> getReservationsByUser(int userId) {
      List<Reservation> reservationList = reservationRepository.findByUserId(userId);
      return reservationList;
   }
}
