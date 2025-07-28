package com.auth.controller;

import com.auth.model.dto.AdminCreateUserRequest;
import com.auth.model.dto.UpdateUserRequest;
import com.auth.model.dto.UserResponse;
import com.auth.model.entity.Role;
import com.auth.model.entity.User;
import com.auth.repository.UserRepository;
import com.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @PostMapping("/users")
    public ResponseEntity<?> createUser(
            @RequestBody AdminCreateUserRequest request,
            @AuthenticationPrincipal User adminUser) {

        User newUser = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .build();

        User createdUser = userService.createUserByAdmin(newUser, adminUser);
        return ResponseEntity.ok(UserResponse.fromUser(createdUser));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal User currentUser, @RequestBody UpdateUserRequest request) {
        if (passwordEncoder.matches(request.getPassword(), currentUser.getPassword())) {
            return ResponseEntity.badRequest().body("The new password must be different from the old one");
        }

        currentUser.setEmail(request.getEmail());
        currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
        currentUser.setFirstName(request.getFirstName());
        currentUser.setLastName(request.getLastName());

        User updatedUser = userService.saveUser(currentUser);
        return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);
        return ResponseEntity.ok("User was delete");
    }
}
