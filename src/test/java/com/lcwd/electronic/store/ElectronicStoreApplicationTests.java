package com.lcwd.electronic.store;

import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.security.JwtHelper;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElectronicStoreApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtHelper jwtHelper;

	@Test
	void contextLoads() {
	}
	/*@Test
	void TestToken()
	{
		User user = userRepository.findByEmail("aryan@gmail.com").get();
		System.out.println(user.getName());
		String token = jwtHelper.generateToken(user);
		System.out.println(token);
		System.out.println("Username is " +jwtHelper.getUsernameFromToken(token));

	}*/

}
