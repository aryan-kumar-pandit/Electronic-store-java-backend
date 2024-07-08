package com.lcwd.electronic.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="categories")
public class Category {

    @Id
    @Column(name="id")
    private String categoryId;
    @Column(name="category_title",length = 60,nullable = false)
    private String title;
    @Column(name="category_desc",length = 50)
    private String description;
    private String coverImage;

}
