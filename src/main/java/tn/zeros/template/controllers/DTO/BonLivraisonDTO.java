package tn.zeros.template.controllers.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BonLivraisonDTO {
    private List<Long> transactionIds;
    private String receiverEmail;
    private LocalDate date;
}
