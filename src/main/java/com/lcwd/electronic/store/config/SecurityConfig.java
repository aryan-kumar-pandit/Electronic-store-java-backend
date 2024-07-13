package com.lcwd.electronic.store.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
   /* @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails admin = User.builder()
                .username("Aryan")
                .password(passwordEncoder().encode("Admin"))
                .roles("Admin")
                .build();
        UserDetails normalUser = User.builder()
                .username("Rahul")
                .password(passwordEncoder().encode("Pandit"))
                .roles("Normal user")
                .build();
        return new InMemoryUserDetailsManager(admin,normalUser);
    }
*/
    @Bean
    public PasswordEncoder passwordEncoder()
    {

        return new BCryptPasswordEncoder();
    }
}
