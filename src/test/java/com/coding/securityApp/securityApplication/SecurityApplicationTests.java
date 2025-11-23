package com.coding.securityApp.securityApplication;


import com.coding.securityApp.securityApplication.entities.User;
import com.coding.securityApp.securityApplication.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {

		User user=new User(1L,"aditya@gmail.com","pass23","aditya");

		String token=jwtService.generateToken(user);
		System.out.println("Generated Token:"+ token);
		Long id=jwtService.getUserIdFromToken(token);
		System.out.println("Id of the user:" + id);
	}

}
