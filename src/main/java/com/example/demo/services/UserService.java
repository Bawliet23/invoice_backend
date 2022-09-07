package com.example.demo.services;

import com.example.demo.dtos.RegisterDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.User;
import com.example.demo.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
    public UserService(IUserRepository userRepository, ModelMapper modelMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public UserDto addUser(RegisterDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        user.setPassword(encoder.encode(userDto.getPassword()));
        User u =  userRepository.save(user);
        return modelMapper.map(u,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
    return null;
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> exist = userRepository.findById(id);
        if (exist.isEmpty())
            return null;
        return modelMapper.map(exist.get(),UserDto.class);
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> exist = userRepository.findById(id);
        if (exist.isEmpty())
           return false;
        userRepository.delete(exist.get());
        return true;
    }
}
