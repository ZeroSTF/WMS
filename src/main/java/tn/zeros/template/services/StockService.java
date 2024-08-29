package tn.zeros.template.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.zeros.template.controllers.DTO.ProduitDTO;
import tn.zeros.template.controllers.DTO.StockDTO;
import tn.zeros.template.entities.Produits;
import tn.zeros.template.entities.Stock;
import tn.zeros.template.entities.StockItem;
import tn.zeros.template.entities.User;
import tn.zeros.template.repositories.StockItemRepository;
import tn.zeros.template.repositories.StockRepository;
import tn.zeros.template.services.IServices.IStockService;

import java.util.ArrayList;
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

    /*public StockDTO getCurrentUserStock() {
        User currentUser = userService.getCurrentUser();
        Stock stock = stockRepository.findByUser(currentUser);

        if (stock == null) {
            throw new EntityNotFoundException("Stock not found for current user");
        }

        return convertToDTO(stock);
    }
    private StockDTO convertToDTO(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setName(stock.getName());
        dto.setDetails(stock.getDetails());

        List<ProduitDTO> produitDTOs = new ArrayList<>();
        for (Produits produit : stock.getProducts()) {
            ProduitDTO produitDTO = new ProduitDTO();
            produitDTO.setId(produit.getId());
            produitDTO.setRefArt(produit.getRefArt());
            produitDTO.setDesignation(produit.getDesignation());
            produitDTO.setQte(produit.getQte());
            produitDTO.setUnite(produit.getUnite());
            // Add other fields as needed
            produitDTOs.add(produitDTO);
        }
        dto.setProducts(produitDTOs);

        return dto;
    }

     */

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

}
