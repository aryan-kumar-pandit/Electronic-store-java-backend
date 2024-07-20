package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.RefreshTokenDto;

public interface RefreshTokenService {
    //create
    RefreshTokenDto createRefreshToken(String username);

    //find by token
    RefreshTokenDto findByToken(String token);

    //verify expiry of token
    void verifyRefreshToken(String token);
}
