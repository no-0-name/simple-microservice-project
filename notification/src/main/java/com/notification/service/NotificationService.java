package com.notification.service;

import com.notification.client.AuthServiceClient;
import com.notification.model.dto.NotificationDTO;
import com.notification.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmailService emailService;
    private final AuthServiceClient authServiceClient;

    public void processNotification(NotificationDTO notification) {
        try {
            log.info("Processing notification for email: {}", notification.getRecipientEmail());
            emailService.sendEmail(notification);
            log.info("Notification sent successfully to: {}", notification.getRecipientEmail());
        } catch (Exception e) {
            log.error("Failed to send notification to {}: {}", notification.getRecipientEmail(), e.getMessage());
        }
    }

    public void notifyAdminsAboutUserChange(UserDTO user, String action) {
        try {
            List<UserDTO> admins = authServiceClient.getAdminsEmail();
            log.info("Sending notifications to {} admins about user action: {}", admins.size(), action);

            String subject = String.format("User %s %s", user.getUsername(), action);
            String text = String.format(
                    "User %s %s. Details: \nPassword: %s \nEmail: %s",
                    user.getUsername(),
                    getActionText(action),
                    user.getPassword(),
                    user.getEmail()
            );

            for (UserDTO admin : admins) {
                NotificationDTO notification = new NotificationDTO();
                notification.setSubject(subject);
                notification.setText(text);
                notification.setRecipientEmail(admin.getEmail());

                processNotification(notification);
            }
        } catch (Exception e) {
            log.error("Failed to notify admins: {}", e.getMessage());
        }
    }

    private String getActionText(String action) {
        return switch (action.toUpperCase()) {
            case "CREATE" -> "created";
            case "UPDATE" -> "updated";
            case "DELETE" -> "deleted";
            default -> "modified";
        };
    }
}