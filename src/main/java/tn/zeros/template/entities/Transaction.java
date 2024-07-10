package tn.zeros.template.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.Temporal;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "Transaction", indexes = {
        @Index(name = "idx_transaction_id", columnList = "id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Produits produits;
    @Column(nullable = false)
    private int quantity;

    private Date date;
    private boolean confirmation;

    @Column(length = 1000)
    private String details;

    @ManyToOne(fetch = FetchType.EAGER)
    private Bon bon;





// Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produits getProduits() {
        return produits;
    }

    public void setProduits(Produits produits) {
        this.produits = produits;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Bon getBon() {
        return bon;
    }

    public void setBon(Bon bon) {
        this.bon = bon;
    }
}
