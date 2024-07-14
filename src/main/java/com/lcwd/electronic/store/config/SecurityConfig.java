package com.lcwd.electronic.store.config;


import com.lcwd.electronic.store.services.impl.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
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

    //form based login
/*    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("index.html")
                .loginProcessingUrl("/process-url")
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/failed")
                .and()
                .logout("/logout");
        return http.build();
    }*/
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {

        return new BCryptPasswordEncoder();
    }
}
