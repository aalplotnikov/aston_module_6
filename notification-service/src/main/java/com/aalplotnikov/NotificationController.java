package com.aalplotnikov;

import com.aalplotnikov.DTO.NotificationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notification")
    public ResponseEntity<Void> save(
            @RequestBody
            NotificationRequestDTO notificationRequestDTO
    ) {
        notificationService.notify(notificationRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
