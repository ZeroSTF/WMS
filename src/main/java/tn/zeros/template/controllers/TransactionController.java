package tn.zeros.template.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;
import tn.zeros.template.repositories.BonRepository;
import tn.zeros.template.services.BonService;
import tn.zeros.template.services.IServices.IUserService;
import tn.zeros.template.services.QRCodeService;
import tn.zeros.template.services.TransactionService;
import tn.zeros.template.services.UserService;


import java.net.URI;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;
    private final IUserService userService;

    private final QRCodeService qrCodeService;
    private final BonService bonService;
    //private final BonRepository bonRepository;

    //  private final UserService userService;

    @GetMapping("/transaction-list")
    public ResponseEntity<List<Transaction>> getTransactionList() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }

    /*
    @GetMapping("/transaction-list")
    public List<Transaction> getAllTransactions() {
        return transactionService.findAll();
    }
*/
    /*
@GetMapping("/transaction-list")
public ResponseEntity<?> getAllTransactions() {
    try {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    } catch (Exception e) {
        log.error("Error in getAllTransactions: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
}*/
    @GetMapping("/transaction/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);

        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction);

    }

    @PostMapping("/add-transaction")
    public ResponseEntity<Void> createTransaction(@RequestBody Transaction transaction) {
        Transaction transactionAdded = transactionService.save(transaction);

        if (transactionAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transactionAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/update-transaction/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        transaction.setId(id);
        Transaction updatedTransaction = transactionService.save(transaction);

        if (updatedTransaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTransaction);


    }

    @DeleteMapping("/delete-transaction/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<?> getAllByUser() {
        User currentUser;
        try {
            /*current user*/
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            currentUser = userService.loadUserByEmail(currentEmail);
            ////////////////////////////////////////////////////////////////////
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(transactionService.findByUser(currentUser));
    }
/*
    @PostMapping("/send")
  //  public ResponseEntity<?> sendTransactions(@RequestBody TransactionSendRequest request) {
    //}
    public void sendTransactions(List<Long> transactionIds, String receiverEmail){
        //recuperer le receiver par mail
        User receiver = userService.loadUserByEmail(receiverEmail);
        //recuperer le curent User (sender)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        User currentUser = userService.loadUserByEmail(currentEmail);

        //


    }*/

    @PostMapping("/create")
    public ResponseEntity<Bon> createBon(@RequestParam Long senderId,
                                         @RequestParam Long receiverId,
                                         @RequestBody List<Long> transactionIds) {
        // Récupération des utilisateurs (sender et receiver)
        User sender = userService.findById(senderId);
        User receiver = userService.findById(receiverId);

        if (sender == null || receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Récupération des transactions à partir de leurs IDs
        List<Transaction> transactions = transactionService.findAllById(transactionIds);

        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Création du Bon
        Bon bon = transactionService.createBon(sender, receiver, transactions);

        // Retourne le Bon créé
        return ResponseEntity.status(HttpStatus.CREATED).body(bon);
    }

    @PostMapping("/create-bon")
    public ResponseEntity<Bon> createBon(@RequestParam Long receiverId, @RequestBody List<Long> transactionIds) {
        //current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        User sender = userService.loadUserByEmail(currentEmail);

        if (sender == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User receiver = userService.findById(receiverId);
        if (receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Transaction> transactions = transactionService.findAllById(transactionIds);
        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Bon bon = transactionService.createBon(sender, receiver, transactions);
        return ResponseEntity.status(HttpStatus.CREATED).body(bon);
    }

/******************* QRCode***************/
   /* @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> getBonQRCode(@PathVariable Long id) {
        try {
            Bon bon = bonService.findById(id);
            byte[] qrCodeImage = qrCodeService.generateQRCodeImage(bon);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
@GetMapping("/{id}/qrcode")
public ResponseEntity<byte[]> getBonQRCode(@PathVariable Long id) {
    log.info("Generating QR code for bon id: {}", id);
    try {
        Bon bon = bonService.findById(id);
        if (bon == null) {
            log.warn("Bon not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Bon found: {}", bon);

        byte[] qrCodeImage = qrCodeService.generateQRCodeImage(bon);
        log.info("QR code generated successfully");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
    } catch (Exception e) {
        log.error("Error generating QR code for bon id: " + id, e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @PostMapping("/scan/{id}")
    public ResponseEntity<Bon> scanBon(@PathVariable Long id) {
        try {
            Bon bon = bonService.findById(id);
            if (bon == null) {
                return ResponseEntity.notFound().build();
            }

            // Mettre à jour le statut du bon
            bon.setStatus(true);
            bon = bonService.save(bon);

            return ResponseEntity.ok(bon);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
