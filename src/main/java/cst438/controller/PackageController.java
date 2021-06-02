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

import cst438.service.PackageService;

@Controller
public class PackageController
{
	@Autowired
	PackageService packageService;
	
	@GetMapping("/")  // localhost:8080/
	public String getIndex(Model model) {
		return "index";
	}
	
	@GetMapping("/packages")  // localhost:8080/packages
	public String getPackageForm(Model model) {
		return "packageForm";
	}

	// Package Form submission
	@PostMapping("/packages/from/{cityFrom}/to/{cityTo}/date/{date}")
	public String getCityInfo(
			@PathVariable("cityFrom") String cityFromName,
			@PathVariable("cityTo") String cityToName, 
			@PathVariable("date") Date date, 
			Model model) {
		List<cst438.domain.Package> packageList = packageService.getPackageList(cityFromName, cityToName, date);
		
		if (packageList == null) {
			return "packageserror";
		}
		
		model.addAttribute("packageList", packageList);
		
		return "packagesshow"; 
     }
}
