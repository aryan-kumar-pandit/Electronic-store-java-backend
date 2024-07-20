package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.*;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.security.JwtHelper;
import com.lcwd.electronic.store.services.RefreshTokenService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtResponse jwtResponse;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RefreshTokenService refreshTokenService;
    private Logger logger= LoggerFactory.getLogger(AuthenticationController.class);
    @PostMapping("/regenerate-token")
    public ResponseEntity<JwtResponse> regenerateToken(@RequestBody RefreshTokenRequest request)
    {
        RefreshTokenDto refreshTokenDto = refreshTokenService.findByToken(request.getRefreshToken());
        RefreshTokenDto refreshTokenDto1 = refreshTokenService.verifyRefreshToken(refreshTokenDto);
        UserDto user = refreshTokenService.getUser(refreshTokenDto1);
        String token = jwtHelper.generateToken(modelMapper.map(user, User.class));
        JwtResponse response = jwtResponse.builder()
                .token(token)
                .refreshTokenDto(refreshTokenDto1)
                .user(user)
                .build();

        return ResponseEntity.ok(response);

    }
    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
    {
        logger.info("Username={} , Password={}",request.getEmail(),request.getPassword());

        this.doAuthenticate(request.getEmail(),request.getPassword());
        User user = (User)userDetailsService.loadUserByUsername(request.getEmail());
        //generate token
        String token = jwtHelper.generateToken(user);

        //Refresh token generation
        RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(user.getEmail());


        //send token as a response
        JwtResponse response = jwtResponse.builder().
                token(token).
                user(modelMapper.map(user, UserDto.class))
                .refreshTokenDto(refreshToken)
                .build();
        return ResponseEntity.ok(response);
    }



    private void doAuthenticate(String email, String password) {
        try {
            Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
            authenticationManager.authenticate(authentication);
        }
        catch (BadCredentialsException ex)
        {
            throw new BadCredentialsException("invalid username or password");
        }
    }
}
