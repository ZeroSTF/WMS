package tn.zeros.template.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

   // @ManyToOne(fetch = FetchType.EAGER)
   // @JsonIgnore
   // Produits produits;
    @Column(nullable = false)
    int quantity;
    private Double montant;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonManagedReference
    @JsonIgnore
    private Bon bon;

    @Column(length = 1000)
    String details;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produits_id")
    private Produits produits;

/*
    @ManyToOne(fetch = FetchType.EAGER)
    Bon bon;
*/




// Getters and setters

}
