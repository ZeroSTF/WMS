package tn.zeros.template.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tn.zeros.template.entities.Notification;
import tn.zeros.template.entities.User;
import tn.zeros.template.services.IServices.INotificationService;
import tn.zeros.template.services.IServices.IUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class NotificationController {
    private final INotificationService notificationService;
    private final IUserService userService;

    @GetMapping("/getAll")
    public List<Notification> getNotifications() {
        return notificationService.findAllNotifications();
    }

    @GetMapping("/get/{notification-id}")
    public Notification retrieveNotification(@PathVariable("notification-id") Long notificationId) {
        return notificationService.findNotification(notificationId);
    }

    @PostMapping("/add")
    public Notification addNotification(@RequestBody Notification c) {
        return notificationService.addNotification(c);
    }

    @DeleteMapping("/delete/{notification-id}")
    public void removeNotification(@PathVariable("notification-id") Long notificationId) {
        notificationService.removeNotification(notificationId);
    }

    @PutMapping("/update")
    public Notification modifyNotification(@RequestBody Notification c) {
        return notificationService.modifyNotification(c);
    }

    @GetMapping("/getUnread")
    public ResponseEntity<?> getUnread() {
        User currentUser;
        try {
            /*current user*/
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            currentUser = userService.loadUserByEmail(currentEmail);
            ////////////////////////////////////////////////////////////////////
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()) ;
        }
        return ResponseEntity.ok().body(notificationService.getUnread(currentUser));
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<?> getAllByUser() {
        User currentUser;
        try {
            /*current user*/
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            currentUser = userService.loadUserByEmail(currentEmail);
            ////////////////////////////////////////////////////////////////////
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()) ;
        }
        return ResponseEntity.ok().body(notificationService.getByUser(currentUser));
    }
}
