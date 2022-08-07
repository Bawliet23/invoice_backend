package com.example.demo.controllers;


import com.example.demo.dtos.UserDto;
import com.example.demo.services.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        UserDto user = userService.getUser(id);
        if (user==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        boolean deleted = userService.deleteUser(id);
        if (!deleted)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User doesn't exist");
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestParam("user") String   userDto,@RequestParam("logo") MultipartFile logo) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userdto = objectMapper.readValue(userDto,UserDto.class);
        UserDto user = userService.addUser(userdto,logo);
        if (user==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
