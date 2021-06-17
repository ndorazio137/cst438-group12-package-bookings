package cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Reservation, Long> {
   @Query("select u from User u order by username")
   List<User> findByUsername(String username);
}