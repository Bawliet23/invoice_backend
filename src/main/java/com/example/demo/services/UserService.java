package com.example.demo.services;

import com.example.demo.dtos.UserDto;
import com.example.demo.entities.User;
import com.example.demo.repositories.IUserRepository;
import com.example.demo.utils.FileHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(IUserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto addUser(UserDto userDto, MultipartFile logo) throws IOException {
        User user = modelMapper.map(userDto,User.class);
        user.setLogo(FileHandler.uploadFile(logo));
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
