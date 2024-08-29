package tn.zeros.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.zeros.template.entities.Stock;
import tn.zeros.template.entities.StockItem;

import java.util.List;

public interface StockItemRepository extends JpaRepository<StockItem,Long> {
    List<StockItem> findByStock(Stock userStock);

  StockItem findByStockIdAndProduitId(Long stockId, Long produitId);

}
