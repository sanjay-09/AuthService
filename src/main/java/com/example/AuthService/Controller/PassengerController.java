package com.example.AuthService.Controller;


import com.example.AuthService.Service.JWTService;
import com.example.AuthService.Service.PassengerService;
import com.example.AuthService.Utils.InternalServerError;
import com.example.AuthService.Utils.PassengerNotFound;
import com.example.AuthService.Utils.PassengerPasswordIncorrect;
import com.example.AuthService.dtos.PassengerRequestDTO;
import com.example.AuthService.dtos.PassengerResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/passenger")
public class PassengerController {
    PassengerService passengerService;

    AuthenticationManager authenticationManager;

    JWTService jwtService;
    PassengerController(PassengerService passengerService,AuthenticationManager authenticationManager,JWTService jwtService){
        this.passengerService=passengerService;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody PassengerRequestDTO passengerRequestDTO){

        PassengerResponseDTO passengerResponseDTO=this.passengerService.createPassenger(passengerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerResponseDTO);

    }

    @GetMapping("/signindep")
    public ResponseEntity<?> manualSignIn(@RequestBody PassengerRequestDTO passengerRequestDTO){
        try{

            String token=this.passengerService.getAccessToken(passengerRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(token);

        }
        catch (PassengerNotFound e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PassengerNotFound());

        }
        catch (PassengerPasswordIncorrect e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PassengerPasswordIncorrect());

        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new InternalServerError());

        }

    }

    @GetMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody PassengerRequestDTO passengerRequestDTO){
        Authentication authentication=this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(passengerRequestDTO.getEmail(),passengerRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            Map<String,Object> map=new HashMap<>();
            map.put("email",passengerRequestDTO.getEmail());
            String token=this.jwtService.createToken(map,passengerRequestDTO.getEmail());


            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not req");
        }

    }



}
