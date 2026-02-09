package com.example.exam.service;

import com.example.exam.dto.user.UserCreatDto;
import com.example.exam.dto.user.UserResponseDto;
import com.example.exam.model.Role;
import com.example.exam.model.User;
import com.example.exam.repository.RoleRepository;
import com.example.exam.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public UserResponseDto create(UserCreatDto dto) {
        User user = mapToEntity(dto);
        User save = userRepository.save(user);
        UserResponseDto userResponseDto = mapToResponseDto(save);
        return userResponseDto;
    }

    public List<UserResponseDto> getAll() {
        List<User> all = userRepository.findAll();
        List<UserResponseDto> list = all.stream().map(this::mapToResponseDto).toList();
        return list;
    }


    private User mapToEntity(UserCreatDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        List<Role> allById = roleRepository.findAllById(dto.getRoleIds());
        user.setRoles(allById);
        return user;
    }
    private UserResponseDto mapToResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setPassword(user.getPassword());
        userResponseDto.setEmail(user.getEmail());
        List<String> roleNames = user.getRoles().stream().map(Role::getName).toList();
        userResponseDto.setRoleNames(roleNames);
        return userResponseDto;

    }


}
