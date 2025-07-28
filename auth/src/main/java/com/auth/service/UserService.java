package com.auth.service;

import com.auth.client.NotificationClient;
import com.auth.model.dto.UserDTO;
import com.auth.model.entity.Role;
import com.auth.model.entity.User;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final NotificationClient notificationClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public User saveUser(User user) {
        sendAdminNotification(user, "CREATE");
        return userRepository.save(user);
    }


    @Transactional
    public User updateUser(User user) {
        User updatedUser = userRepository.save(user);

        if (updatedUser.getRole() == Role.USER) {
            sendAdminNotification(updatedUser, "UPDATE");
        }

        return updatedUser;
    }

    @Transactional
    public User createUserByAdmin(User newUser, User adminUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User createdUser = userRepository.save(newUser);

        return createdUser;
    }

    @Transactional
    public void deleteUser(User user) {
        if (user.getRole() == Role.USER) {
            sendAdminNotification(user, "DELETE");
        }
        userRepository.delete(user);
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    private void sendAdminNotification(User changedUser, String action) {
        try {
            UserDTO userDTO = UserDTO.builder()
                    .username(changedUser.getUsername())
                    .password(changedUser.getPassword())
                    .email(changedUser.getEmail())
                    .build();

            notificationClient.notifyAdminsAboutUserChange(userDTO, action);
        } catch (Exception e) {
            System.err.println("Failed to send notification: " + e.getMessage());
        }
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }
}