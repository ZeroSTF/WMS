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


    private final BonService bonService;
    //private final BonRepository bonRepository;

    //  private final UserService userService;

    @GetMapping("/transaction-list")
    public ResponseEntity<List<Transaction>> getTransactionList() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }


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
}




