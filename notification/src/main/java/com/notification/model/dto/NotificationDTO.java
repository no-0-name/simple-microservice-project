package com.notification.model.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String subject;
    private String text;
    private String recipientEmail;
}
