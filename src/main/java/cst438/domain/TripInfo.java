package cst438.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TripInfo {
   @NotNull
   @Size(min=1, max=45)
   String startingCity;
   @NotNull
   @Size(min=1, max=45)
   String destinationCity;
   @NotNull
   Date departureDate;
   @NotNull
   Date arrivalDate;
   
   TripInfo() {}
   
   TripInfo(String startingCity, String destinationCity, Date departureDate,
         Date arrivalDate) {
      this.startingCity = startingCity;
      this.destinationCity = destinationCity;
      this.departureDate = departureDate;
      this.arrivalDate = arrivalDate;
   }

   public String getStartingCity() {
      return startingCity;
   }

   public void setStartingCity(String startingCity) {
      this.startingCity = startingCity;
   }

   public String getDestinationCity() {
      return destinationCity;
   }

   public void setDestinationCity(String destinationCity) {
      this.destinationCity = destinationCity;
   }

   public Date getDepartureDate() {
      return departureDate;
   }

   public void setDepartureDate(Date departureDate) {
      this.departureDate = departureDate;
   }

   public Date getArrivalDate() {
      return arrivalDate;
   }

   public void setArrivalDate(Date arrivalDate) {
      this.arrivalDate = arrivalDate;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((arrivalDate == null) ? 0 : arrivalDate.hashCode());
      result = prime * result
            + ((departureDate == null) ? 0 : departureDate.hashCode());
      result = prime * result
            + ((destinationCity == null) ? 0 : destinationCity.hashCode());
      result = prime * result
            + ((startingCity == null) ? 0 : startingCity.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      
      if (obj == null)
         return false;
      
      if (getClass() != obj.getClass())
         return false;
      
      TripInfo other = (TripInfo) obj;
      
      if (arrivalDate == null) {
         if (other.arrivalDate != null)
            return false;
      } else if (!arrivalDate.equals(other.arrivalDate))
         return false;
      
      if (departureDate == null) {
         if (other.departureDate != null)
            return false;
      } else if (!departureDate.equals(other.departureDate))
         return false;
      
      if (destinationCity == null) {
         if (other.destinationCity != null)
            return false;
      } else if (!destinationCity.equals(other.destinationCity))
         return false;
      
      if (startingCity == null){
         if (other.startingCity != null)
            return false;
      } else if (!startingCity.equals(other.startingCity))
         return false;
      
      return true;
   }
}
