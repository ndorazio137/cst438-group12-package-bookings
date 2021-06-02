package cst438.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438.domain.Package;
import cst438.domain.PackageRepository;

@Service
public class PackageService {
	@Autowired
	private CarService carService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private FlightService flightService;
	
	public PackageService( ) { }
	
	public List<Package> getInfo(String fromCityName, String cityName, Date date) {
		
		ArrayList<Package> packageList = new ArrayList<Package>();
		
		List<Object> availableCarList = carService.getAvailableCars(cityName, date);
		List<Object> availableHotelList = hotelService.getAvailableHotels(cityName, date);
		List<Object> availableFlightList = flightService.getAvailableFlights(fromCityName, cityName, date);

		// using the available cars, hotels, and flights, generate packages...
		for (Object currentFlight : availableFlightList) {
			for (Object currentHotel : availableHotelList) {
				for (Object currentCar : availableCarList) {
					Package currentPackage = new Package(currentCar, currentHotel, currentFlight);
					packageList.add(currentPackage);
				}
			}
		}
		
		return packageList;
	}
	
}
