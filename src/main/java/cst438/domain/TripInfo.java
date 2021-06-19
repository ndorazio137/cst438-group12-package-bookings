package cst438.domain;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

//Helper class.
public class TripInfo {
   @NotNull
   @Size(min=1, max=45)
   String startingCity;
   @NotNull
   @Size(min=1, max=2)
   String startingState;
   @NotNull
   @Size(min=1, max=45)
   String destinationCity;
   @NotNull
   @Size(min=1, max=2)
   String destinationState;
   @DateTimeFormat(pattern = "yyyy-mm-dd")
   Date departureDate;
   @DateTimeFormat(pattern = "yyyy-mm-dd")
   Date arrivalDate;
   
   @NotNull
   @Size(min=1, max=254)
   String username;
   
   @NotNull
   @Min(1)
   private int numPassengers;
   
   public TripInfo() {}
   
   public TripInfo(String startingCity, String startingState, 
      String destinationCity, String destinationState,
      Date departureDate, Date arrivalDate, int numPassengers, String username) {
      this.startingCity = startingCity;
      this.startingState = startingState;
      this.destinationCity = destinationCity;
      this.destinationState = destinationState;
      this.departureDate = departureDate;
      this.arrivalDate = arrivalDate;
      this.numPassengers = numPassengers;
      this.username = username;
   }

   public String getStartingCity() {
      return startingCity;
   }

   public void setStartingCity(String startingCity) {
      this.startingCity = startingCity;
   }

   public String getStartingState() {
      return startingState;
   }

   public void setStartingState(String startingState) {
      this.startingState = startingState;
   }

   public String getDestinationCity() {
      return destinationCity;
   }

   public void setDestinationCity(String destinationCity) {
      this.destinationCity = destinationCity;
   }

   public String getDestinationState() {
      return destinationState;
   }

   public void setDestinationState(String destinationState) {
      this.destinationState = destinationState;
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

   public int getNumPassengers() {
      return numPassengers;
   }
   
   public void setNumPassengers(int numPassengers) {
      this.numPassengers = numPassengers;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((arrivalDate == null) ? 0 : arrivalDate.hashCode());
      result = prime * result + ((departureDate == null) ? 0 : departureDate.hashCode());
      result = prime * result + ((destinationCity == null) ? 0 : destinationCity.hashCode());
      result = prime * result + ((destinationState == null) ? 0 : destinationState.hashCode());
      result = prime * result + numPassengers;
      result = prime * result + ((startingCity == null) ? 0 : startingCity.hashCode());
      result = prime * result + ((startingState == null) ? 0 : startingState.hashCode());
      result = prime * result + ((username == null) ? 0 : username.hashCode());
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
      if (destinationState == null) {
         if (other.destinationState != null)
            return false;
      } else if (!destinationState.equals(other.destinationState))
         return false;
      if (numPassengers != other.numPassengers)
         return false;
      if (startingCity == null) {
         if (other.startingCity != null)
            return false;
      } else if (!startingCity.equals(other.startingCity))
         return false;
      if (startingState == null) {
         if (other.startingState != null)
            return false;
      } else if (!startingState.equals(other.startingState))
         return false;
      if (username == null) {
         if (other.username != null)
            return false;
      } else if (!username.equals(other.username))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "TripInfo [startingCity=" + startingCity + ", startingState=" + startingState + ", destinationCity="
            + destinationCity + ", destinationState=" + destinationState + ", departureDate=" + departureDate
            + ", arrivalDate=" + arrivalDate + ", username=" + username + ", numPassengers=" + numPassengers + "]";
   }
   
}
