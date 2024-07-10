package tn.zeros.template.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.zeros.template.entities.enums.BonType;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bon{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private BonType type;


    /*
    type ENUM('entree', 'sortie', 'commande', 'retour', 'livraison', 'transfert') NOT NULL,
    date DATE NOT NULL,
    details TEXT
    * */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BonType getType() {
        return type;
    }

    public void setType(BonType type) {
        this.type = type;
    }
}
