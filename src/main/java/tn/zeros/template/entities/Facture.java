package tn.zeros.template.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.zeros.template.entities.enums.PaiementMode;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
    LocalDate date;
    BigDecimal montantTotal;


    @Enumerated(EnumType.STRING)
    PaiementMode paiementMode;
    Boolean paiementStatus = false;

    @OneToMany
    List<Bon> bon;

}
