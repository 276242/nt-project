package com.example.ntproject.service.auth;

import com.example.ntproject.controller.auth.dto.LoginDto;
import com.example.ntproject.controller.auth.dto.LoginResponseDto;
import com.example.ntproject.controller.auth.dto.RegisterDto;
import com.example.ntproject.controller.auth.dto.RegisterResponseDto;
import com.example.ntproject.infrastructure.entity.AuthEntity;
import com.example.ntproject.infrastructure.entity.UserEntity;
import com.example.ntproject.infrastructure.repository.AuthRepository;
import com.example.ntproject.infrastructure.repository.UserRepository;
import com.example.ntproject.service.auth.error.IncorrectPasswordException;
import com.example.ntproject.service.auth.error.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthRepository authRepository, UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDto register(RegisterDto dto) {
        Optional<AuthEntity> existingAuth = authRepository.findByUsername(dto.getUsername());
        if (existingAuth.isPresent()){
            throw UserAlreadyExistsException.createByUsername(dto.getUsername());
        }


        Optional<UserEntity> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            throw UserAlreadyExistsException.createByEmail(dto.getEmail());
        }


        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(dto.getEmail());
        userRepository.save(userEntity);

        AuthEntity authEntity = new AuthEntity();
        authEntity.setUsername(dto.getUsername());
        authEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        authEntity.setRole(dto.getRole());
        authEntity.setUser(userEntity);
        authRepository.save(authEntity);

        return new RegisterResponseDto(authEntity.getUsername(), authEntity.getRole(), authEntity.getId());
    }

    public LoginResponseDto login(LoginDto dto) {
        AuthEntity authEntity = authRepository.findByUsername(dto.getUsername()).orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(dto.getPassword(), authEntity.getPassword())){
            throw IncorrectPasswordException.create();
        }

        String token = jwtService.generateToken(authEntity);

        return new LoginResponseDto(token);
    }
}
