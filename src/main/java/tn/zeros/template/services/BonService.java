package tn.zeros.template.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.zeros.template.controllers.DTO.BonLivraisonDTO;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;
import tn.zeros.template.entities.enums.BonType;
import tn.zeros.template.repositories.BonRepository;
import tn.zeros.template.repositories.TransactionRepository;
import tn.zeros.template.repositories.UserRepository;
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
}
