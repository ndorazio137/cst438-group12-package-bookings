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
   private String jsonCarReservation;
   private String jsonHotelReservation;
   private String jsonFlightReservation;
   
   public Reservation() { }
   
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
      this.jsonCarReservation = carReservationJson;
      this.jsonHotelReservation = hotelReservationJson;
      this.jsonFlightReservation = flightReservationJson;
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

   public String getJsonCarReservation() {
      return jsonCarReservation;
   }

   public void setJsonCarReservation(String jsonCarReservation) {
      this.jsonCarReservation = jsonCarReservation;
   }
   
   public String getJsonHotelReservation() {
      return jsonHotelReservation;
   }

   public void setJsonHotelReservation(String jsonHotelReservation) {
      this.jsonHotelReservation = jsonHotelReservation;
   }
   
   public String getJsonFlightReservation() {
      return jsonFlightReservation;
   }

   public void setJsonFlightReservation(String jsonFlightReservation) {
      this.jsonFlightReservation = jsonFlightReservation;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
         + (int) (carReservationId ^ (carReservationId >>> 32));
      result = prime * result
         + ((jsonCarReservation == null) ? 0 : jsonCarReservation.hashCode());
      result = prime * result
         + (int) (flightReservationId ^ (flightReservationId >>> 32));
      result = prime * result + ((jsonFlightReservation == null) ? 0
         : jsonFlightReservation.hashCode());
      result = prime * result
         + (int) (hotelReservationId ^ (hotelReservationId >>> 32));
      result = prime * result + ((jsonHotelReservation == null) ? 0
         : jsonHotelReservation.hashCode());
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
      if (jsonCarReservation == null) {
         if (other.jsonCarReservation != null)
            return false;
      } else if (!jsonCarReservation.equals(other.jsonCarReservation))
         return false;
      if (flightReservationId != other.flightReservationId)
         return false;
      if (jsonFlightReservation == null) {
         if (other.jsonFlightReservation != null)
            return false;
      } else if (!jsonFlightReservation.equals(other.jsonFlightReservation))
         return false;
      if (hotelReservationId != other.hotelReservationId)
         return false;
      if (jsonHotelReservation == null) {
         if (other.jsonHotelReservation != null)
            return false;
      } else if (!jsonHotelReservation.equals(other.jsonHotelReservation))
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
         + ", jsonCarReservation=" + jsonCarReservation
         + ", jsonHotelReservation=" + jsonHotelReservation
         + ", jsonFlightReservation=" + jsonFlightReservation + "]";
   }
}

