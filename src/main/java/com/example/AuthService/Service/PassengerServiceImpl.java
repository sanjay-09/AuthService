package com.example.AuthService.Service;

import com.example.AuthService.Model.Passenger;
import com.example.AuthService.Respository.PassengerRepository;
import com.example.AuthService.Utils.PassengerNotFound;
import com.example.AuthService.Utils.PassengerPasswordIncorrect;
import com.example.AuthService.dtos.PassengerRequestDTO;
import com.example.AuthService.dtos.PassengerResponseDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Repository
public class PassengerServiceImpl implements PassengerService{

    PassengerRepository passengerRepository;
    PasswordEncoder passwordEncoder;
    JWTService jwtService;

    PassengerServiceImpl(PassengerRepository passengerRepository,PasswordEncoder passwordEncoder,JWTService jwtService){
        this.passengerRepository=passengerRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }


    @Override
    public PassengerResponseDTO createPassenger(PassengerRequestDTO passengerRequestDTO) {

        Passenger p = Passenger.builder().email(passengerRequestDTO.getEmail())
                .name(passengerRequestDTO.getName()).password(passwordEncoder.encode(passengerRequestDTO.getPassword())).build();

       Passenger actualPassenger=this.passengerRepository.save(p);

       return  PassengerResponseDTO.from(actualPassenger);


    }

    public String getAccessToken(PassengerRequestDTO passengerRequestDTO) throws Exception{

        Optional<Passenger> passenger=this.passengerRepository.findByEmail(passengerRequestDTO.getEmail());
        if(passenger.isEmpty()){
            throw new PassengerNotFound();
        }
        Passenger passengerFromDb=passenger.get();
        if(!passwordEncoder.matches(passengerRequestDTO.getPassword(),passengerFromDb.getPassword())){
            throw new PassengerPasswordIncorrect();

        }
        Map<String,Object> map=new HashMap<>();
        map.put("email",passengerFromDb.getEmail());
        map.put("id",passengerFromDb.getId());
        return this.jwtService.createToken(map,passengerFromDb.getEmail());

    }
}
