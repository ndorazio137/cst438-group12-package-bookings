package cst438.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

public class PackageRestController
{
   @GetMapping("/api/{service}")
   public Object getAvailableCars(@PathVariable("service") String serviceName) {
     return null;
   }
}
