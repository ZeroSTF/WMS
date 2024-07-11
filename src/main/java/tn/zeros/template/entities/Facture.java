package tn.zeros.template.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.zeros.template.entities.enums.PaiementMode;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Date date;

    @Column(name = "montant_total", nullable = false, precision = 10, scale = 2)
    BigDecimal montantTotal;

    @ManyToOne
    @JoinColumn(name = "client_id")
    User client;

    @Enumerated(EnumType.STRING)
    @Column(name = "paiement_mode", nullable = false)
    PaiementMode paiementMode;

    @Column(name = "paiement_status", nullable = false)
    Boolean paiementStatus = false;

    @ManyToOne
    @JoinColumn(name = "bon_id")
    Bon bon;

}
