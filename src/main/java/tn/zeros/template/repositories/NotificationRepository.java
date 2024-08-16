package tn.zeros.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.zeros.template.entities.Notification;
import tn.zeros.template.entities.User;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findNotificationsByUser(User user);
    List<Notification> findByUserAndStatus(User user, boolean status);
}
