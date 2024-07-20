package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.RefreshTokenDto;
import com.lcwd.electronic.store.entities.RefreshToken;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.RefreshTokenRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.RefreshTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    UserRepository userRepository;
    RefreshTokenRepository refreshTokenRepository;
    private ModelMapper modelMapper;

    public RefreshTokenServiceImpl(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RefreshTokenDto createRefreshToken(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found with this email"));
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(5 * 24 * 60 * 60))
                .build();
        return this.modelMapper.map(refreshToken,RefreshTokenDto.class);
    }

    @Override
    public RefreshTokenDto findByToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));
        return this.modelMapper.map(refreshToken,RefreshTokenDto.class);
    }

    @Override
    public RefreshTokenDto verifyRefreshToken(RefreshTokenDto refreshTokenDto) {
        RefreshToken refreshToken = modelMapper.map(refreshTokenDto, RefreshToken.class);

        if(refreshTokenDto.getExpiryDate().compareTo(Instant.now())<0)
        {
            refreshTokenRepository.delete(refreshToken);
        }
        else {
            throw new RuntimeException("Refresh token expired");
        }
        return refreshTokenDto;
    }


}
