package tn.zeros.template.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 100)
    String name;


    @Column(columnDefinition = "TEXT")
    String details;


    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    //@ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    private List<StockItem> stockItems;

}
