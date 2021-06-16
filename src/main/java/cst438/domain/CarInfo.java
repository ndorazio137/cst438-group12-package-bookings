package cst438.domain;

//Helper class.
public class CarInfo {
   int id;
   String model;
   String make;
   int year;
   String trany;
   double cityMpg;
   double highwayMpg;
   String carClass;
   String fuelType;
   double rentalPrice;
   String state;
   String city;
   int quantity;
   String image;
   double countyTax;
   double govFee;
   double salesTax;
   double total;
   String imageUrl;
   
   
   
   public CarInfo(int id, String model, String make, int year, String trany, double rentalPrice, String state,
         String city) {
      super();
      this.id = id;
      this.model = model;
      this.make = make;
      this.year = year;
      this.trany = trany;
      this.rentalPrice = rentalPrice;
      this.state = state;
      this.city = city;
   }

   @Override
   public String toString() {
      return "CarInfo [id=" + id + ", model=" + model + ", make=" + make + ", year=" + year + ", trany=" + trany
            + ", cityMpg=" + cityMpg + ", highwayMpg=" + highwayMpg + ", carClass=" + carClass + ", fuelType="
            + fuelType + ", rentalPrice=" + rentalPrice + ", state=" + state + ", city=" + city + ", quantity="
            + quantity + ", image=" + image + ", countyTax=" + countyTax + ", govFee=" + govFee + ", salesTax="
            + salesTax + ", total=" + total + ", imageUrl=" + imageUrl + "]";
   }
   
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((carClass == null) ? 0 : carClass.hashCode());
      result = prime * result + ((city == null) ? 0 : city.hashCode());
      long temp;
      temp = Double.doubleToLongBits(cityMpg);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(countyTax);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      result = prime * result + ((fuelType == null) ? 0 : fuelType.hashCode());
      temp = Double.doubleToLongBits(govFee);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(highwayMpg);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      result = prime * result + id;
      result = prime * result + ((image == null) ? 0 : image.hashCode());
      result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
      result = prime * result + ((make == null) ? 0 : make.hashCode());
      result = prime * result + ((model == null) ? 0 : model.hashCode());
      result = prime * result + quantity;
      temp = Double.doubleToLongBits(rentalPrice);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(salesTax);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      result = prime * result + ((state == null) ? 0 : state.hashCode());
      temp = Double.doubleToLongBits(total);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      result = prime * result + ((trany == null) ? 0 : trany.hashCode());
      result = prime * result + year;
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
      CarInfo other = (CarInfo) obj;
      if (carClass == null) {
         if (other.carClass != null)
            return false;
      } else if (!carClass.equals(other.carClass))
         return false;
      if (city == null) {
         if (other.city != null)
            return false;
      } else if (!city.equals(other.city))
         return false;
      if (Double.doubleToLongBits(cityMpg) != Double.doubleToLongBits(other.cityMpg))
         return false;
      if (Double.doubleToLongBits(countyTax) != Double.doubleToLongBits(other.countyTax))
         return false;
      if (fuelType == null) {
         if (other.fuelType != null)
            return false;
      } else if (!fuelType.equals(other.fuelType))
         return false;
      if (Double.doubleToLongBits(govFee) != Double.doubleToLongBits(other.govFee))
         return false;
      if (Double.doubleToLongBits(highwayMpg) != Double.doubleToLongBits(other.highwayMpg))
         return false;
      if (id != other.id)
         return false;
      if (image == null) {
         if (other.image != null)
            return false;
      } else if (!image.equals(other.image))
         return false;
      if (imageUrl == null) {
         if (other.imageUrl != null)
            return false;
      } else if (!imageUrl.equals(other.imageUrl))
         return false;
      if (make == null) {
         if (other.make != null)
            return false;
      } else if (!make.equals(other.make))
         return false;
      if (model == null) {
         if (other.model != null)
            return false;
      } else if (!model.equals(other.model))
         return false;
      if (quantity != other.quantity)
         return false;
      if (Double.doubleToLongBits(rentalPrice) != Double.doubleToLongBits(other.rentalPrice))
         return false;
      if (Double.doubleToLongBits(salesTax) != Double.doubleToLongBits(other.salesTax))
         return false;
      if (state == null) {
         if (other.state != null)
            return false;
      } else if (!state.equals(other.state))
         return false;
      if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
         return false;
      if (trany == null) {
         if (other.trany != null)
            return false;
      } else if (!trany.equals(other.trany))
         return false;
      if (year != other.year)
         return false;
      return true;
   }
   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public String getModel() {
      return model;
   }
   public void setModel(String model) {
      this.model = model;
   }
   public String getMake() {
      return make;
   }
   public void setMake(String make) {
      this.make = make;
   }
   public int getYear() {
      return year;
   }
   public void setYear(int year) {
      this.year = year;
   }
   public String getTrany() {
      return trany;
   }
   public void setTrany(String trany) {
      this.trany = trany;
   }
   public double getCityMpg() {
      return cityMpg;
   }
   public void setCityMpg(double cityMpg) {
      this.cityMpg = cityMpg;
   }
   public double getHighwayMpg() {
      return highwayMpg;
   }
   public void setHighwayMpg(double highwayMpg) {
      this.highwayMpg = highwayMpg;
   }
   public String getCarClass() {
      return carClass;
   }
   public void setCarClass(String carClass) {
      this.carClass = carClass;
   }
   public String getFuelType() {
      return fuelType;
   }
   public void setFuelType(String fuelType) {
      this.fuelType = fuelType;
   }
   public double getRentalPrice() {
      return rentalPrice;
   }
   public void setRentalPrice(double rentalPrice) {
      this.rentalPrice = rentalPrice;
   }
   public String getState() {
      return state;
   }
   public void setState(String state) {
      this.state = state;
   }
   public String getCity() {
      return city;
   }
   public void setCity(String city) {
      this.city = city;
   }
   public int getQuantity() {
      return quantity;
   }
   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }
   public String getImage() {
      return image;
   }
   public void setImage(String image) {
      this.image = image;
   }
   public double getCountyTax() {
      return countyTax;
   }
   public void setCountyTax(double countyTax) {
      this.countyTax = countyTax;
   }
   public double getGovFee() {
      return govFee;
   }
   public void setGovFee(double govFee) {
      this.govFee = govFee;
   }
   public double getSalesTax() {
      return salesTax;
   }
   public void setSalesTax(double salesTax) {
      this.salesTax = salesTax;
   }
   public double getTotal() {
      return total;
   }
   public void setTotal(double total) {
      this.total = total;
   }
   public String getImageUrl() {
      return imageUrl;
   }
   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }
   
}
