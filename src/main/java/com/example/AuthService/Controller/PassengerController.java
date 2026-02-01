package com.example.AuthService.Controller;


import com.example.AuthService.Service.PassengerService;
import com.example.AuthService.dtos.PassengerRequestDTO;
import com.example.AuthService.dtos.PassengerResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class PassengerController {
    PassengerService passengerService;
    PassengerController(PassengerService passengerService){
        this.passengerService=passengerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody PassengerRequestDTO passengerRequestDTO){

        PassengerResponseDTO passengerResponseDTO=this.passengerService.createPassenger(passengerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerResponseDTO);



    }



}
