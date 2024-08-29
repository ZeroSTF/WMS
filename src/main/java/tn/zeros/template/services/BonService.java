package tn.zeros.template.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.zeros.template.controllers.DTO.CreateBonRequest;
import tn.zeros.template.entities.*;
import tn.zeros.template.entities.enums.BonType;
import tn.zeros.template.repositories.*;
import tn.zeros.template.services.IServices.IBonService;


import java.time.LocalDate;

import java.util.Collections;
import java.util.List;

import static tn.zeros.template.entities.enums.TypeRole.USER;
//import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class BonService implements IBonService {

    private final BonRepository bonRepository;

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final StockItemRepository stockItemRepository;




    @Override
    public List<Bon> findAll() {
        return bonRepository.findAll();
    }

    @Override
    public Bon findById(Long id) {
        return bonRepository.findById(id).orElse(null);
    }
    @Override
    public List<Transaction> findByUser(User user) {
        return transactionRepository.findTransactionByUser(user);
    }

    @Override
    public Bon save(Bon bon) {
        return bonRepository.save(bon);
    }

    @Override
    public void deleteById(Long id) {
        bonRepository.deleteById(id);
    }



    @Override
    public Bon createBonCommande( CreateBonRequest request) {

        // Validation (enhanced with annotations for clarity)
        if ( request.getReceiverId() == null || request.getSenderId() == null || request.getTransactionIds().isEmpty()) {
            throw new IllegalArgumentException("Incomplete bon creation data:  receiverId, senderId or transactionIds are missing.");
        }

        // Efficient transaction retrieval & association (Optional Optimization)
        List<Transaction> transactions;
        if (request.getTransactionIds().size() == 1) {
            transactions = Collections.singletonList(transactionRepository.findById(request.getTransactionIds().get(0))
                    .orElseThrow(() -> new EntityNotFoundException("Transaction not found")));
        } else {
            transactions = transactionRepository.findAllById(request.getTransactionIds());
        }

        // Bon creation and transaction association
        //Optional<User> sender = userRepository.findById(senderId);
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));
        boolean tofactur = request.isTofactur();

        BonType bonType = receiver.getRole().stream()
                .anyMatch(role ->role.getType().equals(USER)) ? BonType.livraison : BonType.commande;

        Bon bon = Bon.builder()
                .type(bonType)
                .sender(sender)
                .receiver(receiver)
                .date(LocalDate.now())
                .transactions(transactions)
                .tofactur(tofactur)
                .build();
        bonRepository.save(bon);
        //////////////////////////////////////////////////
        // Mise à jour des stocks
        for (Transaction transaction : transactions) {
            Produits produit = transaction.getProduits();
            Stock senderStock = sender.getStock();
            Stock receiverStock = receiver.getStock();
            int transactionQuantity = transaction.getQuantity();

            // Trouver ou créer le StockItem correspondant au sender

            StockItem senderStockItem = stockItemRepository.findByStockIdAndProduitId(senderStock.getId(), produit.getId());
            if (senderStockItem == null || senderStockItem.getQuantity() < transactionQuantity) {
                throw new IllegalArgumentException("Stock insuffisant pour le produit: " + produit.getRefArt());
            }

            senderStockItem.setQuantity(senderStockItem.getQuantity() - transactionQuantity);
            stockItemRepository.save(senderStockItem);

            // Trouver ou créer le StockItem correspondant au receiver
            StockItem receiverStockItem = stockItemRepository.findByStockIdAndProduitId(receiverStock.getId(), produit.getId());
            if (receiverStockItem == null) {
                StockItem stockItem = StockItem.builder()
                        .stock(receiverStock)
                        .produit(produit)
                        .quantity(transaction.getQuantity())
                        .build();
                stockItemRepository.save(stockItem);
            } else {

                receiverStockItem.setQuantity(receiverStockItem.getQuantity() + transactionQuantity);
                stockItemRepository.save(receiverStockItem);
            }

            // Explicit transaction association for unidirectional relationships
            transaction.setBon(bon);
        }
        return bonRepository.save(bon);
    }



}

















