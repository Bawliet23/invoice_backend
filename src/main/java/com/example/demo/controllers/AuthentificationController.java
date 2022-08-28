package com.example.demo.controllers;


import com.example.demo.dtos.JwtResponse;
import com.example.demo.dtos.LoginDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.User;
import com.example.demo.security.MyUserPrincipal;
import com.example.demo.services.IUserService;
import com.example.demo.services.JwtUserDetailsService;
import com.example.demo.utils.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthentificationController {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private final IUserService userService;

    public AuthentificationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, ModelMapper modelMapper, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginDto login) throws Exception {
        System.out.println("controller Auth");
        final MyUserPrincipal userDetails = (MyUserPrincipal) userDetailsService.loadUserByUsername(login.getEmail());
//        authenticate(userDetails.getUsername(), login.getPassword());
        final String token = jwtTokenUtil.generateToken(userDetails);
        UserDto user = modelMapper.map(userDetails.getUser(),UserDto.class);
        return ResponseEntity.ok(new JwtResponse(user,token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signIn(@RequestBody UserDto userDto) {
        UserDto client1=userService.addUser(userDto);
        User user = modelMapper.map(client1,User.class);
        MyUserPrincipal userDetails=new MyUserPrincipal(user);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(client1,token));
    }





    private void authenticate(String username, String password) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
