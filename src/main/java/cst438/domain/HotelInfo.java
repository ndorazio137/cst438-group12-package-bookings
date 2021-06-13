package cst438.domain;

//Helper class.
public class HotelInfo {
   private int id;
   private String name;
   private String address;
   private int stars;
   private String city;
   private String state;
   private int pricePerDay;
   private String availableDate;
   private int roomsAvailable;
   
   public HotelInfo() { }
   
   public HotelInfo(int id, String name, String address, int stars, 
         String city, String state, int pricePerDay,
         String availableDate, int roomsAvailable) {
      this.id = id;
      this.name = name;
      this.address = address;
      this.stars = stars;
      this.city = city;
      this.state = state;
      this.pricePerDay = pricePerDay;
      this.availableDate = availableDate;
      this.roomsAvailable = roomsAvailable;
   }

   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getAddress() {
      return address;
   }
   public void setAddress(String address) {
      this.address = address;
   }
   public int getStars() {
      return stars;
   }
   public void setStars(int stars) {
      this.stars = stars;
   }
   public String getCity() {
      return city;
   }
   public void setCity(String city) {
      this.city = city;
   }
   public String getState() {
      return state;
   }
   public void setState(String state) {
      this.state = state;
   }
   public int getPricePerDay() {
      return pricePerDay;
   }
   public void setPricePerDay(int pricePerDay) {
      this.pricePerDay = pricePerDay;
   }
   public String getAvailableDate() {
      return availableDate;
   }
   public void setAvailableDate(String availableDate) {
      this.availableDate = availableDate;
   }
   public int getRoomsAvailable() {
      return roomsAvailable;
   }
   public void setRoomsAvailable(int roomsAvailable) {
      this.roomsAvailable = roomsAvailable;
   }
   
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((address == null) ? 0 : address.hashCode());
      result = prime * result + ((availableDate == null) ? 0 : availableDate.hashCode());
      result = prime * result + ((city == null) ? 0 : city.hashCode());
      result = prime * result + id;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + pricePerDay;
      result = prime * result + roomsAvailable;
      result = prime * result + stars;
      result = prime * result + ((state == null) ? 0 : state.hashCode());
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
      HotelInfo other = (HotelInfo) obj;
      if (address == null) {
         if (other.address != null)
            return false;
      } else if (!address.equals(other.address))
         return false;
      if (availableDate == null) {
         if (other.availableDate != null)
            return false;
      } else if (!availableDate.equals(other.availableDate))
         return false;
      if (city == null) {
         if (other.city != null)
            return false;
      } else if (!city.equals(other.city))
         return false;
      if (id != other.id)
         return false;
      if (name == null) {
         if (other.name != null)
            return false;
      } else if (!name.equals(other.name))
         return false;
      if (pricePerDay != other.pricePerDay)
         return false;
      if (roomsAvailable != other.roomsAvailable)
         return false;
      if (stars != other.stars)
         return false;
      if (state == null) {
         if (other.state != null)
            return false;
      } else if (!state.equals(other.state))
         return false;
      return true;
   }
   
   @Override
   public String toString() {
      return "HotelInfo [id=" + id + ", name=" + name + ", address=" + address + ", stars=" + stars + ", city=" + city
            + ", state=" + state + ", pricePerDay=" + pricePerDay + ", availableDate=" + availableDate
            + ", roomsAvailable=" + roomsAvailable + "]";
   }
}
