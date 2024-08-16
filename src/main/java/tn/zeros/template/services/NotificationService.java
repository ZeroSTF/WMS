package tn.zeros.template.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.zeros.template.entities.Notification;
import tn.zeros.template.entities.User;
import tn.zeros.template.repositories.NotificationRepository;
import tn.zeros.template.repositories.UserRepository;
import tn.zeros.template.services.IServices.INotificationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    private final NotificationRepository  notificationRepository;
    private final UserRepository userRepository;

    @Override
    public List<Notification> findAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findNotification(Long id) {
        if (notificationRepository.findById(id).isPresent()){
            Notification notification = notificationRepository.findById(id).get();
            notification.setStatus(true);
            notificationRepository.save(notification);
            return notification;
        }else {
            throw new EntityNotFoundException("Notification not found with id: " + id);
        }
    }

    @Override
    public Notification addNotification(Notification notification) {
        notification.setStatus(false);
        notification.setDate(LocalDateTime.now());
        return notificationRepository.save(notification);

    }

    @Override
    public void removeNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Notification modifyNotification(Notification notification) {
        Optional<Notification> existingUser = notificationRepository.findById(notification.getId());
        if (existingUser.isPresent()) {
            return notificationRepository.save(notification);
        } else {
            throw new EntityNotFoundException("Notification not found with id: " + notification.getId());
        }
    }

    @Override
    public List<Notification> getUnread(User user) {
        return notificationRepository.findByUserAndStatus(user,false);
    }

    @Override
    public List<Notification> getByUser(User user) {
        return notificationRepository.findNotificationsByUser(user);
    }
}
