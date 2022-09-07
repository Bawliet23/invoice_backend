package com.example.demo.services;

import com.example.demo.dtos.RegisterDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface IUserService {

public UserDto addUser(RegisterDto userDto);
public UserDto updateUser(UserDto userDto);
public UserDto getUser(Long id);
public boolean deleteUser(Long id);

}
