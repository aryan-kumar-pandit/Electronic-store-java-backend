package com.lcwd.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryDto {


    private String categoryId;
    @NotBlank(message = "title is required")
    @Size(min = 4,message = "Minimum 4 characters required")
    private String title;
    @NotBlank(message="Description required")
    private String description;
    @NotBlank
    private String coverImage;

}
