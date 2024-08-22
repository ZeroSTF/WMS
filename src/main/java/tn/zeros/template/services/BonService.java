package tn.zeros.template.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.zeros.template.controllers.DTO.BonLivraisonDTO;
import tn.zeros.template.controllers.DTO.CreateBonRequest;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;
import tn.zeros.template.entities.enums.BonType;
import tn.zeros.template.repositories.BonRepository;
import tn.zeros.template.repositories.TransactionRepository;
import tn.zeros.template.repositories.UserRepository;
import tn.zeros.template.services.IServices.IBonService;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BonService implements IBonService {

    private final BonRepository bonRepository;

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;


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



    /*public Bon createBonCommande(CreateBonRequest request) {
        // Validation (peut être améliorée avec des annotations de validation)
        if (request.getSenderId() == null || request.getReceiverId() == null || request.getTransactionIds().isEmpty()) {
            throw new IllegalArgumentException("Données de la commande incomplètes");
        }

        // Récupération des entités
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Émetteur introuvable"));
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Destinataire introuvable"));

        // Création du bon
        Bon bon = Bon.builder()
                .type(BonType.commande)
                .sender(sender)
                .receiver(receiver)
                .date(LocalDate.now())
                .transactions(transactionRepository.findAllById(request.getTransactionIds()))
                .build();

        return bonRepository.save(bon);
    }

     */
    /*public Bon createBonCommande(CreateBonRequest request) {
        // Validation (enhanced with annotations for clarity)
        if (request.getSenderId() == null || request.getReceiverId() == null || request.getTransactionIds().isEmpty()) {
            throw new IllegalArgumentException("Incomplete bon creation data: senderId, receiverId, or transactionIds are missing.");
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
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        Bon bon = Bon.builder()
                .type(BonType.commande)
                .sender(sender)
                .receiver(receiver)
                .date(LocalDate.now())
                .transactions(transactions)
                .build();

        // Explicit transaction association for unidirectional relationships
        for (Transaction transaction : bon.getTransactions()) {
            transaction.setBon(bon); // Assuming `Transaction` has a `setBon` method
        }

        return bonRepository.save(bon);
    }*/
    public Bon createBonCommande(CreateBonRequest request) {
        long senderId=1;
        // Validation (enhanced with annotations for clarity)
        if ( request.getReceiverId() == null || request.getTransactionIds().isEmpty()) {
            throw new IllegalArgumentException("Incomplete bon creation data:  receiverId, or transactionIds are missing.");
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
        Optional<User> sender = userRepository.findById(senderId);
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));
        boolean tofactur = request.isTofactur();

        Bon bon = Bon.builder()
                .type(BonType.commande)
                //.sender(sender)
                .receiver(receiver)
                .date(LocalDate.now())
                .transactions(transactions)
                .tofactur(tofactur)
                .build();

        // Explicit transaction association for unidirectional relationships
        for (Transaction transaction : bon.getTransactions()) {
            transaction.setBon(bon); // Assuming `Transaction` has a `setBon` method
        }

        return bonRepository.save(bon);
    }

}
