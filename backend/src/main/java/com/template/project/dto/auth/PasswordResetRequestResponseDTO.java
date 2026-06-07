package com.template.project.dto.auth;

import lombok.Builder;
import lombok.Data;
import com.template.project.model.PasswordResetRequest;

import java.time.LocalDateTime;

@Data
@Builder
public class PasswordResetRequestResponseDTO {
    private Long id;
    private String username;
    private PasswordResetRequest.ResetStatus status;
    private LocalDateTime createdAt;
}
