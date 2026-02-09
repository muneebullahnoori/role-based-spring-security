package com.example.exam.web;

import com.example.exam.dto.user.UserCreatDto;
import com.example.exam.dto.user.UserResponseDto;
import com.example.exam.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreatDto dto){
        UserResponseDto userResponseDto = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

}
