package com.example.AuthService;

import com.example.UberEntites.Models.Passenger;
import com.example.AuthService.Respository.PassengerRepository;
import com.example.AuthService.Service.PassengerService;
import com.example.AuthService.Service.PassengerServiceImpl;
import com.example.AuthService.dtos.PassengerRequestDTO;
import com.example.AuthService.dtos.PassengerResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PassengerServiceTest {


    @InjectMocks
    PassengerServiceImpl passengerService;

    @Mock
    PassengerRepository passengerRepository;


    @Mock
    PasswordEncoder passwordEncoder;



    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreatePassenger_Success(){

        PassengerRequestDTO passengerRequestDTO=new PassengerRequestDTO();
        passengerRequestDTO.setEmail("sanjay.s01558@gmail.com");
        passengerRequestDTO.setPassword("Sanjay@098");
        passengerRequestDTO.setName("Sanjay");



        Passenger p=Passenger.builder().name("Sanjay").email("sanjay.s01558@gmail.com").password("encoded-password").build();


       when(passengerRepository.save(ArgumentMatchers.any(Passenger.class))).thenReturn(p);
        when(passwordEncoder.encode(anyString()))
                .thenReturn("encoded-password");

       PassengerResponseDTO passengerResponseDTO=this.passengerService.createPassenger(passengerRequestDTO);

       System.out.println(passengerResponseDTO.getPassword());


       assertEquals("Sanjay",passengerResponseDTO.getName());







    }




}
