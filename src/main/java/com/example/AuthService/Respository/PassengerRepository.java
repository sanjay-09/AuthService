package com.example.AuthService.Respository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.UberEntites.Models.Passenger;

import java.util.Optional;


@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {

    Optional<Passenger> findByEmail(String email);
}
