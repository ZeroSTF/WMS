package tn.zeros.template.services.IServices;

import tn.zeros.template.entities.Notification;
import tn.zeros.template.entities.User;

import java.util.List;

public interface INotificationService {
    public List<Notification> findAllNotifications();
    public Notification findNotification(Long id);

    public Notification addNotification(Notification notification);
    public void removeNotification(Long id);
    public Notification modifyNotification(Notification notification);

    public List<Notification> getUnread(User user);
    public List<Notification> getByUser(User user);
}
