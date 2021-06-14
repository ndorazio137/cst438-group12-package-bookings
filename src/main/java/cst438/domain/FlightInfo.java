package cst438.domain;

import java.util.Date;

//Helper class.
public class FlightInfo {
   private long id;
   private String flightNumber;
   private String departureCity;
   private String arrivalCity;
   private String departureDate;
   private String arrivalDate;
   private String duration;
   private int distance;
   private int cost;
   private int totalSeats;
   private int remainingSeats;
   private int reservedSeats;
   
   public FlightInfo(long id, String flightNumber, String departureCity, String arrivalCity, String departureDate,
         String arrivalDate, String duration, int distance, int cost, int totalSeats, int remainingSeats, int reservedSeats) {
      super();
      this.id = id;
      this.flightNumber = flightNumber;
      this.departureCity = departureCity;
      this.arrivalCity = arrivalCity;
      this.departureDate = departureDate;
      this.arrivalDate = arrivalDate;
      this.duration = duration;
      this.distance = distance;
      this.cost = cost;
      this.totalSeats = totalSeats;
      this.remainingSeats = remainingSeats;
      this.reservedSeats = reservedSeats;
   }

   public long getId() {
      return id;
   }
   
   public void setId(long id) {
      this.id = id;
   }

   public String getFlightNumber() {
      return flightNumber;
   }

   public void setFlightNumber(String flightNumber) {
      this.flightNumber = flightNumber;
   }

   public String getDepartureCity() {
      return departureCity;
   }

   public void setDepartureCity(String departureCity) {
      this.departureCity = departureCity;
   }

   public String getArrivalCity() {
      return arrivalCity;
   }

   public void setArrivalCity(String arrivalCity) {
      this.arrivalCity = arrivalCity;
   }

   public String getDepartureDate() {
      return departureDate;
   }

   public void setDepartureDate(String departureDate) {
      this.departureDate = departureDate;
   }

   public String getArrivalDate() {
      return arrivalDate;
   }

   public void setArrivalDate(String arrivalDate) {
      this.arrivalDate = arrivalDate;
   }

   public String getDuration() {
      return duration;
   }

   public void setDuration(String duration) {
      this.duration = duration;
   }

   public int getDistance() {
      return distance;
   }

   public void setDistance(int distance) {
      this.distance = distance;
   }

   public int getCost() {
      return cost;
   }

   public void setCost(int cost) {
      this.cost = cost;
   }

   public int getTotalSeats() {
      return totalSeats;
   }

   public void setTotalSeats(int totalSeats) {
      this.totalSeats = totalSeats;
   }
   
   public int getRemainingSeats() {
      return remainingSeats;
   }

   public void setRemainingSeats(int remainingSeats) {
      this.remainingSeats = remainingSeats;
   }

   public int getReservedSeats() {
      return reservedSeats;
   }

   public void setReservedSeats(int reservedSeats) {
      this.reservedSeats = reservedSeats;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
         + ((arrivalCity == null) ? 0 : arrivalCity.hashCode());
      result = prime * result
         + ((arrivalDate == null) ? 0 : arrivalDate.hashCode());
      result = prime * result + cost;
      result = prime * result
         + ((departureCity == null) ? 0 : departureCity.hashCode());
      result = prime * result
         + ((departureDate == null) ? 0 : departureDate.hashCode());
      result = prime * result + distance;
      result = prime * result + ((duration == null) ? 0 : duration.hashCode());
      result = prime * result
         + ((flightNumber == null) ? 0 : flightNumber.hashCode());
      result = prime * result + (int) (id ^ (id >>> 32));
      result = prime * result + remainingSeats;
      result = prime * result + reservedSeats;
      result = prime * result + totalSeats;
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
      FlightInfo other = (FlightInfo) obj;
      if (arrivalCity == null) {
         if (other.arrivalCity != null)
            return false;
      } else if (!arrivalCity.equals(other.arrivalCity))
         return false;
      if (arrivalDate == null) {
         if (other.arrivalDate != null)
            return false;
      } else if (!arrivalDate.equals(other.arrivalDate))
         return false;
      if (cost != other.cost)
         return false;
      if (departureCity == null) {
         if (other.departureCity != null)
            return false;
      } else if (!departureCity.equals(other.departureCity))
         return false;
      if (departureDate == null) {
         if (other.departureDate != null)
            return false;
      } else if (!departureDate.equals(other.departureDate))
         return false;
      if (distance != other.distance)
         return false;
      if (duration == null) {
         if (other.duration != null)
            return false;
      } else if (!duration.equals(other.duration))
         return false;
      if (flightNumber == null) {
         if (other.flightNumber != null)
            return false;
      } else if (!flightNumber.equals(other.flightNumber))
         return false;
      if (id != other.id)
         return false;
      if (remainingSeats != other.remainingSeats)
         return false;
      if (reservedSeats != other.reservedSeats)
         return false;
      if (totalSeats != other.totalSeats)
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "FlightInfo [id=" + id + ", flightNumber=" + flightNumber
         + ", departureCity=" + departureCity + ", arrivalCity=" + arrivalCity
         + ", departureDate=" + departureDate + ", arrivalDate=" + arrivalDate
         + ", duration=" + duration + ", distance=" + distance + ", cost="
         + cost + ", totalSeats=" + totalSeats + ", remainingSeats="
         + remainingSeats + ", reservedSeats=" + reservedSeats + "]";
   }
}
