package com.auth.controller;

import com.auth.model.dto.UserResponse;
import com.auth.model.entity.Role;
import com.auth.model.entity.User;
import com.auth.repository.UserRepository;
import com.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/internal")
@RequiredArgsConstructor
public class InternalController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/users/role/ADMIN")
    public ResponseEntity<List<UserResponse>> getAdminsEmail() {
        List<User> admins = userRepository.findByRole(Role.ADMIN);

        List<UserResponse> response = admins.stream()
                .map(user -> UserResponse.builder()
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
