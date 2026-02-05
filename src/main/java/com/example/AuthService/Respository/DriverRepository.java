package com.example.AuthService.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.UberEntites.Models.Driver;

public interface DriverRepository extends JpaRepository<Driver,Long> {
}
