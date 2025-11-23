package com.coding.securityApp.securityApplication.service;

import com.coding.securityApp.securityApplication.dto.LoginDTO;
import com.coding.securityApp.securityApplication.dto.SignUpDTO;
import com.coding.securityApp.securityApplication.dto.UserDTO;
import com.coding.securityApp.securityApplication.entities.User;
import com.coding.securityApp.securityApplication.exceptions.ResourceNotFound;
import com.coding.securityApp.securityApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service  //if we comment this then in Memory user details will be used
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new BadCredentialsException("User with email"+ username+ "Not found"));
    }

    //For Authenticating requests used in filter using Jwt-vid
    public User getUserById(Long UserId){
       return userRepository.findById(UserId).orElseThrow(()-> new ResourceNotFound("User with id"+ UserId+ "Not found"));
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<User> user=userRepository.findByEmail(signUpDTO.getEmail());

        if(user.isPresent()){
            throw new BadCredentialsException("User with this email already Exists" + signUpDTO.getEmail());
        }
        User userToBeCreated=modelMapper.map(signUpDTO,User.class);
        userToBeCreated.setPassword(passwordEncoder.encode(userToBeCreated.getPassword()));

         User savedUser=userRepository.save(userToBeCreated);
         return modelMapper.map(savedUser,UserDTO.class);
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

}
