package tn.zeros.template.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.zeros.template.entities.Stock;
import tn.zeros.template.entities.StockItem;
import tn.zeros.template.entities.User;
import tn.zeros.template.repositories.StockItemRepository;
import tn.zeros.template.repositories.StockRepository;
import tn.zeros.template.services.IServices.IStockService;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class StockService implements IStockService {

    private final StockRepository stockRepository;
    final UserService userService;
    final StockItemRepository stockItemRepository;

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void deleteById(Long id) {
        stockRepository.deleteById(id);
    }


/*
    public List<StockItem> getCurrentUserStock() {
        User currentUser = userService.getCurrentUser();
        Stock userStock = stockRepository.findByUser(currentUser);
        if (userStock != null) {
            return stockItemRepository.findByStock(userStock);
        } else {
            return Collections.emptyList();
        }
    }

 */
    public List<StockItem> getStockByUserId(Long userId) {
        Stock userStock = stockRepository.findByUserId(userId);
        if (userStock != null) {
            return stockItemRepository.findByStock(userStock);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Long stockscount(){
        return stockRepository.count();
    }
    @Override
    public Stock getStockByUser(User user) {
        return stockRepository.findByUser(user);
    }
}
