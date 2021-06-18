package cst438.domain;

import java.util.Date;

public class ReservationInfo {
   
   private String email;      // for car and hotel
   
   private int carId;         // for car booking
   private long flightId;     // for flight booking
   private int hotelId;       // for hotel booking
   
   private int passengers;    // for flight booking
   private Date dateStart;  // for car booking
   private Date dateEnd;    // for car booking
   private String hotelDate;  // for hotel booking, must get from selected hotel
   
   public ReservationInfo(String email, int passengers, Date dateStart, Date dateEnd) {
      super();
      this.email = email;
      this.passengers = passengers;
      this.dateStart = dateStart;
      this.dateEnd = dateEnd;
   }
   
   @Override
   public String toString() {
      return "ReservationInfo [email=" + email + ", carId=" + carId + ", flightId=" + flightId + ", hotelId=" + hotelId
            + ", passengers=" + passengers + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", hotelDate="
            + hotelDate + "]";
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public int getCarId() {
      return carId;
   }

   public void setCarId(int carId) {
      this.carId = carId;
   }

   public long getFlightId() {
      return flightId;
   }

   public void setFlightId(long flightId) {
      this.flightId = flightId;
   }

   public int getHotelId() {
      return hotelId;
   }

   public void setHotelId(int hotelId) {
      this.hotelId = hotelId;
   }

   public int getPassengers() {
      return passengers;
   }

   public void setPassengers(int passengers) {
      this.passengers = passengers;
   }

   public Date getDateStart() {
      return dateStart;
   }

   public void setDateStart(Date dateStart) {
      this.dateStart = dateStart;
   }

   public Date getDateEnd() {
      return dateEnd;
   }

   public void setDateEnd(Date dateEnd) {
      this.dateEnd = dateEnd;
   }

   public String getHotelDate() {
      return hotelDate;
   }

   public void setHotelDate(String hotelDate) {
      this.hotelDate = hotelDate;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + carId;
      result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
      result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
      result = prime * result + ((email == null) ? 0 : email.hashCode());
      result = prime * result + (int) (flightId ^ (flightId >>> 32));
      result = prime * result + ((hotelDate == null) ? 0 : hotelDate.hashCode());
      result = prime * result + hotelId;
      result = prime * result + passengers;
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
      ReservationInfo other = (ReservationInfo) obj;
      if (carId != other.carId)
         return false;
      if (dateEnd == null) {
         if (other.dateEnd != null)
            return false;
      } else if (!dateEnd.equals(other.dateEnd))
         return false;
      if (dateStart == null) {
         if (other.dateStart != null)
            return false;
      } else if (!dateStart.equals(other.dateStart))
         return false;
      if (email == null) {
         if (other.email != null)
            return false;
      } else if (!email.equals(other.email))
         return false;
      if (flightId != other.flightId)
         return false;
      if (hotelDate == null) {
         if (other.hotelDate != null)
            return false;
      } else if (!hotelDate.equals(other.hotelDate))
         return false;
      if (hotelId != other.hotelId)
         return false;
      if (passengers != other.passengers)
         return false;
      return true;
   }
}

