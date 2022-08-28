package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class JwtResponse {
    private UserDto user;
    private String jwt;
}
