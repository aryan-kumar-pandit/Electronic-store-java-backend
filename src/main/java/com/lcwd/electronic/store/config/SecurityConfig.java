package com.lcwd.electronic.store.config;

import com.lcwd.electronic.store.security.JwtAuthenticationEntryPoint;
import com.lcwd.electronic.store.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter filter;
    @Autowired
    JwtAuthenticationEntryPoint entryPoint;

    //security filter chain bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
    {
        security.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());

        security.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        //configuring url's
        security.authorizeHttpRequests(request->{
            request.requestMatchers(HttpMethod.DELETE,"/users/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/users/**").hasAnyRole("ADMIN","NORMAL")
                    .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
                    .requestMatchers("/products/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/users/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/users/**").permitAll()
                    .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
                    .requestMatchers("/categories/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST,"/auth/generate-token").permitAll()
                    .requestMatchers("/auth/**").authenticated()
                    .anyRequest().permitAll();

        });
        //kind of security you want-- we used basic security
        //security.httpBasic(Customizer.withDefaults());
        //entry point
        security.exceptionHandling(ex-> ex.authenticationEntryPoint(entryPoint));
        //session management policy
        security.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //added jwt filter before any other filter
        security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
