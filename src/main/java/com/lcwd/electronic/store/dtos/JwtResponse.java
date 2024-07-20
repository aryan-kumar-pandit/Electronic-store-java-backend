package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private UserDto user;
    private String refreshToken;

}
