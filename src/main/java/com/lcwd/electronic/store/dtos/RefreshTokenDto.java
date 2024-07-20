package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenDto {

    private int id;
    private String token;
    private Instant expiryDate;
    private UserDto user;
}
