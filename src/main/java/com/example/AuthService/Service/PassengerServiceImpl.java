package com.example.AuthService.Service;

import com.example.AuthService.Model.Passenger;
import com.example.AuthService.Respository.PassengerRepository;
import com.example.AuthService.dtos.PassengerRequestDTO;
import com.example.AuthService.dtos.PassengerResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


@Repository
public class PassengerServiceImpl implements PassengerService{

    PassengerRepository passengerRepository;
    PasswordEncoder passwordEncoder;

    PassengerServiceImpl(PassengerRepository passengerRepository,PasswordEncoder passwordEncoder){
        this.passengerRepository=passengerRepository;
        this.passwordEncoder=passwordEncoder;
    }


    @Override
    public PassengerResponseDTO createPassenger(PassengerRequestDTO passengerRequestDTO) {

        Passenger p = Passenger.builder().email(passengerRequestDTO.getEmail())
                .name(passengerRequestDTO.getName()).password(passwordEncoder.encode(passengerRequestDTO.getPassword())).build();

       Passenger actualPassenger=this.passengerRepository.save(p);

       return  PassengerResponseDTO.from(actualPassenger);



    }
}
