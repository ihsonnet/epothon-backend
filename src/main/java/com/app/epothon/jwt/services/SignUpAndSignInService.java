package com.app.epothon.jwt.services;

import com.app.epothon.jwt.dto.request.SignUpForm;
import com.app.epothon.jwt.dto.response.JwtResponse;
import com.app.epothon.jwt.repository.RoleRepository;
import com.app.epothon.jwt.repository.UserRepository;
import com.app.epothon.jwt.security.jwt.JwtProvider;
import com.app.epothon.jwt.dto.request.LoginForm;
import com.app.epothon.jwt.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@AllArgsConstructor
@Service
public class SignUpAndSignInService {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    AuthService authService;


    public Object userSignup(SignUpForm signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            //return true;
            return new JwtResponse("Email Already Exists");
        }


        User user = new User();
        UUID id = UUID.randomUUID();
        String uuid = id.toString();
        user.setId(uuid);
        user.setFullName(signUpRequest.getFullName());

        String[] arrOfUsername = signUpRequest.getEmail().split("@", 2);
        String username = arrOfUsername[0];
        Integer suffix = 0;

        if (userRepository.existsByUsername(username)){
            String name = username+"0"+suffix;
            while(userRepository.existsByUsername(name)){
                suffix++;
                name = username+"0"+suffix;
            }
            username = name;
        }
        user.setUsername(username);

        user.setEmail(signUpRequest.getEmail());
        user.setPhoneNo(signUpRequest.getPhoneNo());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setRoles(authService.getRolesFromStringToRole(signUpRequest.getRole()));

        user.setCreatedBy(arrOfUsername[0]);

        userRepository.saveAndFlush(user);
        System.out.println(1);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        signUpRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        return new JwtResponse("OK", jwt, signUpRequest.getRole());
    }


    public JwtResponse signIn(LoginForm loginRequest) {

        Optional<User> userOptional;
        if (loginRequest.getUsername().contains("@")){
                userOptional = userRepository.findByEmail(loginRequest.getUsername());
        }
        else {
                userOptional = userRepository.findByUsername(loginRequest.getUsername());
        }

        String userName;

        if (userOptional.isPresent()) {
            userName = userOptional.get().getUsername();
        } else {
            userName = "";
            //throw new ResponseStatusException(HttpStatus.valueOf(410),"User Not Exists");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName,
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);

        System.out.println(jwt);
        return new JwtResponse("OK", jwt, authService.getRolesStringFromRole(userOptional.get().getRoles()));
    }

}
