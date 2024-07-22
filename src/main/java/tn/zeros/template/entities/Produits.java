package tn.zeros.template.entities;

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
public class Produits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // New attributes
    @Column(name = "ref_art")
    private String refArt;

    @Column(name = "designation")
    private String designation;

    @Column(name = "collection")
    private String collection;

    @Column(name = "famille")
    private String famille;

    @Column(name = "fournisseur")
    private String fournisseur;

    @Column(name = "ref_fourn")
    private String refFourn;

    @Column(name = "qte")
    private int qte;

    @Column(name = "unite")
    private String unite;

    @Column(name = "tva")
    private float tva;

    @Column(name = "prix_achat")
    private float prixAchat;

    @Column(name = "prix_vente")
    private float prixVente;

    @Column(name = "prix_ttc")
    private float prixTtc;



    
}
