package cst438.domain;

public class Package {
   CarInfo car;
   HotelInfo hotel;
   FlightInfo flight;
   User user;
   
   public Package() {}
   
   public Package(CarInfo car, HotelInfo hotel, FlightInfo flight) {
      super();
      this.car = car;
      this.hotel = hotel;
      this.flight = flight;
   }

   public CarInfo getCar() {
      return car;
   }

   public void setCar(CarInfo car) {
      this.car = car;
   }

   public HotelInfo getHotel() {
      return hotel;
   }

   public void setHotel(HotelInfo hotel) {
      this.hotel = hotel;
   }

   public FlightInfo getFlight() {
      return flight;
   }

   public void setFlight(FlightInfo flight) {
      this.flight = flight;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

//   public TripInfo getTripInfo() {
//      return tripInfo;
//   }
//
//   public void setTripInfo(TripInfo tripInfo) {
//      this.tripInfo = tripInfo;
//   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((car == null) ? 0 : car.hashCode());
      result = prime * result + ((flight == null) ? 0 : flight.hashCode());
      result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
//      result = prime * result + ((tripInfo == null) ? 0 : tripInfo.hashCode());
      result = prime * result + ((user == null) ? 0 : user.hashCode());
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
      Package other = (Package) obj;
      if (car == null) {
         if (other.car != null)
            return false;
      } else if (!car.equals(other.car))
         return false;
      if (flight == null) {
         if (other.flight != null)
            return false;
      } else if (!flight.equals(other.flight))
         return false;
      if (hotel == null) {
         if (other.hotel != null)
            return false;
      } else if (!hotel.equals(other.hotel))
         return false;
//      if (tripInfo == null) {
//         if (other.tripInfo != null)
//            return false;
//      } else if (!tripInfo.equals(other.tripInfo))
//         return false;
      if (user == null) {
         if (other.user != null)
            return false;
      } else if (!user.equals(other.user))
         return false;
      return true;
   }

   @Override
   public String toString() {
//      return "Package [car=" + car + ", hotel=" + hotel + ", flight=" + flight + ", user=" + user + ", tripInfo="
//            + tripInfo + "]";
      return "Package [car=" + car + ", hotel=" + hotel + ", flight=" + flight + ", user=" + user + "]";
   }
   
}
