package cst438.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="reservations")
public class Reservation {
   
   private String reservationId;
   @Id
   private int userId;
   private String carReservationId;
   private String hotelReservationId;
   private String flightReservationId;
   private String  carReservationJson;
   private String hotelReservationJson;
   private String flightReservationJson;

   public Reservation(String reservationId, int userId, String carReservationId,
      String hotelReservationId, String flightReservationId,
      String carReservationJson, String hotelReservationJson,
      String flightReservationJson) {
      this.reservationId = reservationId;
      this.userId = userId;
      this.carReservationId = carReservationId;
      this.hotelReservationId = hotelReservationId;
      this.flightReservationId = flightReservationId;
      this.carReservationJson = carReservationJson;
      this.hotelReservationJson = hotelReservationJson;
      this.flightReservationJson = flightReservationJson;
   }

   public String getReservationId() {
      return reservationId;
   }
   
   public void setReservationId(String reservationId) {
      this.reservationId = reservationId;
   }
   
   public int getUserId() {
      return userId;
   }
   
   public void setUserId(int userId) {
      this.userId = userId;
   }
   
   public String getCarReservationId() {
      return carReservationId;
   }
   
   public void setCarReservationId(String carReservationId) {
      this.carReservationId = carReservationId;
   }
   
   public String getHotelReservationId() {
      return hotelReservationId;
   }
   
   public void setHotelReservationId(String hotelReservationId) {
      this.hotelReservationId = hotelReservationId;
   }
   
   public String getFlightReservationId() {
      return flightReservationId;
   }
   
   public void setFlightReservationId(String flightReservationId) {
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
      return Objects.hash(carReservationId, carReservationJson,
         flightReservationId, flightReservationJson, hotelReservationId,
         hotelReservationJson, reservationId, userId);
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
      return Objects.equals(carReservationId, other.carReservationId)
         && Objects.equals(carReservationJson, other.carReservationJson)
         && Objects.equals(flightReservationId, other.flightReservationId)
         && Objects.equals(flightReservationJson, other.flightReservationJson)
         && Objects.equals(hotelReservationId, other.hotelReservationId)
         && Objects.equals(hotelReservationJson, other.hotelReservationJson)
         && Objects.equals(reservationId, other.reservationId)
         && userId == other.userId;
   }
}
