package com.coding.securityApp.securityApplication.service;

import com.coding.securityApp.securityApplication.dto.LoginDTO;
import com.coding.securityApp.securityApplication.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    public String login(LoginDTO loginDTO) {
        Authentication authentication= authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );

        User user= (User) authentication.getPrincipal();
        return  jwtService.generateToken(user);// while login we will generate the token

    }
}
