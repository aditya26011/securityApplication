package com.coding.securityApp.securityApplication.config;


import com.coding.securityApp.securityApplication.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                //Any request that we make will be authenticated now
                                .authorizeHttpRequests(auth->
                                auth
                                        .requestMatchers("/auth/**","/posts").permitAll()
//                                        .requestMatchers("/posts/**").hasAnyRole("ADMIN")
                                .anyRequest().authenticated())
                .csrf(CsrfConfig->CsrfConfig.disable())
                .sessionManagement(sessionConfig->
                        sessionConfig.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Adding our own jwt filter before username and password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        ;


//                .formLogin(Customizer.withDefaults());//will go to /login path which is default.


        return httpSecurity.build();
    }


    //Used for Login to check the authentication
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //Creating users
//    @Bean
//    UserDetailsService myInMemoryUserDetailService(){
//      UserDetails normalUser=User
//              .withUsername("Aditya")
//              .password(passwordEncoder().encode("Aditya123")).
//              roles("USER")
//              .build();
//
//        UserDetails adminUser=User
//                .withUsername("Admin")
//                .password(passwordEncoder().encode("Admin123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }



}
