package com.example.AuthService.Respository;


import com.example.AuthService.Model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {
}
