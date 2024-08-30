package tn.zeros.template.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProduitDTO {
    private Long id;
    private String refArt;
    private String designation;
    private int qte;
    private String unite;
}
