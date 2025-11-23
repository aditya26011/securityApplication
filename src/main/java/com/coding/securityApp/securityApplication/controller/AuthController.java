package com.coding.securityApp.securityApplication.controller;


import com.coding.securityApp.securityApplication.dto.LoginDTO;
import com.coding.securityApp.securityApplication.dto.SignUpDTO;
import com.coding.securityApp.securityApplication.dto.UserDTO;
import com.coding.securityApp.securityApplication.service.AuthService;
import com.coding.securityApp.securityApplication.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;



    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO=userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO,
                                        HttpServletRequest request, HttpServletResponse response){

        String token=authService.login(loginDTO);
        Cookie cookie= new Cookie("token",token);
        cookie.setHttpOnly(true);// so that it can only be found by http methods
        response.addCookie(cookie);

        return ResponseEntity.ok(token);
    }

}
