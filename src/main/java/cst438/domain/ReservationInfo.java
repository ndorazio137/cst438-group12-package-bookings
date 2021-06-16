package cst438.domain;

public class ReservationInfo {
   private Package pckage;
   private User user;
   private TripInfo tripInfo;
   
   public ReservationInfo(Package pckage, User user, TripInfo tripInfo) {
      super();
      this.pckage = pckage;
      this.user = user;
      this.tripInfo = tripInfo;
   }

   @Override
   public String toString() {
      return "ReservationInfo [pckage=" + pckage + ", user=" + user + ", tripInfo=" + tripInfo + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((pckage == null) ? 0 : pckage.hashCode());
      result = prime * result + ((tripInfo == null) ? 0 : tripInfo.hashCode());
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
      ReservationInfo other = (ReservationInfo) obj;
      if (pckage == null) {
         if (other.pckage != null)
            return false;
      } else if (!pckage.equals(other.pckage))
         return false;
      if (tripInfo == null) {
         if (other.tripInfo != null)
            return false;
      } else if (!tripInfo.equals(other.tripInfo))
         return false;
      if (user == null) {
         if (other.user != null)
            return false;
      } else if (!user.equals(other.user))
         return false;
      return true;
   }

   public Package getPckage() {
      return pckage;
   }

   public void setPckage(Package pckage) {
      this.pckage = pckage;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public TripInfo getTripInfo() {
      return tripInfo;
   }

   public void setTripInfo(TripInfo tripInfo) {
      this.tripInfo = tripInfo;
   }
   
}
