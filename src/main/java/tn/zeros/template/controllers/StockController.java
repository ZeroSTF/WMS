package tn.zeros.template.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.zeros.template.controllers.DTO.StockDTO;
import tn.zeros.template.entities.Stock;
import tn.zeros.template.entities.StockItem;
import tn.zeros.template.entities.User;
import tn.zeros.template.services.StockService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Slf4j
public class StockController {
    final StockService stockService;

 /*   @GetMapping("/currentStock")
    public ResponseEntity<List<StockItem>> getCurrentUserStock() {
        List<StockItem> stockItems = stockService.getCurrentUserStock();
        return ResponseEntity.ok(stockItems);
    }
*/

    @GetMapping("/stocks")
    public List<Stock> getAllStocks() {
        return stockService.findAll();
    }

    @GetMapping("/stock/{id}")
    public Stock getStockById(@PathVariable Long id) {
        return stockService.findById(id);
    }

    @GetMapping("/stock/{userId}")
    public ResponseEntity<List<StockItem>> getStockByUserId(@PathVariable Long userId) {
        List<StockItem> stockItems = stockService.getStockByUserId(userId);
        return ResponseEntity.ok(stockItems);
    }

    @GetMapping("/stocks/count")
    public Long getStocksCount() {
        return stockService.stockscount();
    }

    @GetMapping("/current-stock")
    public Stock getCurrentUserStock() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return stockService.getStockByUser(user);
    }
}
