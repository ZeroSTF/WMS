package tn.zeros.template.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockDTO {
    private Long id;
    private String name;
    private String details;
    private List<ProduitDTO> products;
}
