package com.example.AuthService.Service;

import com.example.AuthService.Helper.AuthPassengerDetail;
import com.example.AuthService.Model.Passenger;
import com.example.AuthService.Respository.PassengerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    PassengerRepository passengerRepository;

    UserDetailsServiceImpl(PassengerRepository passengerRepository){
        this.passengerRepository=passengerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger=this.passengerRepository.findByEmail(email);
        if(passenger.isEmpty()){
            throw new UsernameNotFoundException("user is not found");
        }
        else{

            return new AuthPassengerDetail(passenger.get().getName(),passenger.get().getPassword());
        }

    }
}
