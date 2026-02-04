package com.example.AuthService.Filters;

import com.example.AuthService.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath(); return path.equals("/api/v1/passenger/signup") || path.equals("/api/v1/passenger/signin");
    }

    JWTService jwtService;
    UserDetailsService userDetailsService;

    JwtAuthFilter(JWTService jwtService,UserDetailsService userDetailsService){
        this.jwtService=jwtService;
        this.userDetailsService=userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("the req will not come here");

        String token=null;
        for(Cookie c:request.getCookies()){
            if(c.getName().equals("JwtToken")){
                token=c.getValue();
            }
        }
        if(token==null){
            System.out.println("token not available 401 forbidden");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String email=this.jwtService.extractEmail(token);



        if(email!=null){

            //token is verified
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(email);

            if(this.jwtService.validate(token,userDetails.getUsername())){

                UsernamePasswordAuthenticationToken usernameAuth=new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,null);
                usernameAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernameAuth);

            }

        }

        filterChain.doFilter(request,response);

    }
}
