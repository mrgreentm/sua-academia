package com.jjsoftwares.suaacademia.services;

import com.jjsoftwares.suaacademia.dto.AuthUserDTO;
import com.jjsoftwares.suaacademia.dto.CreateUserDTO;
import com.jjsoftwares.suaacademia.dto.RecoveryJWTTokenDTO;
import com.jjsoftwares.suaacademia.entities.User;
import com.jjsoftwares.suaacademia.repositories.UserRepository;
import com.jjsoftwares.suaacademia.security.authentication.JwtTokenService;
import com.jjsoftwares.suaacademia.security.config.SecurityConfiguration;
import com.jjsoftwares.suaacademia.security.userdetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public RecoveryJWTTokenDTO authenticateUser(AuthUserDTO loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJWTTokenDTO(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDTO createUserDto) {

        User newUser = User.builder()
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
//                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .name(createUserDto.name())
                .build();

        userRepository.save(newUser);
    }

}
