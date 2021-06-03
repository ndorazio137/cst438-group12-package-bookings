package cst438.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438.domain.Package;
import cst438.domain.PackageRepository;
import cst438.domain.TripInfo;

@Service
public class PackageService {
	@Autowired
	private CarService carService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private FlightService flightService;
	
	public PackageService( ) { }
	
	public PackageService(CarService carService, HotelService hotelService,
      FlightService flightService) {
      this.carService = carService;
      this.hotelService = hotelService;
      this.flightService = flightService;
   }

   public List<Package> getPackageList(TripInfo tripInfo) {
		
      System.out.println("Trip Info: ");
      System.out.println(tripInfo);
      
		String startingCity = tripInfo.getStartingCity();
		String destinationCity = tripInfo.getDestinationCity();
		Date departureDate = tripInfo.getDepartureDate();
		Date arrivalDate = tripInfo.getArrivalDate();
		
		ArrayList<Package> packageList = new ArrayList<Package>();
		
		List<Object> availableCarList = carService.getAvailableCars(destinationCity, arrivalDate);
		List<Object> availableHotelList = hotelService.getAvailableHotels(destinationCity, arrivalDate);
		List<Object> availableFlightList = flightService.getAvailableFlights(startingCity, destinationCity, departureDate);
		
		System.out.println(availableCarList);
		System.out.println(availableHotelList);
		System.out.println(availableFlightList);
		
		if (!exists(availableCarList) || !exists(availableHotelList) || !exists(availableFlightList)) {
		   return null;
		}
		
      int shortestListSize = availableCarList.size(); 
      if (availableHotelList.size() < shortestListSize) 
         shortestListSize = availableHotelList.size(); 
      if (availableFlightList.size() < shortestListSize) 
         shortestListSize = availableFlightList.size();
      System.out.println(shortestListSize);
      for (int i = 0; i < shortestListSize; i++) { 
         Package currentPackage = new Package( availableCarList.get(i), 
                                               availableHotelList.get(i),
                                               availableFlightList.get(i)); 
         packageList.add(currentPackage); 
      }
      System.out.println(packageList);
		return packageList;
	}
	
   private boolean exists(List<Object> list) {
      if (list != null) {
         return true;
      }
      
      return false;
   }
}
