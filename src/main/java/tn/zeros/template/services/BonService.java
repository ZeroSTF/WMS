package tn.zeros.template.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.zeros.template.controllers.DTO.BonLivraisonDTO;
import tn.zeros.template.controllers.DTO.CreateBonRequest;
import tn.zeros.template.entities.*;
import tn.zeros.template.entities.enums.BonType;
import tn.zeros.template.repositories.*;
import tn.zeros.template.services.IServices.IBonService;


import java.time.LocalDate;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class BonService implements IBonService {

    private final BonRepository bonRepository;

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;


/*
    @Transactional
    public Bon createBon(List<Long> transactionIds, String receiverEmail, String receiverAddress) {
        List<Transaction> transactions = transactionRepository.findAllById(transactionIds);
        /*User receiver = userRepository.findByEmail(receiverEmail).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(receiverEmail);
            newUser.setAddress(receiverAddress);
            return userRepository.save(newUser);
        });
        Optional<User> receiverOpt = userRepository.findByEmail(receiverEmail);
        if (!receiverOpt.isPresent()) {
            throw new EntityNotFoundException("Receiver not found with email: " + receiverEmail);
        }
        User receiver = receiverOpt.get();

      // Obtenir l'utilisateur actuel (sender)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        User sender = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));

        Bon bon = new Bon();
        bon.setType(BonType.livraison);
        bon.setTransactions(transactions);
        bon.setReceiver(receiver);
        bon.setSender(sender);
        bon.setDate(LocalDate.now());
        bon.setStatus(false);

        transactions.forEach(transaction -> transaction.setBon(bon));

        return bonRepository.save(bon);
    }
*/


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

    public Bon createBonLivraison(BonLivraisonDTO bonLivraisonDTO) {
        Bon bon = new Bon();
        bon.setType(BonType.livraison);

        // Récupérer les transactions à partir des IDs
        List<Transaction> transactions = transactionRepository.findAllById(bonLivraisonDTO.getTransactionIds());
        bon.setTransactions(transactions);

        // Recherche de l'utilisateur destinataire par email
        User receiver = userRepository.findByEmail(bonLivraisonDTO.getReceiverEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur destinataire non trouvé"));
        bon.setReceiver(receiver);

        // Laisser le sender à null pour le moment
        bon.setSender(null);

        bon.setDate(bonLivraisonDTO.getDate());
        bon.setStatus(false);

        // Sauvegarder le bon
        return bonRepository.save(bon);
    }



    public Bon createBonCommande(CreateBonRequest request) {
        // Validation des données d'entrée
        if (request.getReceiverId() == null || request.getSenderId() == null || request.getTransactionIds().isEmpty()) {
            throw new IllegalArgumentException("Incomplete bon creation data: receiverId, or transactionIds are missing.");
        }

        // Récupération des transactions
        List<Transaction> transactions = transactionRepository.findAllById(request.getTransactionIds());
        if (transactions.isEmpty()) {
            throw new EntityNotFoundException("No transactions found for given IDs.");
        }

        // Récupération de l'expéditeur et du destinataire
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        // Récupérer les stocks des utilisateurs
        Stock senderStock = sender.getStock();
        Stock receiverStock = receiver.getStock();

        if (senderStock == null || receiverStock == null) {
            throw new IllegalArgumentException("Les stocks de l'expéditeur ou du destinataire ne peuvent pas être trouvés.");
        }

        // Vérification des stocks disponibles
        /*for (Transaction transaction : transactions) {
            Produits produit = transaction.getProduits();
            int transactionQuantity = transaction.getQuantity();

           // Trouver l'article de stock correspondant dans le stock de l'expéditeur
            Optional<StockItem> senderStockItemOpt = senderStock.getStockItems().stream()
                    .filter(item -> item.getProduit().equals(produit))
                    .findFirst();

            if (senderStockItemOpt.isEmpty() || senderStockItemOpt.get().getQuantity() < transactionQuantity) {
                throw new IllegalArgumentException("Stock insuffisant pour le produit: " + senderStockItemOpt.get().getProduit().getRefArt() );
            }


        }*/

        // Mise à jour des quantités de stock
        for (Transaction transaction : transactions) {
            Produits produit = transaction.getProduits();
            int transactionQuantity = transaction.getQuantity();

            // Déduire du stock de l'expéditeur
            StockItem senderStockItem = senderStock.getStockItems().stream()
                    .filter(item -> item.getProduit().equals(produit))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé dans le stock de l'expéditeur: " + produit.getRefArt() ));

            senderStockItem.setQuantity(senderStockItem.getQuantity() - transactionQuantity);

            // Ajouter au stock du destinataire
            Optional<StockItem> receiverStockItemOpt = receiverStock.getStockItems().stream()
                    .filter(item -> item.getProduit().equals(produit))
                    .findFirst();

            if (receiverStockItemOpt.isPresent()) {
                StockItem receiverStockItem = receiverStockItemOpt.get();
                receiverStockItem.setQuantity(receiverStockItem.getQuantity() + transactionQuantity);
            } else {
                // Si le produit n'est pas encore dans le stock du destinataire, l'ajouter
                StockItem newReceiverStockItem = new StockItem();
                newReceiverStockItem.setStock(receiverStock);
                newReceiverStockItem.setProduit(produit);
                newReceiverStockItem.setQuantity(transactionQuantity);
                receiverStock.getStockItems().add(newReceiverStockItem);
            }
        }

        // Sauvegarder les modifications de stock
        stockRepository.save(senderStock);
        stockRepository.save(receiverStock);

        // Création du bon
        Bon bon = new Bon();
        bon.setSender(sender);
        bon.setReceiver(receiver);
        bon.setTransactions(transactions);
        bon.setDate(LocalDate.now());

        // Vérification du rôle de l'utilisateur destinataire
        bon.setType(BonType.commande);

        bon.setStatus(false);
        bon.setTofactur(false);
        bon.setFactured(false);

        // Sauvegarde du bon
        bonRepository.save(bon);

        return bon;
    }


}

















