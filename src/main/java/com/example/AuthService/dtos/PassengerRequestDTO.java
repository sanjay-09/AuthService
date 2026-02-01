package com.example.AuthService.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerRequestDTO {

    private String name;

    private String email;

    private String password;
}
