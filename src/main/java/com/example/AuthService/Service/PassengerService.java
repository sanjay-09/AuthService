package com.example.AuthService.Service;

import com.example.AuthService.dtos.PassengerRequestDTO;
import com.example.AuthService.dtos.PassengerResponseDTO;

public interface PassengerService {

    PassengerResponseDTO createPassenger(PassengerRequestDTO passengerRequestDTO);

}
