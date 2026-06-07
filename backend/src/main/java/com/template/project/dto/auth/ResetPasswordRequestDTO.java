package com.template.project.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
    @NotBlank
    private String resetToken;
    @NotBlank
    private String newPassword;
}
