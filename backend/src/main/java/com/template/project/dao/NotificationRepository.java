package com.template.project.dao;

import com.template.project.model.Notification;
import com.template.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.template.project.model.NotificationType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    Optional<Notification> findFirstByUserAndTypeAndTargetIdOrderByCreatedAtDesc(User user, NotificationType type, Long targetId);
    Optional<Notification> findByIdAndUser(Long id, User user);
}
