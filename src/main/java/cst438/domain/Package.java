package cst438.domain;

public class Package {
   Object car;
   Object hotel;
   Object flight;
   
   Package() {}

   public Package(Object car, Object hotel, Object flight) {
      super();
      this.car = car;
      this.hotel = hotel;
      this.flight = flight;
   }

   public Object getCar() {
      return car;
   }

   public void setCar(Object car) {
      this.car = car;
   }

   public Object getHotel() {
      return hotel;
   }

   public void setHotel(Object hotel) {
      this.hotel = hotel;
   }

   public Object getFlight() {
      return flight;
   }

   public void setFlight(Object flight) {
      this.flight = flight;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((car == null) ? 0 : car.hashCode());
      result = prime * result + ((flight == null) ? 0 : flight.hashCode());
      result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
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
      return true;
   }

   @Override
   public String toString() {
      return "Package [car=" + car + ", hotel=" + hotel + ", flight=" + flight + "]";
   }
   
   
}
