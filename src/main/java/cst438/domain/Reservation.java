package cst438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reservations")
public class Reservation {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long reservationId;
   private long userId;
   private long carReservationId;
   private long hotelReservationId;
   private long flightReservationId;
   private String carReservationJson;
   private String hotelReservationJson;
   private String flightReservationJson;
   
   
   public Reservation(long userId, long carReservationId,
      long hotelReservationId, long flightReservationId) {
      this.userId = userId;
      this.carReservationId = carReservationId;
      this.hotelReservationId = hotelReservationId;
      this.flightReservationId = flightReservationId;
   }

   public Reservation(long reservationId, long userId, long carReservationId,
      long hotelReservationId, long flightReservationId,
      String carReservationJson, String hotelReservationJson,
      String flightReservationJson) {
      super();
      this.reservationId = reservationId;
      this.userId = userId;
      this.carReservationId = carReservationId;
      this.hotelReservationId = hotelReservationId;
      this.flightReservationId = flightReservationId;
      this.carReservationJson = carReservationJson;
      this.hotelReservationJson = hotelReservationJson;
      this.flightReservationJson = flightReservationJson;
   }

   public long getReservationId() {
      return reservationId;
   }

   public void setReservationId(long reservationId) {
      this.reservationId = reservationId;
   }

   public long getUserId() {
      return userId;
   }

   public void setUserId(long userId) {
      this.userId = userId;
   }

   public long getCarReservationId() {
      return carReservationId;
   }

   public void setCarReservationId(long carReservationId) {
      this.carReservationId = carReservationId;
   }
   
   public long getHotelReservationId() {
      return hotelReservationId;
   }

   public void setHotelReservationId(long hotelReservationId) {
      this.hotelReservationId = hotelReservationId;
   }
   
   public long getFlightReservationId() {
      return flightReservationId;
   }
   
   public void setFlightReservationId(long flightReservationId) {
      this.flightReservationId = flightReservationId;
   }

   public String getCarReservationJson() {
      return carReservationJson;
   }

   public void setCarReservationJson(String carReservationJson) {
      this.carReservationJson = carReservationJson;
   }
   
   public String getHotelReservationJson() {
      return hotelReservationJson;
   }

   public void setHotelReservationJson(String hotelReservationJson) {
      this.hotelReservationJson = hotelReservationJson;
   }
   
   public String getFlightReservationJson() {
      return flightReservationJson;
   }

   public void setFlightReservationJson(String flightReservationJson) {
      this.flightReservationJson = flightReservationJson;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
         + (int) (carReservationId ^ (carReservationId >>> 32));
      result = prime * result
         + ((carReservationJson == null) ? 0 : carReservationJson.hashCode());
      result = prime * result
         + (int) (flightReservationId ^ (flightReservationId >>> 32));
      result = prime * result + ((flightReservationJson == null) ? 0
         : flightReservationJson.hashCode());
      result = prime * result
         + (int) (hotelReservationId ^ (hotelReservationId >>> 32));
      result = prime * result + ((hotelReservationJson == null) ? 0
         : hotelReservationJson.hashCode());
      result = prime * result + (int) (reservationId ^ (reservationId >>> 32));
      result = prime * result + (int) (userId ^ (userId >>> 32));
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
      Reservation other = (Reservation) obj;
      if (carReservationId != other.carReservationId)
         return false;
      if (carReservationJson == null) {
         if (other.carReservationJson != null)
            return false;
      } else if (!carReservationJson.equals(other.carReservationJson))
         return false;
      if (flightReservationId != other.flightReservationId)
         return false;
      if (flightReservationJson == null) {
         if (other.flightReservationJson != null)
            return false;
      } else if (!flightReservationJson.equals(other.flightReservationJson))
         return false;
      if (hotelReservationId != other.hotelReservationId)
         return false;
      if (hotelReservationJson == null) {
         if (other.hotelReservationJson != null)
            return false;
      } else if (!hotelReservationJson.equals(other.hotelReservationJson))
         return false;
      if (reservationId != other.reservationId)
         return false;
      if (userId != other.userId)
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "Reservation [reservationId=" + reservationId + ", userId="
         + userId + ", carReservationId=" + carReservationId
         + ", hotelReservationId=" + hotelReservationId
         + ", flightReservationId=" + flightReservationId
         + ", carReservationJson=" + carReservationJson
         + ", hotelReservationJson=" + hotelReservationJson
         + ", flightReservationJson=" + flightReservationJson + "]";
   }
}
