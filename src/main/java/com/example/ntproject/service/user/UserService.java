package com.example.ntproject.service.user;

import com.example.ntproject.controller.user.dto.GetUserDto;
import com.example.ntproject.controller.user.dto.PatchUserDto;
import com.example.ntproject.controller.user.dto.PatchUserResponseDto;
import com.example.ntproject.infrastructure.entity.AuthEntity;
import com.example.ntproject.infrastructure.entity.UserEntity;
import com.example.ntproject.infrastructure.repository.AuthRepository;
import com.example.ntproject.infrastructure.repository.UserRepository;
import com.example.ntproject.service.auth.OwnershipService;
import com.example.ntproject.service.user.error.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends OwnershipService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthRepository authRepository) {
        super(authRepository);
        this.userRepository = userRepository;
    }

    public GetUserDto getUserByUsername(String username) {
        AuthEntity auth = authRepository.findByUsername(username).orElseThrow(() -> UserNotFound.createByUsername(username));
        UserEntity user = auth.getUser();

        return new GetUserDto(user.getId(), user.getName(), user.getLastName(), user.getEmail());
    }

    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #id)")
    public PatchUserResponseDto update(long id, PatchUserDto dto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> UserNotFound.createById(id));

        dto.getName().ifPresent(user::setName);
        dto.getLastName().ifPresent(user::setLastName);
        dto.getEmail().ifPresent(user::setEmail);

        userRepository.save(user);

        return new PatchUserResponseDto(user.getId(), user.getName(), user.getLastName(), user.getEmail());
    }

    public GetUserDto getOneById(long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> UserNotFound.createById(id));
        return mapUser(user);
    }


    public List<GetUserDto> getAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(this::mapUser).toList();
    }

    public void delete(long id) {
        if (!userRepository.existsById(id)) {
            throw UserNotFound.createById(id);
        }
        userRepository.deleteById(id);
    }

    public GetUserDto mapUser(UserEntity user) {
        return new GetUserDto(user.getId(), user.getName(), user.getLastName(), user.getEmail());
    }
}
