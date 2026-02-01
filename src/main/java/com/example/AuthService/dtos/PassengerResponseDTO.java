package com.example.AuthService.dtos;

import com.example.AuthService.Model.Passenger;
import lombok.*;

import java.util.Date;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerResponseDTO {

    private Long id;

   private String name;

    private String email;

    private String password;

    private Date createdAt;

    private Date updatedAt;

    public static PassengerResponseDTO from(Passenger p){
        PassengerResponseDTO passengerResponseDTO=PassengerResponseDTO.builder().
                id(p.getId()).name(p.getName()).email(p.getEmail()).createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
                .password(p.getPassword()).build();
        return passengerResponseDTO;


    }
}
