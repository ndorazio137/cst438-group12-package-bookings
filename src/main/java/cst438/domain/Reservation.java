package cst438.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="reservations")
public class Reservation {
   
   private int reservationId;
   @Id
   private int userId;
   private String carReservationId;
   private String hotelReservationId;
   private String flightReservationId;
   private String jsonCarReservation;
   private String jsonHotelReservation;
   private String jsonFlightReservation;

   public Reservation() { }
   
   public Reservation(int userId, String carReservationId,
         String hotelReservationId, String flightReservationId) {
      this.userId = userId;
      this.carReservationId = carReservationId;
      this.hotelReservationId = hotelReservationId;
      this.flightReservationId = flightReservationId;
   }
   
   public Reservation(int userId, String carReservationId,
      String hotelReservationId, String flightReservationId,
      String carReservationJson, String hotelReservationJson,
      String flightReservationJson) {
      this.userId = userId;
      this.carReservationId = carReservationId;
      this.hotelReservationId = hotelReservationId;
      this.flightReservationId = flightReservationId;
      this.jsonCarReservation = carReservationJson;
      this.jsonHotelReservation = hotelReservationJson;
      this.jsonFlightReservation = flightReservationJson;
   }

   public int getReservationId() {
      return reservationId;
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
      return jsonCarReservation;
   }
   
   public void setCarReservationJson(String carReservationJson) {
      this.jsonCarReservation = carReservationJson;
   }
   
   public String getHotelReservationJson() {
      return jsonHotelReservation;
   }
   
   public void setHotelReservationJson(String hotelReservationJson) {
      this.jsonHotelReservation = hotelReservationJson;
   }
   
   public String getFlightReservationJson() {
      return jsonFlightReservation;
   }
   
   public void setFlightReservationJson(String flightReservationJson) {
      this.jsonFlightReservation = flightReservationJson;
   }

   @Override
   public int hashCode() {
      return Objects.hash(carReservationId, jsonCarReservation,
         flightReservationId, jsonFlightReservation, hotelReservationId,
         jsonHotelReservation, reservationId, userId);
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
         && Objects.equals(jsonCarReservation, other.jsonCarReservation)
         && Objects.equals(flightReservationId, other.flightReservationId)
         && Objects.equals(jsonFlightReservation, other.jsonFlightReservation)
         && Objects.equals(hotelReservationId, other.hotelReservationId)
         && Objects.equals(jsonHotelReservation, other.jsonHotelReservation)
         && Objects.equals(reservationId, other.reservationId)
         && userId == other.userId;
   }
}
