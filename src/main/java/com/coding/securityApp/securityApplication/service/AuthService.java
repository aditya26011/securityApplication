package com.coding.securityApp.securityApplication.service;

import com.coding.securityApp.securityApplication.dto.LoginDTO;
import com.coding.securityApp.securityApplication.dto.LoginResponseDTO;
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

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication= authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );

        User user= (User) authentication.getPrincipal();
        String accessToken=  jwtService.generateAccessToken(user);// while login we will generate the token
        String refreshToken= jwtService.generateRefreshToken(user);

        sessionService.generateNewSession(user,refreshToken);

        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId=jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        User user=userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);
    }
}
