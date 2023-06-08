package com.spring.blog.dto;

import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class PersonDTO {
    private long id;
    private String name;
    private int age;
}
