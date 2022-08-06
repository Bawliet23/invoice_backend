package com.example.demo.services;

import com.example.demo.dtos.UserDto;
import com.example.demo.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

public UserDto addUser(UserDto userDto);
public UserDto updateUser(UserDto userDto);
public UserDto getUser(Long id);
public boolean deleteUser(Long id);

}
