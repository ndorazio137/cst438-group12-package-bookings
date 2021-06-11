package cst438.domain;

//Helper class.
public class FlightInfo {
   long id;
   
   public FlightInfo(long id) {
      this.id = id;
   }
   
   
   public long getId() {
      return id;
   }
   
   public void setId(int id) {
      this.id = id;
   }

   @Override
   public int hashCode() {
      final long prime = 31;
      long result = 1;
      result = prime * result + id;
      
      return (int)result;
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
      if (id != other.id)
         return false;
      return true;
   }
}
