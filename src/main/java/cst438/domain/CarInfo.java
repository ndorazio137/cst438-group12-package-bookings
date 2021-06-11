package cst438.domain;

//Helper class.
public class CarInfo {
   int id;
   String carModel;
   
   public CarInfo(int id, String carModel) {
      this.carModel = carModel;
      this.id = id;
   }
   
   public String getCarModel() {
      return carModel;
   }
   
   public void setCarModel(String carModel) {
      this.carModel = carModel;
   }
   
   public int getId() {
      return id;
   }
   
   public void setId(int id) {
      this.id = id;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((carModel == null) ? 0 : carModel.hashCode());
      result = prime * result + id;
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
      if (carModel == null) {
         if (other.carModel != null)
            return false;
      } else if (!carModel.equals(other.carModel))
         return false;
      if (id != other.id)
         return false;
      return true;
   }
}
