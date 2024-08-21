package tn.zeros.template.controllers.DTO;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateBonRequest {
    private Long senderId;
    private Long receiverId;
    private List<Long> transactionIds;
}
