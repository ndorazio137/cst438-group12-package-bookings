package cst438.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.json.JSONObject;

@Entity
@Table(name="reservations")
public class Reservation {
   @Id
   String reservationId;
   int userId;
   String carReservationId;
   String hotelReservationId;
   String flightReservationId;
   JSONObject carReservationJson;
   JSONObject hotelReservationJson;
   JSONObject flightReservationJson;

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
   
   public JSONObject getCarReservationJson() {
      return carReservationJson;
   }
   
   public void setCarReservationJson(JSONObject carReservationJson) {
      this.carReservationJson = carReservationJson;
   }
   
   public JSONObject getHotelReservationJson() {
      return hotelReservationJson;
   }
   
   public void setHotelReservationJson(JSONObject hotelReservationJson) {
      this.hotelReservationJson = hotelReservationJson;
   }
   
   public JSONObject getFlightReservationJson() {
      return flightReservationJson;
   }
   
   public void setFlightReservationJson(JSONObject flightReservationJson) {
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
