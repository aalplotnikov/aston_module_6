package com.aalplotnikov;

import com.aalplotnikov.DTO.NotificationRequestDTO;
import com.aalplotnikov.DTO.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void shouldHandleCreateNotification() throws Exception {
        mockMvc.perform(post("/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "aalplotnikov@gmail.com",
                                  "operation": "CREATE"
                                }
                                """))
                .andExpect(status().isNoContent());

        verify(notificationService).notify(
                new NotificationRequestDTO(
                        "aalplotnikov@gmail.com",
                        Operation.CREATE
                )
        );
    }

    @Test
    void shouldHandleDeleteNotification() throws Exception {
        mockMvc.perform(post("/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "aalplotnikov@gmail.com",
                                  "operation": "DELETE"
                                }
                                """))
                .andExpect(status().isNoContent());

        verify(notificationService).notify(
                new NotificationRequestDTO(
                        "aalplotnikov@gmail.com",
                        Operation.DELETE
                )
        );
    }
}