package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.User;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class JwtResponse {
    private String token;
    private UserDto user;
    private String refreshToken;

}
