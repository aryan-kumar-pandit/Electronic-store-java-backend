package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {

    private String roleId;

    private String name;

}
