package com.notification.conrtoller;

import com.notification.model.dto.NotificationDTO;
import com.notification.model.dto.UserDTO;
import com.notification.service.EmailService;
import com.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {
    private final EmailService emailService;
    private final NotificationService notificationService;

    @PostMapping
    public void notifyAdminsAboutUserChange(
            @RequestBody UserDTO userDTO,
            @RequestParam String action) {
        notificationService.notifyAdminsAboutUserChange(userDTO, action);
    }
}