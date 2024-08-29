package tn.zeros.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.zeros.template.entities.Produits;
import tn.zeros.template.entities.Stock;
import tn.zeros.template.entities.StockItem;
import tn.zeros.template.entities.User;

import java.util.Optional;

//import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    //Optional<Stock> findById(Long aLong);
    Stock findByUser(User user);
    Stock findByUserId(Long userId);

    //Optional<StockItem> findByUserAndProduit(User user, Produits produit);
}
