package com.coding.securityApp.securityApplication.controller;


import com.coding.securityApp.securityApplication.dto.LoginDTO;
import com.coding.securityApp.securityApplication.dto.LoginResponseDTO;
import com.coding.securityApp.securityApplication.dto.SignUpDTO;
import com.coding.securityApp.securityApplication.dto.UserDTO;
import com.coding.securityApp.securityApplication.service.AuthService;
import com.coding.securityApp.securityApplication.service.SessionService;
import com.coding.securityApp.securityApplication.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;



    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO=userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO,
                                        HttpServletRequest request, HttpServletResponse response){

        LoginResponseDTO loginResponseDTO=authService.login(loginDTO);
        Cookie cookie= new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly("production".equals(deployEnv ));// so that it can only be found by http methods
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
      String refreshToken=  Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()->new AuthenticationServiceException("Refresh token not found in the cookies"));

        LoginResponseDTO loginResponseDTO= authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDTO);
    }

}
