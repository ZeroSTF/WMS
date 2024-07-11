package tn.zeros.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.zeros.template.entities.Stock;

//import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    //Optional<Stock> findById(Long aLong);
}
