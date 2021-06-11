package cst438.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import cst438.domain.Package;
import cst438.service.PackageService;

@Controller
public class PackageController {
   @Autowired
   PackageService packageService;

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
   
   // Package Form submission
   @PostMapping("/packages")
   public String getCityInfo(@Valid TripInfo tripInfo, 
         BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "trip_info_form";
      }

      /*
       * List<Package> packageList = packageService.getPackageList(tripInfo);
       * 
       * if (packageList == null) return "packages_error";
       * 
       * model.addAttribute("packageList", packageList);
       */
      List<Package> packageList = new ArrayList<Package>();
      
      int i;
      for (i = 0; i < 10; i++) {
         Package pkge = new Package("Car", "Hotel", "Flight");
         packageList.add(pkge);
      }
      model.addAttribute("packageList", packageList);
      return "packages_show";
   }
}
