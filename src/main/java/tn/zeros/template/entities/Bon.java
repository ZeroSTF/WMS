package tn.zeros.template.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.zeros.template.entities.enums.BonType;

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
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bon{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    BonType type;

   // @JsonBackReference
    @OneToMany(mappedBy = "bon")
    private List<Transaction> transactions;

    @ManyToOne(fetch = FetchType.EAGER)
    
    private User receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User sender;

    private LocalDate date;
    boolean status=false;
    boolean factured=false;

}
