package com.example.AuthService.Utils;

public class PassengerPasswordIncorrect  extends  Exception{

    public PassengerPasswordIncorrect(){
        super("password is incorrect,please enter the correct password");
    }

}
