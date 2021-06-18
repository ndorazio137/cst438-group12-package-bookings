package cst438.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long userId;
   
   @NotNull
   @Column(unique = true)
   @Size(min=1, max=254)
   private String username;
   
   @NotNull
   @Size(min=1, max=254)
   private String password;
   
   @Size(min=1, max=254)
   private String firstName;
   
   @Size(min=1, max=254)
   private String lastName;
   
   public User() {}
   
   public User(@NotNull @Size(min = 1, max = 254) String username, @NotNull @Size(min = 1, max = 254) String password,
         @NotNull @Size(min = 1, max = 254) String firstName, @NotNull @Size(min = 1, max = 254) String lastName) {
      super();
      this.username = username;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
   }
   
   
   
   public long getUserId() {
      return userId;
   }

   public String getUsername() {
      return username;
   }
   
   public void setUsername(String username) {
      this.username = username;
   }
   
   public String getPassword() {
      return password;
   }
   
   public void setPassword(String password) {
      this.password = password;
   }
   
   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
         + ((firstName == null) ? 0 : firstName.hashCode());
      result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
      result = prime * result + ((password == null) ? 0 : password.hashCode());
      result = prime * result + (int) (userId ^ (userId >>> 32));
      result = prime * result + ((username == null) ? 0 : username.hashCode());
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
      User other = (User) obj;
      if (firstName == null) {
         if (other.firstName != null)
            return false;
      } else if (!firstName.equals(other.firstName))
         return false;
      if (lastName == null) {
         if (other.lastName != null)
            return false;
      } else if (!lastName.equals(other.lastName))
         return false;
      if (password == null) {
         if (other.password != null)
            return false;
      } else if (!password.equals(other.password))
         return false;
      if (userId != other.userId)
         return false;
      if (username == null) {
         if (other.username != null)
            return false;
      } else if (!username.equals(other.username))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName=" + firstName
            + ", lastName=" + lastName + "]";
   }
}
