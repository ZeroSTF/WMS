package tn.zeros.template.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;




@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    int quantity;
    private Double montant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Bon bon;

    @Column(length = 1000)
    String details;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    private Produits produits;





// Getters and setters

}
