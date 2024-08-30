package tn.zeros.template.services.IServices;

import tn.zeros.template.entities.Stock;
import tn.zeros.template.entities.User;

import java.util.List;

public interface IStockService {
    List<Stock> findAll();
    Stock findById(Long id);
    Stock save(Stock stock);
    void deleteById(Long id);
    Long stockscount();
    Stock getStockByUser(User user);
}
