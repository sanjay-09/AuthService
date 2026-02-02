package com.example.AuthService.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtServiceDemo implements CommandLineRunner {

    @Value("${jwt_expiry}")
    private int expiry;

    @Value("${jwt_secret_key}")
    private String SECRET;


    private Key secretKey(){
        return  Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    }

    public String generateToken(Map<String,Object> payload,String email){

        Date d=new Date();
        Date exp=new Date(d.getTime()*expiry+10000L);
        return Jwts.builder().claims(payload).subject(email).issuedAt(new Date()).expiration(exp).signWith(this.secretKey()).compact();

    }

    public Claims getAllClaims(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) this.secretKey()) // Replaces .setSigningKey(this.getKey())
                .build()
                .parseSignedClaims(token) // Replaces .parseClaimsJws(token)
                .getPayload(); // Replaces .getBody()
    }

    public <T> T getClaim(String token, Function<Claims,T> claimResolver){

        Claims c=this.getAllClaims(token);
        return claimResolver.apply(c);

    }

    public String getEmail(String token){
        return getClaim(token,(c)->c.getSubject());
    }


    public Object getPayload(String token,String key){
        Claims c=this.getAllClaims(token);
        return c.get(key);
    }

    @Override
    public void run(String... args) throws Exception {

        Map<String,Object> map=new HashMap<>();
        map.put("email","sanjay.s01558@gmail.com");
        map.put("name","sanjay");

        String token=this.generateToken(map,"sanjay.s01558@gmail.com");


        System.out.println(this.getEmail(token));
    }
}
