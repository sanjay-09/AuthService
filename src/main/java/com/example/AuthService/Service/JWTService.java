package com.example.AuthService.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService implements CommandLineRunner {

    @Value("${jwt_expiry}")
    private int expiry;
    @Value("${jwt_secret_key}")
    private String SECRET;


    /// encode the secret key
    public Key getKey(){
      return  Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    }

    //generated the token
    public String createToken(Map<String,Object> payload,String email){
        Date d=new Date();
        Date expiryDate=new Date(d.getTime()+expiry*1000L);
        Key k= this.getKey();
        return Jwts.builder().claims(payload).issuedAt(new Date()).expiration(expiryDate).signWith(k).subject(email).
                compact();
    }


    //verifying the token
    private Claims getAllClaims(String token){
         return Jwts.parser()
                .verifyWith((SecretKey) this.getKey()) // Replaces .setSigningKey(this.getKey())
                .build()
                .parseSignedClaims(token) // Replaces .parseClaimsJws(token)
                .getPayload(); // Replaces .getBody()
    }

    private <T> T extractClaimInfo(String token,Function<Claims,T> claimsResolver){
        Claims c=this.getAllClaims(token);
        return claimsResolver.apply(c);

    }

    private String extractEmail(String token){
        return extractClaimInfo(token,(c)->c.getSubject());
    }

    private Object extractPayLoadBasedOnKey(String token,String key){
        Claims c=this.getAllClaims(token);
        return c.get(key);
    }


    @Override
    public void run(String... args) throws Exception {


        //Formal flow


//        Map<String, Object> map=new HashMap<>();
//        map.put("email","sanjay.s01558@gmail.com");
//        map.put("name","Sanjay");
//
//        String token=this.createToken(map,"sanjay.s01558@gmail.com");
//        System.out.println(token);
//        //token comes from user
//        //i should extract the subject(which i have kept email in my case) and check it in the db




    }
}
