package com.template.project.service.audit;

import lombok.RequiredArgsConstructor;
import com.template.project.util.SecurityUtils;
import org.springframework.stereotype.Service;
import com.template.project.model.AuditLogAction;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditProducer auditProducer;

    /**
     * Records an audit log entry for a specific action performed by a user.
     * If the current user is authenticated, their username is attached to the log.
     * Otherwise, the action is logged under 'anonymousUser'.
     *
     * @param action the {@link AuditLogAction} representing the action type
     * @param details a detailed description of the action and its context
     */
    public void logAction(AuditLogAction action, String details) {
        String username = SecurityUtils.getCurrentUsername();
        if (username != null && !username.equals("anonymousUser")) {
            auditProducer.logAction(action, details, username);
        } else {
            auditProducer.logAction(action, details, "anonymousUser");
        }
    }
}
