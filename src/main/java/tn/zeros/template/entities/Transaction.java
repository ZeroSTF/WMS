package tn.zeros.template.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.Temporal;

import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    Produits produits;
    @Column(nullable = false)
    int quantity;

    //@Temporal (TemporalType.DATE)
    Date date;
    boolean confirmation;

    @Column(length = 1000)
    String details;

    @ManyToOne(fetch = FetchType.EAGER)
    Bon bon;





// Getters and setters

}
