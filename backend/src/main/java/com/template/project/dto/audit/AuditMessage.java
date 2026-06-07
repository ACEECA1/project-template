package com.template.project.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.template.project.model.AuditLogAction;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditMessage implements Serializable {
    private AuditLogAction action;
    private String details;
    private String username;
}
