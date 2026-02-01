package com.example.AuthService.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends  BaseModel  {

    @Enumerated(value= EnumType.STRING)
    private BookingStatus bookingStatus;

    private Date StartTime;

    private Date endTime;

    private Long Distance;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Passenger passenger;



}