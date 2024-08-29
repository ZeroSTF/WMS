package tn.zeros.template.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Stock stock;

    //@ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    private Produits produit;

    @Column(nullable = false)
    private int quantity;
}
