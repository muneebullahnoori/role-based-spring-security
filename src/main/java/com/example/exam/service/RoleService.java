package com.example.exam.service;

import com.example.exam.dto.role.RoleCreateDto;
import com.example.exam.dto.role.RoleResponseDto;
import com.example.exam.model.Role;
import com.example.exam.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleResponseDto create(RoleCreateDto dto) {
        Role role = mapToEntity(dto);
        Role save = roleRepository.save(role);
        return mapToResponseDto(save);
    }

    public List<RoleResponseDto> getAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponseDto> list = roles.stream().map(this::mapToResponseDto).toList();
        return list;
    }


    private Role mapToEntity(RoleCreateDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return role;
    }
    private RoleResponseDto mapToResponseDto(Role save) {
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.setId(save.getId());
        roleResponseDto.setName(save.getName());
        roleResponseDto.setDescription(save.getDescription());
        return roleResponseDto;
    }


}
