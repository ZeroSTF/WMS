package tn.zeros.template.entities;

import jakarta.persistence.*;
import lombok.*;
import tn.zeros.template.entities.enums.NotifType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotifType type;
    private String content;
    private LocalDateTime date;
    private boolean status;
    @ManyToOne
    private User user;

    public Notification(User user, NotifType type, String content){
        this.user=user;
        this.type=type;
        this.content=content;

        this.date= LocalDateTime.now();
        this.status=false;
    }
}
