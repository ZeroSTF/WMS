package tn.zeros.template.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.zeros.template.entities.Stock;
import tn.zeros.template.repositories.StockRepository;
import tn.zeros.template.services.IServices.IStockService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class StockService implements IStockService {

    private final StockRepository stockRepository;

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
}
