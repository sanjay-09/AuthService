package com.example.AuthService;

import com.example.AuthService.Controller.PassengerController;
import com.example.AuthService.Service.JWTService;
import com.example.AuthService.Service.PassengerService;
import com.example.AuthService.dtos.PassengerRequestDTO;
import com.example.AuthService.dtos.PassengerResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PassengerControllerTest {

    @InjectMocks
    PassengerController passengerController;

    @Mock
    PassengerService passengerService;


    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JWTService jwtService;

    @Mock
    Authentication authentication;


    @Mock
    HttpServletResponse httpServletResponse;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void TestRegisterUser_Success(){

        PassengerRequestDTO passengerRequestDTO=new PassengerRequestDTO();
        passengerRequestDTO.setEmail("sanjay.s01558@gmail.com");
        passengerRequestDTO.setName("sanjay");
        passengerRequestDTO.setPassword("Sanjay@098");


        PassengerResponseDTO passengerResponseDTO=new PassengerResponseDTO();
        passengerResponseDTO.setEmail("sanjay.s01558@gmail.com");
        passengerResponseDTO.setPassword("Sanjay@098");

        when(passengerService.createPassenger(passengerRequestDTO)).thenReturn(passengerResponseDTO);

        ResponseEntity<?> resp=passengerController.registerUser(passengerRequestDTO);
        //Test
        assertEquals(HttpStatus.CREATED,resp.getStatusCode());



    }




    @Test
    public void TestSignIn_Success(){

        String token="abcdefghijklmnopqrtuvwxyz";
        PassengerRequestDTO passengerRequestDTO=new PassengerRequestDTO();
        passengerRequestDTO.setEmail("sanjay.s01558@gmail.com");
        passengerRequestDTO.setPassword("Sanjay@098");
        //Mocking

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);

        when(jwtService.createToken(anyMap(),eq("sanjay.s01558@gmail.com"))).thenReturn(token);

        ResponseEntity<?> resp=passengerController.signIn(passengerRequestDTO,httpServletResponse);

        ArgumentCaptor<String> argumentCaptor=ArgumentCaptor.forClass(String.class);

        verify(httpServletResponse).setHeader(eq(HttpHeaders.SET_COOKIE),argumentCaptor.capture());

        String val=argumentCaptor.getValue();


        assertTrue(val.contains("JwtToken"));

        //test
        assertEquals(HttpStatus.OK,resp.getStatusCode());















    }


}
