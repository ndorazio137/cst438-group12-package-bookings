package cst438.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cst438.domain.TripInfo;
import cst438.domain.CarInfo;
import cst438.domain.Package;
import cst438.service.CarService;
import cst438.service.PackageService;

@Controller
public class PackageController {
   @Autowired
   private PackageService packageService;
   @Autowired
   private CarService carService;

   @GetMapping("/") // localhost:8080/
   public String getIndex(Model model) {
      return "index";
   }

   @GetMapping("/packages") // localhost:8080/packages
   public String getPackageForm(Model model) {
      TripInfo tripInfo = new TripInfo();
      model.addAttribute("tripInfo", tripInfo);
      return "trip_info_form";
   }
   
//   // Package Form submission
//   @PostMapping("/packages")
//   public String getPackageInfo(@Valid TripInfo tripInfo, 
//         BindingResult result, Model model) {
//      if (result.hasErrors()) {
//         return "trip_info_form";
//      }
//
//      /*
//       * List<Package> packageList = packageService.getPackageList(tripInfo);
//       * 
//       * if (packageList == null) return "packages_error";
//       * 
//       * model.addAttribute("packageList", packageList);
//       */
//      List<Package> packageList = new ArrayList<Package>();
//      
//      int i;
//      for (i = 0; i < 10; i++) {
//         Package pkge = new Package("Car", "Hotel", "Flight");
//         packageList.add(pkge);
//      }
//      model.addAttribute("packageList", packageList);
//      return "packages_show";
//   }
   
   
   // Testing API endpoint
   @PostMapping("/packages")
   public String getCarList(@Valid TripInfo tripInfo, 
      BindingResult result,
      Model model) throws ParseException {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
      String cityName = "Los Angeles";
      String dateInString = "1-Jul-2021";
      Date startDate = formatter.parse(dateInString);
      Date endDate = formatter.parse(dateInString);
      List<CarInfo> carList = carService.getAvailableCars(cityName, startDate, endDate);
      System.out.println(carList);
      model.addAttribute("carList", carList);
      return "test";
   }
   
   // Testing API endpoint
   @GetMapping("/packages/details")
   public String getCarDetails(
      Model model) {
      int carId = 383;
      CarInfo carInfo = carService.getCarDetails(carId);
      System.out.println(carInfo.toString());
      List<CarInfo> carList = new ArrayList<CarInfo>();
      carList.add(carInfo);
      
      model.addAttribute("carList", carList);
      return "test";
   }
}
