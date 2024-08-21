package tn.zeros.template.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.entities.Produits;
import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;
import tn.zeros.template.entities.enums.BonType;
import tn.zeros.template.entities.enums.TypeRole;
import tn.zeros.template.repositories.BonRepository;
import tn.zeros.template.repositories.ProduitsRepository;
import tn.zeros.template.repositories.TransactionRepository;
import tn.zeros.template.repositories.UserRepository;
import tn.zeros.template.services.IServices.ITransactionService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class TransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;
    private final BonRepository bonRepository;
    private final UserRepository userRepository;
    private final ProduitsRepository produitsRepository;

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> findByUser(User user) {
        return transactionRepository.findTransactionByUser(user);
    }

    public List<Transaction> findAllById(Iterable<Long> ids) {
        List<Transaction> result = new ArrayList<>();
        for (Long id : ids) {
            transactionRepository.findById(id).ifPresent(result::add);
        }
        return result;
    }

    /*public void sendTransactions(List<Long> transactionIds, Long receiverId)
            throws UserNotFoundException, NoValidTransactionsException {
        // Récupérer l'utilisateur récepteur
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + receiverId));

        // Récupérer les transactions
        List<Transaction> transactions = transactionRepository.findAllById(transactionIds);
        if (transactions.isEmpty()) {
            throw new NoValidTransactionsException("No valid transactions found");
        }

        // Créer un nouveau bon de type "Livraison"
        Bon deliveryBon = new Bon();
        deliveryBon.setType(BonType.LIVRAISON);
        deliveryBon.setSenderStock(transactions.get(0).getBon().getSenderStock());
        deliveryBon.setReceiverStock(receiver.getStock());
        deliveryBon.setCreationDate(LocalDateTime.now());
        deliveryBon.setStatus(BonStatus.PENDING);
        Bon savedBon = bonRepository.save(deliveryBon);

        // Lier les transactions au bon
        for (Transaction transaction : transactions) {
            transaction.setBon(savedBon);
            transaction.setReceiver(receiver);
        }
        transactionRepository.saveAll(transactions);

        // Mettre à jour les stocks
        stockService.updateStocks(savedBon, transactions);
    }*/

    @Transactional
    public Bon createBon(User sender, User receiver, List<Transaction> transactions) {
        // Déterminer le type de bon en fonction du rôle du receiver
        BonType bonType = BonType.valueOf("commande"); /*receiver.getRole().stream()
                .anyMatch(role -> role.getType() == TypeRole.AGENT) ? BonType.commande : BonType.livraison;
*/
        // Création du Bon
        Bon bon = new Bon();
        bon.setType(bonType);
        bon.setSender(sender);
        bon.setReceiver(receiver);
        bon.setDate(LocalDate.now());
        bon.setTransactions(transactions);
        bon.setStatus(false);

        bonRepository.save(bon);
        // Gestion des transactions et des produits
        for (Transaction transaction : transactions) {

        /*    Produits produit = transaction.getProduits();

            // Diminuer la quantité de produits dans le dépôt de l'expéditeur (sender)
            if (produit.getQte() >= transaction.getQuantity()) {
                produit.setQte(produit.getQte() - transaction.getQuantity());
            } else {
                throw new IllegalArgumentException("Quantité insuffisante pour le produit: " + produit.getRefArt());
            }
*/
            // Augmenter la quantité de produits dans le dépôt du destinataire (receiver)
        /*    Produits receiverProduct = produitsRepository.findByUserAndName(receiver, produit.getName())
                    .orElse(new Produits(produit.getName(), 0, receiver.getDepot()));

            receiverProduct.setQte(receiverProduct.getQte() + transaction.getQuantity());
*/
            // Affecter le bon à la transaction
            transaction.setBon(bon);

            // Sauvegarde des produits dans les dépôts respectifs
          //  produitsRepository.save(produit);
    //        produitsRepository.save(receiverProduct);
        }

        // Sauvegarde du Bon et des transactions

        transactionRepository.saveAll(transactions);

        return bon;
    }


}